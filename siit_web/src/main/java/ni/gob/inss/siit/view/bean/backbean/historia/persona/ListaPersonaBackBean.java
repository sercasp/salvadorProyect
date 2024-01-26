package ni.gob.inss.siit.view.bean.backbean.historia.persona;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.model.entity.catalogo.DivisionPolitica;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.application.LabelName;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.security.SystemSecurityException;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import ni.gob.inss.siit.businesslogic.service.historia.PersonaService;
import ni.gob.inss.siit.businesslogic.service.util.UtilService;
import ni.gob.inss.siit.model.entity.historia.Persona;
import ni.gob.inss.siit.view.utils.web.Utils;
import ni.gob.inss.siit.view.utils.web.calcularEdad;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Creado por  jvillanueva1 on 01/12/2015.
 * Modificado por jvillanueva 16/07/2020.
 * Modificado por jvillanueva 26/02/2021.
 * Modificado por jvillanueva 26/03/2021.
 * Modificado por jvillanueva 29/05/2021.
 * Modificado por jvillanueva 01/04/2022.
 */

@Data
@Named
@Scope("view")
public class ListaPersonaBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private Persona selectedRow;
    private Map<String, Object> personaSeleccionada;
    private boolean nuevoRegistro;
    private String txtBuscar;
    private Integer hfId;
    private String txtPrimerApellido;
    private String txtSegundoApellido;
    private String txtPrimerNombre;
    private String txtSegundoNombre;
    private String cmbSexo;
    private String cmbPaisNacimiento;
    private String cmbTempDepartamentoNacimiento;
    private String cmbMunicipioNacimiento;
    private Date dpFechaNacimiento;
    private String txtDni;
    private String cmbEtnia;
    private String cmbEstadoCivil;
    private String cmbPaisDomicilio;
    private String cmbTempDepartamentoDocmicilio;
    private String cmbMunicipioDomicilio;
    private Integer cmbLocalidadDomicilio;
    private String txtDireccion;
    private String txtDireccionActual;
    private String txtTelefono;
    private String txtMovil;
    private String txtCorreo;
    private String cmbApartadoPostal;
    private String cmbEscolaridad;
    private String cmbGrupoSanguineo;
    private String cmbFactorRh;
    private String cmbProfesion;
    private String txtObservacion;
    private Boolean rbVerificado;
    private Integer txtEdad;
    private String regExpNombre;
    private String regExpSoloNumero;
    private String regExpSoloLetras;
    private String regExpCorreo;
    private String regExpSoloLetrasConEspacio;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private List listaAuditoria;
    private List<Map<String, Object>> listaPersonas;
    private List listaLocalidad;
    private List listaEscolaridad;
    private List listaGrupoSanguineo;
    private List listaEtnia;
    private List listaEstadoCivil;
    private List listaPaisNacimiento;
    private List listaPaisDomicilio;
    private List listaProfesion;
    private List<DivisionPolitica> listaDepartamentoNacimiento;
    private List<DivisionPolitica> listaDepartamentoDomicilio;
    private List<DivisionPolitica> listaMunicipioNacimiento;
    private List<DivisionPolitica> listaMunicipioDomicilio;
    public LazyDataModel<Persona> persona;
    private Boolean otroPais;
    private Boolean autoriza;

    /**
     * ***********************************************************************************
     * DEPENDENCIAS
     * ***********************************************************************************
     */
    private PersonaService oPersonaService;
    private AuditoriaService oDialogAuditoriaService;
    private UtilService oUtilService;

    @Inject
    @Autowired
    public ListaPersonaBackBean(PersonaService oPersonaService,
                                AuditoriaService oDialogAuditoriaService,
                                UtilService oUtilService) {
        this.oPersonaService = oPersonaService;
        this.oAuditoriaService = oDialogAuditoriaService;
        this.oUtilService = oUtilService;
    }

    /**
     * ***********************************************************************************
     * CONSTRUCTOR
     * ***********************************************************************************
     */
    @PostConstruct
    public void init() throws SystemSecurityException {
        limpiar();
        cargarValidaciones();
    }

    /**
     * **********************************************************************************
     * ACCIONES
     * ***********************************************************************************
     */
    private void cargarValidaciones() {
        try {
            regExpNombre = RegExpresion.regExpDescripcion;
            regExpSoloNumero = RegExpresion.regExpSoloNumeros;
            regExpSoloLetras = RegExpresion.regExpSoloLetras;
            regExpCorreo = RegExpresion.regExpEmail;
            regExpSoloLetrasConEspacio = RegExpresion.regExpSoloLetrasConEspacio;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }



    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Persona.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarListaLocalidad() {
        try {
            this.listaLocalidad = oPersonaService.buscarLocalidades();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaLocalidad()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    private void cargarListaGrupoSanguineo() {
        try {
            this.listaGrupoSanguineo = oPersonaService.buscarGruposSanguineos();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaGrupoSanguineo()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    private void cargarListaEscolaridad() {
        try {
            this.listaEscolaridad = oPersonaService.buscarEscolaridades();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaEscolaridad()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    private void cargarListaEtnia() {
        try {
            this.listaEtnia = oPersonaService.buscarEtnias();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaEtnia()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    private void cargarListaEstadoCivil() {
        try {
            this.listaEstadoCivil = oPersonaService.buscarEstadosCiviles();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaEstadoCivil()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    private void cargarListaProfesion() {
        try {
            this.listaProfesion = oPersonaService.buscarProfesion();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaProfesion()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }
    private void cargarPaises() {
        try {
            this.listaPaisNacimiento = oPersonaService.buscarPaises();
            this.listaPaisDomicilio = oPersonaService.buscarPaises();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarPaises()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void seleccionandoPaisNacimiento(ValueChangeEvent event) {
        if (event.getNewValue() == null) {
            mostrarMensajeInfo("No esta el país");
        } else if (event.getNewValue().equals("NI")) {
            otroPais = false;
        } else {
            otroPais = true;
            cmbTempDepartamentoNacimiento = null;
            cmbMunicipioNacimiento = null;
        }
    }

    public void seleccionandoPaisDomicilio(ValueChangeEvent event) {
        if (event.getNewValue() == null) {
            mostrarMensajeInfo("No esta el país Domicilio");
        } else if (event.getNewValue().equals("NI")) {
            otroPais = false;
        } else {
            otroPais = true;
            cmbTempDepartamentoDocmicilio = null;
            cmbMunicipioDomicilio = null;
        }
    }





    private boolean validar() {
        Utils u = new Utils();
        u.main(txtDni);
        onDateSelect();
        return u.getVerificado();
    }

    public void cargarLista() {
        try {
            if (txtBuscar == null || txtBuscar.equals("")) {
                mostrarMensajeInfo("Debe ingresar un nombre para iniciar la búsqueda");
            } else {
                this.listaPersonas = oPersonaService.buscar(txtBuscar);

                LabelName dialog = new LabelName();
                if (listaPersonas.size() == 0) {
                    mostrarMensajeInfo(dialog.getTableEmptyMessage());
                }
            }
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
        txtPrimerApellido = null;
        txtSegundoApellido = null;
        txtPrimerNombre = null;
        txtSegundoNombre = null;
        cmbSexo = null;
        cmbPaisNacimiento = null;
        cmbTempDepartamentoNacimiento = null;
        cmbMunicipioNacimiento = null;
        dpFechaNacimiento = null;
        txtDni = null;
        cmbEtnia = null;
        cmbEstadoCivil = null;
        cmbProfesion=null;
        cmbPaisDomicilio = null;
        cmbTempDepartamentoDocmicilio = null;
        cmbMunicipioDomicilio = null;
        cmbLocalidadDomicilio = null;
        txtDireccion = null;
        txtDireccionActual = null;
        txtTelefono = null;
        txtMovil = null;
        txtCorreo = null;
        cmbApartadoPostal = null;
        cmbEscolaridad = null;
        cmbGrupoSanguineo = null;
        cmbFactorRh = null;
        txtObservacion = null;
        rbVerificado = null;
        nuevoRegistro = true;

    }

    public void editar() {
        if (personaSeleccionada != null) {
            cargar(Integer.parseInt(personaSeleccionada.get("id").toString()));
        }
    }

    public void agregar() {
        limpiar();
        cargarListaGrupoSanguineo();
        cargarListaEscolaridad();
        cargarListaEtnia();
        cargarListaEstadoCivil();
        cargarListaProfesion();
        cargarPaises();
    }

    private void cargar(Integer id) {
        try {
            Persona oPersona = oPersonaService.obtenerPersonaPorId(id);
            hfId = oPersona.getId();
            txtPrimerApellido = oPersona.getPrimerApellido();
            txtSegundoApellido = oPersona.getSegundoApellido();
            txtPrimerNombre = oPersona.getPrimerNombre();
            txtSegundoNombre = oPersona.getSegundoNombre();
            cmbSexo = oPersona.getSexo();
            cmbPaisNacimiento = oPersona.getPaisNacimientoByPaisNacimientoCodigoAlfa2() == null ? null : oPersonaService.obtenerPais(oPersona.getPaisNacimientoByPaisNacimientoCodigoAlfa2().getCodigoAlfa2()).get(0).getCodigoAlfa2();
//            cmbTempDepartamentoNacimiento = oPersona.getMunicipioNacimientoByMunicipioNacimientoCodigo() == null ? null : oPersonaService.obtenerDepartamentoPorMunicipio(oPersona.getMunicipioNacimientoByMunicipioNacimientoCodigo().getCodigo()).get(0).getDivisionesPoliticas().getId();

            if(cmbMunicipioNacimiento == null && cmbTempDepartamentoNacimiento == null){
                cmbTempDepartamentoNacimiento = "";
                cmbMunicipioNacimiento = "";
            }else{
                cmbTempDepartamentoNacimiento =  oPersona.getMunicipioNacimientoByMunicipioNacimientoCodigo().getCodigo().substring(0,2);
                cmbMunicipioNacimiento =         oPersona.getMunicipioNacimientoByMunicipioNacimientoCodigo().getCodigo();
            }


            dpFechaNacimiento =      oPersona.getFechaNacimiento();
            onDateSelect();
            txtDni = oPersona.getDni();
            cmbEtnia = oPersona.getEtniaByEtniaCodigo() == null ? null : oPersonaService.obtenerCatalogo(oPersona.getEtniaByEtniaCodigo().getCodigo()).get(0).getCodigo();
            cmbEstadoCivil = oPersona.getEstadoCivilByEstadoCivilCodigo() == null ? null : oPersonaService.obtenerCatalogo(oPersona.getEstadoCivilByEstadoCivilCodigo().getCodigo()).get(0).getCodigo();
            cmbProfesion = oPersona.getRefProfesion() == null ? null : oPersonaService.obtenerCatalogo(oPersona.getRefProfesion().getCodigo()).get(0).getCodigo();
            cmbPaisDomicilio = oPersona.getPaisDomicilioByPaisDomicilioCodigoAlfa2() == null ? null : oPersonaService.obtenerPais(oPersona.getPaisDomicilioByPaisDomicilioCodigoAlfa2().getCodigoAlfa2()).get(0).getCodigoAlfa2();
            cmbTempDepartamentoDocmicilio = oPersona.getMunicipioDomicilioByMunicipioDomicilioCodigo().getCodigo().substring(0,2);
            cmbMunicipioDomicilio =         oPersona.getMunicipioDomicilioByMunicipioDomicilioCodigo().getCodigo();
//            cmbLocalidadDomicilio = oPersona.getLocalidadDomicilioByLocalidadDomicilioId().getDivisionesPoliticas().getId();
            txtDireccion = oPersona.getDireccion();
            txtDireccionActual = oPersona.getDireccionActual();
            txtTelefono = oPersona.getTelefono();
            txtMovil = oPersona.getMovil();
            txtCorreo = oPersona.getCorreo();
            cmbApartadoPostal = oPersona.getApartadoPostal();
            cmbEscolaridad = oPersona.getEscolaridadByEscolaridadCodigo() == null ? null : oPersonaService.obtenerCatalogo(oPersona.getEscolaridadByEscolaridadCodigo().getCodigo()).get(0).getCodigo();
            cmbGrupoSanguineo = oPersona.getGrupoSanguineoByGrupoSanguineo() == null ? null : oPersonaService.obtenerCatalogo(oPersona.getGrupoSanguineoByGrupoSanguineo().getCodigo()).get(0).getCodigo();
            cmbFactorRh = oPersona.getFactorRh();
            txtObservacion = oPersona.getObservacion();
            rbVerificado = oPersona.getVerificado();
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnElminiarVisible = isServidorReplicacion();

            nuevoRegistro = false;
            cargarListaGrupoSanguineo();
            cargarListaEscolaridad();
            cargarListaEtnia();
            cargarListaEstadoCivil();
            cargarListaProfesion();
            cargarPaises();

//            if (cmbPaisNacimiento != "157"){
//                if (cmbTempDepartamentoDocmicilio != null) {
//                    cargarMunicipiosDomicilio(cmbTempDepartamentoDocmicilio);
//                }
//            }else {
//                if (cmbTempDepartamentoNacimiento != null) {
//                    cargarMunicipiosNacimiento(cmbTempDepartamentoNacimiento);
//                }
//                if (cmbTempDepartamentoDocmicilio != null) {
//                    cargarMunicipiosDomicilio(cmbTempDepartamentoDocmicilio);
//                }
//            }
            PrimeFaces.current().ajax().update("formPersonas");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void elminar() {
        try {
            Persona oPersona = oPersonaService.obtenerPersonaPorId(hfId);
            oPersonaService.eliminar(oPersona);
            limpiar();
            cargarLista();
            mostrarMensajeInfo(MessagesResults.EXITO_ELIMINAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void guardar() {
        if (cmbPaisNacimiento != "157"){
            guardarCompleto();
        }else

        if (validar()) {
            if (txtEdad < 16) {
                mostrarMensajeError("La Persona debe ser mayor de 16 años para aplicar.");
            } else {
                guardarCompleto();
            }
        } else {
            mostrarMensajeError("Cédula Incorrecta!.");
        }
    }

    private void guardarCompleto() {
        java.sql.Date sqlDateInicio = new java.sql.Date(dpFechaNacimiento.getTime());
        try {
            Persona oPersona;
            if (nuevoRegistro) {
                oPersona = new Persona();
                oPersona.setPrimerApellido(txtPrimerApellido.toUpperCase());
                oPersona.setSegundoApellido(txtSegundoApellido.toUpperCase());
                oPersona.setPrimerNombre(txtPrimerNombre.toUpperCase());
                oPersona.setSegundoNombre(txtSegundoNombre.toUpperCase());
                oPersona.setSexo(cmbSexo.toUpperCase());
                oPersona.setPaisNacimientoByPaisNacimientoCodigoAlfa2(cmbPaisNacimiento == null ? null : oPersonaService.obtenerPais(cmbPaisNacimiento).get(0));
                oPersona.setMunicipioNacimientoByMunicipioNacimientoCodigo(cmbMunicipioNacimiento == null ? null : oPersonaService.obtenerMunicipio(cmbMunicipioNacimiento).get(0));
                oPersona.setFechaNacimiento(sqlDateInicio);
                oPersona.setDni(txtDni.toUpperCase());
                oPersona.setEtniaByEtniaCodigo(cmbEtnia == null ? null : oPersonaService.obtenerCatalogo(cmbEtnia).get(0));
                oPersona.setEstadoCivilByEstadoCivilCodigo(cmbEstadoCivil==null? null : oPersonaService.obtenerCatalogo(cmbEstadoCivil).get(0));
                oPersona.setRefProfesion(cmbProfesion==null? null : oPersonaService.obtenerCatalogo(cmbProfesion).get(0));
                oPersona.setPaisDomicilioByPaisDomicilioCodigoAlfa2(cmbPaisDomicilio == null ? null : oPersonaService.obtenerPais(cmbPaisDomicilio).get(0));
                oPersona.setMunicipioDomicilioByMunicipioDomicilioCodigo(cmbMunicipioDomicilio == null ? null : oPersonaService.obtenerMunicipio(cmbMunicipioDomicilio).get(0));
                oPersona.setLocalidadDomicilioByLocalidadDomicilioId(null);
                oPersona.setDireccion(txtDireccion.equals("") ? null : txtDireccion.toUpperCase());
                oPersona.setDireccionActual(txtDireccionActual.equals("") ? null : txtDireccionActual.toUpperCase());
                oPersona.setTelefono(txtTelefono.equals("") ? null : txtTelefono.toUpperCase());
                oPersona.setMovil(txtMovil.equals("") ? null : txtMovil.toUpperCase());
                oPersona.setCorreo(txtCorreo.equals("") ? null : txtCorreo.toLowerCase());
                oPersona.setApartadoPostal(cmbApartadoPostal == null ? null : cmbApartadoPostal.toUpperCase());
                oPersona.setEscolaridadByEscolaridadCodigo(cmbEscolaridad == null ? null : oPersonaService.obtenerCatalogo(cmbEscolaridad).get(0));
                oPersona.setGrupoSanguineoByGrupoSanguineo(cmbGrupoSanguineo == null? null : oPersonaService.obtenerCatalogo(cmbGrupoSanguineo).get(0));
                oPersona.setFactorRh(cmbFactorRh == null ? null : cmbFactorRh.toUpperCase());
                oPersona.setObservacion(txtObservacion.equals("") ? null : txtObservacion.toUpperCase());
                oPersona.setVerificado(rbVerificado);
                oPersona.setFallecido(false);
                oPersona.setCreadoPor(this.getUsuarioActual());
                oPersona.setCreadoEl(this.getTimeNow());
                oPersona.setCreadoEnIp(this.getRemoteIp());
                oPersona.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oPersonaService.agregar(oPersona);
                mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
            } else {
                oPersona = oPersonaService.obtenerPersonaPorId(hfId);
                oPersona.setPrimerApellido(txtPrimerApellido.toUpperCase());
                oPersona.setSegundoApellido(txtSegundoApellido.toUpperCase());
                oPersona.setPrimerNombre(txtPrimerNombre.toUpperCase());
                oPersona.setSegundoNombre(txtSegundoNombre.toUpperCase());
                oPersona.setSexo(cmbSexo.toUpperCase());
                oPersona.setPaisNacimientoByPaisNacimientoCodigoAlfa2(cmbPaisNacimiento== null ? null : oPersonaService.obtenerPais(cmbPaisNacimiento).get(0));
                oPersona.setMunicipioNacimientoByMunicipioNacimientoCodigo(cmbMunicipioNacimiento==null ? null : oPersonaService.obtenerMunicipio(cmbMunicipioNacimiento).get(0));
                oPersona.setFechaNacimiento(sqlDateInicio);
                oPersona.setDni(txtDni.toUpperCase());
                oPersona.setEtniaByEtniaCodigo(cmbEtnia==null  ? null : oPersonaService.obtenerCatalogo(cmbEtnia).get(0));
                oPersona.setEstadoCivilByEstadoCivilCodigo(cmbEstadoCivil == null ? null : oPersonaService.obtenerCatalogo(cmbEstadoCivil).get(0));
                oPersona.setRefProfesion(cmbEstadoCivil == null ? null : oPersonaService.obtenerCatalogo(cmbProfesion).get(0));
                oPersona.setPaisDomicilioByPaisDomicilioCodigoAlfa2(cmbPaisDomicilio == null ? null : oPersonaService.obtenerPais(cmbPaisDomicilio).get(0));
                oPersona.setMunicipioDomicilioByMunicipioDomicilioCodigo(cmbMunicipioDomicilio == null ? null : oPersonaService.obtenerMunicipio(cmbMunicipioDomicilio).get(0));
                //oPersona.setLocalidadDomicilioByLocalidadDomicilioId(cmbLocalidadDomicilio == 0 ? null : oPersonaService.obtenerLocalidad(cmbLocalidadDomicilio).get(0));
                oPersona.setLocalidadDomicilioByLocalidadDomicilioId(null);
                oPersona.setDireccion(txtDireccion.equals("") ? null : txtDireccion.toUpperCase());
                oPersona.setDireccionActual(txtDireccionActual.equals("") ? null : txtDireccionActual.toUpperCase());
                oPersona.setTelefono(txtTelefono.equals("") ? null : txtTelefono.toUpperCase());
                oPersona.setMovil(txtMovil.equals("") ? null : txtMovil.toUpperCase());
                oPersona.setCorreo(txtCorreo.equals("") ? null : txtCorreo);
                oPersona.setApartadoPostal(cmbApartadoPostal == null ? null : cmbApartadoPostal.toUpperCase());
                oPersona.setEscolaridadByEscolaridadCodigo(cmbEscolaridad == null? null : oPersonaService.obtenerCatalogo(cmbEscolaridad).get(0));
                oPersona.setGrupoSanguineoByGrupoSanguineo(cmbGrupoSanguineo == null? null : oPersonaService.obtenerCatalogo(cmbGrupoSanguineo).get(0));
                oPersona.setFactorRh(cmbFactorRh == null ? null : cmbFactorRh.toUpperCase());
                oPersona.setObservacion(txtObservacion.equals("") ? null : txtObservacion.toUpperCase());
                oPersona.setVerificado(rbVerificado);
                oPersona.setFallecido(false);
                oPersona.setModificadoPor(this.getUsuarioActual());
                oPersona.setModificadoEl(this.getTimeNow());
                oPersona.setModificadoEnIp(this.getRemoteIp());
                oPersona.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oPersonaService.actualizar(oPersona);
                mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oPersona.getId());
            PrimeFaces.current().executeScript("PF('modalPersonas').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public void onDateSelect() {
        java.sql.Date sqlDateInicio = new java.sql.Date(dpFechaNacimiento.getTime());
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String d = formatter.format(sqlDateInicio);
        calcularEdad u = new calcularEdad();
        txtEdad = u.calcularEdad(d);
    }

    /**
     * Cerrar la Modal Persona
     */
    public void cerrarModal() {
        PrimeFaces.current().executeScript("PF('modalPersonas').hide()");
    }

}