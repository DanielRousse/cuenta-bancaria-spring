package com.academia.banco;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/** Más desconfiado: marca como sospechoso todo retiro de más de $1,000.00. */
@Component
public class AntifraudeEstricto implements ServicioAntifraude {

    private static final BigDecimal TOPE = new BigDecimal("1000.00");

    @Override
    public boolean esSospechosa(String numeroCuenta, BigDecimal monto) {
        return monto.compareTo(TOPE) > 0;
    }
}
