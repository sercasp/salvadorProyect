package ni.gob.inss.barista.model.entity.catalogo;

import lombok.Data;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "localidad", schema = "catalogo")
public class Localidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(
            name = "id",
            unique = true,
            nullable = false
    )
    private Integer id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "ref_unidad_territorial"
    )
    //private ZonaLocalidad zonasLocalidades;
    private DivisionPolitica divisionesPoliticas;
    @Column(
            name = "nombre",
            length = 100
    )
    private String nombre;
    @Column(
            name = "tipo_area",
            length = 1
    )
    private String tipoArea;
    @Column(
            name = "codigo",
            length = 20
    )
    private String codigo;
    @Column(
            name = "referencia",
            length = 500
    )
    private String referencia;
    @Column(
            name = "latitud",
            precision = 10,
            scale = 6
    )
    private BigDecimal latitud;
    @Column(
            name = "longitud",
            precision = 10,
            scale = 6
    )
    private BigDecimal longitud;
    @Column(
            name = "pasivo"
    )
    private Boolean pasivo;
    @Column(
            name = "ref_unidad_salud"
    )
    private Integer refUnidadSalud;
    @Column(
            name = "creado_el",
            length = 29
    )
    private Timestamp creadoEl;
    @Column(
            name = "modificado_el",
            length = 29
    )
    private Timestamp modificadoEl;
    @Column(
            name = "creado_en_ip",
            length = 20
    )
    private String creadoEnIp;
    @Column(
            name = "modificado_en_ip",
            length = 20
    )
    private String modificadoEnIp;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "creado_por", referencedColumnName = "id")
    private Usuario creadoPor;
    @ManyToOne
    @JoinColumn(name = "modificado_por", referencedColumnName = "id")
    private Usuario modificadoPor;

    public Localidad() {
    }

    public Localidad(Integer id) {
        this.id = id;
    }

    public Localidad(Integer id, DivisionPolitica divisionesPoliticas, String nombre, String tipoArea, String codigo, String referencia, BigDecimal latitud, BigDecimal longitud, Boolean pasivo, Integer refUnidadSalud, Timestamp creadoEl, Timestamp modificadoEl, Usuario creadoPor, Usuario modificadoPor, String creadoEnIp, String modificadoEnIp) {
        this.id = id;
        //this.zonasLocalidades = zonasLocalidades;
        this.divisionesPoliticas = divisionesPoliticas;
        this.nombre = nombre;
        this.tipoArea = tipoArea;
        this.codigo = codigo;
        this.referencia = referencia;
        this.latitud = latitud;
        this.longitud = longitud;
        this.pasivo = pasivo;
        this.refUnidadSalud = refUnidadSalud;
        this.creadoEl = creadoEl;
        this.modificadoEl = modificadoEl;
        this.creadoPor = creadoPor;
        this.modificadoPor = modificadoPor;
        this.creadoEnIp = creadoEnIp;
        this.modificadoEnIp = modificadoEnIp;
    }
}
