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
 * ENTIDAD PARA PARÁMETROS ENTIDADES</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 03/06/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
@Data
@Entity
@Table(name = "parametro_entidad", schema = "seguridad")
//@SequenceGenerator(name = "parametros_entidades_SEQ", sequenceName = "seguridad.parametros_entidades_id_seq", allocationSize = 1)
public class ParametroEntidad implements AuditableEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotNull
    @NotEmpty
    @Column(name = "codigo", nullable = false)
    private String codigo;
    @Basic
    @NotNull
    @NotEmpty
    @Column(name = "descriptor", nullable = false)
    private String descriptor;
    @Basic
    @NotNull
    @Column(name = "aplicable_usuario", nullable = false)
    private Boolean aplicableUsuario;
    @Basic
    @NotNull
    @NotEmpty
    @Column(name = "tipo_valor", nullable = false)
    private String tipoValor;
    @Basic
    @Column(name = "sentencia")
    private String sentencia;
    @Basic
    @Column(name = "lista_valores")
    private String listaValores;
    @Basic
    @Column(name = "nombre_valores")
    private String nombreValores;
    @Basic
    @NotNull
    @NotEmpty
    @Column(name = "valor", nullable = false)
    private String valor;
    @ManyToOne
    @JoinColumn(name = "modulo_id", referencedColumnName = "id")
    private Modulo modulosByModuloId;
    @ManyToOne
    @JoinColumn(name = "entidad_id", referencedColumnName = "id")
    private Entidad entidadIdByEntidadId;
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
