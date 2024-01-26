package ni.gob.inss.barista.model.entity.seguridad;

import lombok.Data;
import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.utils.RegExpresion;
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
 * ENTIDAD PARA ROLES</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/29/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
@Data
@Entity
@Table(name = "rol", schema = "seguridad")
//@SequenceGenerator(name = "ROLE_SEQ", sequenceName = "seguridad.roles_id_seq", allocationSize = 1)
public class Role implements AuditableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 100)
    @Pattern(regexp = RegExpresion.regExpDescripcion)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic
    @NotNull
    @Column(name = "pasivo", nullable = false)
    private Boolean pasivo;
    @Basic
    @NotEmpty
    @NotNull
    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;
    @OneToMany(mappedBy = "rolesByRolId")
    private Collection<Permiso> permisosesById;
    @OneToMany(mappedBy = "rolesByRolId")
    private Collection<RoleAutorizacion> rolesAutorizacionesesById;
    @OneToMany(mappedBy = "rolesByRolId")
    private Collection<RoleMenu> rolesMenusesById;
    @NotNull
    @Basic
    @Column(name = "creado_el", nullable = false)
    private Timestamp creadoEl;
    @Basic
    @Column(name = "modificado_el")
    private Timestamp modificadoEl;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "creado_por")
    private Usuario creadoPor;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "modificado_por")
    private Usuario modificadoPor;
    @NotNull
    @NotEmpty
    @Basic
    @Size(max = 20)
    @Column(name = "creado_en_ip", nullable = false, length = 20)
    private String creadoEnIp;
    @Basic
    @Size(max = 20)
    @Column(name = "modificado_en_ip", length = 20)
    private String modificadoEnIp;
    @Basic
    @Size(max = 100)
    @Column(name = "creado_en_ordenador", nullable = false, length = 100)
    private String creadoEnOrdenador;
    @Basic
    @Size(max = 100)
    @Column(name = "modificado_en_ordenador", length = 100)
    private String modificadoEnOrdenador;

    @Transient
    @Override
    public String getFilaId() {
        return Long.toString(getId());
    }

}
