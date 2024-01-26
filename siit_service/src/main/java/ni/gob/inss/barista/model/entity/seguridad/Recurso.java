package ni.gob.inss.barista.model.entity.seguridad;

import lombok.Data;
import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.entity.personal.Miembro;
import ni.gob.inss.barista.model.utils.RegExpresion;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * ENTIDAD PARA RECURSOS</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 6/03/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
@Data
@Entity
@Table(name = "recurso", schema = "seguridad")
//@SequenceGenerator(name = "recursos_SEQ", sequenceName = "seguridad.recursos_id_seq", allocationSize = 1)
public class Recurso implements AuditableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 100)
    @Pattern(regexp = RegExpresion.regExpNombre)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 1000)
    @Column(name = "url", nullable = false, length = 1000)
    private String url;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 1000)
    @Column(name = "codigo", length = 50)
    private String codigo;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 1)
    @Pattern(regexp = RegExpresion.regExpSoloLetras)
    @Column(name = "tipo", nullable = false, length = 1)
    private String tipo;
    @Basic
    @NotNull
    @Column(name = "auditable", nullable = false)
    private Boolean auditable;
    @Basic
    @Column(name = "icono_css", length = 50)
    private String iconoCss;
    @Basic
    @Column(name = "texto_ayuda", length = 500)
    private String textoAyuda;
    @Basic
    @NotNull
    @Column(name = "pasivo", nullable = false)
    private Boolean pasivo;
    @Basic
    @Column(name = "url_ayuda", length = 1000)
    private String urlAyuda;
    @Basic
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "miembro_id", referencedColumnName = "id")
    private Miembro miembrosByRecursoId;
    @OneToMany(mappedBy = "recursosByRecursoId")
    private Collection<Menu> menusesById;
    @Transient
    private Integer miembroId;
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
