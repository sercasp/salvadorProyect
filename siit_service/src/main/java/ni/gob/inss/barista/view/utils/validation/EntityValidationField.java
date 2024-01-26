package ni.gob.inss.barista.view.utils.validation;

import javax.validation.constraints.Pattern;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Utilidad para obtener validaciónes de una entidad</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/27/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
public class EntityValidationField {
    public static String getRegExpresionPattern(Class type, String field) throws NoSuchMethodException {
        String regExpresion = "";
        Pattern oPattern = (Pattern) type.getMethod("get" + field).getAnnotation(Pattern.class);
        regExpresion = oPattern.regexp();
        return regExpresion;
    }
}