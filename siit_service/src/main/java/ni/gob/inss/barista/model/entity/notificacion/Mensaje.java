package ni.gob.inss.barista.model.entity.notificacion;

import ni.gob.inss.barista.model.utils.RegExpresion;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Entidad para mensajes</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/07/2014
 * @since 1.0 *
 */
@Entity
@Table(name = "mensaje", schema = "notificacion")
//@SequenceGenerator(name = "Mensaje_SEQ", sequenceName = "notificacion.\"Mensaje_id_seq\"", allocationSize = 1)
public class Mensaje {
    private int id;
    private String titulo;
    private String mensaje;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Mensaje_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @NotEmpty
    @NotNull
    @Pattern(regexp= RegExpresion.regExpDescripcion)
    @Column(name = "titulo", nullable = false, insertable = true, updatable = true, length = 200)
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String descriptor) {
        this.titulo = descriptor;
    }

    @Basic
    @NotEmpty
    @NotNull
    @Pattern(regexp= RegExpresion.regExpDescripcion)
    @Column(name = "mensaje", nullable = false, insertable = true, updatable = true, length = 1000)
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mensaje mensaje1 = (Mensaje) o;

        if (id != mensaje1.id) return false;
        if (titulo != null ? !titulo.equals(mensaje1.titulo) : mensaje1.titulo != null) return false;
        return mensaje != null ? mensaje.equals(mensaje1.mensaje) : mensaje1.mensaje == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (mensaje != null ? mensaje.hashCode() : 0);
        return result;
    }
}
