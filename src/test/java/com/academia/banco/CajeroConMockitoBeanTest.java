package com.academia.banco;

import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** Spring arma el cajero real… pero con UNA pieza cambiada por un mock de Mockito. */
@SpringJUnitConfig(ConfiguracionBanco.class)
class CajeroConMockitoBeanTest {

    @MockitoBean
    Notificador notificador;

    @Autowired
    CajeroAutomatico cajero;

    @Autowired
    RepositorioEnMemoria repositorio;

    @Test
    void elRetiroAvisaPorSms() {
        repositorio.abrir("201", "Eva", "1000.00");
        cajero.retirar("201", new BigDecimal("100.00"));
        verify(notificador).enviarSms("201", "Retiro de $100.00. Saldo: $900.00");
    }
}
