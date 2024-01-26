package ni.gob.inss.barista.businesslogic.service.core.auditoria;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Auditoria</br>
 *
 * @author jfletes
 * @version 1.0, 5/23/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public class Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tabla;
    private String schema;
    private Object id;

    private String creadoPor;
    private String ModificadoPor;

    private Timestamp creadoEl;
    private Timestamp modificadoEl;

    private String creadoEnIp;
    private String modificadoEnIp;

    private List auditTrailList;

    private boolean mostrarDetalleAuditoria;


    public boolean isMostrarDetalleAuditoria() {
        return mostrarDetalleAuditoria;
    }

    public void setMostrarDetalleAuditoria(boolean mostrarDetalleAuditoria) {
        this.mostrarDetalleAuditoria = mostrarDetalleAuditoria;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List getAuditTrailList() {
        return auditTrailList;
    }

    public void setAuditTrailList(List auditTrailList) {
        this.auditTrailList = auditTrailList;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getModificadoPor() {
        return ModificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        ModificadoPor = modificadoPor;
    }

    public String getCreadoEnIp() {
        return creadoEnIp;
    }

    public void setCreadoEnIp(String creadoEnIp) {
        this.creadoEnIp = creadoEnIp;
    }

    public String getModificadoEnIp() {
        return modificadoEnIp;
    }

    public void setModificadoEnIp(String modificadoEnIp) {
        this.modificadoEnIp = modificadoEnIp;
    }

    public Timestamp getCreadoEl() {
        return creadoEl;
    }

    public void setCreadoEl(Timestamp creadoEl) {
        this.creadoEl = creadoEl;
    }

    public Timestamp getModificadoEl() {
        return modificadoEl;
    }

    public void setModificadoEl(Timestamp modificadoEl) {
        this.modificadoEl = modificadoEl;
    }
}