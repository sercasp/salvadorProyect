package ni.gob.inss.barista.model.entity.catalogo;

import lombok.Data;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(
        name = "division_politica",
        schema = "catalogo",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"codigo"}
        ), @UniqueConstraint(
                columnNames = {"codigo_iso"}
        )}
)
public class DivisionPolitica implements Serializable {
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
            name = "division_politica_id"
    )
    private DivisionPolitica divisionesPoliticas;

    @Column(
            name = "nombre",
            length = 50
    )
    private String nombre;

    @Column(
            name = "codigo",
            unique = true,
            length = 10
    )
    private String codigo;

    @Column(
            name = "area",
            precision = 10
    )
    private BigDecimal area;

    @Column(
            name = "cabecera"
    )
    private Boolean cabecera;

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
            name = "altitud"
    )
    private Integer altitud;

    @Column(
            name = "codigo_iso",
            unique = true,
            length = 3
    )
    private String codigoIso;
    @Column(
            name = "codigo_alterno",
            length = 10
    )
    private String codigoAlterno;
    @Column(
            name = "pasivo"
    )
    private Boolean pasivo;
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
//    @OneToMany(
//            fetch = FetchType.LAZY,
//            mappedBy = "divisionesPoliticas"
//    )
//    private Set<DivisionPolitica> divisionesPoliticases = new HashSet(0);
    @NotNull
    @ManyToOne
    @JoinColumn(name = "creado_por", referencedColumnName = "id")
    private Usuario creadoPor;
    @ManyToOne
    @JoinColumn(name = "modificado_por", referencedColumnName = "id")
    private Usuario modificadoPor;

    public DivisionPolitica() {
    }

    public DivisionPolitica(Integer id) {
        this.id = id;
    }
    public DivisionPolitica(Integer id, DivisionPolitica divisionesPoliticas, String nombre, String codigo, BigDecimal area, Boolean cabecera, BigDecimal latitud, BigDecimal longitud, Integer altitud, String codigoIso, String codigoAlterno, Boolean pasivo, Timestamp creadoEl, Timestamp modificadoEl, Usuario creadoPor, Usuario modificadoPor, String creadoEnIp, String modificadoEnIp) {
        this.id = id;
        this.divisionesPoliticas = divisionesPoliticas;
        this.nombre = nombre;
        this.codigo = codigo;
        this.area = area;
        this.cabecera = cabecera;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.codigoIso = codigoIso;
        this.codigoAlterno = codigoAlterno;
        this.pasivo = pasivo;
        this.creadoEl = creadoEl;
        this.modificadoEl = modificadoEl;
        this.creadoPor = creadoPor;
        this.modificadoPor = modificadoPor;
        this.creadoEnIp = creadoEnIp;
        this.modificadoEnIp = modificadoEnIp;
//        this.divisionesPoliticases = divisionesPoliticases;
    }
}