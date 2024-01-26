package ni.gob.inss.barista.view.bean.backbean;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.core.SeguridadService;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.personal.MiembroService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.personal.Miembro;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.model.entity.seguridad.RecursoUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.barista.view.bean.application.ConfigApplicationBean;
import ni.gob.inss.barista.view.bean.backbean.home.IndexBackBean;
import ni.gob.inss.barista.view.bean.backbean.home.MenuPrincipalBackBean;
import ni.gob.inss.barista.view.bean.session.SessionBean;
import ni.gob.inss.barista.view.errorHandler.ErrBitNotice;
import ni.gob.inss.barista.view.security.SystemSecurityException;
import ni.gob.inss.barista.view.utils.web.FacesUtils;
import ni.gob.inss.barista.view.utils.web.NavigationResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

public class BaseBackBean {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static Logger logger = Logger.getLogger(BaseBackBean.class);
    protected String creadoPor;
    protected boolean verInfoPag;
    protected boolean verInfoErrores;
    protected boolean verInfoAuditoria;
    protected Recurso oRecursoVerificado;
    protected Locale locale;

    /**
     * **********************************************************************************
     * DEPENDENCIAS
     * **********************************************************************************
     */

    @Autowired
    @Qualifier("sessionBean")
    protected SessionBean sessionBean;

    @Autowired
    protected MiembroService oMiembroService;

    @Autowired
    protected ConfigApplicationBean oConfigApplicationBean;

    @Autowired
    protected AuditoriaService oAuditoriaService;

    @Autowired
    protected IndexBackBean oIndexBackBean;

    @Autowired
    protected ErrBitNotice oErrBitNotice;

    @Autowired
    protected SeguridadService oSeguridadService;

    @Autowired
    protected MenuPrincipalBackBean oMenuPrincipalBackBean;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @PostConstruct
    public void initBaseBackBean() throws SystemSecurityException {
        validarSeguridad("");
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     * Validación de Seguridad y Carga la información de la pantalla
     *
     * @param codigoRecurso código de recurso de la pantalla
     */
    public void validarSeguridad(String codigoRecurso) throws SystemSecurityException {

        //Validando seguridad
        if (oMenuPrincipalBackBean.getRecursoVerificado() != null) {
            oRecursoVerificado = oMenuPrincipalBackBean.getRecursoVerificado();
        } else {
            oRecursoVerificado = oSeguridadService.verificarPermiso(sessionBean.getUsuarioActual(), getEntidadActual(), oIndexBackBean.getPaginaActual());
            if (oRecursoVerificado == null) {
                mostrarMensajeError("No esta autorizado para ver este recurso");
                oIndexBackBean.setPaginaActual(NavigationResults.BLANK);
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


    /**
     * Muesta un mensaje de error, si es del tipo ServiceException lo graba en el log y envia el error
     * a errbit, a la vez verifica si el usuario tiene permiso para ver el detalle del error.
     *
     * @param mensaje Mensaje a mostrar
     * @param e       Excepción generada
     */
    public void mostrarMensajeError(String targetErrorClassName, String targetErrorMethodName, String mensaje, Exception e) {
        //Si es una instancia de BusinessException se muestra el mensaje de error de lo contrario se verifica si el usuario puede o no
        //ver la información completa del error
        if ((e instanceof BusinessException)) {
            FacesUtils.addErrorMessage(mensaje + e.getMessage());
        } else {
            if (this.isVerInfoErrores()) {
                FacesUtils.addErrorMessage(mensaje + e.getMessage());
            } else {
                FacesUtils.addErrorMessage(mensaje);
            }
            logger.error(targetErrorClassName + "#" + targetErrorMethodName, e);
            oErrBitNotice.setErrorData(targetErrorClassName + "#" + targetErrorMethodName,
                    oRecursoVerificado.getUrl(), sessionBean.getUsuarioActual(), e);
            oErrBitNotice.sendError();
        }
    }

    public void mostrarMensajeError(String mensaje) {
        FacesUtils.addErrorMessage(mensaje);
    }

    /**
     * Muestra un mensaje de información
     *
     * @param mensaje mensaje de información
     */
    public void mostrarMensajeInfo(String mensaje) {
        FacesUtils.addInfoMessage(mensaje);
    }

    /**
     * Muestra un mensaje de información
     *
     * @param mensaje mensaje de información
     */
    public void mostrarMensajeSuccess(String mensaje) {
        FacesUtils.addSuccessMessage(mensaje);
    }

    public void mostrarMensajeException(Exception e) {
        if (e.getClass().getName().equals("javax.validation.ConstraintViolationException")) {
            mostrarMensajeError(((ConstraintViolationImpl) ((ConstraintViolationException) e).getConstraintViolations()
                    .toArray()[0]).getMessageTemplate());
        } else {
            mostrarMensajeError(e.getMessage());
        }
    }

    public void mostrarMensajeException(String targetErrorClassName, String targetErrorMethodName, String mensaje, Exception e) {
        if (e.getClass().getName().equals("javax.validation.ConstraintViolationException")) {
            mostrarMensajeError(((ConstraintViolationImpl) ((ConstraintViolationException) e).getConstraintViolations()
                    .toArray()[0]).getMessageTemplate());
        } else {
            mostrarMensajeError(targetErrorClassName, targetErrorMethodName, mensaje, e);
        }
    }

    /**
     * ***********************************************************************************
     * PROPIEDADES
     * ************************************************************************************
     */
    public boolean isServidorReplicacion() {
        return oConfigApplicationBean.isServidorReplicacion();
    }

    public boolean isVerInfoPag() {
        return verInfoPag;
    }

    public boolean isVerInfoErrores() {
        return verInfoErrores;
    }

    public boolean isVerInfoAuditoria() {
        return verInfoAuditoria;
    }

    public Entidad getEntidadActual() {
        return this.sessionBean.getEntidadActual();
    }

    public Usuario getUsuarioActual() {
        return this.sessionBean.getUsuarioActual();
    }

    public String getRemoteIp() {
        return WebUtils.getRemoteIp();
    }

    public Date getNow() {
        return WebUtils.now();
    }

    public Timestamp getTimeNow() {
        return new Timestamp(this.getNow().getTime());
    }

    public String getNombre() {
        return oRecursoVerificado == null ? "" : oRecursoVerificado.getNombre();
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public String getCreadoEl() {
        return oRecursoVerificado == null ? "" : oConfigApplicationBean.dateToString(oRecursoVerificado.getCreadoEl());
    }

    public String getDescripcion() {
        return oRecursoVerificado == null ? "" : oRecursoVerificado.getDescripcion();
    }

    public String getUrlAyuda() {
        return oRecursoVerificado == null ? "" : (oConfigApplicationBean.getHelpHost() + oRecursoVerificado.getUrlAyuda());
    }

    public String dateToString(Date oDate) {
        return oConfigApplicationBean.dateToString(oDate);
    }

    public String dateToTime(Date oDate) {
        return oConfigApplicationBean.dateToTime(oDate);
    }

    public String dateToDateTime(Date oDate) {
        return oConfigApplicationBean.dateToDateTime(oDate);
    }

    public String dateToDateTimeWithSeconds(Date oDate) {
        return oConfigApplicationBean.dateToDateTimeWithSeconds(oDate);
    }

    public void obtenerInfoPagina() throws EntityNotFoundException {
        Miembro oMiembro = oMiembroService.obtenerMiembroPorId(oRecursoVerificado.getMiembroId());
        this.creadoPor = oMiembro.getPrimerNombre() + " " + oMiembro.getSegundoNombre() + " "
                + oMiembro.getPrimerApellido() + " " + oMiembro.getSegundoApellido()
                + " (" + oMiembro.getIdentificador() + ")";
    }

    public Locale getLocale() {
        return Locale.getDefault();
    }
}