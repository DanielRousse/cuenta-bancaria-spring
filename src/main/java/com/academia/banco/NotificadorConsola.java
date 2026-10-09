package com.academia.banco;

import org.springframework.stereotype.Component;

/** En vez de mandar un SMS de verdad (cuesta dinero), lo escribe en la consola. */
@Component
public class NotificadorConsola implements Notificador {

    @Override
    public void enviarSms(String numeroCuenta, String mensaje) {
        System.out.println("   [SMS a la cuenta " + numeroCuenta + "] " + mensaje);
    }
}
