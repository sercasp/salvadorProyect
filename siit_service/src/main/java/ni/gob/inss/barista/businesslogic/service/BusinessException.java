package ni.gob.inss.barista.businesslogic.service;
/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Excepción de lógica para la capa de servicio</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.1, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public class BusinessException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 5394136727929338065L;
    /**
     * Construye un error con un mensaje
     *
     * @param msg mensaje
     */
    public BusinessException(String msg) {
        super(msg);
    }
    /**
     * Construye un error con una causa
     *
     * @param cause excepción
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }
    /**
     * Construye un error con un mensaje y una causa
     *
     * @param msg   mensaje
     * @param cause exepción
     */
    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}