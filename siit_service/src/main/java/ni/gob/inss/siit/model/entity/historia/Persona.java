package ni.gob.inss.siit.model.entity.historia;

import lombok.*;
import ni.gob.inss.barista.model.auditoria.Audit;
import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.DivisionPolitica;
import ni.gob.inss.barista.model.entity.catalogo.Localidad;
import ni.gob.inss.barista.model.entity.catalogo.Pais;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Creado por jvillanueva1 on 01/12/2015.
 * Modificado por jvillanueva el 11/05/2021.
 */
@Data
@Audit(except = {"creadoPor", "modificadoPor", "creadoEl", "modificadoEl", "creadoEnIp", "modificadoEnIp", "creadoEnOrdenador", "modificadoEnOrdenador"})
@Entity
@Table(name = "persona", schema = "historia")
@SequenceGenerator(name = "persona_seq", sequenceName = "historia.persona_id_seq", allocationSize = 1)
public class Persona implements Serializable, AuditableEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_pais_nacimiento", referencedColumnName = "codigo_alfa2")
    private Pais paisNacimientoByPaisNacimientoCodigoAlfa2;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_municipio_nacimiento", referencedColumnName = "codigo")
    private DivisionPolitica municipioNacimientoByMunicipioNacimientoCodigo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_etnia", referencedColumnName = "codigo")
    private Catalogo etniaByEtniaCodigo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_estado_civil", referencedColumnName = "codigo")
    private Catalogo estadoCivilByEstadoCivilCodigo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_pais_domicilio", referencedColumnName = "codigo_alfa2")
    private Pais paisDomicilioByPaisDomicilioCodigoAlfa2;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_municipio_domicilio", referencedColumnName = "codigo")
    private DivisionPolitica municipioDomicilioByMunicipioDomicilioCodigo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localidad_domicilio_id", referencedColumnName = "id")
    private Localidad localidadDomicilioByLocalidadDomicilioId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_escolaridad", referencedColumnName = "codigo")
    private Catalogo escolaridadByEscolaridadCodigo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_grupo_sanguineo", referencedColumnName = "codigo")
    private Catalogo grupoSanguineoByGrupoSanguineo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_profesion", referencedColumnName = "codigo")
    private Catalogo refProfesion;
    @Basic
    @Column(name = "cargo")
    private String cargo;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_seq")
    private int id;

    @Basic
    @Column(name = "primer_apellido")
    private String primerApellido;

    @Basic
    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Basic
    @Column(name = "primer_nombre")
    private String primerNombre;

    @Basic
    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Basic
    @Column(name = "sexo")
    private String sexo;

    @Basic
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Basic
    @Column(name = "dni")
    private String dni;

    @Basic
    @Column(name = "direccion")
    private String direccion;

    @Basic
    @Column(name = "telefono")
    private String telefono;

    @Basic
    @Column(name = "movil")
    private String movil;

    @Basic
    @Column(name = "correo")
    private String correo;

    @Basic
    @Column(name = "apartado_postal")
    private String apartadoPostal;

    @Basic
    @Column(name = "factor_rh")
    private String factorRh;

    @Basic
    @Column(name = "observacion")
    private String observacion;

    @Basic
    @Column(name = "verificado")
    private Boolean verificado;

    @Basic
    @Column(name = "fallecido")
    private boolean fallecido;

    @Basic
    @Column(name = "fecha_fallecido")
    private Date fechaFallecido;

    @Basic
    @Column(name = "creado_el")
    private Timestamp creadoEl;

    @Basic
    @Column(name = "modificado_el")
    private Timestamp modificadoEl;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "creado_por", referencedColumnName = "id")
    private Usuario creadoPor;
    @ManyToOne
    @JoinColumn(name = "modificado_por", referencedColumnName = "id")
    private Usuario modificadoPor;

    @Basic
    @Column(name = "creado_en_ip")
    private String creadoEnIp;

    @Basic
    @Column(name = "modificado_en_ip")
    private String modificadoEnIp;

    @Basic
    @Column(name = "direccion_actual")
    private String direccionActual;

    @Basic
    @Column(name = "numero_pasaporte")
    private String numeroPasaporte;


    @Basic
    @Column(name = "institucion")
    private String institucion;

    @Basic
    @NotNull
    @Column(name = "creado_en_ordenador", length = 100)
    private String creadoEnOrdenador;
    @Basic
    @Column(name = "modificado_en_ordenador", length = 100)
    private String modificadoEnOrdenador;

    @Transient
    @Override
    public String getFilaId() {
        return Long.toString(getId());
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", primerNombre='" + primerNombre + '\'' +
                ", segundoNombre='" + segundoNombre + '\'' +
                ", sexo='" + sexo + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}
