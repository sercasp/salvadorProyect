package ni.gob.inss.barista.view.bean.backbean.catalogos.tipocatalogo;


import ni.gob.inss.barista.businesslogic.service.catalogos.TipoCatalogoService;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.validation.EntityValidationField;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * BackBean para pantalla tipos de catálogos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/22/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Named
@Scope("view")
public class ListaTipoCatalogoBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private TiposCatalogo selectedRow;
    private boolean nuevoRegistro;
    private Integer hfId;
    private String txtCodigo;
    private String txtNombre;
    private String txtBuscar;
    private String txtDescripcion;
    private List<TiposCatalogo> listaTiposCatalogos;
    private String regExpCodigo;
    private String regExpNombre;
    private String regExpDescripcion;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private boolean rbPasivo;
    private List listaAuditoria;
    private List<Catalogo> listaCatalogos;

    /**
     * **********************************************************************************
     * DEPENDENCIAS
     * **********************************************************************************
     */

    @Autowired
    private TipoCatalogoService oTipoCatalogoService;

    @Autowired
    AuditoriaService oDialogAuditoriaService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
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
            regExpCodigo = EntityValidationField.getRegExpresionPattern(TiposCatalogo.class, "Codigo");
            regExpNombre = EntityValidationField.getRegExpresionPattern(TiposCatalogo.class, "Nombre");
            regExpDescripcion = EntityValidationField.getRegExpresionPattern(TiposCatalogo.class, "Descripcion");
        } catch (NoSuchMethodException e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(TiposCatalogo.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarLista() {
        try {
            this.listaTiposCatalogos = oTipoCatalogoService.buscar(txtBuscar);
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
        txtDescripcion = null;
        rbPasivo = false;
        nuevoRegistro = true;
        listaCatalogos = null;
        selectedRow = null;
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
            TiposCatalogo oTiposCatalogo = oTipoCatalogoService.obtener(roleId);
            hfId = oTiposCatalogo.getId();
            txtCodigo = oTiposCatalogo.getCodigo();
            txtNombre = oTiposCatalogo.getNombre();
            txtDescripcion = oTiposCatalogo.getDescripcion();
            rbPasivo = oTiposCatalogo.getPasivo();
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnElminiarVisible = isServidorReplicacion();
            nuevoRegistro = false;
            this.cargarLista();
            listaCatalogos = oTipoCatalogoService.obtenerCatalogos(oTiposCatalogo);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer roleId)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void elminar() {
        try {
            TiposCatalogo oTiposCatalogo = oTipoCatalogoService.obtener(hfId);
            oTipoCatalogoService.eliminar(oTiposCatalogo);
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
            TiposCatalogo oTiposCatalogo;
            if (nuevoRegistro) {
                oTiposCatalogo = new TiposCatalogo();
                oTiposCatalogo.setCodigo(txtCodigo);
                oTiposCatalogo.setNombre(txtNombre);
                oTiposCatalogo.setDescripcion(txtDescripcion);
                oTiposCatalogo.setCreadoPor(this.getUsuarioActual());
                oTiposCatalogo.setCreadoEl(this.getTimeNow());
                oTiposCatalogo.setCreadoEnIp(this.getRemoteIp());
                oTiposCatalogo.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oTiposCatalogo.setPasivo(false);
                oTipoCatalogoService.agregar(oTiposCatalogo);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
            } else {
                oTiposCatalogo = oTipoCatalogoService.obtener(hfId);
                oTiposCatalogo.setNombre(txtNombre);
                oTiposCatalogo.setDescripcion(txtDescripcion);
                oTiposCatalogo.setPasivo(rbPasivo);
                oTiposCatalogo.setModificadoPor(this.getUsuarioActual());
                oTiposCatalogo.setModificadoEl(this.getTimeNow());
                oTiposCatalogo.setModificadoEnIp(this.getRemoteIp());
                oTiposCatalogo.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oTipoCatalogoService.actualizar(oTiposCatalogo);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oTiposCatalogo.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    /**
     * ***********************************************************************************
     * PROPIEDADES
     * ************************************************************************************
     */

    public String getRegExpCodigo() {
        return regExpCodigo;
    }

    public String getRegExpNombre() {
        return regExpNombre;
    }

    public String getRegExpDescripcion() {
        return regExpDescripcion;
    }

    public List<TiposCatalogo> getListaTiposCatalogos() {
        return listaTiposCatalogos;
    }

    public TiposCatalogo getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(TiposCatalogo selectedRow) {
        this.selectedRow = selectedRow;
    }

    public String getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(String txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public String getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(String txtNombre) {
        this.txtNombre = txtNombre;
    }

    public String getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(String txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public String getTxtDescripcion() {
        return txtDescripcion;
    }

    public void setTxtDescripcion(String txtDescripcion) {
        this.txtDescripcion = txtDescripcion;
    }

    public Integer getHfId() {
        return hfId;
    }

    public void setHfId(Integer hfId) {
        this.hfId = hfId;
    }

    public boolean isBtnElminiarVisible() {
        return btnElminiarVisible;
    }

    public void setBtnElminiarVisible(boolean btnElminiarVisible) {
        this.btnElminiarVisible = btnElminiarVisible;
    }

    public boolean isBtnGuardarVisible() {
        return btnGuardarVisible;
    }

    public void setBtnGuardarVisible(boolean btnGuardarVisible) {
        this.btnGuardarVisible = btnGuardarVisible;
    }

    public boolean isBtnAuditoriaVisible() {
        return btnAuditoriaVisible;
    }

    public void setBtnAuditoriaVisible(boolean btnAuditoriaVisible) {
        this.btnAuditoriaVisible = btnAuditoriaVisible;
    }

    public boolean isRbPasivo() {
        return rbPasivo;
    }

    public void setRbPasivo(boolean rbPasivo) {
        this.rbPasivo = rbPasivo;
    }

    public boolean isNuevoRegistro() {
        return nuevoRegistro;
    }

    public List getListaAuditoria() {
        return listaAuditoria;
    }

    public List<Catalogo> getListaCatalogos() {
        return listaCatalogos;
    }
}