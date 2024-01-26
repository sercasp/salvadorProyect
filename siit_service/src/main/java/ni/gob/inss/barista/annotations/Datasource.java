package ni.gob.inss.barista.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jjrivera on 4/27/2017.
 * Modificado por jvillanueva el 03/08/2023
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Datasource {

    public String nombre() default "sessionFactory";

}
