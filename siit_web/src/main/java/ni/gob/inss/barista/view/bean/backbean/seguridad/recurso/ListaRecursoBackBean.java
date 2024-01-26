package ni.gob.inss.barista.view.bean.backbean.seguridad.recurso;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.personal.MiembroService;
import ni.gob.inss.barista.businesslogic.service.seguridad.RecursoService;
import ni.gob.inss.barista.model.entity.personal.Miembro;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
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
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * BackBean para la pantalla de Recursos</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 03/06/2014
 * @since 1.0 *
 * Modificado el 02/08/2023 por jvillanueva
 */

@Data
@Named
@Scope("view")
public class ListaRecursoBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private static MessageDigest md;
    AuditoriaService oDialogAuditoriaService;
    private Recurso selectedRow;
    private boolean nuevoRegistro;
    private Integer hfId;
    private String txtCodigo;
    private String txtNombre;
    private String txtUrl;
    private String cmbTipo;
    private Boolean rbAuditable;
    private String txtIconoCss;
    private String txtTextoAyuda;
    private Boolean rbPasivo;
    private String txtUrlAyuda;
    private String txtDescripcion;
    private Integer cmbMiembro;
    private String txtBuscar;
    private List<Recurso> listaRecursos;
    private List<Miembro> listaMiembros;
    private List<Recurso> secuencia;
    private String regExpCodigo;
    private String regExpDescriptor;
    private String regExpUrl;
    private String selectTipo;
    private Boolean disableTxtCodigo;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private List listaAuditoria;
    private RecursoService oRecursoService;
    private MiembroService oMiembroService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaRecursoBackBean(RecursoService oRecursoService, MiembroService oMiembroService, AuditoriaService oDialogAuditoriaService) {
        this.oRecursoService = oRecursoService;
        this.oMiembroService = oMiembroService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarLista();
        cargarValidaciones();
        cargarMiembros();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    private void cargarValidaciones() {
        try {
            regExpCodigo = RegExpresion.regExpCodigoRecurso;
            regExpDescriptor = RegExpresion.regExpDescripcion;
            regExpUrl = RegExpresion.regExpUrl;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Recurso.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarMiembros() {
        try {
            this.listaMiembros = oRecursoService.obtenerMiembros();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarMiembros()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarLista() {
        try {
            this.listaRecursos = oRecursoService.buscar(txtBuscar, null);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void seleccionarTipoEvent(ValueChangeEvent event) {
        selectTipo = (String) event.getNewValue();
        switch (selectTipo) {
            case "P":
                txtCodigo = oRecursoService.secuenciaCodigo().get(0).get("codigo").toString();
                disableTxtCodigo = true;
                break;
            case "M":
                txtCodigo = oRecursoService.secuenciaCodigo().get(0).get("codigo").toString();
                disableTxtCodigo = true;
                break;
            case "R":
                txtCodigo = null;
                disableTxtCodigo = false;
                break;
        }
    }

    public void cargarTxtAyuda() {
        String var;
        txtTextoAyuda = txtNombre;
    }

    public void limpiar() {
        btnElminiarVisible = false;
        btnGuardarVisible = isServidorReplicacion();
        btnAuditoriaVisible = false;
        txtBuscar = "";
        hfId = null;
        txtCodigo = null;
        txtNombre = null;
        txtUrl = null;
        cmbTipo = null;
        cmbMiembro = null;
        rbAuditable = null;
        txtTextoAyuda = null;
        rbPasivo = null;
        txtUrlAyuda = null;
        txtDescripcion = null;
        txtIconoCss = null;
        nuevoRegistro = true;
        disableTxtCodigo = false;
        selectedRow = null;
    }

    public void editar() {
        if (selectedRow != null) {
            cargar(selectedRow.getId());
        } else {
            mostrarMensajeInfo("Seleccione un registro");
        }
    }

    private void cargar(Integer id) {
        try {
            Recurso oRecurso = oRecursoService.obtenerRecursoPorId(id);
            hfId = oRecurso.getId();
            txtCodigo = oRecurso.getCodigo();
            txtNombre = oRecurso.getNombre();
            txtUrl = oRecurso.getUrl();
            cmbTipo = oRecurso.getTipo();
            rbAuditable = oRecurso.getAuditable();
            txtIconoCss = oRecurso.getIconoCss();
            txtTextoAyuda = oRecurso.getTextoAyuda();
            rbPasivo = oRecurso.getPasivo();
            txtUrlAyuda = oRecurso.getUrlAyuda();
            txtDescripcion = oRecurso.getDescripcion();
            txtIconoCss = oRecurso.getIconoCss();
            cmbMiembro = oRecurso.getMiembrosByRecursoId() == null ? null : oRecurso.getMiembrosByRecursoId().getId();
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

    public void elminar() {
        try {
            Recurso oRecurso = oRecursoService.obtenerRecursoPorId(hfId);
            oRecursoService.eliminar(oRecurso);
            limpiar();
            cargarLista();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "elminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void guardar() {
        try {
            Recurso oRecurso;
            if (nuevoRegistro) {
                oRecurso = new Recurso();
                oRecurso.setCodigo(txtCodigo);
                oRecurso.setNombre(txtNombre.toUpperCase());
                oRecurso.setUrl(txtUrl);
                oRecurso.setTipo(cmbTipo);
                oRecurso.setAuditable(rbAuditable);
                oRecurso.setIconoCss(txtIconoCss);
                oRecurso.setTextoAyuda(txtTextoAyuda.toUpperCase());
                oRecurso.setPasivo(false);
                oRecurso.setUrlAyuda(txtUrlAyuda);
                oRecurso.setDescripcion(txtDescripcion.toUpperCase());
                oRecurso.setMiembrosByRecursoId(oMiembroService.obtenerMiembroPorId(cmbMiembro));
                oRecurso.setCreadoPor(this.getUsuarioActual());
                oRecurso.setCreadoEl(this.getTimeNow());
                oRecurso.setCreadoEnIp(this.getRemoteIp());
                oRecurso.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oRecursoService.agregar(oRecurso);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
            } else {
                oRecurso = oRecursoService.obtenerRecursoPorId(hfId);
                oRecurso.setCodigo(txtCodigo);
                oRecurso.setNombre(txtNombre.toUpperCase());
                oRecurso.setUrl(txtUrl);
                oRecurso.setTipo(cmbTipo);
                oRecurso.setAuditable(rbAuditable);
                oRecurso.setIconoCss(txtIconoCss);
                oRecurso.setTextoAyuda(txtTextoAyuda.toUpperCase());
                oRecurso.setPasivo(rbPasivo);
                oRecurso.setUrlAyuda(txtUrlAyuda);
                oRecurso.setDescripcion(txtDescripcion);
                oRecurso.setMiembrosByRecursoId(oMiembroService.obtenerMiembroPorId(cmbMiembro));
                oRecurso.setModificadoPor(this.getUsuarioActual());
                oRecurso.setModificadoEl(this.getTimeNow());
                oRecurso.setModificadoEnIp(this.getRemoteIp());
                oRecurso.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oRecursoService.actualizar(oRecurso);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oRecurso.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }
}