package ni.gob.inss.barista.view.bean.backbean.seguridad.autorizacion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.seguridad.AutorizacionService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ModuloService;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Establecimientos</br>
 *
 * @author barista
 * @version 1.0, 19/06/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Named
@Scope("view")
public class ListaAutorizacionBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private Autorizacion selectedRow;
    private boolean nuevoRegistro;
    private Integer hfId;
    private Integer cmbModulo;
    private String txtCodigo;
    private String txtNombre;
    private String txtDescripcion;
    private Boolean rbPasivo;
    private String txtBuscar;
    private List<Autorizacion> listaAutorizaciones;
    private List<Modulo> listaModulos;
    private String regExpCodigo;
    private String regExpDescriptor;
    private String mensajeValidacionCodigo;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private List listaAuditoria;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final AuditoriaService oDialogAuditoriaService;
    private final ModuloService oModuloService;
    private final AutorizacionService oAutorizacionService;

    @Inject
    @Autowired
    public ListaAutorizacionBackBean(AutorizacionService oAutorizacionService, AuditoriaService oDialogAuditoriaService, ModuloService oModuloService) {
        this.oAutorizacionService = oAutorizacionService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
        this.oModuloService = oModuloService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarValidaciones();
        cargarModulos();
        txtBuscar = "";
        cargarLista();
        cmbModulo = 0;
        mensajeValidacionCodigo = "Este Código Ya Existe ¿Desea Guardarlo?";
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public void cargarValidaciones() {
        try {
            mostrarMensajeInfo("Libre del barista");
            regExpCodigo = RegExpresion.regExpSoloNumeros;
            regExpDescriptor = RegExpresion.regExpSoloLetrasConEspacio;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Autorizacion.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarModulos() {
        try {
            listaModulos = new ArrayList<>();
            this.listaModulos = oModuloService.obtenerModulos();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarModulos()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void limpiar() {
        btnElminiarVisible = false;
        btnGuardarVisible = isServidorReplicacion();
        btnAuditoriaVisible = false;
        hfId = null;
        txtNombre = null;
        txtDescripcion = null;
        txtCodigo = null;
        rbPasivo = null;
        nuevoRegistro = true;
        cmbModulo = null;
        selectedRow = null;
    }

    public void editar() {
        if (selectedRow != null) {

            cargar(selectedRow.getId());
        } else {
            mostrarMensajeInfo("Seleccione un registro");
        }
    }

    public void cargarLista() {
        try {
            this.listaAutorizaciones = oAutorizacionService.buscar(txtBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    private void cargar(Integer id) {
        try {
            Autorizacion oAutorizacion = oAutorizacionService.obtenerAutorizacionPorId(id);
            hfId = oAutorizacion.getId();
            txtNombre = oAutorizacion.getNombre();
            txtDescripcion = oAutorizacion.getDescripcion();
            txtCodigo = oAutorizacion.getCodigo();
            if (oAutorizacion.getModuloIdByModuloId() == null) {
                cmbModulo = 0;
            } else {
                cmbModulo = oAutorizacion.getModuloIdByModuloId().getId();
            }
            rbPasivo = oAutorizacion.getPasivo();
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnElminiarVisible = isServidorReplicacion();
            nuevoRegistro = false;

            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void eliminar() {
        try {
            Autorizacion oAutorizacion = oAutorizacionService.obtenerAutorizacionPorId(hfId);
            oAutorizacionService.eliminar(oAutorizacion);
            limpiar();
            cargarLista();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void cerrarDialogoConfirmacion() {
        PrimeFaces.current().executeScript("PF('confirmacion').hide()");
    }

    public void validarGuardar() {
        List<Autorizacion> listaTemporalAutorizacion;
        listaTemporalAutorizacion = oAutorizacionService.obtenerTodasLasAutorizaciones(txtCodigo);

        if (listaTemporalAutorizacion.size() != 0 && nuevoRegistro) {
            PrimeFaces.current().executeScript("PF('confirmacion').show()");
        } else {
            guardar();
        }
    }

    public void guardar() {
        try {
            Autorizacion oAutorizacion;
            if (nuevoRegistro) {
                oAutorizacion = new Autorizacion();
                oAutorizacion.setNombre(txtNombre);
                oAutorizacion.setCodigo(txtCodigo);
                oAutorizacion.setDescripcion(txtDescripcion);
                oAutorizacion.setModuloIdByModuloId(cmbModulo == null ? null : oModuloService.obtener(cmbModulo));
                oAutorizacion.setPasivo(false);
                oAutorizacion.setCreadoPor(this.getUsuarioActual());
                oAutorizacion.setCreadoEl(this.getTimeNow());
                oAutorizacion.setCreadoEnIp(this.getRemoteIp());
                oAutorizacion.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oAutorizacionService.agregar(oAutorizacion);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                limpiar();
            } else {
                oAutorizacion = oAutorizacionService.obtenerAutorizacionPorId(hfId);
                oAutorizacion.setDescripcion(txtDescripcion);
                oAutorizacion.setModuloIdByModuloId(cmbModulo == null ? null : oModuloService.obtener(cmbModulo));
                oAutorizacion.setPasivo(rbPasivo);
                oAutorizacion.setModificadoPor(this.getUsuarioActual());
                oAutorizacion.setModificadoEl(this.getTimeNow());
                oAutorizacion.setModificadoEnIp(this.getRemoteIp());
                oAutorizacion.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oAutorizacionService.actualizar(oAutorizacion);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
                PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
            }
            cargarLista();
        } catch (Exception e) {
            mostrarMensajeException(this.getClass().getSimpleName(), "guardar()", "Error: ", e);
        }
        cerrarDialogoConfirmacion();
    }
}