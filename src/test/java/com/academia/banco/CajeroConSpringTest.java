package com.academia.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** Prueba de INTEGRACIÓN: Spring arma el cajero con las piezas reales, como en App. */
@SpringJUnitConfig(ConfiguracionBanco.class)
class CajeroConSpringTest {

    @Autowired
    CajeroAutomatico cajero;

    @Autowired
    RepositorioEnMemoria repositorio;

    @Autowired
    ApplicationContext contexto;

    @Test
    void retiroConLasPiezasReales() {
        repositorio.abrir("101", "Ana", "1000.00");
        cajero.retirar("101", new BigDecimal("100.00"));
        assertEquals(new BigDecimal("900.00"), cajero.consultarSaldo("101"));
    }

    @Test
    void elCajeroUsaElAntifraudeEstricto() {
        repositorio.abrir("102", "Luis", "5000.00");
        assertThrows(OperacionRechazadaException.class,
                () -> cajero.retirar("102", new BigDecimal("2000.00")));
        assertEquals(new BigDecimal("5000.00"), cajero.consultarSaldo("102"));
    }

    @Test
    void quienNoPideNombreRecibeElPrimary() {
        assertInstanceOf(AntifraudePorMonto.class, contexto.getBean(ServicioAntifraude.class));
    }

    @Test
    void singletonYPrototype() {
        assertSame(contexto.getBean(CajeroAutomatico.class), contexto.getBean(CajeroAutomatico.class));
        assertNotSame(contexto.getBean(SesionCajero.class), contexto.getBean(SesionCajero.class));
    }
}
