package ni.gob.inss.barista.model.auditoria;

import org.hibernate.CallbackException;

/**
 * @author Felix Medina
 * @author <a href=mailto:fmedina@inss.gob.ni>fmedina@inss.gob.ni</a>
 * @version 1.0, &nbsp; 20/04/2014
 * Modificado por jvillanueva el 03/08/2023
 */
public class AuditException extends CallbackException {
    private static final long serialVersionUID = 1L;

    /**
     * @param pMsg
     */
    public AuditException(String pMsg) {
        super(pMsg);
    }

    /**
     * Construye un error con una causa
     *
     * @param pCausa excepción
     */
    public AuditException(Exception pCausa) {
        super(pCausa);
    }

    /**
     * @param pMsg
     * @param pCausa
     */
    public AuditException(String pMsg, Throwable pCausa) {
        super(pMsg);
    }
}