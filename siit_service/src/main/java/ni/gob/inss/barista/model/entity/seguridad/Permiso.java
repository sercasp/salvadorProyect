package ni.gob.inss.barista.model.entity.seguridad;

import lombok.Data;
import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
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
 * ENTIDAD PARA PERMISOS</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/29/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 21/08/2023
 */
@Data
@Entity
@Table(name = "permiso", schema = "seguridad")
//@SequenceGenerator(name = "permisos_SEQ", sequenceName = "seguridad.permisos_id_seq", allocationSize = 1)
public class Permiso implements AuditableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @NotNull
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

    @Basic
    @NotNull
    @NotEmpty
    @Column(name = "creado_en_ip", nullable = false, length = 20)
    private String creadoEnIp;

    @Basic
    @Column(name = "modificado_en_ip", length = 20)
    private String modificadoEnIp;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Role rolesByRolId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuariosByUsuarioId;

    @ManyToOne
    @JoinColumn(name = "entidad_id", referencedColumnName = "id")
    private Entidad entidad;

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
