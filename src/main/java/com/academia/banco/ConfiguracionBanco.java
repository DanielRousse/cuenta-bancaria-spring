package com.academia.banco;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** Le dice a Spring dónde buscar las piezas (@ComponentScan) y le da la que no es nuestra: el reloj. */
@Configuration
@ComponentScan
public class ConfiguracionBanco {

    @Bean
    public Clock reloj() {
        return Clock.system(ZoneId.of("America/Mexico_City"));
    }
}
