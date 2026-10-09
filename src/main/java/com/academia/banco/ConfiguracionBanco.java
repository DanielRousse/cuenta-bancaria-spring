package com.academia.banco;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** La receta: cada método @Bean le dice a Spring cómo crear UNA pieza. */
@Configuration
public class ConfiguracionBanco {

    @Bean
    public RepositorioEnMemoria repositorio() {
        return new RepositorioEnMemoria();
    }

    @Bean
    public ServicioAntifraude antifraude() {
        return new AntifraudePorMonto();
    }

    @Bean
    public Notificador notificador() {
        return new NotificadorConsola();
    }

    @Bean
    public Clock reloj() {
        return Clock.system(ZoneId.of("America/Mexico_City"));
    }

    // Spring ve que este método pide 4 piezas: se las pasa, ya creadas, desde los métodos de arriba
    @Bean
    public CajeroAutomatico cajero(RepositorioCuentas repositorio, ServicioAntifraude antifraude,
                                   Notificador notificador, Clock reloj) {
        return new CajeroAutomatico(repositorio, antifraude, notificador, reloj);
    }
}
