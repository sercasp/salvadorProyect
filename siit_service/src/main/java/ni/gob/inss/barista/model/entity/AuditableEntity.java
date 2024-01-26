package ni.gob.inss.barista.model.entity;

import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import javax.persistence.Transient;
import java.sql.Timestamp;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz para entidades auditables</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/26/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface AuditableEntity {
    @Transient
    String getFilaId();

    void setCreadoPor(Usuario creadoPor);

    Usuario getCreadoPor();

    void setModificadoPor(Usuario modificadoPor);

    Usuario getModificadoPor();

    void setCreadoEnIp(String creadoEnIp);

    String getCreadoEnIp();

    void setModificadoEnIp(String modificadoEnIp);

    String getModificadoEnIp();

    void setCreadoEl(Timestamp creadoEl);

    Timestamp getCreadoEl();

    void setModificadoEl(Timestamp modificadoEl);

    Timestamp getModificadoEl();

    void setCreadoEnOrdenador(String creadoEnOrdenador);

    String getCreadoEnOrdenador();

    void setModificadoEnOrdenador(String modificadoEnOrdenador);

    String getModificadoEnOrdenador();
}
