package ni.gob.inss.barista.model.entity.auditoria;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Entidad Para SesionUsuario</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/6/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 15/08/2023
 */
@Entity
@Table(name = "sesion_usuario", schema = "auditoria")
public class SesionUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer usuarioId;
    private Timestamp fechaSesion;
    private String ipSesion;
    private String navegador;
    private String hostName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @NotNull
    @Column(name = "usuario_id", nullable = false, insertable = true, updatable = true)
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Basic
    @NotNull
    @Column(name = "fecha_sesion", nullable = false, insertable = true, updatable = true)
    public Timestamp getFechaSesion() {
        return fechaSesion;
    }

    public void setFechaSesion(Timestamp fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    @Basic
    @Column(name = "ip_sesion", insertable = true, updatable = true, length = 20)
    public String getIpSesion() {
        return ipSesion;
    }

    public void setIpSesion(String ipSesion) {
        this.ipSesion = ipSesion;
    }

    @Basic
    @Column(name = "navegador", nullable = false, insertable = true, updatable = true, length = 250)
    public String getNavegador() {
        return navegador;
    }

    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }

    @Basic
    @Column(name = "nombre_ordenador", nullable = false, insertable = true, updatable = true, length = 250)
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SesionUsuario that = (SesionUsuario) o;

        if (fechaSesion != null ? !fechaSesion.equals(that.fechaSesion) : that.fechaSesion != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (ipSesion != null ? !ipSesion.equals(that.ipSesion) : that.ipSesion != null) return false;
        if (usuarioId != null ? !usuarioId.equals(that.usuarioId) : that.usuarioId != null) return false;
        if (navegador != null ? !navegador.equals(that.navegador) : that.navegador != null) return false;
        if (hostName != null ? !hostName.equals(that.hostName) : that.hostName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (usuarioId != null ? usuarioId.hashCode() : 0);
        result = 31 * result + (fechaSesion != null ? fechaSesion.hashCode() : 0);
        result = 31 * result + (ipSesion != null ? ipSesion.hashCode() : 0);
        result = 31 * result + (navegador != null ? navegador.hashCode() : 0);
        result = 31 * result + (hostName != null ? hostName.hashCode() : 0);
        return result;
    }
}