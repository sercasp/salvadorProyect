package ni.gob.inss.barista.view.bean.backbean.seguridad.modulo;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ModuloService;
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
 * Created by jmendoza on 6/23/2015.
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
@Scope("view")
public class ListaModuloBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    AuditoriaService oDialogAuditoriaService;
    private Modulo selectedRow;
    private boolean nuevoRegistro;
    private Integer hfId;
    private String txtCodigo;
    private String txtNombre;
    private String txtUrlInicio;
    private String txtUrlImagen;
    private Boolean rbBloqueado;
    private String txtBuscar;
    private List<Modulo> listaModulos;
    private String regExpCodigo;
    private String regExpNombre;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private List listaAuditoria;
    private ModuloService oModuloService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaModuloBackBean(ModuloService oModuloService, AuditoriaService oDialogAuditoriaService) {
        this.oModuloService = oModuloService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
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

    public void cargarValidaciones() {
        try {
            regExpCodigo = RegExpresion.regExpSoloLetras;
            regExpNombre = RegExpresion.regExpNombre;
            mostrarMensajeInfo("Libre del barista");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Modulo.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void limpiar() {
        btnElminiarVisible = false;
        btnGuardarVisible = isServidorReplicacion();
        btnAuditoriaVisible = false;
        hfId = null;
        txtBuscar = "";
        txtNombre = null;
        txtUrlInicio = null;
        txtUrlImagen = null;
        rbBloqueado = false;
        txtCodigo = null;
        nuevoRegistro = true;
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
            this.listaModulos = oModuloService.buscar(txtBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    private void cargar(Integer id) {
        try {

            Modulo oModulo = oModuloService.obtenerModuloPorId(id);
            hfId = oModulo.getId();
            txtNombre = oModulo.getNombre();
            txtNombre = oModulo.getNombre();
            txtUrlInicio = oModulo.getUrlInicio();
            txtUrlImagen = oModulo.getUrlImagen();
            rbBloqueado = oModulo.getBloqueado();
            txtCodigo = oModulo.getCodigo();
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
            if (validarEliminar(hfId)) {
                return;
            }
            Modulo oModulo = oModuloService.obtenerModuloPorId(hfId);
            oModuloService.eliminar(oModulo);
            cargarLista();
            limpiar();
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    /**
     * Este metodo se encarga de verificar si un módulo tiene alguna relación con otra tabla
     *
     */
    private Boolean validarEliminar(Integer prIdModulo) {
        List<Modulo> listaModulosEnReporte = oModuloService.verificarModuloEnReporte(prIdModulo);
        List<Modulo> listaModulosEnParametroEntidad = oModuloService.verificarModuloEnParametroEntidad(prIdModulo);
        List<Modulo> listaModulosEnSessionModulo = oModuloService.verificarModuloEnSesionModulo(prIdModulo);
        if (listaModulosEnReporte.size() > 0) {
            mostrarMensajeError("Este módulo no se puede eliminar, ya que está siendo utilizado");
            return true;
        }
        if (listaModulosEnParametroEntidad.size() > 0) {
            mostrarMensajeError("Este módulo no se puede eliminar, ya que está siendo utilizado");
            return true;
        }
        if (listaModulosEnSessionModulo.size() > 0) {
            mostrarMensajeError("Este módulo no se puede eliminar, ya que está siendo utilizado");
            return true;
        }
        return false;
    }

    public void nombreMayuscula() {
        String begin = txtNombre.substring(0, 1).toUpperCase();
        String end = txtNombre.substring(1);
        txtNombre = begin + end;
    }

    public void guardar() {
        try {
            Modulo oModulo;
            nombreMayuscula();
            if (nuevoRegistro) {
                oModulo = new Modulo();
                oModulo.setCodigo(txtCodigo.trim().toUpperCase());
                oModulo.setNombre(txtNombre);
                oModulo.setUrlInicio(txtUrlInicio);
                oModulo.setUrlImagen(txtUrlImagen);
                oModulo.setBloqueado(rbBloqueado);
                oModulo.setCreadoEl(this.getTimeNow());
                oModulo.setCreadoEnIp(this.getRemoteIp());
                oModulo.setCreadoPor(this.getUsuarioActual());
                oModulo.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oModuloService.agregar(oModulo);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
            } else {
                oModulo = oModuloService.obtenerModuloPorId(hfId);
                oModulo.setCodigo(txtCodigo.trim().toUpperCase());
                oModulo.setNombre(txtNombre);
                oModulo.setUrlInicio(txtUrlInicio);
                oModulo.setUrlImagen(txtUrlImagen);
                oModulo.setBloqueado(rbBloqueado);
                oModulo.setModificadoEl(this.getTimeNow());
                oModulo.setModificadoEl(this.getTimeNow());
                oModulo.setModificadoEnIp(this.getRemoteIp());
                oModulo.setModificadoPor(this.getUsuarioActual());
                oModulo.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oModuloService.actualizar(oModulo);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oModulo.getId());
            this.cargarLista();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }
}