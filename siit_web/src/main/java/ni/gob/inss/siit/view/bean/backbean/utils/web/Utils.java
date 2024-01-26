package ni.gob.inss.siit.view.bean.backbean.utils.web;

import com.sun.faces.component.visit.FullVisitContext;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fespinoza on 07/02/2015.
 */
public class Utils extends BaseBackBean {

    private final String LETRAS = "ABCDEFGHJKLMNPQRSTUVWXY";
    private Boolean verificado = null;

    private String cedula = null;

    /**
     * Para Pruebas
     *
     * @param args
     */
    public void main(String args) {
        boolean gui = false;

        Utils cedula = new Utils();

        if (args.equals("")) {
            cedula.setCedula(JOptionPane.showInputDialog("Indique la cédula a validar"));
            gui = true;
        } else
            cedula.setCedula(args);

        if (cedula.isCedulaValida()) {
            verificado = true;
        } else {
            verificado = false;
        }
    }

    /**
     * Retorna true si la cédula es válida
     * false en caso contrario
     *
     * @return
     */
    public boolean isCedulaValida() {
        if (!isPrefijoValido())
            return false;

        if (!isFechaValida())
            return false;

        if (!isSufijoValido())
            return false;

        if (!isLetraValida())
            return false;

        return true;
    }

    /**
     * Retorna true si el prefijo de la cédula es válida
     * false en caso contrario
     *
     * @return
     */
    public boolean isPrefijoValido() {
        String prefijo = this.getPrefijoCedula();

        if (prefijo == null) return false;

        Pattern p = Pattern.compile("^[0-9]{3}$");
        Matcher m = p.matcher(prefijo);
        if (!m.find())
            return false;

        return true;
    }

    /**
     * Retorna true si la fecha de la cédula es válida
     * false en caso contrario
     *
     * @return
     */
    public boolean isFechaValida() {
        String fecha = this.getFechaCedula();

        if (fecha == null) return false;

        Pattern p = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])([0-9]{2})$");
        Matcher m = p.matcher(fecha);
        if (!m.find())
            return false;

        // verificar si la fecha existe en el calendario
        try {
            DateFormat df = new SimpleDateFormat("ddMMyy");
            if (!fecha.equals(df.format(df.parse(fecha)))) {
                return false;
            }
        } catch (ParseException ex) {
            return false;
        }

        return true;
    }

    /**
     * Retorna true si el sufijo de la cédula es válida
     * false en caso contrario
     *
     * @return
     */
    public boolean isSufijoValido() {
        String sufijo = this.getSufijoCedula();

        if (sufijo == null) return false;

        Pattern p = Pattern.compile("^[0-9]{4}[A-Y]$");
        Matcher m = p.matcher(sufijo);
        if (!m.find())
            return false;

        return true;
    }

    /**
     * Retorna true si la letra de la cédula es válida
     * false en caso contrario
     *
     * @return
     */
    public boolean isLetraValida() {
        String letraValida = null;
        String letra = this.getLetraCedula();

        if (letra == null) return false;

        letraValida = String.valueOf(this.calcularLetra());

        return letraValida.equals(letra);
    }

    /**
     * Retorna un entero que representa la posición en la cadena LETRAS
     * de la letra final de una cédula
     *
     * @return
     */
    public int getPosicionLetra() {
        int posicionLetra = 0;
        String cedulaSinLetra = this.getCedulaSinLetra();
        long numeroSinLetra = 0;

        if (cedulaSinLetra == null) return -1;

        try {
            numeroSinLetra = Long.parseLong(cedulaSinLetra);
        } catch (NumberFormatException e) {
            return -1;
        }

        posicionLetra = (int) (numeroSinLetra - Math.floor((double) numeroSinLetra / 23.0) * 23);

        return posicionLetra;
    }

    /**
     * * Calcular la letra al final de la cédula nicaraguense.
     * <p>
     * <p>
     * Basado en el trabajo de: Arnoldo Suarez Bonilla - arsubo@yahoo.es
     * <p>
     * <p>
     * http://espanol.groups.yahoo.com/group/ptic-nic/message/873
     * Mie, 6 de Feb, 2008 2:39 pm
     * <p>
     * <p>
     * Es correcto, los ultimos 4 numeros de la cédula son un consecutivo de  las personas que nacen en la misma fecha y municipio.
     * La letra se calcula con el siguiente algoritmo (yo se los envío en SQL).
     * <p>
     * <p>
     * <p>
     * <p>
     * declare  @cedula      varchar(20),
     *
     * @return Letra válida de cédula dada
     * @val numeric(13, 0),
     * @letra char(1),
     * @Letras varchar(20)
     * <p>
     * select @Letras = 'ABCDEFGHJKLMNPQRSTUVWXY'
     * select @cedula = '0012510750012' --PARTE NUMERICA DE LA CEDULA SIN GUIONES
     * --CALCULO DE LA LETRA DE LA CEDULA
     * select @val = convert(numeric(13, 0), @cedula) - floor(convert(numeric(13, 0), @cedula) / 23) * 23
     * select @letra = SUBSTRING(@Letras, @val + 1, 1)
     * select @letra
     */
    public char calcularLetra() {
        int posicionLetra = getPosicionLetra();

        if (posicionLetra < 0)
            return '?';

        return LETRAS.charAt(posicionLetra);
    }

    /**
     * Fiajr el Número de Cédula
     *
     * @param cedula Número de Cédula con o sin guiones
     */
    public void setCedula(String cedula) {
        cedula = cedula.trim().replaceAll("-", "");

        if (cedula == null || cedula.length() != 14)
            this.cedula = null;
        else
            this.cedula = cedula.trim().replaceAll("-", "").toUpperCase();
    }

    /**
     * Retorna el Número de Cédula
     *
     * @return
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Retorna el prefijo de la cédula
     *
     * @return
     */
    public String getPrefijoCedula() {
        if (cedula != null)
            return cedula.substring(0, 3);
        else
            return null;
    }

    /**
     * Retorna la fecha en la cédula
     *
     * @return
     */
    public String getFechaCedula() {
        if (cedula != null)
            return cedula.substring(3, 9);
        else
            return null;
    }

    /**
     * Retorna el sufijo en la cédula
     *
     * @return
     */
    public String getSufijoCedula() {
        if (cedula != null)
            return cedula.substring(9, 14);
        else
            return null;
    }

    /**
     * Retorna la letra de la cédula
     *
     * @return
     */
    public String getLetraCedula() {
        if (cedula != null)
            return cedula.substring(13, 14);
        else
            return null;
    }

    /**
     * Retorna la cédula sin la letra de validación
     *
     * @return
     */
    public String getCedulaSinLetra() {
        if (cedula != null)
            return cedula.substring(0, 13);
        else
            return null;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    /**
     * Retorna el objeto de un control en la vista
     *
     * @return
     */
    public static UIComponent buscarComponente(String id) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];
        root.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });
        return found[0];
    }
}

