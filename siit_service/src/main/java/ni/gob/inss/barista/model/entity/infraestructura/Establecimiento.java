package ni.gob.inss.barista.model.entity.infraestructura;

import ni.gob.inss.barista.model.auditoria.Audit;
import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * DESCRIPCIÓN</br>
 *
 * @author JAIRO HELÍ MENDOZA AGUIRRE
 * @version 1.0, 05/08/2014
 * @since 1.0 *
 */
@Audit(except = {"creadoEl", "modificadoEl", "creadoPor", "modificadoPor", "creadoEnIp",
        "modificadoEnIp", "creadoEnOrdenador", "modificadoEnOrdenador"})
@Entity
@Table(name = "establecimiento", schema = "infraestructura", uniqueConstraints = @UniqueConstraint(columnNames = "codigo"))
//@SequenceGenerator(name = "establecimiento_seq", sequenceName = "infraestructura.establecimientos_id_seq", allocationSize = 1)
public class Establecimiento implements AuditableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer codigo;
    private String nombre;
    private Boolean pasivo;
    private Timestamp creadoEl;
    private Timestamp modificadoEl;
    private Usuario creadoPor;
    private Usuario modificadoPor;
    private String creadoEnIp;
    private String modificadoEnIp;
    private String creadoEnOrdenador;
    private String modificadoEnOrdenador;

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "establecimiento_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "codigo", nullable = true, insertable = true, updatable = true)
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "nombre", nullable = true, insertable = true, updatable = true, length = 255)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "pasivo", nullable = true, insertable = true, updatable = true)
    public Boolean getPasivo() {
        return pasivo;
    }

    public void setPasivo(Boolean pasivo) {
        this.pasivo = pasivo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Establecimiento that = (Establecimiento) o;

        if (codigo != null ? !codigo.equals(that.codigo) : that.codigo != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (pasivo != null ? !pasivo.equals(that.pasivo) : that.pasivo != null) return false;
        if (creadoEl != null ? !creadoEl.equals(that.creadoEl) : that.creadoEl != null) return false;
        if (creadoEnIp != null ? !creadoEnIp.equals(that.creadoEnIp) : that.creadoEnIp != null) return false;
        if (creadoPor != null ? !creadoPor.equals(that.creadoPor) : that.creadoPor != null) return false;
        if (creadoEnOrdenador != null ? !creadoEnOrdenador.equals(that.creadoEnOrdenador) : that.creadoEnOrdenador != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modificadoEl != null ? !modificadoEl.equals(that.modificadoEl) : that.modificadoEl != null)
            return false;
        if (modificadoEnIp != null ? !modificadoEnIp.equals(that.modificadoEnIp) : that.modificadoEnIp != null)
            return false;
        if (modificadoPor != null ? !modificadoPor.equals(that.modificadoPor) : that.modificadoPor != null)
            return false;
        if (modificadoEnOrdenador != null ? !modificadoEnOrdenador.equals(that.modificadoEnOrdenador) : that.modificadoEnOrdenador != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (pasivo != null ? pasivo.hashCode() : 0);
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

    @Transient
    @Override
    public String getFilaId() {
        return Long.toString(getId());
    }
}
