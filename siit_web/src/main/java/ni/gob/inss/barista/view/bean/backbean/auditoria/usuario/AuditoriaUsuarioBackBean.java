package ni.gob.inss.barista.view.bean.backbean.auditoria.usuario;


import lombok.Getter;
import ni.gob.inss.barista.businesslogic.service.auditoria.usuario.AuditoriaUsuarioService;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.seguridad.UsuarioService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrail;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrailDetail;
import ni.gob.inss.barista.model.entity.auditoria.NavegacionUsuario;
import ni.gob.inss.barista.model.entity.auditoria.SesionUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * BackBean para pantalla auditoria de usuarios</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 07/08/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Getter
@Named
@Scope("view")
public class AuditoriaUsuarioBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private Usuario selectedRow;
    private Integer hfId;
    private String txtUsername;
    private String txtNombres;
    private String txtApellidos;
    private String txtBuscar;
    private String txtReferencia;
    private String otTituloPanelDetalle;
    private String txtCantidadInicioSesion;
    private String txtSessionActual;
    private String txtIpActual;
    private Integer txtIntentosFallidos;
    private String txtFechaBloqueo;
    private Date txtBusquedaAuditoriaFecha;
    private Date txtBusquedaAuditoriaFechafin;
    private String esquemaSeleccionado;
    private String tablaSeleccionada;
    private List<String> listaEsquema;
    private List<String> listaTabla;
    private Boolean rbAuditarInicioSession;
    private Boolean rbAuditarNavegacion;
    private Boolean rbPasivo;
    private Boolean tab;
    private List<Usuario> listaUsuarios;
    private List<SesionUsuario> listaSession;
    private List<NavegacionUsuario> listaNavegacion;
    private List<AuditTrail> listaAuditTrail;
    private List<AuditTrailDetail> detalleAuditoria;
    private Integer txtBusquedaAuditoriaId;
    private Boolean registroSeleccionado;
    private Date calFechaInicio;
    private Date calFechaFinal;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final AuditoriaUsuarioService oAuditoriaUsuarioService;
    private final UsuarioService oUsuarioService;
    private final AuditoriaService oAuditoriaService;

    @Inject
    @Autowired
    public AuditoriaUsuarioBackBean(AuditoriaUsuarioService oAuditoriaUsuarioService, UsuarioService oUsuarioService, AuditoriaService oAuditoriaService) {
        this.oAuditoriaUsuarioService = oAuditoriaUsuarioService;
        this.oUsuarioService = oUsuarioService;
        this.oAuditoriaService = oAuditoriaService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarLista();
        otTituloPanelDetalle = "ID: ";
        txtBusquedaAuditoriaFechafin = null;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public void cargarLista() {
        try {
            this.listaUsuarios = oAuditoriaUsuarioService.buscar(txtBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void limpiar() {
        txtBuscar = "";
        hfId = null;
        txtUsername = null;
        txtNombres = null;
        txtApellidos = null;
        txtReferencia = null;
        rbPasivo = false;
        tab = false;
        rbAuditarNavegacion = false;
        rbAuditarInicioSession = false;
        txtCantidadInicioSesion = null;
        txtSessionActual = null;
        txtIpActual = null;
        txtIntentosFallidos = null;
        txtFechaBloqueo = null;
        listaNavegacion = null;
        listaSession = null;
        registroSeleccionado = false;
        txtBusquedaAuditoriaFecha = null;
        esquemaSeleccionado = null;
        tablaSeleccionada = null;
        listaEsquema = null;
        listaTabla = null;
        listaAuditTrail = null;
        otTituloPanelDetalle = "ID: ";
    }

    public void editar() throws EntityNotFoundException {
        if (selectedRow != null) {
            limpiar();
            cargar(selectedRow.getId());
            cargarDatosoperacionesInit();
            txtBuscar = null;
            tab = true;
            PrimeFaces.current().ajax().update("modalSelectUsuario");
            PrimeFaces.current().executeScript("PF('modalSelectUsuario').hide()");

        } else {
            mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
        }
    }

    private void cargar(Integer id) {
        try {
            limpiar();
            Usuario oUsuario = oAuditoriaUsuarioService.obtenerUsuarioPorId(id);
            hfId = oUsuario.getId();
            txtUsername = oUsuario.getUsername();
            txtNombres = oUsuario.getNombres();
            txtApellidos = oUsuario.getApellidos();
            txtReferencia = oUsuario.getReferencia();
            rbAuditarInicioSession = oUsuario.getAuditarInicioSesion();
            rbAuditarNavegacion = oUsuario.getAuditarNavegacion();
            rbPasivo = oUsuario.getPasivo();
            txtCantidadInicioSesion = oUsuario.getCantidadInicioSesion().toString();
            txtSessionActual = dateToDateTimeWithSeconds(oUsuario.getSesionActual());
            txtIpActual = oUsuario.getIpActual();
            txtIntentosFallidos = oUsuario.getIntentosFallidos();
            txtFechaBloqueo = dateToDateTimeWithSeconds(oUsuario.getFechaBloqueo());
            listaSession = oAuditoriaUsuarioService.buscarInicioSession(oUsuario);
            listaNavegacion = oAuditoriaUsuarioService.buscarNavegacion(oUsuario);
            registroSeleccionado = true;
            otTituloPanelDetalle = "ID: " + hfId + " " + txtUsername + " " + txtNombres + " " + txtApellidos;
            this.cargarLista();
        } catch (Exception e) {
            limpiar();
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void fechaEvent(SelectEvent event) throws EntityNotFoundException {
        txtBusquedaAuditoriaFecha = (Date) event.getObject();
        if (txtBusquedaAuditoriaFechafin != null) {
            Usuario oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
            listaAuditTrail = oAuditoriaUsuarioService.obtenerAuditoriaPorUsuario(oUsuario, txtBusquedaAuditoriaFecha, txtBusquedaAuditoriaFechafin, null, null);
            esquemaSeleccionado = null;
            tablaSeleccionada = null;
            listaTabla = null;
            listaEsquema = new ArrayList<>();
            for (AuditTrail a : listaAuditTrail) {
                if (!listaEsquema.contains(a.getSchema())) {
                    listaEsquema.add(a.getSchema());
                }
            }
        }
    }

    public void cargarDatosoperacionesInit() throws EntityNotFoundException {
        Usuario oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
        Date FechaInicio = Calendar.getInstance().getTime();
        Date FechaFinal = Calendar.getInstance().getTime();
        listaAuditTrail = oAuditoriaUsuarioService.obtenerAuditoriaPorUsuario(oUsuario, FechaInicio, FechaFinal, null, null);
        esquemaSeleccionado = null;
        tablaSeleccionada = null;
        listaTabla = null;
        listaEsquema = new ArrayList<>();
        for (AuditTrail a : listaAuditTrail) {
            if (!listaEsquema.contains(a.getSchema())) {
                listaEsquema.add(a.getSchema());
            }
        }
    }

    public void quitarFiltros() throws EntityNotFoundException {
        calFechaInicio = null;
        calFechaFinal = null;
        txtBusquedaAuditoriaFecha = null;
        txtBusquedaAuditoriaFechafin = null;
        tablaSeleccionada = null;
        esquemaSeleccionado = null;
        cargarDatosoperacionesInit();
    }

    public void fechaEvent1(SelectEvent event) throws EntityNotFoundException {
        txtBusquedaAuditoriaFechafin = (Date) event.getObject();
        if (txtBusquedaAuditoriaFecha != null) {
            Usuario oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
            listaAuditTrail = oAuditoriaUsuarioService.obtenerAuditoriaPorUsuario(oUsuario, txtBusquedaAuditoriaFecha, txtBusquedaAuditoriaFechafin, null, null);
            esquemaSeleccionado = null;
            tablaSeleccionada = null;
            listaTabla = null;
            listaEsquema = new ArrayList<>();
            for (AuditTrail a : listaAuditTrail) {
                if (!listaEsquema.contains(a.getSchema())) {
                    listaEsquema.add(a.getSchema());
                }
            }
        }
    }

    public void esquemaEvent(ValueChangeEvent event) throws EntityNotFoundException {
        Usuario oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
        esquemaSeleccionado = (String) event.getNewValue();
        listaTabla = new ArrayList<>();
        tablaSeleccionada = null;
        if (esquemaSeleccionado == null) {
            listaAuditTrail = oAuditoriaUsuarioService.obtenerAuditoriaPorUsuario(oUsuario, txtBusquedaAuditoriaFecha, txtBusquedaAuditoriaFechafin, null, null);
        } else {
            listaAuditTrail = oAuditoriaUsuarioService.obtenerAuditoriaPorUsuario(oUsuario, txtBusquedaAuditoriaFecha, txtBusquedaAuditoriaFechafin,
                    esquemaSeleccionado, null);
            for (AuditTrail a : listaAuditTrail) {
                if (!listaTabla.contains(a.getTabla())) {
                    listaTabla.add(a.getTabla());
                }
            }
        }
    }

    public void tablaEvent(ValueChangeEvent event) throws EntityNotFoundException {
        Usuario oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
        tablaSeleccionada = (String) event.getNewValue();
        if (tablaSeleccionada == null) {
            listaAuditTrail = oAuditoriaUsuarioService.obtenerAuditoriaPorUsuario(oUsuario, txtBusquedaAuditoriaFecha, txtBusquedaAuditoriaFechafin,
                    esquemaSeleccionado, null);
        } else {
            listaAuditTrail = oAuditoriaUsuarioService.obtenerAuditoriaPorUsuario(oUsuario, txtBusquedaAuditoriaFecha, txtBusquedaAuditoriaFechafin,
                    esquemaSeleccionado, tablaSeleccionada);
        }
    }

    public void idEvent(ValueChangeEvent event) throws EntityNotFoundException {
        Usuario oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
        tablaSeleccionada = (String) event.getNewValue();

        if (tablaSeleccionada == null) {
            listaAuditTrail = oAuditoriaUsuarioService.obtenerAuditoriaPorUsuario(oUsuario, txtBusquedaAuditoriaFecha, txtBusquedaAuditoriaFechafin,
                    esquemaSeleccionado, null);
        } else {
            listaAuditTrail = oAuditoriaUsuarioService.obtenerAuditoriaPorUsuario(oUsuario, txtBusquedaAuditoriaFecha, txtBusquedaAuditoriaFechafin,
                    esquemaSeleccionado, tablaSeleccionada);
        }
    }

    public void obtenerDetalleAuditoria(ToggleEvent event) {
        AuditTrail oAuditTrail = (AuditTrail) event.getData();
        detalleAuditoria = oAuditoriaService.obtenerDetalleAuditoria(oAuditTrail);
    }
}