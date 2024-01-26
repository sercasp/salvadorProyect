package ni.gob.inss.barista.view.security;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Excepción de seguridad</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 03/06/2014
 * @since 1.0 *
 * Modificado por jvillanueva 23/10/2023
 */
public class SystemSecurityException extends Exception {
    private static final long serialVersionUID = 5394136727929338065L;

    /**
     * Construye un error con un mensaje
     *
     * @param msg mensaje
     */
    public SystemSecurityException(String msg) {
        super(msg);
    }

    /**
     * Construye un error con una causa
     *
     * @param cause excepción
     */
    public SystemSecurityException(Throwable cause) {
        super(cause);
    }

    /**
     * Construye un error con un mensaje y una causa
     *
     * @param msg   mensaje
     * @param cause exepción
     */
    public SystemSecurityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}