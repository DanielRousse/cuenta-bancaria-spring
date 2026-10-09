package com.academia.banco;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Con Spring: tú le das la configuración, Spring crea las piezas y las conecta. */
public class App {

    public static void main(String[] args) {
        try (var contexto = new AnnotationConfigApplicationContext(ConfiguracionBanco.class)) {

            System.out.println("Piezas (beans) que creó Spring:");
            Arrays.stream(contexto.getBeanDefinitionNames())
                    .filter(nombre -> !nombre.startsWith("org.springframework"))
                    .forEach(nombre -> System.out.println("   " + nombre));

            RepositorioEnMemoria repositorio = contexto.getBean(RepositorioEnMemoria.class);
            repositorio.abrir("001", "Ana", "10000.00");

            SesionCajero ana = contexto.getBean(SesionCajero.class);
            ana.retirar("001", "500.00");
            SesionCajero luis = contexto.getBean(SesionCajero.class);
            luis.retirar("001", "2000.00");

            System.out.println("¿Ana y Luis tienen la misma sesión?  " + (ana == luis));
            System.out.println("¿Y el mismo cajero?                 " + (ana.getCajero() == luis.getCajero()));
            System.out.println("Saldo final de la 001: $" + contexto.getBean(CajeroAutomatico.class).consultarSaldo("001"));
        }
    }
}
