package ni.gob.inss.barista.model.entity.seguridad;

import lombok.Data;
import ni.gob.inss.barista.model.entity.AuditableEntity;
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
 * Entidad para RecursoUsuario</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/06/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 21/08/2023
 */
@Data
@Entity
@Table(name = "recurso_usuario", schema = "seguridad")
//@SequenceGenerator(name = "RecursoUsuario_SEQ", sequenceName = "seguridad.recursos_usuarios_id_seq", allocationSize = 1)
public class RecursoUsuario implements AuditableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Basic
    @Column(name = "recurso_id")
    private Integer recursoId;
    @NotNull
    @Basic
    @Column(name = "usuario_id")
    private Integer usuarioId;
    @NotNull
    @Basic
    @Column(name = "entidad_id")
    private Integer entidadId;
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
