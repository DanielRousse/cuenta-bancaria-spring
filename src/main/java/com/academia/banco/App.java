package com.academia.banco;

import java.math.BigDecimal;
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

            CajeroAutomatico cajero = contexto.getBean(CajeroAutomatico.class);
            System.out.println("Saldo de la 001: $" + cajero.consultarSaldo("001"));
            cajero.retirar("001", new BigDecimal("500.00"));
            System.out.println("Saldo de la 001: $" + cajero.consultarSaldo("001"));
        }
    }
}
