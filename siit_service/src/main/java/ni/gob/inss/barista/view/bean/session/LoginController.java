package ni.gob.inss.barista.view.bean.session;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.core.SeguridadService;
import ni.gob.inss.barista.businesslogic.service.seguridad.UsuarioService;
import ni.gob.inss.barista.model.entity.auditoria.SesionUsuario;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.application.ConfigApplicationBean;
import ni.gob.inss.barista.view.bean.application.ConfigValue;
import ni.gob.inss.barista.view.errorHandler.ErrBitNotice;
import ni.gob.inss.barista.view.notification.UsuarioNotificacion;
import ni.gob.inss.barista.view.utils.web.FacesUtils;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.barista.view.utils.web.NavigationResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Bean de Sessión</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
@Scope("session")
public class LoginController implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(SessionBean.class);

    private String username;
    private String password;
    private String token;
    private Boolean loggedIn;
    private Usuario usuarioActual;
    private Entidad entidadActual;
    private Integer cmbEntidadActual;
    private Modulo moduloActual;
    private Boolean soloMovil;
    private Boolean soloWeb;
    private Boolean ambosSitios;
    private Boolean dobleAutenticacion = false;
    private String paginaActual;
    private String breadcrum;
    private String ip;
    private String regExpClave;
    private String regExpUsuario;
    private Date ultimaActividad;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */

    UsuarioService oUsuarioService;
    private SeguridadService oSeguridadService;
    private ConfigApplicationBean oConfigApplicationBean;
    private ErrBitNotice oErrBitNotice;

    @Inject
    @Autowired
    public LoginController(SeguridadService oSeguridadService, ConfigApplicationBean oConfigApplicationBean, ErrBitNotice oErrBitNotice, UsuarioService oUsuarioService) {
        this.oSeguridadService = oSeguridadService;
        this.oConfigApplicationBean = oConfigApplicationBean;
        this.oErrBitNotice = oErrBitNotice;
        this.oUsuarioService = oUsuarioService;
    }

    @PostConstruct
    public void init() {
        this.clear();
        cargarValidaciones();
    }

    private void cargarValidaciones() {
        regExpClave = RegExpresion.regExpAlphaNumeric;
        regExpUsuario = RegExpresion.regExpSoloLetras;
    }

    public void establecerIp() {
        Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParamMap.containsKey("ip")) {
            ip = requestParamMap.get("ip");
        }
    }
    public String loginIndex(){
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL urlResource = classLoader.getResource("config.properties");
            Properties props = new Properties();
            props.load(urlResource.openStream());
            this.dobleAutenticacion = Boolean.valueOf(props.getProperty("general.dobleAutenticacion"));
            Boolean tieneRecursoMovil = false;
            Boolean tieneRecursoWeb = false;
            this.usuarioActual = this.oSeguridadService.login(this.username, this.password, this.oConfigApplicationBean.getTiempoBloqueoSesion(), this.oConfigApplicationBean.getIntentosBloqueoSesion(), WebUtils.getRemoteIp());
            this.usuarioActual.setIpActual("192.168.0.114");
            if (this.dobleAutenticacion) {
                if (this.token == null) {
                    FacesUtils.addInfoMessage("Ingrese el token que se envió por sms");
                    this.usuarioActual.setToken(this.oSeguridadService.generarPassword());
                    this.oUsuarioService.actualizar(this.usuarioActual);
                    this.usuarioActual = null;
                    return "/login.html";
                }

                if (this.token.equals("")) {
                    return "/login.html";
                }

                this.usuarioActual = this.oSeguridadService.login(this.username, this.password, this.oConfigApplicationBean.getTiempoBloqueoSesion(), this.oConfigApplicationBean.getIntentosBloqueoSesion(), WebUtils.getRemoteIp());
                if (!this.usuarioActual.getToken().equals(this.token)) {
                    FacesUtils.addErrorMessage("Los token no son iguales");
                    return "/login.html";
                }
            }

            this.loggedIn = true;
            HttpServletRequest oRequest = FacesUtils.getServletRequest();
            oRequest.getSession().setAttribute("usuarioActual", this.usuarioActual);
            List<Map<String, Object>> recursosAuditables = this.oSeguridadService.obtenerRecursosAuditablesPorUsuario(this.usuarioActual.getId());
            Iterator var8 = recursosAuditables.iterator();

            while(var8.hasNext()) {
                Map<String, Object> recurso = (Map)var8.next();
                if (recurso.get("tipo").equals("M")) {
                    tieneRecursoMovil = true;
                } else if (recurso.get("tipo").equals("P")) {
                    tieneRecursoWeb = true;
                }

                if (tieneRecursoWeb && tieneRecursoMovil) {
                    break;
                }
            }

            if (this.oConfigApplicationBean.getAuditarInicioSesion() == ConfigValue.YES || this.oConfigApplicationBean.getAuditarInicioSesion() == ConfigValue.BY_USER && this.usuarioActual.getAuditarInicioSesion()) {
                String navegador = oRequest.getHeader("USER-AGENT");
                SesionUsuario oSesionUsuario = new SesionUsuario();
                oSesionUsuario.setUsuarioId(this.usuarioActual.getId());
                oSesionUsuario.setFechaSesion(WebUtils.nowTimeStamp());
                oSesionUsuario.setIpSesion("192.168.0.114");
                oSesionUsuario.setHostName(WebUtils.getRemoteHostName());
                oSesionUsuario.setNavegador(navegador);
                this.oSeguridadService.auditarInicioSesion(oSesionUsuario);
            }

            return "/web/views/banco/vistaBanco.xhtml";
        } catch (BusinessException var10) {
            this.loggedIn = false;
            this.dobleAutenticacion = false;
            FacesUtils.addErrorMessage(var10.getMessage());
            return "/login.html";
        } catch (Exception var11) {
            logger.error(var11);
            this.loggedIn = false;
            this.dobleAutenticacion = false;
            FacesUtils.addErrorMessage("Error al inicia sessión");
            var11.printStackTrace();
            this.oErrBitNotice.setErrorData(this.getClass() + "#loginAction()", "/login.html", (Usuario)null, var11);
            this.oErrBitNotice.sendError();
            return "/login.html";
        }
    }

    public String logoutAction() {
        UsuarioNotificacion.removerUsuarioLista(usuarioActual);
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return NavigationResults.LOGIN;
    }

    public String continueAction() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        //Invalidando sessión
        HttpSession sesionActual = SessionList.getSession(this.usuarioActual.getUsername());
        sesionActual.invalidate();
        SessionList.addUser(this.usuarioActual.getUsername(), usuarioActual.getReferencia(), usuarioActual.getTelefono(), usuarioActual.obtenerNombreCompleto(), session);
        return NavigationResults.LOGIN;
    }

    public void clear() {
        this.username = null;
        this.password = null;
        this.loggedIn = false;
        this.usuarioActual = null;
        this.entidadActual = null;
        this.moduloActual = null;
        this.soloWeb = false;
        this.soloMovil = false;
        this.ambosSitios = false;
        breadcrum = null;
    }

    public String getNombreModuloActual() {
        return moduloActual == null ? "" : moduloActual.getNombre();
    }

    public String getNombreEntidadActual() {
        return entidadActual == null ? oConfigApplicationBean.getNombreAplicacion() : entidadActual.getNombre();
    }
}