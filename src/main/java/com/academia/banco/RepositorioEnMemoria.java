package com.academia.banco;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** Las cuentas viven en un Map, en memoria: al terminar el programa se pierden. */
public class RepositorioEnMemoria implements RepositorioCuentas {

    private final Map<String, CuentaBancaria> cuentas = new HashMap<>();

    /** Abre una cuenta con un depósito inicial (para tener con qué jugar). */
    public void abrir(String numeroCuenta, String titular, String depositoInicial) {
        CuentaBancaria cuenta = new CuentaBancaria(titular);
        cuenta.depositar(new BigDecimal(depositoInicial));
        cuentas.put(numeroCuenta, cuenta);
    }

    @Override
    public Optional<CuentaBancaria> buscar(String numeroCuenta) {
        return Optional.ofNullable(cuentas.get(numeroCuenta));
    }

    @Override
    public BigDecimal totalRetiradoEn(String numeroCuenta, LocalDate fecha) {
        return new BigDecimal("0.00");   // simplificación: este repositorio no lleva la cuenta por día
    }

    @Override
    public void guardar(String numeroCuenta, CuentaBancaria cuenta) {
        cuentas.put(numeroCuenta, cuenta);
    }
}
