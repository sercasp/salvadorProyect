package ni.gob.inss.barista.model.entity.seguridad;

import lombok.Getter;
import lombok.Setter;
import ni.gob.inss.barista.model.auditoria.Audit;
import ni.gob.inss.barista.model.entity.AuditableEntity;
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
 * ENTIDAD PARA USUARIOS</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/29/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
@Getter
@Setter
@Audit(except = {"creadoPor", "modificadoPor", "creadoEl", "modificadoEl", "creadoEnIp", "modificadoEnIp", "password",
        "cantidadInicioSesion", "sesionActual", "ipActual", "passwordTemporal", "intentosFallidos", "fechaBloqueo"})
@Entity
//@SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "seguridad.users_id_seq", allocationSize = 1)
@Table(name = "usuario", schema = "seguridad")
public class Usuario implements AuditableEntity, Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = RegExpresion.regExpSoloLetras)
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 100)
    @Pattern(regexp = RegExpresion.regExpNombre)
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    @Basic
    @NotEmpty
    @NotNull
    @Size(max = 100)
    @Pattern(regexp = RegExpresion.regExpNombre)
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    @Basic
    @NotNull
    @NotEmpty
    @Size(max = 100)
    @Column(name = "email", nullable = false)
    private String email;
    @Basic
    @NotNull
    @NotEmpty
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;
    @Basic
    @Column(name = "cantidad_inicio_sesion")
    private Integer cantidadInicioSesion;
    @Basic
    @Column(name = "sesion_actual")
    private Timestamp sesionActual;
    @Basic
    @Column(name = "ip_actual")
    private String ipActual;
    @Basic
    @Pattern(regexp = RegExpresion.regExpDescripcion)
    @Column(name = "referencia", length = 200)
    private String referencia;
    @Basic
    @NotNull
    @Column(name = "ver_info_pantalla", nullable = false)
    private Boolean verInfoPantalla;
    @Basic
    @NotNull
    @Column(name = "ver_auditoria", nullable = false)
    private Boolean verAuditoria;
    @Basic
    @NotNull
    @Column(name = "ver_info_errores", nullable = false)
    private Boolean verInfoErrores;
    @Basic
    @NotNull
    @Column(name = "super_administrador", nullable = false)
    private Boolean superAdministrador;
    @Basic
    @NotNull
    @Column(name = "auditar_inicio_sesion", nullable = false)
    private Boolean auditarInicioSesion;
    @Basic
    @NotNull
    @Column(name = "auditar_navegacion", nullable = false)
    private Boolean auditarNavegacion;
    @Basic
    @NotNull
    @Column(name = "password_temporal", nullable = false)
    private Boolean passwordTemporal;
    @Basic
    @NotNull
    @Column(name = "intentos_fallidos", nullable = false)
    private Integer intentosFallidos;
    @Basic
    @Column(name = "fecha_bloqueo")
    private Timestamp fechaBloqueo;
    @Basic
    @NotNull
    @Column(name = "pasivo", nullable = false)
    private Boolean pasivo;
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
    @Basic
    @Column(name = "telefono")
    @Size(max = 8)
    private String telefono;
    @Basic
    @Column(name = "token")
    @Size(max = 6)
    private String token;

//    @OneToMany(mappedBy = "usuario")
//    private Set<AuditTrail> auditTrail = new HashSet<AuditTrail>(0);

    public String obtenerNombreCompleto() {
        return (nombres + " " + apellidos);
    }

    public String obtenerUsernameNombreCompleto() {
        return ("(" + username + ") " + obtenerNombreCompleto());
    }

    @Transient
    @Override
    public String getFilaId() {
        return Long.toString(getId());
    }

}