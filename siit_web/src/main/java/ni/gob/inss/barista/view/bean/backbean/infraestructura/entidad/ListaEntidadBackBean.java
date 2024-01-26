package ni.gob.inss.barista.view.bean.backbean.infraestructura.entidad;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.infraestructura.EntidadService;
import ni.gob.inss.barista.businesslogic.service.infraestructura.EstablecimientoService;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
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
 * @author MARYLU ZELAYA RODRIGUE
 * @version 1.0, 05/08/2014
 * @since 1.0 *
 * <p>
 * Modificado por:
 * @since 07/10/2016
 * Se elimino el metodo de eliminar entidad
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
@Scope("view")
public class ListaEntidadBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private final EntidadService oEntidadService;
    AuditoriaService oDialogAuditoriaService;
    //Propiedades de pantalla
    private Entidad selectedRow;
    private boolean nuevoRegistro;
    private String txtBuscar;
    private Integer hfId;
    private Integer txtCodigo;
    private String txtSiglas;
    private String txtNombre;
    private Integer cmbEstablecimiento;
    private Boolean rbPasivo;
    private String txtDireccion;
    private String txtTelefono;
    private String txtUsuario;
    private String txtClave;
    private Integer cmbEntidadesCombo;
    private Integer cmbGrupoEntidad;
    private String cmbGrupoEntidadModal;
    private String regExpCodigo;
    private String regExpNombre;
    private String regExpSinEspacio;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private List listaAuditoria;
    private List listaEstablecimientos;
    private List listaEntidadesCombo;
    private List listaEntidades;
    private List listaGrupoEntidades;
    private List listaRelacionEntidadGrupo;
    private EstablecimientoService oEstablecimientoService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaEntidadBackBean(EntidadService oEntidadService, EstablecimientoService oEstablecimientoService, AuditoriaService oDialogAuditoriaService) {
        this.oEntidadService = oEntidadService;
        this.oEstablecimientoService = oEstablecimientoService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarLista();
        cargarValidaciones();
        cargarEstablecimientos();
        cargarEntidadesCombo();
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
            regExpSinEspacio = RegExpresion.regExpUrl;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Entidad.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarLista() {
        try {
            this.listaEntidades = oEntidadService.buscar(txtBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarEstablecimientos() {
        try {
            this.listaEstablecimientos = oEntidadService.obtenerEstablecimientos();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarEstablecimientos()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarEntidadesCombo(Integer id) {
        try {
            this.listaEntidadesCombo = oEntidadService.obtenerEntidadesCombo(id);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarEntidadesCombo()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarEntidadesCombo() {
        try {
            this.listaEntidadesCombo = oEntidadService.obtenerEntidadesCombo();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarEntidadesCombo()", MessagesResults.ERROR_OBTENER_LISTA, e);
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
        txtSiglas = null;
        txtDireccion = null;
        txtUsuario = null;
        txtClave = null;
        txtTelefono = null;
        cmbGrupoEntidad = null;
        cmbEntidadesCombo = null;
        cmbEstablecimiento = null;
        rbPasivo = false;
        nuevoRegistro = true;
        selectedRow = null;
        cargarEstablecimientos();
        cargarEntidadesCombo();
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
            Entidad oEntidad = oEntidadService.obtenerEntidadPorId(id);
            hfId = oEntidad.getId();
            txtCodigo = oEntidad.getCodigo();
            txtNombre = oEntidad.getNombre();
            txtSiglas = oEntidad.getSiglas();
            txtDireccion = oEntidad.getDireccion();
            txtTelefono = oEntidad.getTelefono();
            cmbEntidadesCombo = oEntidad.getEntidadByEntidadId() == null ? null : oEntidad.getEntidadByEntidadId().getId();
            cmbEstablecimiento = oEntidad.getEstablecimientoByEstablecimientoId().getId();
            rbPasivo = oEntidad.getPasivo();
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnElminiarVisible = isServidorReplicacion();
            nuevoRegistro = false;
            this.cargarEntidadesCombo(oEntidad.getId());
            this.cargarLista();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void guardar() {
        try {
            Entidad oEntidad;
            //RelacionEntidadGrupo oRelacionEntidadGrupo;

            if (nuevoRegistro) {
                oEntidad = new Entidad();
                oEntidad.setCodigo(txtCodigo);
                oEntidad.setNombre(txtNombre);
                oEntidad.setSiglas(txtSiglas);
                oEntidad.setDireccion(txtDireccion);
                oEntidad.setTelefono(txtTelefono);
                if (cmbEntidadesCombo == null || cmbEntidadesCombo == 0) {
                    oEntidad.setEntidadByEntidadId(null);

                } else {
                    oEntidad.setEntidadByEntidadId(oEntidadService.obtenerEntidadPorId(cmbEntidadesCombo));
                }
                oEntidad.setEstablecimientoByEstablecimientoId(oEstablecimientoService.obtenerEstablecimientoPorId(cmbEstablecimiento));
                oEntidad.setPasivo(rbPasivo);
                oEntidad.setCreadoPor(this.getUsuarioActual());
                oEntidad.setCreadoEl(this.getTimeNow());
                oEntidad.setCreadoEnIp(this.getRemoteIp());
                oEntidad.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oEntidadService.agregar(oEntidad);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                limpiar();
                cargarLista();
            } else {
                oEntidad = oEntidadService.obtenerEntidadPorId(hfId);
                oEntidad.setCodigo(txtCodigo);
                oEntidad.setNombre(txtNombre);
                oEntidad.setSiglas(txtSiglas);
                oEntidad.setDireccion(txtDireccion);
                oEntidad.setTelefono(txtTelefono);
                if (cmbEntidadesCombo == null || cmbEntidadesCombo == 0) {
                    oEntidad.setEntidadByEntidadId(null);

                } else {
                    oEntidad.setEntidadByEntidadId(oEntidadService.obtenerEntidadPorId(cmbEntidadesCombo));
                }
                oEntidad.setEstablecimientoByEstablecimientoId(oEstablecimientoService.obtenerEstablecimientoPorId(cmbEstablecimiento));
                oEntidad.setPasivo(rbPasivo);
                oEntidad.setModificadoPor(this.getUsuarioActual());
                oEntidad.setModificadoEl(this.getTimeNow());
                oEntidad.setModificadoEnIp(this.getRemoteIp());
                oEntidad.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oEntidadService.actualizar(oEntidad);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
                limpiar();
                cargarLista();
            }
//            this.cargar(oEntidad.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }
}