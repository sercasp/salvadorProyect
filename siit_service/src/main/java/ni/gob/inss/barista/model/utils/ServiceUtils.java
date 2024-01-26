package ni.gob.inss.barista.model.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Utiliades para la capa de servicio</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/10/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public class ServiceUtils {
    public static Date now() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Timestamp nowTimeStamp() {
        return new Timestamp(now().getTime());
    }
}