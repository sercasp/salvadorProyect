package ni.gob.inss.barista.model.entity.seguridad;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * ENTIDAD PARA PARAMETROS USUARIO</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/29/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 21/08/2023
 */
@Data
@Entity
@Table(name = "parametro_usuario", schema = "seguridad")
//@SequenceGenerator(name = "parametros_usuarios_SEQ", sequenceName = "seguridad.parametros_usuarios_id_seq", allocationSize = 1)
public class ParametroUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "parametro_id", referencedColumnName = "id")
    private Parametro parametroIdByParametroId;
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuarioIdByUsuarioId;
    @Basic
    @NotNull
    @NotEmpty
    @Column(name = "valor", nullable = false, length = 100)
    private String valor;
}