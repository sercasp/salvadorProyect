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
 * <b>Copyright (c) 2016 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * ENTIDAD PARA ROLES REPORTES</br>
 *
 * @author HERNALDO JOSÉ MAYORGA HERNÁNDEZ
 * @version 1.0, 10/07/2016
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */

@Data
@Entity
@Table(name = "rol_reporte", schema = "seguridad")
//@SequenceGenerator(name = "RoleReporte_SEQ", sequenceName = "seguridad.rol_reporte_id_seq", allocationSize = 1)
public class RoleReporte implements AuditableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Role roleByRoleId;
    @ManyToOne
    @JoinColumn(name = "reporte_id", referencedColumnName = "id")
    private Reporte reporteByReporteId;
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
        return this.getId().toString();
    }
}
