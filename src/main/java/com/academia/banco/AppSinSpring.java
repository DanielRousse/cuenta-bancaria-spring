package com.academia.banco;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZoneId;

/** Sin Spring: TÚ creas cada pieza y TÚ se las pasas al cajero. */
public class AppSinSpring {

    public static void main(String[] args) {
        RepositorioEnMemoria repositorio = new RepositorioEnMemoria();
        ServicioAntifraude antifraude = new AntifraudePorMonto();
        Notificador notificador = new NotificadorConsola();
        Clock reloj = Clock.system(ZoneId.of("America/Mexico_City"));

        CajeroAutomatico cajero = new CajeroAutomatico(repositorio, antifraude, notificador, reloj);

        repositorio.abrir("001", "Ana", "10000.00");
        System.out.println("Saldo de la 001: $" + cajero.consultarSaldo("001"));
        cajero.retirar("001", new BigDecimal("500.00"));
        System.out.println("Saldo de la 001: $" + cajero.consultarSaldo("001"));
    }
}
