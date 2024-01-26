package ni.gob.inss.barista.model.entity;

import ni.gob.inss.barista.model.auditoria.Audit;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <b>LINUS</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Encapsula los atributos y metodos que toda Entidad debe tener.</br>
 *
 * @author bdelgado
 * @version 1.0, 28/01/2015
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@MappedSuperclass
@Audit(except = {"creadoPor", "modificadoPor", "creadoEl", "modificadoEl", "creadoEnIp", "modificadoEnIp"})
public class EntityBase implements AuditableEntity, Serializable {
    private Timestamp creadoEl;
    private Timestamp modificadoEl;
    private Usuario creadoPor;
    private Usuario modificadoPor;
    private String creadoEnIp;
    private String modificadoEnIp;
    private String creadoEnOrdenador;
    private String modificadoEnOrdenador;

    @Transient
    @Override
    public String getFilaId() {
        return "";
    }

    @NotNull
    @Basic
    @Column(name = "creado_el", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreadoEl() {
        return creadoEl;
    }

    public void setCreadoEl(Timestamp creadoEl) {
        this.creadoEl = creadoEl;
    }

    @Basic
    @Column(name = "modificado_el", nullable = true, insertable = true, updatable = true)
    public Timestamp getModificadoEl() {
        return modificadoEl;
    }

    public void setModificadoEl(Timestamp modificadoEl) {
        this.modificadoEl = modificadoEl;
    }

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "creado_por")
    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "modificado_por")
    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    @NotNull
    @NotEmpty
    @Basic
    @Size(min = 0, max = 20)
    @Column(name = "creado_en_ip", nullable = false, insertable = true, updatable = true, length = 20)
    public String getCreadoEnIp() {
        return creadoEnIp;
    }

    public void setCreadoEnIp(String creadoEnIp) {
        this.creadoEnIp = creadoEnIp;
    }

    @Basic
    @Size(min = 0, max = 20)
    @Column(name = "modificado_en_ip", nullable = true, insertable = true, updatable = true, length = 20)
    public String getModificadoEnIp() {
        return modificadoEnIp;
    }

    public void setModificadoEnIp(String modificadoEnIp) {
        this.modificadoEnIp = modificadoEnIp;
    }

    @Basic
    @Size(min = 0, max = 100)
    @Column(name = "creado_en_ordenador", nullable = false, insertable = true, updatable = true, length = 100)
    public String getCreadoEnOrdenador() {
        return creadoEnOrdenador;
    }

    public void setCreadoEnOrdenador(String creadoEnOrdenador) {
        this.creadoEnOrdenador = creadoEnOrdenador;
    }

    @Basic
    @Size(min = 0, max = 100)
    @Column(name = "modificado_en_ordenador", nullable = true, insertable = true, updatable = true, length = 100)
    public String getModificadoEnOrdenador() {
        return modificadoEnOrdenador;
    }

    public void setModificadoEnOrdenador(String modificadoEnOrdenador) {
        this.modificadoEnOrdenador = modificadoEnOrdenador;
    }
}