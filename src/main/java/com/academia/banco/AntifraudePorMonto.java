package com.academia.banco;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/** Marca como sospechoso todo retiro de más de $3,000.00. */
@Component
public class AntifraudePorMonto implements ServicioAntifraude {

    private static final BigDecimal TOPE = new BigDecimal("3000.00");

    @Override
    public boolean esSospechosa(String numeroCuenta, BigDecimal monto) {
        return monto.compareTo(TOPE) > 0;
    }
}
