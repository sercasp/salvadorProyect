package ni.gob.inss.barista.view.bean.backbean.seguridad.parametroEntidad;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.infraestructura.EntidadService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ModuloService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ParametroEntidadService;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.ParametroEntidad;
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
 * Interfaz de acceso a datos para ParametroEntidads Entidades</br>
 *
 * @author VIRGINIA ELIZABETH MORA MUNGUIA
 * @version 1.0, 03/06/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */


@Data
@Named
@Scope("view")
public class ListaParametroEntidadBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    AuditoriaService oDialogAuditoriaService;
    private ParametroEntidad selectedRow;
    private Integer hfId;
    private Integer cmbModulo;
    private Integer somDelegacion;
    private String txtCodigo;
    private String txtDescriptor;
    private String txtValor;
    private String txtBuscar;
    private String regExpCodigo;
    private String mensajeValidacion;
    private String regExpDescriptor;
    private String somTipoParametro;
    private String itListaValor;
    private String itNombreListaValor;
    private String itaSentenciaSQL;
    private List listaAuditoria;
    private List<Modulo> listaModulos;
    private List<Entidad> listaDelegaciones;
    private List<ParametroEntidad> listaParametroEntidads;
    private boolean nuevoRegistro;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private Boolean visibleCaracter;
    private Boolean visibleListaValores;
    private Boolean visibleSentenciaSQL;
    private ParametroEntidadService oParametroEntidadService;
    private ModuloService oModuloService;
    private EntidadService oEntidadService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaParametroEntidadBackBean(ParametroEntidadService oParametroEntidadService, ModuloService oModuloService, AuditoriaService oDialogAuditoriaService, EntidadService oEntidadService) {
        this.oParametroEntidadService = oParametroEntidadService;
        this.oModuloService = oModuloService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
        this.oEntidadService = oEntidadService;
    }

    @PostConstruct
    public void init() {
        nuevoRegistro = true;
        cargarLista();
        cargarValidaciones();
        cargarModulos();
        cargarDelegaciones();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    private void cargarValidaciones() {
        try {
            regExpCodigo = RegExpresion.regExpSoloNumeros;
            regExpDescriptor = RegExpresion.regExpSoloLetrasConEspacio;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(ParametroEntidad.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarModulos() {
        try {
            this.listaModulos = oParametroEntidadService.obtenerModulos();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarModulos()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarLista() {
        try {
            txtBuscar = txtBuscar == null ? "" : txtBuscar;
            this.listaParametroEntidads = oParametroEntidadService.buscar(txtBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarDelegaciones() {
        try {
            listaDelegaciones = oParametroEntidadService.obtenerDelegaciones();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarDelegaciones()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void limpiar() {
        btnElminiarVisible = false;
        btnGuardarVisible = isServidorReplicacion();
        btnAuditoriaVisible = false;
        txtBuscar = "";
        hfId = null;
        txtCodigo = null;
        txtDescriptor = null;
        txtValor = null;
        cmbModulo = null;
        somDelegacion = null;
        nuevoRegistro = true;
        selectedRow = null;
        somTipoParametro = null;
        itaSentenciaSQL = null;
        itNombreListaValor = null;
        itListaValor = null;
        seleccionarTipoParametro();
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
            ParametroEntidad oParametroEntidad = oParametroEntidadService.obtenerParametroEntidadPorId(id);
            hfId = oParametroEntidad.getId();
            txtCodigo = oParametroEntidad.getCodigo();
            txtDescriptor = oParametroEntidad.getDescriptor();
            somTipoParametro = oParametroEntidad.getTipoValor();
            itListaValor = oParametroEntidad.getListaValores() != null ? oParametroEntidad.getListaValores() : null;
            itNombreListaValor = oParametroEntidad.getNombreValores() != null ? oParametroEntidad.getNombreValores() : null;
            itaSentenciaSQL = oParametroEntidad.getSentencia() != null ? oParametroEntidad.getSentencia() : null;
            txtValor = oParametroEntidad.getValor();
            somDelegacion = oParametroEntidad.getEntidadIdByEntidadId() == null ? null : oParametroEntidad.getEntidadIdByEntidadId().getId();
            cmbModulo = oParametroEntidad.getModulosByModuloId() == null ? null : oParametroEntidad.getModulosByModuloId().getId();
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnElminiarVisible = isServidorReplicacion();
            nuevoRegistro = false;
            this.cargarLista();
            this.cargarModulos();
            seleccionarTipoParametro();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void elminar() {
        try {
            ParametroEntidad oParametroEntidad = oParametroEntidadService.obtenerParametroEntidadPorId(hfId);
            oParametroEntidadService.eliminar(oParametroEntidad);
            limpiar();
            cargarLista();
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "elminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void guardar() {
        try {
            ParametroEntidad oParametroEntidad = new ParametroEntidad();
            cmbModulo = cmbModulo == null ? 0 : cmbModulo;
            if (validarGuardar()) {
                mostrarMensajeInfo(mensajeValidacion);
                return;
            }

            if (nuevoRegistro) {
                if (somTipoParametro == null || somTipoParametro.equals("")) {
                    mostrarMensajeError("Seleccione un tipo de parámetro");
                    return;
                } else {
                    oParametroEntidad.setCodigo(txtCodigo);
                    oParametroEntidad.setDescriptor(txtDescriptor);
                    oParametroEntidad.setTipoValor(somTipoParametro);
                    oParametroEntidad.setListaValores(somTipoParametro.equals("L") ? itListaValor : null);
                    oParametroEntidad.setNombreValores(somTipoParametro.equals("L") ? itNombreListaValor : null);
                    oParametroEntidad.setSentencia(somTipoParametro.equals("V") ? itaSentenciaSQL : somTipoParametro.equals("S") ? itaSentenciaSQL : null);
                    oParametroEntidad.setValor(txtValor);
                    oParametroEntidad.setEntidadIdByEntidadId(oEntidadService.obtenerEntidadPorId(somDelegacion));
                    oParametroEntidad.setModulosByModuloId(cmbModulo != 0 ? oModuloService.obtenerModuloPorId(cmbModulo) : null);
                    oParametroEntidad.setAplicableUsuario(false);
                    oParametroEntidad.setCreadoPor(this.getUsuarioActual());
                    oParametroEntidad.setCreadoEl(this.getTimeNow());
                    oParametroEntidad.setCreadoEnIp(this.getRemoteIp());
                    oParametroEntidad.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                    oParametroEntidadService.guardar(oParametroEntidad);
                    mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                }
            } else {
                oParametroEntidad = oParametroEntidadService.obtenerParametroEntidadPorId(hfId);
                oParametroEntidad.setCodigo(txtCodigo);
                oParametroEntidad.setDescriptor(txtDescriptor);
                oParametroEntidad.setTipoValor(somTipoParametro);
                oParametroEntidad.setListaValores(somTipoParametro.equals("L") ? itListaValor : null);
                oParametroEntidad.setNombreValores(somTipoParametro.equals("L") ? itNombreListaValor : null);
                oParametroEntidad.setSentencia(somTipoParametro.equals("V") ? itaSentenciaSQL : somTipoParametro.equals("S") ? itaSentenciaSQL : null);
                oParametroEntidad.setValor(txtValor);
                oParametroEntidad.setEntidadIdByEntidadId(oEntidadService.obtenerEntidadPorId(somDelegacion));
                oParametroEntidad.setModulosByModuloId(cmbModulo != 0 ? oModuloService.obtenerModuloPorId(cmbModulo) : null);
                oParametroEntidad.setAplicableUsuario(false);
                oParametroEntidad.setModificadoPor(this.getUsuarioActual());
                oParametroEntidad.setModificadoEl(this.getTimeNow());
                oParametroEntidad.setModificadoEnIp(this.getRemoteIp());
                oParametroEntidad.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oParametroEntidadService.actualizar(oParametroEntidad);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oParametroEntidad.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public void validarSplit() {
        String[] valoresLista;
        String[] valoresNombres;
        if (itListaValor != null && itNombreListaValor != null) {
            valoresLista = itListaValor.split(";");
            valoresNombres = itNombreListaValor.split(";");
            if (valoresLista.length != valoresNombres.length) {
                mostrarMensajeInfo("Debe Ingresar Una Misma Cantidad de Valores En Los Campos Lista y Nombre de Valores");
            }
        }
    }

    /**
     * Este metodo se encarga de validar que cuando un usuario vaya a guardar listas de valores unicos o multiples,
     * estos tengan la misma cantidad de palabras; Ej: en el campo lista valor lo que el usuario escribe es s;n y en
     * nombre de valor el escribe si;no, ambos campos separados por el ; tiene dos palabras, si al momento de validar
     * estos campos no contienen la misma cantidad de palabras separadas por el ; no se procedera a guardar esta
     * información.
     *
     * @return se retorna un valor booleano true o false
     */
    public Boolean validarGuardar() {
        String[] valoresLista;
        String[] valoresNombres;

        switch (somTipoParametro) {
            case "L":
                valoresLista = itListaValor.split(";");
                valoresNombres = itNombreListaValor.split(";");
                if (valoresLista.length != valoresNombres.length) {
                    mensajeValidacion = "Debe Ingresar Una Misma Cantidad de Valores En Los Campos Lista y Nombre de Valores";
                    return true;
                }
                break;
            case "V":
            case "S":
                itaSentenciaSQL.toLowerCase();
                if (!itaSentenciaSQL.startsWith("select ") && !itaSentenciaSQL.startsWith("SELECT ")) {
                    mensajeValidacion = "La sentencia solo puede ser de tipo select";
                    return true;
                }
                break;
        }
        return false;
    }

    public void seleccionarTipoParametro() {
        somTipoParametro = somTipoParametro == null ? "" : somTipoParametro;
        switch (somTipoParametro) {
            case "N":
            case "C":
                visibleCaracter = true;
                visibleListaValores = false;
                visibleSentenciaSQL = false;
                break;
            case "L":
                visibleCaracter = true;
                visibleListaValores = true;
                visibleSentenciaSQL = false;
                break;
            case "V":
            case "S":
                visibleCaracter = true;
                visibleListaValores = false;
                visibleSentenciaSQL = true;
                break;
            default:
                visibleCaracter = false;
                visibleListaValores = false;
                visibleSentenciaSQL = false;
                break;
        }
    }

    public void eliminar() {
        try {
            ParametroEntidad oParametroEntidad = oParametroEntidadService.obtenerParametroEntidadPorId(hfId);
            oParametroEntidadService.eliminar(oParametroEntidad);
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            cargarLista();
            limpiar();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }
}