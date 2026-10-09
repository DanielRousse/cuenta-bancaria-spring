# Cajero automático — Spring Core e inyección de dependencias

**Autor:** Jonathan Daniel Reyes Gordillo

## Cómo correrlo

```bash
./correr.sh App final-app
./probar.sh final
```

## Las piezas

| Bean | Clase | Cómo lo declara Spring (`@Component` o `@Bean`) | Singleton o prototype |
|---|---|---|---|
| `configuracionBanco` | `ConfiguracionBanco` | `@Configuration` (clase de configuración con `@ComponentScan`) | Singleton |
| `cajeroAutomatico` | `CajeroAutomatico` | `@Component` | Singleton |
| `repositorioEnMemoria` | `RepositorioEnMemoria` | `@Component` | Singleton |
| `notificadorConsola` | `NotificadorConsola` | `@Component` | Singleton |
| `antifraudePorMonto` | `AntifraudePorMonto` | `@Component` (con `@Primary`) | Singleton |
| `antifraudeEstricto` | `AntifraudeEstricto` | `@Component` | Singleton |
| `sesionCajero` | `SesionCajero` | `@Component` (con `@Scope("prototype")`) | Prototype |
| `reloj` | `Clock` (`SystemClock`) | `@Bean` (en `ConfiguracionBanco`) | Singleton |

## Boleto de salida

1. **¿Qué es la inyección de dependencias? Explícalo con el cajero, en tus palabras.**
   - Es un patrón de diseño en el cual una clase no fabrica ni instancia los objetos que requiere para operar, sino que los solicita a través de su constructor para que un agente externo se los proporcione listos para usarse.
   - En el caso del `CajeroAutomatico`, la clase no ejecuta `new` para instanciar su repositorio, antifraude, notificador o reloj; simplemente declara en su constructor que necesita un `RepositorioCuentas`, un `ServicioAntifraude`, un `Notificador` y un `Clock`. Esto desacopla la lógica del cajero de las implementaciones concretas, facilitando intercambiar proveedores o introducir dobles de prueba (mocks) sin tocar su código.

2. **En la MP-1, ¿quién decidía qué antifraude usaba el cajero? ¿Y desde la MP-2?**
   - **En la MP-1 (sin Spring):** Lo decidíamos **nosotros manualmente** en el método `main` de `AppSinSpring` al instanciar explícitamente `new AntifraudePorMonto()` y pasárselo al constructor del cajero.
   - **Desde la MP-2 (con Spring):** Lo decide **el contenedor de Spring (`ApplicationContext`)**. Spring analiza los tipos requeridos por el constructor, busca en su registro de beans cuál califica para el tipo `ServicioAntifraude` y se lo inyecta automáticamente, resolviendo ambigüedades mediante `@Primary` o `@Qualifier`.

3. **¿Cuándo usarías `@Bean` en vez de `@Component`? Da el ejemplo de hoy.**
   - Se utiliza `@Bean` cuando necesitamos que Spring administre una clase externa que **no es nuestra y cuyo código fuente no podemos editar** para agregarle `@Component` (como librerías de terceros o clases nativas del JDK), o cuando la creación del objeto requiere llamadas a métodos fábrica o configuración procedural previa.
   - **El ejemplo de hoy:** La clase `Clock` de Java (`java.time.Clock`). Al ser una clase nativa de la biblioteca estándar de Java, no podemos modificarla para añadirle `@Component`. Por ello, creamos un método anotado con `@Bean` en `ConfiguracionBanco` que ejecuta `Clock.system(ZoneId.of("America/Mexico_City"))`.

4. **¿Qué gana: `@Primary` o `@Qualifier`? ¿Por qué tiene sentido?**
   - Gana **`@Qualifier`**.
   - **¿Por qué tiene sentido?** Porque `@Qualifier` es una selección específica y explícita por nombre de bean solicitada en el punto de inyección, mientras que `@Primary` representa únicamente una preferencia general por defecto cuando no se indica ningún nombre. En cualquier jerarquía de configuración, una regla explícita y particular siempre tiene precedencia sobre una regla genérica por defecto.

5. **En tu proyecto de Empleados de la Semana 3 nunca escribiste `@ComponentScan`. ¿Quién lo hace? (Pista: abre la anotación `@SpringBootApplication` con `Ctrl+clic` y busca las anotaciones que tiene arriba.)**
   - Lo hace de manera automática la meta-anotación **`@SpringBootApplication`**.
   - Al inspeccionar la definición de `@SpringBootApplication`, se comprueba que incluye tres meta-anotaciones fundamentales:
     1. `@SpringBootConfiguration` (que a su vez incluye `@Configuration`).
     2. `@EnableAutoConfiguration` (habilita la configuración automática de componentes y starters).
     3. **`@ComponentScan`** (escanea automáticamente componentes, servicios y controladores a partir del paquete base donde reside la clase principal y todos sus subpaquetes).
