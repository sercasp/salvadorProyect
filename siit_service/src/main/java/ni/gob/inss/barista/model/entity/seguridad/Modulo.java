package ni.gob.inss.barista.model.entity.seguridad;

import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.utils.RegExpresion;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * ENTIDAD PARA MODULO</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/29/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 21/08/2023
 */

@NamedQueries({
        @NamedQuery(
                name = "obtenerModulosPorUsuarioOrderNombre",
                query = "select distinct modulos.id as id, modulos.urlInicio, modulos.nombre as nombre, modulos.urlImagen as url_imagen," +
                        "sm.fechaUltimaSesion as ultima_sesion, coalesce(sm.cantidad,0) as cantidad from Modulo as modulos " +
                        "join modulos.menusesById as menus join menus.rolesMenusesById as roles_menus " +
                        "join roles_menus.rolesByRolId as roles join roles.permisosesById as permisos " +
                        "join menus.recursosByRecursoId as recursos " +
                        "left join modulos.sesionesModulosesById as sm with sm.entidad.id = :entidadId " +
                        "and sm.usuarioId = :usuarioId WHERE permisos.usuariosByUsuarioId.id = :usuarioId and " +
                        "permisos.entidad.id= :entidadId and modulos.bloqueado=false and menus.pasivo=false " +
                        "and recursos.tipo = :tipo and upper(modulos.nombre) like upper(:nombreModulo) " +
                        "and roles.pasivo =false order by modulos.nombre asc"
        ),
        @NamedQuery(
                name = "obtenerModulosPorUsuarioOrderCantidad",
                query = "select distinct modulos.id as id, modulos.urlInicio, modulos.nombre as nombre, modulos.urlImagen as url_imagen," +
                        "sm.fechaUltimaSesion as ultima_sesion, coalesce(sm.cantidad,0) as cantidad from Modulo as modulos " +
                        "join modulos.menusesById as menus join menus.rolesMenusesById as roles_menus " +
                        "join roles_menus.rolesByRolId as roles join roles.permisosesById as permisos " +
                        "join menus.recursosByRecursoId as recursos " +
                        "left join modulos.sesionesModulosesById as sm with sm.entidad.id = :entidadId " +
                        "and sm.usuarioId = :usuarioId WHERE permisos.usuariosByUsuarioId.id = :usuarioId and " +
                        "permisos.entidad.id= :entidadId and modulos.bloqueado=false and menus.pasivo=false " +
                        "and recursos.tipo = :tipo and upper(modulos.nombre) like upper(:nombreModulo) " +
                        "and roles.pasivo =false order by coalesce(sm.cantidad,0) desc"
        )
})

@Entity
@Table(name = "modulo", schema = "seguridad")
//@SequenceGenerator(name = "modulos_SEQ", sequenceName = "seguridad.modulos_id_seq", allocationSize = 1)
public class Modulo implements Serializable, AuditableEntity {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nombre;
    private String urlInicio;
    private String urlImagen;
    private Boolean bloqueado;
    private String codigo;
    private Collection<Menu> menusesById;
    private Collection<SesionModulo> sesionesModulosesById;
    private Timestamp creadoEl;
    private Timestamp modificadoEl;
    private Usuario creadoPor;
    private Usuario modificadoPor;
    private String creadoEnIp;
    private String modificadoEnIp;
    private String creadoEnOrdenador;
    private String modificadoEnOrdenador;

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE3, generator = "modulos_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 100)
    @Pattern(regexp = RegExpresion.regExpNombre)
    @Column(name = "nombre", length = 100)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 1000)
    @Column(name = "url_inicio", nullable = false, length = 1000)
    public String getUrlInicio() {
        return urlInicio;
    }

    public void setUrlInicio(String urlInicio) {
        this.urlInicio = urlInicio;
    }

    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 1000)
    @Column(name = "url_imagen", length = 1000)
    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Basic
    @NotNull
    @Column(name = "bloqueado", nullable = false)
    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 5)
    @Pattern(regexp = RegExpresion.regExpSoloLetras)
    @Column(name = "codigo", nullable = false, length = 5)
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    @NotNull
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
    @Size(max = 20)
    @Column(name = "creado_en_ip", nullable = false, length = 20)
    public String getCreadoEnIp() {
        return creadoEnIp;
    }

    public void setCreadoEnIp(String creadoEnIp) {
        this.creadoEnIp = creadoEnIp;
    }

    @Basic
    @Size(max = 20)
    @Column(name = "modificado_en_ip", length = 20)
    public String getModificadoEnIp() {
        return modificadoEnIp;
    }

    public void setModificadoEnIp(String modificadoEnIp) {
        this.modificadoEnIp = modificadoEnIp;
    }

    @Basic
    @Size(max = 100)
    @Column(name = "creado_en_ordenador", length = 100)
    public String getCreadoEnOrdenador() {
        return creadoEnOrdenador;
    }

    public void setCreadoEnOrdenador(String creadoEnOrdenador) {
        this.creadoEnOrdenador = creadoEnOrdenador;
    }

    @Basic
    @Size(max = 100)
    @Column(name = "modificado_en_ordenador", length = 100)
    public String getModificadoEnOrdenador() {
        return modificadoEnOrdenador;
    }

    public void setModificadoEnOrdenador(String modificadoEnOrdenador) {
        this.modificadoEnOrdenador = modificadoEnOrdenador;
    }

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "modulosByModuloId", orphanRemoval = true)
    public Collection<Menu> getMenusesById() {
        return menusesById;
    }

    public void setMenusesById(Collection<Menu> menusesById) {
        this.menusesById = menusesById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Modulo modulo = (Modulo) o;

        if (id != null ? !id.equals(modulo.id) : modulo.id != null) return false;
        if (nombre != null ? !nombre.equals(modulo.nombre) : modulo.nombre != null) return false;
        if (urlInicio != null ? !urlInicio.equals(modulo.urlInicio) : modulo.urlInicio != null) return false;
        if (urlImagen != null ? !urlImagen.equals(modulo.urlImagen) : modulo.urlImagen != null) return false;
        if (bloqueado != null ? !bloqueado.equals(modulo.bloqueado) : modulo.bloqueado != null) return false;
        if (codigo != null ? !codigo.equals(modulo.codigo) : modulo.codigo != null) return false;
        if (menusesById != null ? !menusesById.equals(modulo.menusesById) : modulo.menusesById != null) return false;
        if (sesionesModulosesById != null ? !sesionesModulosesById.equals(modulo.sesionesModulosesById) : modulo.sesionesModulosesById != null)
            return false;
        if (creadoEl != null ? !creadoEl.equals(modulo.creadoEl) : modulo.creadoEl != null) return false;
        if (modificadoEl != null ? !modificadoEl.equals(modulo.modificadoEl) : modulo.modificadoEl != null)
            return false;
        if (creadoPor != null ? !creadoPor.equals(modulo.creadoPor) : modulo.creadoPor != null) return false;
        if (modificadoPor != null ? !modificadoPor.equals(modulo.modificadoPor) : modulo.modificadoPor != null)
            return false;
        if (creadoEnIp != null ? !creadoEnIp.equals(modulo.creadoEnIp) : modulo.creadoEnIp != null) return false;
        if (modificadoEnIp != null ? !modificadoEnIp.equals(modulo.modificadoEnIp) : modulo.modificadoEnIp != null)
            return false;
        if (creadoEnOrdenador != null ? !creadoEnOrdenador.equals(modulo.creadoEnOrdenador) : modulo.creadoEnOrdenador != null)
            return false;
        if (modificadoEnOrdenador != null ? !modificadoEnOrdenador.equals(modulo.modificadoEnOrdenador) : modulo.modificadoEnOrdenador != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (urlInicio != null ? urlInicio.hashCode() : 0);
        result = 31 * result + (urlImagen != null ? urlImagen.hashCode() : 0);
        result = 31 * result + (bloqueado != null ? bloqueado.hashCode() : 0);
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 31 * result + (menusesById != null ? menusesById.hashCode() : 0);
        result = 31 * result + (sesionesModulosesById != null ? sesionesModulosesById.hashCode() : 0);
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

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "modulosByModuloId", orphanRemoval = true)
    public Collection<SesionModulo> getSesionesModulosesById() {
        return sesionesModulosesById;
    }

    public void setSesionesModulosesById(Collection<SesionModulo> sesionesModulosesById) {
        this.sesionesModulosesById = sesionesModulosesById;
    }

    @Transient
    @Override
    public String getFilaId() {
        return Long.toString(getId());
    }
}