package com.academia.banco;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** Lo que pasa mientras UN cliente está frente al cajero. Cada cliente necesita la suya. */
@Component
@Scope("prototype")
public class SesionCajero {

    private static int creadas = 0;

    private final int numero;
    private final CajeroAutomatico cajero;

    public SesionCajero(CajeroAutomatico cajero) {
        this.numero = ++creadas;
        this.cajero = cajero;
        System.out.println("   (se creó la sesión #" + numero + ")");
    }

    public void retirar(String numeroCuenta, String monto) {
        System.out.println("Sesión #" + numero + ": retiro de $" + monto + " de la cuenta " + numeroCuenta);
        try {
            cajero.retirar(numeroCuenta, new BigDecimal(monto));
        } catch (RuntimeException e) {
            System.out.println("   Rechazado: " + e.getMessage());
        }
    }

    public CajeroAutomatico getCajero() {
        return cajero;
    }
}
