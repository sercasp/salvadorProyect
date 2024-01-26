package ni.gob.inss.barista.view.bean.backbean.catalogos.catalogos;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.catalogos.CatalogoService;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * BackBean para pantalla de catálogos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/24/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Data
@Named
@Scope("view")
public class CatalogoBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private final AuditoriaService oDialogAuditoriaService;
    private final CatalogoService oCatalogoService;
    private Catalogo selectedRow;
    private boolean nuevoRegistro;
    private String selectedTipoCatalogo;
    private Integer hfId;
    private String txtCodigo;
    private String txtNombre;
    private String txtBuscar;
    private String txtDescripcion;
    private String txtRefTipoCatalogo;
    private String txtCodigoAlterno;
    private Integer txtOrden;
    private String regExpCodigo;
    private String regExpCodigoAlterno;
    private String regExpOrden;
    private String regExpNombre;
    private String regExpDescripcion;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private boolean rbPasivo;
    private List listaAuditoria;
    private List<Catalogo> listaCatalogos;
    private List<TiposCatalogo> listaTiposCatalogosActivos;
    private List<TiposCatalogo> listaTiposCatalogos;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */

    @Inject
    @Autowired
    public CatalogoBackBean(AuditoriaService oDialogAuditoriaService, CatalogoService oCatalogoService) {
        this.oDialogAuditoriaService = oDialogAuditoriaService;
        this.oCatalogoService = oCatalogoService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarLista();
        cargarValidaciones();
        cargarTipoCatalogoBusqueda();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private void cargarValidaciones() {
        regExpCodigo = RegExpresion.regExpAlphaNumeric;
        regExpCodigoAlterno = RegExpresion.regExpAlphaNumeric;
        regExpOrden = RegExpresion.regExpSoloNumeros;
        regExpNombre = RegExpresion.regExpDescripcion;
        regExpDescripcion = RegExpresion.regExpDescripcion;
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Catalogo.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarLista() {
        try {
            this.listaCatalogos = oCatalogoService.buscar(selectedTipoCatalogo, txtBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    private void cargarTipoCatalogo(Catalogo oCatalogo) {
        try {
            this.listaTiposCatalogos = oCatalogoService.obtenerTipoCatalogo(oCatalogo);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarTipoCatalogo(Catalogo oCatalogo)", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    private void cargarTipoCatalogoBusqueda() {
        try {
            this.listaTiposCatalogosActivos = oCatalogoService.obtenerTipoCatalogo();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarTipoCatalogo(Catalogo oCatalogo)", MessagesResults.ERROR_OBTENER_LISTA, e);
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
        txtDescripcion = null;
        txtOrden = null;
        txtCodigoAlterno = null;
        txtRefTipoCatalogo = null;
        rbPasivo = false;
        nuevoRegistro = true;
        selectedRow = null;
        cargarTipoCatalogo(new Catalogo());
    }

    public void editar() {
        if (selectedRow != null) {
            cargar(selectedRow.getId());
        } else {
            mostrarMensajeInfo(MessagesResults.SELECCIONE_UN_REGISTRO);
        }
    }

    private void cargar(Integer roleId) {
        try {
            Catalogo oCatalogo = oCatalogoService.obtener(roleId);
            hfId = oCatalogo.getId();
            txtRefTipoCatalogo = oCatalogo.getRefTipoCatalogo();
            txtCodigo = oCatalogo.getCodigo();
            txtCodigoAlterno = oCatalogo.getCodigoAlterno();
            txtOrden = oCatalogo.getOrden();
            txtNombre = oCatalogo.getNombre();
            txtDescripcion = oCatalogo.getDescripcion();
            rbPasivo = oCatalogo.getPasivo();
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnElminiarVisible = isServidorReplicacion();
            nuevoRegistro = false;
            this.cargarLista();
            cargarTipoCatalogo(oCatalogo);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer roleId)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void elminar() {
        try {
            Catalogo oCatalogo = oCatalogoService.obtener(hfId);
            oCatalogoService.eliminar(oCatalogo);
            limpiar();
            cargarLista();
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "elminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void guardar() {
        try {
            Catalogo oCatalogo;
            if (nuevoRegistro) {
                oCatalogo = new Catalogo();
                oCatalogo.setRefTipoCatalogo(txtRefTipoCatalogo);
                oCatalogo.setCodigo(oCatalogo.getRefTipoCatalogo() + "|" + txtCodigo);
                oCatalogo.setCodigoAlterno(txtCodigoAlterno);
                oCatalogo.setNombre(txtNombre);
                oCatalogo.setDescripcion(txtDescripcion);
                oCatalogo.setOrden(txtOrden);
                oCatalogo.setCreadoPor(this.getUsuarioActual());
                oCatalogo.setCreadoEl(this.getTimeNow());
                oCatalogo.setCreadoEnIp(this.getRemoteIp());
                oCatalogo.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oCatalogo.setPasivo(false);
                oCatalogoService.agregar(oCatalogo);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
            } else {
                oCatalogo = oCatalogoService.obtener(hfId);
                oCatalogo.setCodigoAlterno(txtCodigoAlterno);
                oCatalogo.setNombre(txtNombre);
                oCatalogo.setDescripcion(txtDescripcion);
                oCatalogo.setOrden(txtOrden);
                oCatalogo.setPasivo(rbPasivo);
                oCatalogo.setModificadoPor(this.getUsuarioActual());
                oCatalogo.setModificadoEl(this.getTimeNow());
                oCatalogo.setModificadoEnIp(this.getRemoteIp());
                oCatalogo.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oCatalogoService.actualizar(oCatalogo);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oCatalogo.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public void tipoCatalogoBuscarEvent(ValueChangeEvent event) {
        selectedTipoCatalogo = (String) event.getNewValue();
        cargarLista();
    }
}