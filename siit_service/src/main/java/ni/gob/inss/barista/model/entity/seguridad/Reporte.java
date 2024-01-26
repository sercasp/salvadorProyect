package ni.gob.inss.barista.model.entity.seguridad;

import ni.gob.inss.barista.model.entity.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author jjrivera
 * @version 1.0
 * @since 5/10/2016
 * Modificación de jvillanueva el 12/07/2023
 */
@Entity
@Table(name = "reporte", schema = "seguridad")
//@SequenceGenerator(name = "ReporteIdSeq", sequenceName = "seguridad.reporte_id_seq", allocationSize = 1)
public class Reporte implements Serializable, AuditableEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nombre;
    private Recurso recursoPorRecursoId;
    private Modulo moduloPorModuloId;
    private Integer orden;
    private Boolean pasivo;
    private Timestamp creadoEl;
    private Timestamp modificadoEl;
    private Usuario creadoPor;
    private Usuario modificadoPor;
    private String creadoEnIp;
    private String modificadoEnIp;
    private Collection<RoleReporte> rolesReportesById;
    private String modificadoEnOrdenador;
    private String creadoEnOrdenador;

    @Id
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReporteIdSeq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Size(max = 100)
    @Basic
    @Column(name = "nombre", nullable = false, length = 100)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "recurso_id", referencedColumnName = "id")
    public Recurso getRecursoPorRecursoId() {
        return recursoPorRecursoId;
    }

    public void setRecursoPorRecursoId(Recurso recursoPorRecursoId) {
        this.recursoPorRecursoId = recursoPorRecursoId;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "modulo_id", referencedColumnName = "id")
    public Modulo getModuloPorModuloId() {
        return moduloPorModuloId;
    }

    public void setModuloPorModuloId(Modulo moduloPorModuloId) {
        this.moduloPorModuloId = moduloPorModuloId;
    }

    @NotNull
    @Basic
    @Column(name = "orden", nullable = false)
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Basic
    @Column(name = "pasivo")
    public Boolean getPasivo() {
        return pasivo;
    }

    public void setPasivo(Boolean pasivo) {
        this.pasivo = pasivo;
    }

    @NotNull
    @Basic
    @Column(name = "creado_el", nullable = false)
    public Timestamp getCreadoEl() {
        return creadoEl;
    }

    public void setCreadoEl(Timestamp creadoEl) {
        this.creadoEl = creadoEl;
    }

    @Basic
    @Column(name = "modificado_el")
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
    @Basic
    @Column(name = "creado_en_ip", nullable = false, length = 20)
    public String getCreadoEnIp() {
        return creadoEnIp;
    }

    public void setCreadoEnIp(String creadoEnIp) {
        this.creadoEnIp = creadoEnIp;
    }

    @Basic
    @Column(name = "modificado_en_ip", length = 20)
    public String getModificadoEnIp() {
        return modificadoEnIp;
    }

    public void setModificadoEnIp(String modificadoEnIp) {
        this.modificadoEnIp = modificadoEnIp;
    }

    @Basic
    @Size(max = 100)
    @Column(name = "modificado_en_ordenador", length = 100)
    @Override
    public String getModificadoEnOrdenador() {
        return modificadoEnOrdenador;
    }

    @Override
    public void setModificadoEnOrdenador(String modificadoEnOrdenador) {
        this.modificadoEnOrdenador = modificadoEnOrdenador;
    }

    @Basic
    @Size(max = 100)
    @Column(name = "creado_en_ordenador", nullable = false, length = 100)
    @Override
    public String getCreadoEnOrdenador() {
        return creadoEnOrdenador;
    }

    @Override
    public void setCreadoEnOrdenador(String creadoEnOrdenador) {
        this.creadoEnOrdenador = creadoEnOrdenador;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reporte reporte = (Reporte) o;

        if (id != null ? !id.equals(reporte.id) : reporte.id != null) return false;
        if (nombre != null ? !nombre.equals(reporte.nombre) : reporte.nombre != null) return false;
        if (recursoPorRecursoId != null ? !recursoPorRecursoId.equals(reporte.recursoPorRecursoId) : reporte.recursoPorRecursoId != null)
            return false;
        if (moduloPorModuloId != null ? !moduloPorModuloId.equals(reporte.moduloPorModuloId) : reporte.moduloPorModuloId != null)
            return false;
        if (orden != null ? !orden.equals(reporte.orden) : reporte.orden != null) return false;
        if (pasivo != null ? !pasivo.equals(reporte.pasivo) : reporte.pasivo != null) return false;
        if (creadoEl != null ? !creadoEl.equals(reporte.creadoEl) : reporte.creadoEl != null) return false;
        if (modificadoEl != null ? !modificadoEl.equals(reporte.modificadoEl) : reporte.modificadoEl != null)
            return false;
        if (creadoPor != null ? !creadoPor.equals(reporte.creadoPor) : reporte.creadoPor != null) return false;
        if (modificadoPor != null ? !modificadoPor.equals(reporte.modificadoPor) : reporte.modificadoPor != null)
            return false;
        if (creadoEnIp != null ? !creadoEnIp.equals(reporte.creadoEnIp) : reporte.creadoEnIp != null) return false;
        if (modificadoEnIp != null ? !modificadoEnIp.equals(reporte.modificadoEnIp) : reporte.modificadoEnIp != null)
            return false;
        if (rolesReportesById != null ? !rolesReportesById.equals(reporte.rolesReportesById) : reporte.rolesReportesById != null)
            return false;
        if (modificadoEnOrdenador != null ? !modificadoEnOrdenador.equals(reporte.modificadoEnOrdenador) : reporte.modificadoEnOrdenador != null)
            return false;
        if (creadoEnOrdenador != null ? !creadoEnOrdenador.equals(reporte.creadoEnOrdenador) : reporte.creadoEnOrdenador != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (recursoPorRecursoId != null ? recursoPorRecursoId.hashCode() : 0);
        result = 31 * result + (moduloPorModuloId != null ? moduloPorModuloId.hashCode() : 0);
        result = 31 * result + (orden != null ? orden.hashCode() : 0);
        result = 31 * result + (pasivo != null ? pasivo.hashCode() : 0);
        result = 31 * result + (creadoEl != null ? creadoEl.hashCode() : 0);
        result = 31 * result + (modificadoEl != null ? modificadoEl.hashCode() : 0);
        result = 31 * result + (creadoPor != null ? creadoPor.hashCode() : 0);
        result = 31 * result + (modificadoPor != null ? modificadoPor.hashCode() : 0);
        result = 31 * result + (creadoEnIp != null ? creadoEnIp.hashCode() : 0);
        result = 31 * result + (modificadoEnIp != null ? modificadoEnIp.hashCode() : 0);
        result = 31 * result + (rolesReportesById != null ? rolesReportesById.hashCode() : 0);
        result = 31 * result + (modificadoEnOrdenador != null ? modificadoEnOrdenador.hashCode() : 0);
        result = 31 * result + (creadoEnOrdenador != null ? creadoEnOrdenador.hashCode() : 0);
        return result;
    }

    @Transient
    @Override
    public String getFilaId() {
        return Long.toString(getId());
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<RoleReporte> getRolesReportesById() {
        return rolesReportesById;
    }

    public void setRolesReportesById(Collection<RoleReporte> rolesReportesById) {
        this.rolesReportesById = rolesReportesById;
    }
}
