package ni.gob.inss.barista.view.bean.backbean.notificacion.general;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.notificacion.MensajeNotificacionService;
import ni.gob.inss.barista.businesslogic.service.notificacion.NotificacionGeneralService;
import ni.gob.inss.barista.model.entity.notificacion.Mensaje;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.application.MessageNotification;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Data
@Named
@Scope("view")
public class NotificacionGeneralBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private final static String CHANNEL = "/general";
    NotificacionGeneralService oNotificacionGeneralService;
    MessageNotification oMessageNotification;
    MensajeNotificacionService oMensajeNotificacionService;
    private String txtTitulo;
    private String txtMensaje;
    private String regExpTexto;
    private Mensaje mensajeSeleccionado;
    private List<Mensaje> listaMensajes;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public NotificacionGeneralBackBean(NotificacionGeneralService oNotificacionGeneralService, MessageNotification oMessageNotification, MensajeNotificacionService oMensajeNotificacionService) {
        this.oNotificacionGeneralService = oNotificacionGeneralService;
        this.oMessageNotification = oMessageNotification;
        this.oMensajeNotificacionService = oMensajeNotificacionService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarValidaciones();
        listaMensajes = oNotificacionGeneralService.obtenerMensajes();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private void cargarValidaciones() {
        regExpTexto = RegExpresion.regExpDescripcion;
    }

    public void limpiar() {
        txtTitulo = null;
        txtMensaje = null;
    }

    public void seleccionar() {
        if (mensajeSeleccionado == null) {
            mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
        } else {
            txtTitulo = mensajeSeleccionado.getTitulo();
            txtMensaje = mensajeSeleccionado.getMensaje();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        }
    }

    public void enivarMensaje() {
        try {
            if (txtTitulo == null || txtTitulo.equals("")) {
                mostrarMensajeError("Título Requerido");
            } else if (txtMensaje == null || txtMensaje.equals("")) {
                mostrarMensajeError("Mensaje Requerido");
            } else {
                oMessageNotification.setTitle(txtTitulo);
                oMessageNotification.setMessage(txtMensaje);
                Mensaje oMensaje = new Mensaje();
                oMensaje.setMensaje(txtMensaje);
                oMensaje.setTitulo(txtTitulo);
                oMensajeNotificacionService.agregar(oMensaje);
                //TODO
                /*EventBus eventBus = EventBusFactory.getDefault().eventBus();
                eventBus.publish("/general", "mensaje enviado");*/
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "enivarMensaje()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }
}