package ni.gob.inss.barista.model.entity.seguridad;

import lombok.Data;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * ENTIDAD PARA SESION MODULO</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/29/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
@Data
@Entity
@Table(name = "sesion_modulo", schema = "seguridad")
//@SequenceGenerator(name = "SESSION_MODULO_SEQ", sequenceName = "seguridad.sesiones_modulos_id_seq", allocationSize = 1)
public class SesionModulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotNull
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;
    @Basic
    @NotNull
    @Column(name = "fecha_ultima_sesion", nullable = false)
    private Timestamp fechaUltimaSesion;
    @ManyToOne
    @JoinColumn(name = "modulo_id", referencedColumnName = "id")
    private Modulo modulosByModuloId;
    @Basic
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @ManyToOne
    @JoinColumn(name = "entidad_id", referencedColumnName = "id")
    private Entidad entidad;
}
