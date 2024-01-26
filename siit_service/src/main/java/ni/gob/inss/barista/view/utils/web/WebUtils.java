package ni.gob.inss.barista.view.utils.web;

import ni.gob.inss.barista.view.bean.session.SessionBean;
import org.apache.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


/**
 * Utilidades para el sistema.
 *
 * @author Juan Evangelista Fletes Garcia
 * @since 12/14/2013
 * Modificado por jvillanueva el 08/08/2023
 */
public class WebUtils {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static Logger logger = Logger.getLogger(WebUtils.class);

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private WebUtils() {
    }

    public static String getRemoteIp() {
        String strRemoteIp = "";
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            SessionBean oSessionBean = (SessionBean) request.getSession().getAttribute("sessionBean");
            if (oSessionBean.getIp() != null) {
                strRemoteIp = oSessionBean.getIp();
            } else {
                strRemoteIp = InetAddress.getByName(request.getRemoteAddr()).getHostAddress();
            }
            //strRemoteIp = ip.getHostAddress();
        } catch (Exception e) {
            logger.error(e);
        }
        return strRemoteIp;
    }

    public static String getRemoteHostName() {
        String strRemoteHostName = "";
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            SessionBean oSessionBean = (SessionBean) request.getSession().getAttribute("sessionBean");
            if (oSessionBean.getIp() != null) {
                InetAddress inetAddress = InetAddress.getByName(oSessionBean.getIp());
                strRemoteHostName = inetAddress.getCanonicalHostName();
            } else {
                InetAddress inetAddress = InetAddress.getByName(request.getRemoteAddr());
                strRemoteHostName = inetAddress.getHostName();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return strRemoteHostName;
    }

    public static String getRemoteHost() {
        String strRemoteHostName = "";
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            SessionBean oSessionBean = (SessionBean) request.getSession().getAttribute("sessionBean");
            if (oSessionBean.getIp() != null) {
                InetAddress inetAddress = InetAddress.getByName(oSessionBean.getIp());
                strRemoteHostName = inetAddress.getHostName();
            } else {
                InetAddress inetAddress = InetAddress.getByName(request.getRemoteAddr());
                strRemoteHostName = inetAddress.getHostName();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return strRemoteHostName;
    }

    public static Date now() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Timestamp nowTimeStamp() {
        return new Timestamp(now().getTime());
    }
}