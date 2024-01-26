package ni.gob.inss.barista.model.auditoria;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Anotación para configuración de auditoría</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/11/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Audit {
    public boolean insert() default true;

    public boolean delete() default true;

    public boolean update() default true;

    public String[] only() default "";

    public String[] except() default "";
}