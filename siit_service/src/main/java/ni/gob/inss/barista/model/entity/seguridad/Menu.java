package ni.gob.inss.barista.model.entity.seguridad;

import lombok.Data;
import ni.gob.inss.barista.model.entity.AuditableEntity;
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
 * ENTIDAD PARA MENUS</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/29/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificación de jvillanueva el 21/08/2023
 */
@Data
@Entity
@Table(name = "menu", schema = "seguridad")
@SequenceGenerator(name = "menus_SEQ", sequenceName = "seguridad.menus_id_seq", allocationSize = 1)
public class Menu implements AuditableEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "menus_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    private Integer recursoId;
    private Integer moduloId;
    private Integer menuId;
    @Basic
    @NotNull
    @Column(name = "orden", nullable = false)
    private Integer orden;
    @Basic
    @NotNull
    @Column(name = "pasivo", nullable = false)
    private Boolean pasivo;
    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menusByMenuId;
    @OneToMany(mappedBy = "menusByMenuId")
    private Collection<Menu> menusesById;
    @ManyToOne
    @JoinColumn(name = "modulo_id", referencedColumnName = "id")
    private Modulo modulosByModuloId;
    @ManyToOne
    @JoinColumn(name = "recurso_id", referencedColumnName = "id")
    private Recurso recursosByRecursoId;
    @OneToMany(mappedBy = "menusByMenuId")
    private Collection<RoleMenu> rolesMenusesById;
    @NotNull
    @Basic
    @Column(name = "creado_el", nullable = false)
    private Timestamp creadoEl;
    @Basic
    @Column(name = "modificado_el")
    private Timestamp modificadoEl;
    @ManyToOne
    @JoinColumn(name = "creado_por", referencedColumnName = "id")
    private Usuario creadoPor;
    @ManyToOne
    @JoinColumn(name = "modificado_por", referencedColumnName = "id")
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
    @Column(name = "creado_en_ordenador", length = 100)
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