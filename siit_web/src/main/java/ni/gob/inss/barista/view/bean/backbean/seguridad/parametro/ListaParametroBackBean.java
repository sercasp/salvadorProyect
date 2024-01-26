package ni.gob.inss.barista.view.bean.backbean.seguridad.parametro;


import lombok.*;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ParametroService;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
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
 * BackBean para pantalla Parametroes</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/28/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
@Scope("view")
public class ListaParametroBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private Parametro selectedRow;
    private boolean nuevoRegistro;
    private Integer hfId;
    private Integer cmbModulo;
    private String txtCodigo;
    private String txtDescriptor;
    private String txtValor;
    private String txtBuscar;
    private String regExpCodigo;
    private String regExpDescriptor;
    private String mensajeValidacion;
    private String somTipoParametro;
    private String itListaValor;
    private String itNombreListaValor;
    private String itaSentenciaSQL;
    private List listaAuditoria;
    private List<Modulo> listaModulos;
    private List<Parametro> listaParametros;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private Boolean visibleCaracter;
    private Boolean visibleListaValores;
    private Boolean visibleSentenciaSQL;
    private ParametroService oParametroService;
    AuditoriaService oDialogAuditoriaService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaParametroBackBean(ParametroService oParametroService,  AuditoriaService oDialogAuditoriaService) {
        this.oParametroService = oParametroService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarLista();
        cargarValidaciones();
        cargarModulos();
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
            mostrarMensajeInfo("Libre del barista");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Parametro.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarModulos() {
        try {
            this.listaModulos = oParametroService.obtenerModulos();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarModulos()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarLista() {
        try {
            this.listaParametros = oParametroService.buscar(txtBuscar);
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
        txtDescriptor = null;
        txtValor = null;
        cmbModulo = null;
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
            Parametro oParametro = oParametroService.obtenerParametroPorId(id);
            hfId = oParametro.getId();
            txtCodigo = oParametro.getCodigo();
            txtDescriptor = oParametro.getDescriptor();
            somTipoParametro = oParametro.getTipoValor();
            itListaValor = oParametro.getListaValores() != null ? oParametro.getListaValores() : null;
            itNombreListaValor = oParametro.getNombreValores() != null ? oParametro.getNombreValores() : null;
            itaSentenciaSQL = oParametro.getSentencia() != null ? oParametro.getSentencia() : null;
            txtValor = oParametro.getValor();
            //cmbModulo = oParametro.getModulosByModuloId() == null ? null : oParametro.getModulosByModuloId().getId();
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
            Parametro oParametro = oParametroService.obtenerParametroPorId(hfId);
            oParametroService.eliminar(oParametro);
            limpiar();
            cargarLista();
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "elminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void guardar() {
        try {
            Parametro oParametro = new Parametro();
            cmbModulo = cmbModulo == null ? 0 : cmbModulo;
            if (validarGuardar()) {
                mostrarMensajeInfo(mensajeValidacion);
                return;
            }

            if (nuevoRegistro) {
                if (somTipoParametro == null || somTipoParametro.equals("")) {
                    mostrarMensajeError("Seleccione un tipo de parametro");
                    return;
                } else {
                    oParametro.setCodigo(txtCodigo);
                    oParametro.setDescriptor(txtDescriptor);
                    oParametro.setTipoValor(somTipoParametro);
                    oParametro.setListaValores(somTipoParametro.equals("L") ? itListaValor : null);
                    oParametro.setNombreValores(somTipoParametro.equals("L") ? itNombreListaValor : null);
                    oParametro.setSentencia(somTipoParametro.equals("V") ? itaSentenciaSQL : somTipoParametro.equals("S") ? itaSentenciaSQL : null);
                    oParametro.setValor(txtValor);
                    //oParametro.setModulosByModuloId(cmbModulo != 0 ? oModuloService.obtenerModuloPorId(cmbModulo) : null);
                    oParametro.setAplicableUsuario(false);
                    oParametro.setCreadoPor(this.getUsuarioActual());
                    oParametro.setCreadoEl(this.getTimeNow());
                    oParametro.setCreadoEnIp(this.getRemoteIp());
                    oParametro.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                    oParametroService.agregar(oParametro);
                    mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                }
            } else {
                oParametro = oParametroService.obtenerParametroPorId(hfId);
                oParametro.setCodigo(txtCodigo);
                oParametro.setDescriptor(txtDescriptor);
                oParametro.setTipoValor(somTipoParametro);
                oParametro.setListaValores(somTipoParametro.equals("L") ? itListaValor : null);
                oParametro.setNombreValores(somTipoParametro.equals("L") ? itNombreListaValor : null);
                oParametro.setSentencia(somTipoParametro.equals("V") ? itaSentenciaSQL : somTipoParametro.equals("S") ? itaSentenciaSQL : null);
                oParametro.setValor(txtValor);
                //oParametro.setModulosByModuloId(cmbModulo != 0 ? oModuloService.obtenerModuloPorId(cmbModulo) : null);
                oParametro.setAplicableUsuario(false);
                oParametro.setModificadoPor(this.getUsuarioActual());
                oParametro.setModificadoEl(this.getTimeNow());
                oParametro.setModificadoEnIp(this.getRemoteIp());
                oParametro.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oParametroService.actualizar(oParametro);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oParametro.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
            //mostrarMensajeError(MessagesResults.ERROR_GUARDAR);
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
            Parametro oParametro = oParametroService.obtenerParametroPorId(hfId);
            oParametroService.eliminar(oParametro);
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            cargarLista();
            limpiar();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }
}