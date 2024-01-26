package ni.gob.inss.barista.model.entity.infraestructura;

import ni.gob.inss.barista.model.auditoria.Audit;
import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.entity.seguridad.Permiso;
import ni.gob.inss.barista.model.entity.seguridad.SesionModulo;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Establecimientos</br>
 *
 * @author J
 * @version 1.0, 18/06/2014
 * @since 1.0 *
 */
@Audit(except = {"creadoEl", "modificadoEl", "creadoPor", "modificadoPor", "creadoEnIp",
        "modificadoEnIp", "creadoEnOrdenador", "modificadoEnOrdenador"})
@Entity
@Table(name = "entidad", schema = "infraestructura", uniqueConstraints = @UniqueConstraint(columnNames = "codigo"))
//@SequenceGenerator(name = "entidad_seq", sequenceName = "infraestructura.entidad_id_seq", allocationSize = 1)
public class Entidad implements AuditableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer codigo;
    private String nombre;
    private String siglas;
    private Establecimiento establecimientoByEstablecimientoId;
    private Boolean pasivo;
    private Collection<Permiso> permisos;
    private Collection<SesionModulo> sessionesModulo;
    private String telefono;
    private String direccion;
    private Entidad entidadByEntidadId;
    private Timestamp creadoEl;
    private Timestamp modificadoEl;
    private Usuario creadoPor;
    private Usuario modificadoPor;
    private String creadoEnIp;
    private String modificadoEnIp;
    private String creadoEnOrdenador;
    private String modificadoEnOrdenador;

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entidad_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "codigo", nullable = false, insertable = true, updatable = true)
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "nombre", nullable = false, insertable = true, updatable = true, length = 255)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "pasivo", nullable = false, insertable = true, updatable = true)
    public Boolean getPasivo() {
        return pasivo;
    }

    public void setPasivo(Boolean pasivo) {
        this.pasivo = pasivo;
    }

    @Basic
    @Column(name = "siglas", nullable = false, insertable = true, updatable = true, length = 4)
    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    @Basic
    @Column(name = "telefono", nullable = true, insertable = true, updatable = true, length = 50)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Basic
    @Column(name = "direccion", nullable = true, insertable = true, updatable = true, length = 50)
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @OneToMany(mappedBy = "entidad")
    public Collection<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Collection<Permiso> permisos) {
        this.permisos = permisos;
    }

    @OneToMany(mappedBy = "entidad")
    public Collection<SesionModulo> getSessionesModulo() {
        return sessionesModulo;
    }

    public void setSessionesModulo(Collection<SesionModulo> sessionesModulo) {
        this.sessionesModulo = sessionesModulo;
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
    @JoinColumn(name = "creado_por", referencedColumnName = "id")
    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    @ManyToOne
    @JoinColumn(name = "modificado_por", referencedColumnName = "id")
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
    @Column(name = "creado_en_ordenador", nullable = true, insertable = true, updatable = true, length = 100)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entidad entidad = (Entidad) o;

        if (id != null ? !id.equals(entidad.id) : entidad.id != null) return false;
        if (codigo != null ? !codigo.equals(entidad.codigo) : entidad.codigo != null) return false;
        if (nombre != null ? !nombre.equals(entidad.nombre) : entidad.nombre != null) return false;
        if (siglas != null ? !siglas.equals(entidad.siglas) : entidad.siglas != null) return false;
        if (establecimientoByEstablecimientoId != null ? !establecimientoByEstablecimientoId.equals(entidad.establecimientoByEstablecimientoId) : entidad.establecimientoByEstablecimientoId != null)
            return false;
        if (pasivo != null ? !pasivo.equals(entidad.pasivo) : entidad.pasivo != null) return false;
        if (permisos != null ? !permisos.equals(entidad.permisos) : entidad.permisos != null) return false;
        if (sessionesModulo != null ? !sessionesModulo.equals(entidad.sessionesModulo) : entidad.sessionesModulo != null)
            return false;
        if (telefono != null ? !telefono.equals(entidad.telefono) : entidad.telefono != null) return false;
        if (direccion != null ? !direccion.equals(entidad.direccion) : entidad.direccion != null) return false;
        if (entidadByEntidadId != null ? !entidadByEntidadId.equals(entidad.entidadByEntidadId) : entidad.entidadByEntidadId != null)
            return false;
        if (creadoEl != null ? !creadoEl.equals(entidad.creadoEl) : entidad.creadoEl != null) return false;
        if (modificadoEl != null ? !modificadoEl.equals(entidad.modificadoEl) : entidad.modificadoEl != null)
            return false;
        if (creadoPor != null ? !creadoPor.equals(entidad.creadoPor) : entidad.creadoPor != null) return false;
        if (modificadoPor != null ? !modificadoPor.equals(entidad.modificadoPor) : entidad.modificadoPor != null)
            return false;
        if (creadoEnIp != null ? !creadoEnIp.equals(entidad.creadoEnIp) : entidad.creadoEnIp != null) return false;
        if (modificadoEnIp != null ? !modificadoEnIp.equals(entidad.modificadoEnIp) : entidad.modificadoEnIp != null)
            return false;
        if (creadoEnOrdenador != null ? !creadoEnOrdenador.equals(entidad.creadoEnOrdenador) : entidad.creadoEnOrdenador != null)
            return false;
        return modificadoEnOrdenador != null ? modificadoEnOrdenador.equals(entidad.modificadoEnOrdenador) : entidad.modificadoEnOrdenador == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (siglas != null ? siglas.hashCode() : 0);
        result = 31 * result + (establecimientoByEstablecimientoId != null ? establecimientoByEstablecimientoId.hashCode() : 0);
        result = 31 * result + (pasivo != null ? pasivo.hashCode() : 0);
        result = 31 * result + (permisos != null ? permisos.hashCode() : 0);
        result = 31 * result + (sessionesModulo != null ? sessionesModulo.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        result = 31 * result + (entidadByEntidadId != null ? entidadByEntidadId.hashCode() : 0);
        result = 31 * result + (creadoEl != null ? creadoEl.hashCode() : 0);
        result = 31 * result + (modificadoEl != null ? modificadoEl.hashCode() : 0);
        result = 31 * result + (creadoPor != null ? creadoPor.hashCode() : 0);
        result = 31 * result + (modificadoPor != null ? modificadoPor.hashCode() : 0);
        result = 31 * result + (creadoEnIp != null ? creadoEnIp.hashCode() : 0);
        result = 31 * result + (modificadoEnIp != null ? modificadoEnIp.hashCode() : 0);
        result = 31 * result + (creadoEnOrdenador != null ? creadoEnOrdenador.hashCode() : 0);
        result = 31 * result + (modificadoEnOrdenador != null ? modificadoEnOrdenador.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
    public Establecimiento getEstablecimientoByEstablecimientoId() {
        return establecimientoByEstablecimientoId;
    }

    public void setEstablecimientoByEstablecimientoId(Establecimiento establecimientoByEstablecimientoId) {
        this.establecimientoByEstablecimientoId = establecimientoByEstablecimientoId;
    }

    @ManyToOne
    @JoinColumn(name = "entidad_id", referencedColumnName = "id")
    public Entidad getEntidadByEntidadId() {
        return entidadByEntidadId;
    }

    public void setEntidadByEntidadId(Entidad entidadByEntidadId) {
        this.entidadByEntidadId = entidadByEntidadId;
    }

    @Transient
    @Override
    public String getFilaId() {
        return Long.toString(getId());
    }

}
