package ni.gob.inss.barista.view.bean.backbean.home;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.SeguridadService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ModuloService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.entity.auditoria.NavegacionUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.view.bean.application.ConfigApplicationBean;
import ni.gob.inss.barista.view.bean.application.ConfigValue;
import ni.gob.inss.barista.view.bean.session.SessionBean;
import ni.gob.inss.barista.view.security.SystemSecurityException;
import ni.gob.inss.barista.view.utils.web.FacesUtils;
import ni.gob.inss.barista.view.utils.web.NavigationResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Representa BackBean para pantalla ListaUsuario
 *
 * @author Juan Evangelista Fletes Garcia
 * @since 30/04/2013
 * Modificado por jvillanueva el 08/08/2023
 */

@Data
@Named
@Scope("view")
public class MenuPrincipalMobileBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(MenuPrincipalBackBean.class);
    final SessionBean sessionBean;
    final ConfigApplicationBean oConfigApplicationBean;
    final SeguridadService oSeguridadService;
    final ModuloService oModuloService;
    private Recurso oRecursoVerificado;
    private String page;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public MenuPrincipalMobileBackBean(@Qualifier("sessionBean") SessionBean sessionBean, ConfigApplicationBean oConfigApplicationBean, SeguridadService oSeguridadService, ModuloService oModuloService) {
        this.sessionBean = sessionBean;
        this.oConfigApplicationBean = oConfigApplicationBean;
        this.oSeguridadService = oSeguridadService;
        this.oModuloService = oModuloService;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    public String goToUrl() throws SystemSecurityException, DAOException {
        String url = this.page;
        //Validando seguridad
        oRecursoVerificado = oSeguridadService.verificarPermiso(sessionBean.getUsuarioActual(), sessionBean.getEntidadActual(), url);
        if (oRecursoVerificado == null) {
            FacesUtils.addErrorMessage("No está autorizado para este recurso");
            sessionBean.setPaginaActual(NavigationResults.BLANK);
        } else {
            // Auditando Navegación
            if ((oConfigApplicationBean.getAuditarNavegacion() == ConfigValue.YES) ||
                    (oConfigApplicationBean.getAuditarNavegacion() == ConfigValue.BY_USER && sessionBean.getUsuarioActual().getAuditarNavegacion())) {
                HttpServletRequest oRequest = FacesUtils.getServletRequest();
                String navegador = oRequest.getHeader("USER-AGENT");
                NavegacionUsuario oNavegacionUsuario = new NavegacionUsuario();
                oNavegacionUsuario.setUsuarioId(sessionBean.getUsuarioActual().getId());
                oNavegacionUsuario.setUrl(url);
                oNavegacionUsuario.setFechaNavegacion(WebUtils.nowTimeStamp());
                oNavegacionUsuario.setIpSesion(WebUtils.getRemoteIp());
                oNavegacionUsuario.setNavegador(navegador);
                oSeguridadService.auditarNavegacion(oNavegacionUsuario);
            }
            sessionBean.setPaginaActual(url);
            FacesContext context = FacesContext.getCurrentInstance();
            String viewId = context.getViewRoot().getViewId();
            ViewHandler handler = context.getApplication().getViewHandler();
            UIViewRoot root = handler.createView(context, viewId);
            root.setViewId(viewId);
            context.setViewRoot(root);
        }
        return NavigationResults.PAGE_MOBILE;
    }

    public Recurso getRecursoVerificado() {
        return oRecursoVerificado;
    }
}