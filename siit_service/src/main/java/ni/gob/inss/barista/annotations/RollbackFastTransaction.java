package ni.gob.inss.barista.annotations;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Crea una transaccion que hace rollback si encuentra
 * cualquier error.
 * Modificado por jvillanueva el 03/08/2023
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(rollbackFor = Throwable.class)
public @interface RollbackFastTransaction {
}