package ni.gob.inss.barista.view.bean.backbean.home;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.SeguridadService;
import ni.gob.inss.barista.businesslogic.service.seguridad.UsuarioService;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.session.SessionBean;
import ni.gob.inss.barista.view.utils.web.FacesUtils;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.barista.view.utils.web.NavigationResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 14/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Data
@Named
@Scope("view")
public class ChangePasswordBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private String txtCambiarClave;
    private String txtCambiarConfirmacion;
    private String regExpClave;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private SeguridadService oSeguridadService;
    private SessionBean oSessionBean;
    private UsuarioService oUsuarioService;

    @Inject
    @Autowired
    public ChangePasswordBackBean(SeguridadService oSeguridadService,
                                  SessionBean oSessionBean,
                                  UsuarioService oUsuarioService) {
        this.oSeguridadService = oSeguridadService;
        this.oSessionBean = oSessionBean;
        this.oUsuarioService = oUsuarioService;
    }

    @PostConstruct
    public void init() {
        cargarValidaciones();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private void cargarValidaciones() {
        regExpClave = RegExpresion.regExpAlphaNumeric;
    }

    public String cambiarClave() {
        Usuario oUsuario;
        try {
            oUsuario = oSessionBean.getUsuarioActual();
            oSeguridadService.cambiarClave(oUsuario, txtCambiarClave, txtCambiarConfirmacion, false);
            oUsuario.setPassword(oSeguridadService.encode(txtCambiarClave));
            oUsuario.setPasswordTemporal(false);
            oUsuarioService.actualizar(oUsuario);
            FacesUtils.addInfoMessage("Clave cambiada correctamente");
            return NavigationResults.HOME;
        } catch (Exception e) {
            FacesUtils.addErrorMessage(MessagesResults.ERROR_GUARDAR + e.getMessage());
            return NavigationResults.CHANGE_PASSWORD_NO_REDIRECT;
        }
    }
}