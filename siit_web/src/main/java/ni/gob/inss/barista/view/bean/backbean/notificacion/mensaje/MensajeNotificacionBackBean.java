package ni.gob.inss.barista.view.bean.backbean.notificacion.mensaje;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.notificacion.MensajeNotificacionService;
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
public class MensajeNotificacionBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    MensajeNotificacionService oMensajeNotificacionService;
    MessageNotification oMessageNotification;
    private Integer id;
    private String txtTitulo;
    private String txtMensaje;
    private String txtBuscar;
    private String regExpTexto;
    private boolean nuevoRegistro;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private Mensaje mensajeSeleccionado;
    private List<Mensaje> listaMensajes;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public MensajeNotificacionBackBean(MensajeNotificacionService oMensajeNotificacionService, MessageNotification oMessageNotification) {
        this.oMensajeNotificacionService = oMensajeNotificacionService;
        this.oMessageNotification = oMessageNotification;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarValidaciones();
        cargarLista();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private void cargarValidaciones() {
        regExpTexto = RegExpresion.regExpDescripcion;
    }

    public void cargarLista() {
        listaMensajes = oMensajeNotificacionService.buscar(txtBuscar);
    }

    public void limpiar() {
        btnElminiarVisible = false;
        btnGuardarVisible = isServidorReplicacion();
        txtBuscar = "";
        id = null;
        txtTitulo = null;
        txtMensaje = null;
        nuevoRegistro = true;
    }

    public void editar() {
        if (mensajeSeleccionado != null) {
            cargar(mensajeSeleccionado.getId());
        } else {
            mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
        }
    }

    private void cargar(Integer mensajeId) {
        try {
            Mensaje oMensaje = oMensajeNotificacionService.obtener(mensajeId);
            id = oMensaje.getId();
            txtTitulo = oMensaje.getTitulo();
            txtMensaje = oMensaje.getMensaje();
            btnElminiarVisible = isServidorReplicacion();
            nuevoRegistro = false;
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void elminar() {
        try {
            Mensaje oMensaje = oMensajeNotificacionService.obtener(id);
            oMensajeNotificacionService.eliminar(oMensaje);
            limpiar();
            cargarLista();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
            mostrarMensajeInfo(MessagesResults.EXITO_ELIMINAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "elminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void guardar() {
        try {
            Mensaje oMensaje;
            if (nuevoRegistro) {
                oMensaje = new Mensaje();
                oMensaje.setTitulo(txtTitulo);
                oMensaje.setMensaje(txtMensaje);
                oMensajeNotificacionService.agregar(oMensaje);
                mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
            } else {
                oMensaje = oMensajeNotificacionService.obtener(id);
                oMensaje.setTitulo(txtTitulo);
                oMensaje.setMensaje(txtMensaje);
                oMensajeNotificacionService.actualizar(oMensaje);
                mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oMensaje.getId());
            cargarLista();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }
}