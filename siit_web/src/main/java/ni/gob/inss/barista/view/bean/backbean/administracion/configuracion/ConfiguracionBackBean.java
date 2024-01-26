package ni.gob.inss.barista.view.bean.backbean.administracion.configuracion;

import ni.gob.inss.barista.view.bean.application.ConfigApplicationBean;
import ni.gob.inss.barista.view.bean.application.ConfigValue;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 19/06/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Named
@Scope("view")
public class ConfiguracionBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private static String auditarInicioSesion;
    private static String auditarNavegacion;
    private String produccion;
    private String servidorReplicacionText;
    private String siglasEntidad;
    private String nombreEntidad;
    private String nombreAplicacion;
    private String descripcionAplicacion;
    private String temaAplicacion;
    private String nombreEntorno;
    private String formatoFecha;
    private String formatoHora;
    private String errbitApiKey;
    private String errbitHost;
    private String projectRoot;
    private String errbitHabilitado;
    private String redmineHost;
    private String redminePort;
    private String redmineProject;
    private String helpHost;
    private String tiempoBloqueoSesion;
    private String intentosBloqueoSesion;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */

    @Autowired
    ConfigApplicationBean oConfigApplicationBean;

    @PostConstruct
    public void init() {
        produccion = oConfigApplicationBean.isProduccion() ? "SI" : "NO";
        servidorReplicacionText = oConfigApplicationBean.isServidorReplicacion() ? "SI" : "NO";
        siglasEntidad = oConfigApplicationBean.getSiglasEntidad();
        nombreEntidad = oConfigApplicationBean.getNombreEntidad();
        nombreAplicacion = oConfigApplicationBean.getNombreAplicacion();
        descripcionAplicacion = oConfigApplicationBean.getDescripcionAplicacion();
        temaAplicacion = oConfigApplicationBean.getTemaAplicacion();
        nombreEntorno = oConfigApplicationBean.getNombreEntorno();
        formatoFecha = oConfigApplicationBean.getFormatoFecha();
        formatoHora = oConfigApplicationBean.getFormatoHora();
        errbitApiKey = oConfigApplicationBean.getErrbitApiKey();
        errbitHost = oConfigApplicationBean.getErrbitHostPort();
        projectRoot = oConfigApplicationBean.getProjectRoot();
        errbitHabilitado = oConfigApplicationBean.isErrbitHabilitado() ? "SI" : "NO";
        redmineHost = oConfigApplicationBean.getRedmineHost();
        redminePort = oConfigApplicationBean.getRedminePort();
        redmineProject = oConfigApplicationBean.getRedmineProject();
        helpHost = oConfigApplicationBean.getHelpHost();
        tiempoBloqueoSesion = String.valueOf(oConfigApplicationBean.getTiempoBloqueoSesion());
        intentosBloqueoSesion = String.valueOf(oConfigApplicationBean.getIntentosBloqueoSesion());
        auditarInicioSesion = ConfigValue.toString(oConfigApplicationBean.getAuditarInicioSesion());
        auditarNavegacion = ConfigValue.toString(oConfigApplicationBean.getAuditarNavegacion());
    }

    /**
     * ***********************************************************************************
     * PROPIEDADES
     * ************************************************************************************
     */
    public String getProduccion() {
        return produccion;
    }

    public String getServidorReplicacionText() {
        return servidorReplicacionText;
    }

    public String getSiglasEntidad() {
        return siglasEntidad;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public String getNombreAplicacion() {
        return nombreAplicacion;
    }

    public String getDescripcionAplicacion() {
        return descripcionAplicacion;
    }

    public String getTemaAplicacion() {
        return temaAplicacion;
    }

    public String getNombreEntorno() {
        return nombreEntorno;
    }

    public String getFormatoFecha() {
        return formatoFecha;
    }

    public String getFormatoHora() {
        return formatoHora;
    }

    public String getErrbitApiKey() {
        return errbitApiKey;
    }

    public String getErrbitHost() {
        return errbitHost;
    }

    public String getProjectRoot() {
        return projectRoot;
    }

    public String getErrbitHabilitado() {
        return errbitHabilitado;
    }

    public String getRedmineHost() {
        return redmineHost;
    }

    public String getRedminePort() {
        return redminePort;
    }

    public String getRedmineProject() {
        return redmineProject;
    }

    public String getHelpHost() {
        return helpHost;
    }

    public String getTiempoBloqueoSesion() {
        return tiempoBloqueoSesion;
    }

    public String getIntentosBloqueoSesion() {
        return intentosBloqueoSesion;
    }

    public String getAuditarInicioSesion() {
        return auditarInicioSesion;
    }

    public String getAuditarNavegacion() {
        return auditarNavegacion;
    }
}