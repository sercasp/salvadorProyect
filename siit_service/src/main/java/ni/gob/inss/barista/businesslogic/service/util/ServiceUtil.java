package ni.gob.inss.barista.businesslogic.service.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public class ServiceUtil {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    public static final long MINUTO_EN_MILES = 60000;
    public static final long MINUTO_EN_SEGUNDOS = 60;

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    static public Date now() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    static public Timestamp nowTimeStamp() {
        return new Timestamp(now().getTime());
    }

    public static String toFormatedDate(Date fecha, String formato) {
        String result;
        DateFormat df = new SimpleDateFormat(formato);
        result = df.format(fecha);
        return result;
    }

    public static long diffMinutes(Timestamp currentTime, Timestamp oldTime) {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();
        long diff = milliseconds2 - milliseconds1;
        //long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        //long diffHours = diff / (60 * 60 * 1000);
        //long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffMinutes;
    }

    public static long diffSeconds(Date currentTime, Date oldTime) {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();
        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        //long diffMinutes = diff / (60 * 1000);
        //long diffHours = diff / (60 * 60 * 1000);
        //long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffSeconds;
    }
}