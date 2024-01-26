package ni.gob.inss.siit.view.utils.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Creado por jvillanueva1 on 01/12/2015.
 */
public class calcularEdad {
    //Este es el método calcularEdad que se manda a llamar en el main
    public static Integer calcularEdad(String fecha){
        Date fechaNac=null;
        try {
            /**Se puede cambiar la mascara por el formato de la fecha
             que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
             en este caso es día mes año*/
            fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        } catch (Exception ex) {
            System.out.println("Error:"+ex);
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        //Se crea un objeto con la fecha actual
        Calendar fechaActual = Calendar.getInstance();
        //Se asigna la fecha recibida a la fecha de nacimiento.
        fechaNacimiento.setTime(fechaNac);
        //Se restan la fecha actual y la fecha de nacimiento
        int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
        //Se ajusta el año dependiendo el mes y el día
        if(mes<0 || (mes==0 && dia<0)){
            año--;
        }
        //Regresa la edad en base a la fecha de nacimiento
        return año;
    }
}
