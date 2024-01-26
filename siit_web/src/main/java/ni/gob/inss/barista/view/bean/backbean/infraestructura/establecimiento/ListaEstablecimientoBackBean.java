package ni.gob.inss.barista.view.bean.backbean.infraestructura.establecimiento;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.infraestructura.EstablecimientoService;
import ni.gob.inss.barista.model.entity.infraestructura.Establecimiento;
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
 * DESCRIPCIÓN</br>
 *
 * @author JAIRO HELÍ MENDOZA AGUIRRE
 * @version 1.0, 05/08/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
@Scope("view")
public class ListaEstablecimientoBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    AuditoriaService oDialogAuditoriaService;
    private Establecimiento selectedRow;
    private boolean nuevoRegistro;
    private String txtBuscar;
    private Integer hfId;
    private Integer txtCodigo;
    private String txtNombre;
    private Boolean rbPasivo;
    private String regExpCodigo;
    private String regExpNombre;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private List listaAuditoria;
    private List listaEstablecimientos;
    private EstablecimientoService oEstablecimientoService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaEstablecimientoBackBean(EstablecimientoService oEstablecimientoService, AuditoriaService oDialogAuditoriaService) {
        this.oEstablecimientoService = oEstablecimientoService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarLista();
        cargarValidaciones();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private void cargarValidaciones() {
        try {
            regExpCodigo = RegExpresion.regExpSoloNumeros;
            regExpNombre = RegExpresion.regExpDescripcion;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Establecimiento.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarLista() {
        try {
            this.listaEstablecimientos = oEstablecimientoService.buscar(txtBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void limpiar() {
        btnElminiarVisible = false;
        btnGuardarVisible = isServidorReplicacion();
        btnAuditoriaVisible = false;
        txtBuscar = "";
        hfId = null;
        txtCodigo = null;
        txtNombre = null;
        rbPasivo = false;
        nuevoRegistro = true;
        selectedRow = null;
    }

    public void editar() {
        if (selectedRow != null) {
            cargar(selectedRow.getId());
        } else {
            mostrarMensajeInfo(MessagesResults.SELECCIONE_UN_REGISTRO);
        }
    }

    private void cargar(Integer id) {
        try {
            Establecimiento oEstablecimiento = oEstablecimientoService.obtenerEstablecimientoPorId(id);
            hfId = oEstablecimiento.getId();
            txtCodigo = oEstablecimiento.getCodigo();
            txtNombre = oEstablecimiento.getNombre();
            rbPasivo = oEstablecimiento.getPasivo();
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnElminiarVisible = isServidorReplicacion();
            nuevoRegistro = false;
            this.cargarLista();
            //this.cargarModulos();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void eliminar() {
        try {
            Establecimiento oEstablecimiento = oEstablecimientoService.obtenerEstablecimientoPorId(hfId);
            oEstablecimientoService.eliminar(oEstablecimiento);
            limpiar();
            cargarLista();
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void guardar() {
        try {
            Establecimiento oEstablecimiento;
            if (nuevoRegistro) {
                oEstablecimiento = new Establecimiento();
                oEstablecimiento.setCodigo(txtCodigo);
                oEstablecimiento.setNombre(txtNombre);
                oEstablecimiento.setPasivo(rbPasivo);
                oEstablecimiento.setCreadoPor(this.getUsuarioActual());
                oEstablecimiento.setCreadoEl(this.getTimeNow());
                oEstablecimiento.setCreadoEnIp(this.getRemoteIp());
                oEstablecimiento.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oEstablecimientoService.agregar(oEstablecimiento);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
            } else {
                oEstablecimiento = oEstablecimientoService.obtenerEstablecimientoPorId(hfId);
                oEstablecimiento.setCodigo(txtCodigo);
                oEstablecimiento.setNombre(txtNombre);
                oEstablecimiento.setPasivo(rbPasivo);
                oEstablecimiento.setModificadoPor(this.getUsuarioActual());
                oEstablecimiento.setModificadoEl(this.getTimeNow());
                oEstablecimiento.setModificadoEnIp(this.getRemoteIp());
                oEstablecimiento.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oEstablecimientoService.actualizar(oEstablecimiento);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oEstablecimiento.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }
}