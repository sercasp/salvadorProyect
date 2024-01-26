package ni.gob.inss.barista.view.bean.backbean;

import ni.gob.inss.barista.model.entity.seguridad.RecursoUsuario;
import ni.gob.inss.barista.view.bean.backbean.home.IndexMobileBackBean;
import ni.gob.inss.barista.view.bean.backbean.home.MenuPrincipalMobileBackBean;
import ni.gob.inss.barista.view.security.SystemSecurityException;
import ni.gob.inss.barista.view.utils.web.NavigationResults;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Clase base para todos los BackBeans</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
public class BaseMobileBackBean extends BaseBackBean {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static Logger logger = Logger.getLogger(BaseMobileBackBean.class);

    /**
     * **********************************************************************************
     * DEPENDENCIAS
     * **********************************************************************************
     */
    @Autowired
    IndexMobileBackBean oIndexMobileBackBean;

    @Autowired
    MenuPrincipalMobileBackBean oMenuPrincipalMobileBackBean;

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     * Validación de Seguridad y Carga la información de la pantalla
     *
     * @param codigoRecurso código de recurso de la pantalla
     */
    @Override
    public void validarSeguridad(String codigoRecurso) throws SystemSecurityException {
        //Validando seguridad
        if (oMenuPrincipalMobileBackBean.getRecursoVerificado() != null) {
            oRecursoVerificado = oMenuPrincipalMobileBackBean.getRecursoVerificado();
        } else {
            oRecursoVerificado = oSeguridadService.verificarPermiso(sessionBean.getUsuarioActual(), getEntidadActual(), oIndexMobileBackBean.getPaginaActual());
            if (oRecursoVerificado == null) {
                mostrarMensajeError("No esta autorizado para ver este recurso");
                oIndexMobileBackBean.setPaginaActual(NavigationResults.BLANK);
                throw new SystemSecurityException("No esta autorizado para ver este recurso");
            }
        }
        //Fin Validando seguridad
        boolean esSuperAdministrador = sessionBean.getUsuarioActual().getSuperAdministrador();
        this.verInfoPag = esSuperAdministrador ? true : sessionBean.getUsuarioActual().getVerInfoPantalla();
        this.verInfoErrores = esSuperAdministrador ? true : sessionBean.getUsuarioActual().getVerInfoErrores();

        if (esSuperAdministrador) {
            this.verInfoAuditoria = true;
        } else {
            this.verInfoAuditoria = sessionBean.getUsuarioActual().getVerAuditoria();
            if (verInfoAuditoria && oRecursoVerificado.getAuditable()) {
                List<RecursoUsuario> oRecursoUsuarioList =
                        oAuditoriaService.obtenerRecursosPorUsuarioEntidad(sessionBean.getUsuarioActual(),
                                sessionBean.getEntidadActual().getId());

                if (oRecursoUsuarioList.size() > 0) {
                    verInfoAuditoria = false;
                    for (RecursoUsuario ru : oRecursoUsuarioList) {
                        if (ru.getRecursoId().equals(oRecursoVerificado.getId())) {
                            verInfoAuditoria = true;
                            break;
                        }
                    }
                }
            }
        }
    }

}
