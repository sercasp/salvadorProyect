package ni.gob.inss.barista.model.auditoria;

import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Utiliadades para auditoría</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/11/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Component
public class AuditUtils {
    private String nombreSistema = "";

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @PostConstruct
    private void init() throws IOException {
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL urlResource = classLoader.getResource("config.properties");

        if (urlResource != null) {
            props.load(urlResource.openStream());
            nombreSistema = props.getProperty("general.nombreAplicacion");
        }
    }

    public Usuario obtenerUsuarioActual() {
        HttpServletRequest oRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return (Usuario) oRequest.getSession().getAttribute("usuarioActual");
    }

    public String obtenerNombreSistema() {
        return nombreSistema;
    }
}