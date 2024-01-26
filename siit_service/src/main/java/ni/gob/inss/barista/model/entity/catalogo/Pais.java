package ni.gob.inss.barista.model.entity.catalogo;

import lombok.Data;
import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Entidad TiposCatalogo</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/22/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Data
@Entity
@Table(name = "pais", schema = "catalogo")
@SequenceGenerator(
        name = "PAIS_SEQ",
        sequenceName = "catalogo.pais_id_seq",
        allocationSize = 1
)
public class Pais implements AuditableEntity, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PAIS_SEQ"
    )
    @Column(
            name = "id",
            nullable = false,
            insertable = true,
            updatable = true
    )
    private Integer id;
    @Basic
    @NotEmpty
    @NotNull
    @Size(
            min = 0,
            max = 100
    )
    @Pattern(
            regexp = "^[a-zA-Z0-9_ .áéíóúÁÉÍÓÚÑñ-]*$"
    )
    @Column(
            name = "nombre",
            nullable = false,
            insertable = true,
            updatable = true,
            length = 100
    )
    private String nombre;
    @Basic
    @NotEmpty
    @NotNull
    @Size(
            min = 0,
            max = 2
    )
    @Pattern(
            regexp = "^[a-zA-Z]*$"
    )
    @Column(
            name = "codigo_alfa2",
            nullable = false,
            insertable = true,
            updatable = true,
            length = 2
    )
    private String codigoAlfa2;
    @Basic
    @NotNull
    @Column(
            name = "codigo",
            nullable = false,
            insertable = true,
            updatable = true
    )
    private Integer codigo;
    @Basic
    @NotNull
    @Column(
            name = "pasivo",
            nullable = false,
            insertable = true,
            updatable = true
    )
    private Boolean pasivo;

    public Pais() {
    }

    @NotNull
    @Basic
    @Column(name = "creado_el", nullable = false, insertable = true, updatable = true)
    private Timestamp creadoEl;
    @Basic
    @Column(name = "modificado_el", nullable = true, insertable = true, updatable = true)
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
    @Size(min = 0, max = 20)
    @Column(name = "creado_en_ip", nullable = false, insertable = true, updatable = true, length = 20)
    private String creadoEnIp;
    @Basic
    @Size(min = 0, max = 20)
    @Column(name = "modificado_en_ip", nullable = true, insertable = true, updatable = true, length = 20)
    private String modificadoEnIp;
    @Basic
    @Size(min = 0, max = 100)
    @Column(name = "creado_en_ordenador", nullable = true, insertable = true, updatable = true, length = 100)
    private String creadoEnOrdenador;
    @Basic
    @Size(min = 0, max = 100)
    @Column(name = "modificado_en_ordenador", nullable = true, insertable = true, updatable = true, length = 100)
    private String modificadoEnOrdenador;

    @Transient
    public String getFilaId() {
        return Long.toString((long) this.getId());
    }
}