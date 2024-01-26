package ni.gob.inss.barista.model.entity.catalogo;

import lombok.Data;
import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.barista.model.utils.RegExpresion;
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
 * Entidad para Catálogos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/28/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 04/08/2023
 */
@Data
@Entity
@Table(name = "catalogo", schema = "catalogo")
//@SequenceGenerator(name = "Catalogo_SEQ", sequenceName = "catalogo.catalogos_id_seq", allocationSize = 1)
public class Catalogo implements AuditableEntity, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Integer id;
    @Basic
    @Pattern(regexp = RegExpresion.regExpDescripcion)
    @Size(min = 0, max = 500)
    @Column(name = "descripcion", nullable = true, insertable = true, updatable = true, length = 500)
    private String descripcion;
    @Basic
    @NotNull
    @Column(name = "pasivo", nullable = false, insertable = true, updatable = true)
    private Boolean pasivo;
    @Basic
    @NotNull
    @NotEmpty
    @Size(min = 0, max = 20)
    @Column(name = "codigo", nullable = false, insertable = true, updatable = true, length = 20)
    private String codigo;
    @Basic
    @NotNull
    @Column(name = "orden", nullable = false, insertable = true, updatable = true)
    private Integer orden;
    @Basic
    @Size(min = 0, max = 10)
    @Column(name = "codigo_alterno", nullable = true, insertable = true, updatable = true, length = 10)
    private String codigoAlterno;
    @Basic
    @NotNull
    @NotEmpty
    @Size(min = 0, max = 150)
    @Pattern(regexp = RegExpresion.regExpDescripcion)
    @Column(name = "nombre", nullable = false, insertable = true, updatable = true, length = 150)
    private String nombre;
    @Basic
    @NotNull
    @NotEmpty
    @Size(min = 0, max = 20)
    @Column(name = "ref_tipo_catalogo", nullable = false, insertable = true, updatable = true, length = 20)
    private String refTipoCatalogo;
    @NotNull
    @Basic
    @Column(name = "creado_el", nullable = false, insertable = true, updatable = true)
    private Timestamp creadoEl;
    @Basic
    @Column(name = "modificado_el", nullable = true, insertable = true, updatable = true)
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
    @Override
    public String getFilaId() {
        return this.getId().toString();
    }
}