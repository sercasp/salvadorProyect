package ni.gob.inss.barista.model.entity.seguridad;

import lombok.Data;
import ni.gob.inss.barista.model.auditoria.Audit;
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
 * ENTIDAD PARA AUTORIZACION</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.1, 5/29/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificación de jvillanueva el 21/08/2023
 */
@Data
@Audit(except = {"creadoEl", "modificadoEl", "creadoPor", "modificadoPor", "creadoEnIp",
        "modificadoEnIp", "creadoEnOrdenador", "modificadoEnOrdenador"})
@Entity
@Table(name = "autorizacion", schema = "seguridad")
//@SequenceGenerator(name = "autorizaciones_SEQ", sequenceName = "seguridad.users_id_seq", allocationSize = 1)
public class Autorizacion implements AuditableEntity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 150)
    @Column(name = "nombre", length = 150)
    private String nombre;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 30)
    @Column(name = "codigo", nullable = false, length = 30)
    private String codigo;
    @Basic
    @NotEmpty
    @Size(max = 1000)
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @Basic
    @NotNull
    @Column(name = "pasivo", nullable = false)
    private Boolean pasivo;
    @OneToMany(mappedBy = "autorizacionesByAutorizacionId")
    private Collection<RoleAutorizacion> rolesAutorizacionesesById;
    @ManyToOne
    @JoinColumn(name = "modulo_id", referencedColumnName = "id")
    private Modulo moduloIdByModuloId;
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