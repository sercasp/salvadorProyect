package ni.gob.inss.barista.view.bean.application;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * BEAN DE CONFIGURACION</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/27/2014
 * @since 1.0 *
 */
@Named
public class ConfigApplicationBean implements Serializable {
    private static ConfigValue produccion;
    private static ConfigValue servidorReplicacion;
    private static String siglasEntidad;
    private static String nombreEntidad;
    private static String nombreAplicacion;
    private static String descripcionAplicacion;
    private static String temaAplicacion;
    private static String nombreEntorno;
    private static String formatoFecha;
    private static String formatoHora;
    private static String paginatorTemplate;
    private static String currentPageReportTemplate;
    private static String errbitApiKey;
    private static String errbitHost;
    private static String errbitPort;
    private static String projectRoot;
    private static ConfigValue errbitHabilitado;
    private static String mavenModuleCount;
    private static String redmineHost;
    private static String redminePort;
    private static String redmineProject;
    private static String redmineUser;
    private static String redminePassword;
    private static String redmineApiKey;

    private static String helpHost;

    private static int tiempoBloqueoSesion;
    private static int intentosBloqueoSesion;

    private static ConfigValue auditarInicioSesion;
    private static ConfigValue auditarNavegacion;

    private URL urlResource;
    private Properties props;


    @PostConstruct
    public void init() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        urlResource = classLoader.getResource("config.properties");
        props = new Properties();
        props.load(urlResource.openStream());
        produccion = getConfigValueEnum("general.produccion");
        servidorReplicacion = getConfigValueEnum("general.servidorReplicacion");
        siglasEntidad = getConfigValueString("general.siglasEntidad");
        nombreEntidad = getConfigValueString("general.nombreEntidad");
        nombreAplicacion = getConfigValueString("general.nombreAplicacion");
        descripcionAplicacion = getConfigValueString("general.descripcionAplicacion");
        temaAplicacion = getConfigValueString("general.temaAplicacion");
        nombreEntorno = getConfigValueString("general.nombreEntorno");
        formatoFecha = getConfigValueString("general.formatoFecha");
        formatoHora = getConfigValueString("general.formatoHora");
        paginatorTemplate = getConfigValueString("general.paginatorTemplate");
        currentPageReportTemplate = getConfigValueString("general.currentPageReportTemplate");
        temaAplicacion = getConfigValueString("general.temaAplicacion");

        errbitApiKey = getConfigValueString("errbit.ApiKey");
        errbitHost = getConfigValueString("errbit.Host");
        errbitPort = getConfigValueString("errbit.Port");
        projectRoot = getConfigValueString("errbit.projectRoot");
        errbitHabilitado = getConfigValueEnum("errbit.Habilitado");
        mavenModuleCount = getConfigValueString("errbit.MavenModuleCount");

        redmineHost = getConfigValueString("redmine.Host");
        redminePort = getConfigValueString("redmine.Port");
        redmineProject = getConfigValueString("redmine.Project");
        redmineUser = getConfigValueString("redmine.User");
        redminePassword = getConfigValueString("redmine.Password");
        redmineApiKey = getConfigValueString("redmine.ApiKey");

        helpHost = getConfigValueString("help.Host");

        tiempoBloqueoSesion= Integer.parseInt(getConfigValueString("seguridad.tiempoBloqueoSesion"));
        intentosBloqueoSesion= Integer.parseInt(getConfigValueString("seguridad.intentosBloqueoSesion"));

        auditarInicioSesion = getConfigValueEnum("auditoria.auditarInicioSesion");
        auditarNavegacion = getConfigValueEnum("auditoria.auditarNavegacion");
    }

    private ConfigValue getConfigValueEnum(String property) {
        String propertyValue = props.getProperty(property);
        if(propertyValue.trim().toUpperCase().equals("YES")){
            return ConfigValue.YES;
        }
        else if(propertyValue.trim().toUpperCase().equals("NO")){
            return ConfigValue.NO;
        }
        else if(propertyValue.trim().toUpperCase().equals("BY_USER")){
            return ConfigValue.BY_USER;
        }
        else{
            return ConfigValue.NO;
        }
    }

    private String getConfigValueString(String property) {
        return props.getProperty(property);
    }

    public int getTiempoBloqueoSesion() {
        return tiempoBloqueoSesion;
    }

    public int getIntentosBloqueoSesion() {
        return intentosBloqueoSesion;
    }

    public ConfigValue getAuditarNavegacion() {
        return auditarNavegacion;
    }

    public ConfigValue getAuditarInicioSesion() {
        return auditarInicioSesion;
    }

    public boolean isErrbitHabilitado() {
        return errbitHabilitado.getValue();
    }

    public String getHelpHost() {
        return helpHost;
    }

    public String getRedmineProject() {
        return redmineProject;
    }

    public String getRedmineUser() {
        return redmineUser;
    }

    public String getRedminePassword() {
        return redminePassword;
    }

    public String getRedmineApiKey() {
        return redmineApiKey;
    }

    public String getRedminePort() {
        return redminePort;
    }

    public String getRedmineHost() {
        return redmineHost;
    }

    public String getErrbitPort() {
        return errbitPort;
    }

    public String getErrbitHost() {
        return errbitHost;
    }

    public String getErrbitApiKey() {
        return errbitApiKey;
    }

    public String getErrbitHostPort(){
        return errbitHost + ":" + errbitPort;
    }

    public String getProjectRoot() {
        return projectRoot;
    }

    public static String getMavenModuleCount() {
        mavenModuleCount =  mavenModuleCount==null ? "2" : mavenModuleCount;
        return mavenModuleCount;
    }

    public boolean isServidorReplicacion() {
        return servidorReplicacion.getValue();
    }

    public String getCurrentPageReportTemplate() {
        return currentPageReportTemplate;
    }

    public String getPaginatorTemplate() {
        return paginatorTemplate;
    }

    public String getFormatoFecha() {
        return formatoFecha;
    }

    public String getFormatoHora() {
        return formatoHora;
    }

    public boolean isProduccion() {
        return produccion.getValue();
    }

    public String getNombreEntorno() {
        return nombreEntorno;
    }

    public String getNombreAplicacion() {
        return nombreAplicacion;
    }

    public String getTemaAplicacion() {
        return temaAplicacion;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public String getAplicacionNombreEntidad() {
        return  siglasEntidad + " - " + nombreAplicacion;
    }

    public String getSiglasEntidad() {
        return siglasEntidad;
    }

    public String getDescripcionAplicacion() {
        return descripcionAplicacion;
    }

    public String dateToString(Date oDate){
        if(oDate==null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat(formatoFecha);
        return  dateFormat.format(oDate);
    }

    public String dateToTime(Date oDate){
        if(oDate==null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat(formatoHora);
        return  dateFormat.format(oDate);
    }

    public String dateToDateTime(Date oDate){
        if(oDate==null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat(formatoFecha + " " + formatoHora);
        return  dateFormat.format(oDate);
    }

    public String dateToDateTimeWithSeconds(Date oDate){
        if(oDate==null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat(formatoFecha + " " + formatoHora +":ss");
        return  dateFormat.format(oDate);
    }
}