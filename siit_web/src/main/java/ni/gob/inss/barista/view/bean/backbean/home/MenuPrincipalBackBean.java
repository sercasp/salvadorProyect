package ni.gob.inss.barista.view.bean.backbean.home;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.NavegacionService;
import ni.gob.inss.barista.businesslogic.service.core.SeguridadService;
import ni.gob.inss.barista.businesslogic.service.seguridad.MenuService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.entity.auditoria.NavegacionUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.view.bean.application.ConfigApplicationBean;
import ni.gob.inss.barista.view.bean.application.ConfigValue;
import ni.gob.inss.barista.view.bean.session.SessionBean;
import ni.gob.inss.barista.view.errorHandler.ErrBitNotice;
import ni.gob.inss.barista.view.utils.web.FacesUtils;
import ni.gob.inss.barista.view.utils.web.NavigationResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.menu.MenuModel;
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
import java.util.Map;


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
public class MenuPrincipalBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(MenuPrincipalBackBean.class);

    final SessionBean sessionBean;
    MenuModel menuPrincipal;
    String breadcrumMenu;
    ConfigApplicationBean oConfigApplicationBean;
    SeguridadService oSeguridadService;
    ErrBitNotice oErrBitNotice;
    MenuService oMenuService;
    private MenuModel model;
    private Recurso oRecursoVerificado;
    private NavegacionService oNavegacionService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public MenuPrincipalBackBean(@Qualifier("sessionBean") SessionBean sessionBean, NavegacionService oNavegacionService, ConfigApplicationBean oConfigApplicationBean, SeguridadService oSeguridadService, ErrBitNotice oErrBitNotice, MenuService oMenuService) {
        this.sessionBean = sessionBean;
        this.oNavegacionService = oNavegacionService;
        this.oConfigApplicationBean = oConfigApplicationBean;
        this.oSeguridadService = oSeguridadService;
        this.oErrBitNotice = oErrBitNotice;
        this.oMenuService = oMenuService;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public void goToUrlNew() throws DAOException {
        Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer urlId = Integer.parseInt(requestParamMap.get("url"));

        String url = oMenuService.obtenerMenuPorId(urlId).getRecursosByRecursoId().getUrl();
        //String url = oMenuService.obtenerMenuPorId2(urlId).getRecursosByRecursoId().getUrl();
        String breadcrum = oMenuService.obtenerBreadcrum(urlId);

        //Validando seguridad
        oRecursoVerificado = oSeguridadService.verificarPermiso(sessionBean.getUsuarioActual(), sessionBean.getEntidadActual(), url);
        if (oRecursoVerificado == null) {
            FacesUtils.addErrorMessage("No está autorizado para este recurso");
            sessionBean.setPaginaActual(NavigationResults.BLANK);
        } else {
            //Auditando Navegación
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
                oNavegacionUsuario.setHost(WebUtils.getRemoteHostName());
                oSeguridadService.auditarNavegacion(oNavegacionUsuario);
            }
            sessionBean.setBreadcrum(breadcrum);
            sessionBean.setPaginaActual(url);
            FacesContext context = FacesContext.getCurrentInstance();
            String viewId = context.getViewRoot().getViewId();
            ViewHandler handler = context.getApplication().getViewHandler();
            UIViewRoot root = handler.createView(context, viewId);
            root.setViewId(viewId);
            context.setViewRoot(root);
        }
    }

    public Recurso getRecursoVerificado() {
        return oRecursoVerificado;
    }
}