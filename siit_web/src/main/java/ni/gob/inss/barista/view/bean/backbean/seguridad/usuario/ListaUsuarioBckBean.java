package ni.gob.inss.barista.view.bean.backbean.seguridad.usuario;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ni.gob.inss.barista.businesslogic.service.core.SeguridadService;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.infraestructura.EntidadService;
import ni.gob.inss.barista.businesslogic.service.seguridad.PermisoService;
import ni.gob.inss.barista.businesslogic.service.seguridad.RoleService;
import ni.gob.inss.barista.businesslogic.service.seguridad.UsuarioService;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Permiso;
import ni.gob.inss.barista.model.entity.seguridad.Role;

import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.bean.session.SessionList;
import ni.gob.inss.barista.view.utils.treeMenu.Tree;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import java.io.*;
import java.util.*;

/**
AUTOR scastillo
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Named
@Scope("view")
public class ListaUsuarioBckBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private Role rolNoAgregadoSeleccionado;
    private Usuario selectedRow;
    private TreeNode detalleRol;
    private Entidad selectedEntidadSinAgregar;
    private String txtUsername;
    private String txtNombres;
    private String txtApellidos;
    private String txtEmail;
    private String txtPassword;
    private String txtBuscar;
    private String txtBuscarRol;
    private String txtReferencia;
    private String claveTemporal;
    private String regExpNombresyApellidos;
    private String regExpEmail;
    private String regExpReferencia;
    private String regExpUsername;
    private String regExpClave;
    private String txtCambiarClave;
    private String txtCambiarConfirmacion;
    private String claveConfirmacion;
    private String seleccionandoRol;
    private String seleccionandoEntidad;
    private String olClaveGenerada;
    private String somTipoGeneracionClave;
    private String pwContrasena;
    private String pwConfirmacionContrasena;
    private String txtTelefono;
    private String regExpSoloNumeros = RegExpresion.regExpSoloNumeros;
    private Integer hfId;
    private Integer index = 0;
    private Integer seleccionandoRolId;
    private Integer seleccionandoEntidadId;
    private boolean nuevoRegistro;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private boolean btnAgregarPermisoVisible;
    private boolean btnQuitarPermisoVisible;
    private boolean btnCambiarClaveVisible;
    private boolean usuarioConectado;
    private Boolean rbVerInfoPantalla;
    private Boolean rbCambiarClaveTemporal;
    private Boolean rbVerInfoError;
    private Boolean rbVerAuditoria;
    private Boolean rbSuperAdministrador;
    private Boolean rbAuditarInicioSession;
    private Boolean rbAuditarNavegacion;
    private Boolean rbPasivo;
    private Boolean rbClaveTemporal;
    private Boolean esClaveManual;
    private Boolean esClaveAleatoria;
    private Boolean sbbClaveTemporal;
    private Boolean cambiarContrasena;
    private List<Map<String, Object>> selectedRol;
    private Map<String, Object> selectedEntidad;
    private Map<String, Object> usuarioImportarSeleccionado;
    private List listaAuditoria;
    private List<Role> selectedAgregarRol;
    private List<Role> listaAgregarRoles;
    private List<Usuario> listaUsuarios;
    private List<Entidad> listaEntidadesSinAgregar;
    private List<Map<String, Object>> listaRoles;
    private List<Map<String, Object>> listaEntidades;
    private List<Map<String, Object>> listaUsuariosImportar;
    private String empleadoSeleccionado;
    private List<Map<String,Object>> listaEmpleados;

    /**
     * **********************************************************************************
     * DEPENDENCIAS
     * **********************************************************************************
     */
    @Autowired
    private UsuarioService oUsuarioService;
    private SeguridadService oSeguridadService;

    @Autowired
    AuditoriaService oDialogAuditoriaService;

    @Autowired
    RoleService oRoleService;

    @Autowired
    EntidadService oEntidadService;

    @Autowired
    PermisoService oPermisoService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaUsuarioBckBean(UsuarioService oUsuarioService, SeguridadService oSeguridadService, AuditoriaService oDialogAuditoriaService,
                                RoleService oRoleService, EntidadService oEntidadService, PermisoService oPermisoService
                                ) {
        this.oUsuarioService = oUsuarioService;
        this.oSeguridadService = oSeguridadService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
        this.oRoleService = oRoleService;
        this.oEntidadService = oEntidadService;
        this.oPermisoService = oPermisoService;
    }

    @PostConstruct
    public void init() {
        cargarLista();
        cargarValidaciones();
        nuevoRegistro = true;
        txtBuscarRol = "";
        esClaveAleatoria = false;
        esClaveManual = false;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    private void cargarValidaciones() {
        try {
            regExpNombresyApellidos = RegExpresion.regExpSoloLetrasConEspacio;
            regExpEmail = RegExpresion.regExpEmail;
            regExpReferencia = RegExpresion.regExpDescripcion;
            regExpUsername = RegExpresion.regExpSoloLetras;
            regExpClave = RegExpresion.regExpAlphaNumeric;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }


    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Usuario.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarLista() {
        try {
            txtBuscar = txtBuscar == null ? "" : txtBuscar;
            this.listaUsuarios = oUsuarioService.buscar(txtBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void limpiar() {
        limpiarCamposPermisos();
        btnElminiarVisible = false;
        btnGuardarVisible = true;
        btnAuditoriaVisible = false;
        btnCambiarClaveVisible = false;
        txtBuscar = "";
        hfId = null;
        txtUsername = null;
        txtNombres = null;
        txtApellidos = null;
        txtEmail = null;
        txtPassword = null;
        txtReferencia = null;
        nuevoRegistro = true;
        listaEntidades = null;
        listaRoles = null;
        rbPasivo = false;
        rbAuditarNavegacion = false;
        rbAuditarInicioSession = false;
        rbVerInfoError = false;
        rbVerInfoPantalla = false;
        rbVerAuditoria = false;
        rbSuperAdministrador = false;
        claveTemporal = null;
        rbClaveTemporal = false;
        usuarioConectado = false;
        cambiarContrasena = false;
        esClaveAleatoria = false;
        esClaveManual = false;
        pwContrasena = null;
        pwConfirmacionContrasena = null;
        somTipoGeneracionClave = null;
        rolNoAgregadoSeleccionado = null;
        selectedRow = null;
        txtTelefono = "";
        empleadoSeleccionado = "";
        TabView tab = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("formDetalle:tabV");
        tab.setActiveIndex(0);
    }

    public void limpiarCamposPermisos() {
        seleccionandoEntidadId = null;
        seleccionandoEntidad = null;
        seleccionandoRolId = null;
        seleccionandoRol = null;
        selectedEntidadSinAgregar = null;
        rolNoAgregadoSeleccionado = null;
        selectedRol = null;
    }

    public void editar() {
        if (selectedRow != null) {
            cargar(selectedRow.getId());
            esClaveAleatoria = false;
            esClaveManual = false;
            TabView tab = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("formDetalle:tabV");
            tab.setActiveIndex(0);
        } else {
            mostrarMensajeInfo(MessagesResults.SELECCIONE_UN_REGISTRO);
        }
    }

    private void cargar(Integer id) {
        try {
            btnGuardarVisible = true;
            btnAgregarPermisoVisible = true;
            btnQuitarPermisoVisible = true;
            btnElminiarVisible = true;
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnCambiarClaveVisible = true;
            rbClaveTemporal = false;
            cambiarContrasena = false;
            claveTemporal = null;
            somTipoGeneracionClave = null;
            pwContrasena = null;
            pwConfirmacionContrasena = null;
            Usuario oUsuario = oUsuarioService.obtenerUsuarioPorId(id);
            hfId = oUsuario.getId();
            txtUsername = oUsuario.getUsername();
            txtNombres = oUsuario.getNombres();
            txtApellidos = oUsuario.getApellidos();
            txtEmail = oUsuario.getEmail();
            txtReferencia = oUsuario.getReferencia();
            rbVerInfoPantalla = oUsuario.getVerInfoPantalla();
            rbVerInfoError = oUsuario.getVerInfoErrores();
            rbVerAuditoria = oUsuario.getVerAuditoria();
            rbSuperAdministrador = oUsuario.getSuperAdministrador();
            rbAuditarInicioSession = oUsuario.getAuditarInicioSesion();
            rbAuditarNavegacion = oUsuario.getAuditarNavegacion();
            txtTelefono = oUsuario.getTelefono();
            rbPasivo = oUsuario.getPasivo();
            nuevoRegistro = false;
            this.cargarLista();
            listaEntidades = oUsuarioService.listaEntidades(oUsuario);
            listaRoles = oUsuarioService.listaRoles(oUsuario, getEntidadActual());
            usuarioConectado = SessionList.contains(oUsuario.getUsername());

            if (oUsuario.getSuperAdministrador() && !getUsuarioActual().getSuperAdministrador()) {
                mostrarMensajeError("Este usuario no puede ser modificado");
                btnElminiarVisible = false;
                btnGuardarVisible = false;
                btnAgregarPermisoVisible = false;
                btnQuitarPermisoVisible = false;
                btnCambiarClaveVisible = false;
            }
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void eliminar() {
        try {
            Usuario oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
            if (validarEliminar(hfId)) {
                return;
            }
            if (oUsuario.getCantidadInicioSesion() > 0) {
                mostrarMensajeError("No puede eliminar este usuario por que ya inicio sesión");
                return;
            }
            oUsuarioService.quitarTodosRoles(oUsuario);
            oUsuarioService.eliminar(oUsuario);
            limpiar();
            cargarLista();
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    /**
     * Este metodo verifica que un usuario no se encuentre relacionado con alguna otra tabla, de ser así
     * no podra ser eliminado
     *
     * @param prIdUsuario parametro de busqueda
     * @return retorna un valor booleano
     */
    private Boolean validarEliminar(Integer prIdUsuario) {
        List<Usuario> listaUsuariosPermiso = oUsuarioService.verificarPermisosUsuario(prIdUsuario);
        List<Usuario> listaUsuariosParametro = oUsuarioService.verificarParametroUsuario(prIdUsuario);
        List<Usuario> listaUsuariosSesionUsuario = oUsuarioService.verificarSesionUsuario(prIdUsuario);
        List<Usuario> listaUsuariosSesionModulo = oUsuarioService.verificarSesionUsuarioModulo(prIdUsuario);
        List<Usuario> listaUsuariosNavegacionUsuario = oUsuarioService.verificarNavegacionUsuario(prIdUsuario);
        List<Usuario> listaUsuariosRecursos = oUsuarioService.verificarRecursoUsuario(prIdUsuario);

        if (listaUsuariosPermiso.size() > 0) {
            mostrarMensajeError("No puede eliminar este usuario por que tiene permisos asociados");
            return true;
        }
        if (listaUsuariosParametro.size() > 0) {
            mostrarMensajeError("No puede eliminar este usuario por que ya inicio sesión");
            return true;
        }
        if (listaUsuariosSesionUsuario.size() > 0) {
            mostrarMensajeError("No puede eliminar este usuario por que ya inicio sesión");
            return true;
        }
        if (listaUsuariosSesionModulo.size() > 0) {
            mostrarMensajeError("No puede eliminar este usuario por que ya inicio sesión");
            return true;
        }
        if (listaUsuariosNavegacionUsuario.size() > 0) {
            mostrarMensajeError("No puede eliminar este usuario por que ya inicio sesión");
            return true;
        }
        if (listaUsuariosRecursos.size() > 0) {
            mostrarMensajeError("No puede eliminar este usuario por que ya inicio sesión");
            return true;
        }
        return false;
    }



    public void guardar() {
        try {

            if (validarCamposContrasena()) {
                return;
            }
            Usuario newUsuario = new Usuario();

            if (!nuevoRegistro){
                newUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
            }

            newUsuario.setUsername(txtUsername);
            newUsuario.setNombres(txtNombres);
            newUsuario.setApellidos(txtApellidos);
            newUsuario.setEmail(txtEmail);
            newUsuario.setReferencia(txtReferencia);
            newUsuario.setVerAuditoria(rbVerAuditoria);
            newUsuario.setVerInfoErrores(rbVerInfoError);
            newUsuario.setVerInfoPantalla(rbVerInfoPantalla);
            newUsuario.setTelefono(txtTelefono);
            newUsuario.setSuperAdministrador(true);
            newUsuario.setAuditarInicioSesion(rbAuditarInicioSession);
            newUsuario.setAuditarNavegacion(rbAuditarNavegacion);

            if (nuevoRegistro) {

                String tmpClave = claveTemporal;

                newUsuario.setPassword(oSeguridadService.encode(pwContrasena));
                newUsuario.setPasswordTemporal(sbbClaveTemporal);
                newUsuario.setCantidadInicioSesion(0);
                newUsuario.setTelefono(txtTelefono);
                newUsuario.setIntentosFallidos(0);
                newUsuario.setCreadoPor(this.getUsuarioActual());
                newUsuario.setCreadoEl(this.getTimeNow());
                newUsuario.setCreadoEnIp(this.getRemoteIp());
                newUsuario.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                newUsuario.setPasivo(false);

                oUsuarioService.agregar(newUsuario);

                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);

//                selectedRow = newUsuario;
                cargar(newUsuario.getId());
                claveTemporal = tmpClave;
                rbClaveTemporal = true;
                btnCambiarClaveVisible = false;

            } else {

                pwContrasena = pwContrasena == null ? "" : pwContrasena;

                if (!pwContrasena.equals("")) {
                    newUsuario.setPassword(oSeguridadService.encode(pwContrasena));
                    newUsuario.setPasswordTemporal(sbbClaveTemporal);
                }
                newUsuario.setModificadoPor(this.getUsuarioActual());
                newUsuario.setModificadoEl(this.getTimeNow());
                newUsuario.setModificadoEnIp(this.getRemoteIp());
                newUsuario.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                newUsuario.setPasivo(rbPasivo);
                newUsuario.setModificadoEnOrdenador(WebUtils.getRemoteHostName());

                oUsuarioService.actualizar(newUsuario);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
                cargar(newUsuario.getId());
                cambiarContrasena = false;
            }
            nuevoRegistro = false;
            PrimeFaces.current().ajax().update("formDetalle:tabV");
            PrimeFaces.current().ajax().update("formLista");
        } catch (Exception e) {
            if (e.getClass().getName().equals("javax.validation.ConstraintViolationException")) {
                mostrarMensajeError(((ConstraintViolationImpl) ((ConstraintViolationException) e).getConstraintViolations().toArray()[0]).getMessageTemplate());
            } else {
                mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
            }
        }
    }


    public void agregarRol() {
        if (selectedEntidadSinAgregar == null) {
            mostrarMensajeInfo("Seleccione una entidad");
            return;
        }
        if (rolNoAgregadoSeleccionado == null) {
            mostrarMensajeInfo("Seleccione un rol");
            return;
        }
        try {
            Permiso oPermiso = new Permiso();
            oPermiso.setRolesByRolId(oRoleService.obtener(seleccionandoRolId));
            oPermiso.setEntidad(oEntidadService.obtenerEntidad(seleccionandoEntidadId));
            oPermiso.setUsuariosByUsuarioId(oUsuarioService.obtenerUsuarioPorId(selectedRow.getId()));
            oPermiso.setCreadoPor(this.getUsuarioActual());
            oPermiso.setCreadoEl(this.getTimeNow());
            oPermiso.setCreadoEnIp(this.getRemoteIp());
            oPermiso.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
            oPermisoService.agregar(oPermiso);
            mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
            listaEntidades = oUsuarioService.listaEntidades(oUsuarioService.obtenerUsuarioPorId(selectedRow.getId()));
            listaRoles = oUsuarioService.listaRoles(selectedRow, getEntidadActual());
            limpiarCamposPermisos();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "agregarRol()", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void quitarRol() {
        Usuario oUsuario;
        try {
            if (selectedRol == null) {
                mostrarMensajeInfo(MessagesResults.SELECCIONE_UN_REGISTRO);
            } else {
                oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);

                for (Map<String, Object> rol : selectedRol) {
                    Permiso oPermiso;
                    oPermiso = oPermisoService.obtener(Integer.parseInt(rol.get("id").toString()));
                    oUsuarioService.quitarRol(oUsuario, oPermiso, getEntidadActual());
                }
                listaRoles = oUsuarioService.listaRoles(oUsuario, getEntidadActual());
                listaEntidades = oUsuarioService.listaEntidades(oUsuario);
                limpiarCamposPermisos();
                selectedRol = null;
                mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "quitarRol()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    /**
     * Este metodo verifica que los campos de la contraseña esten llenos al guardar un usuario
     *
     * @return retorna un valor booleano
     */
    private Boolean validarCamposContrasena() {
        pwContrasena = pwContrasena == null ? "" : pwContrasena;
        pwConfirmacionContrasena = pwConfirmacionContrasena == null ? "" : pwConfirmacionContrasena;

        if(nuevoRegistro ){
            if (pwContrasena.equals("") && pwConfirmacionContrasena.equals("")) {
                mostrarMensajeInfo("La contraseña es requerida para guardar un usuario");
                mostrarMensajeInfo("La confirmación de la contraseña es requerida para guardar un usuario");
                return true;
            }
            if (pwContrasena.equals("")) {
                mostrarMensajeInfo("La contraseña es requerida para guardar un usuario");
                return true;
            }
            if (pwConfirmacionContrasena.equals("")) {
                mostrarMensajeError("La confirmación de la contraseña es requerida para guardar un usuario");
                return true;
            }

        }

        if (!pwContrasena.equals("") && pwContrasena.length() <= 5) {
            mostrarMensajeError("La contraseña debe tener mínimo 6 caracteres");
            return true;
        }

        if (!pwContrasena.equals(pwConfirmacionContrasena)) {
            mostrarMensajeError("La clave y la confirmación de la clave no coinciden");
            return true;
        }


        return false;
    }

    public void generarClaveTemporal() {
        Usuario oUsuario;
        try {
            if (nuevoRegistro) {
                pwContrasena = oSeguridadService.generarPassword();
                pwConfirmacionContrasena = pwContrasena;
            } else {
                oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
                pwContrasena = oSeguridadService.generarPassword();
                pwConfirmacionContrasena = pwContrasena;

                oSeguridadService.cambiarClave(oUsuario, pwContrasena, pwConfirmacionContrasena, true);
                cambiarContrasena = true;
                rbClaveTemporal = true;
                btnCambiarClaveVisible = false;
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "generarClaveTemporal()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public void obtenerDetalleRole(ToggleEvent event) {
        Role oRole = (Role) event.getData();
        List<Map<String, Object>> listaResult = oRoleService.obtenerModulosMenuPorRole(oRole);
        Tree oTree = new Tree("Menus", "id", "nombre", "menuId", "moduloId", "estado");
        oTree.setRegistros(listaResult);
        detalleRol = oTree.generateTree();
    }

    public void cargarListaAgregarRoles() {
        try {
            if (seleccionandoEntidad == null) {
                mostrarMensajeError("Debe Seleccionar Una Entidad");
            } else {
                Usuario oUsuario = oUsuarioService.obtenerUsuarioPorId(hfId);
                this.listaAgregarRoles = oUsuarioService.listaAgregarRoles(oUsuario, oEntidadService.obtenerEntidad(seleccionandoEntidadId));
                selectedAgregarRol = null;
                PrimeFaces.current().executeScript("PF('wvDlgAgregarRol').show()");
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaAgregarRoles()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void agregarEntidadListado() {
        try {
            listaEntidadesSinAgregar = oUsuarioService.listaEntidadesSinAgregar(oUsuarioService.obtenerUsuarioPorId(hfId));
            seleccionandoRolId = null;
            seleccionandoRol = null;
            PrimeFaces.current().executeScript("PF('wvDlgAgregarEntidad').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "extraerEntidad()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void seleccionandoEntidad() {
        seleccionandoEntidadId = selectedEntidadSinAgregar.getId();
        seleccionandoEntidad = selectedEntidadSinAgregar.getNombre();
    }

    public void seleccionandoRol() {
        seleccionandoRolId = rolNoAgregadoSeleccionado.getId();
        seleccionandoRol = rolNoAgregadoSeleccionado.getNombre();
    }

    /**
     * Este metodo verifica si el usuario seleccionó clave manual o aleatoria, según sea lo que haya seleccionado,
     * se visualizarán campos en la pantalla
     */
    public void seleccionarTipoGeneracionClave() {
        limpiarCamposContrasena();
        somTipoGeneracionClave = somTipoGeneracionClave == null ? "" : somTipoGeneracionClave;
        if (somTipoGeneracionClave.equals("")) {
            esClaveAleatoria = false;
            esClaveManual = false;
        } else if (somTipoGeneracionClave.equals("aleatoria")) {
            esClaveAleatoria = true;
            esClaveManual = true;
            generarClaveTemporal();
        } else {
            esClaveManual = true;
            esClaveAleatoria = false;
        }
    }

    /**
     * Este metodo limpia las variables de contraseña
     */
    private void limpiarCamposContrasena() {
        pwContrasena = "";
        pwConfirmacionContrasena = "";
        claveTemporal = null;
        olClaveGenerada = null;
    }

    /**
     * Este metodo se encarga de cambiar de valor la variable sbbRequerido cuando el usuario le de clic,
     * inicialmente su valor es false, cuando el usuario la clickee esta cambiara a true o bien a false,
     * según sea el valor que tenga dicha variable
     *
     * @return retorna true o false
     */
    public Boolean seleccionEnClaveTemporal() {
        sbbClaveTemporal = sbbClaveTemporal ? true : false;
        return sbbClaveTemporal;
    }

    public void modalImportarUsuarios() {
        listaUsuariosImportar = new ArrayList<>();
        PrimeFaces.current().executeScript("PF('wvDlgAgregarUsuarios').show()");
        PrimeFaces.current().ajax().update("formularioImportar");
    }

    public void subirUsuarios(FileUploadEvent event) {
        try {
            listaUsuariosImportar = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(event.getFile().getContents())));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length != 9) {
                    listaUsuariosImportar = new ArrayList<>();
                    mostrarMensajeError("Archivo no válido");
                    break;
                }
                Map<String, Object> fila = new HashMap<>();
                fila.put("usuario", campos[0]);
                fila.put("nombres", campos[1]);
                fila.put("apellidos", campos[2]);
                fila.put("email", campos[3]);
                fila.put("ref", campos[4]);
                fila.put("telefono", campos[5]);
                fila.put("password", campos[6]);
                fila.put("temporal", campos[7]);
                fila.put("permiso", campos[8]);
                listaUsuariosImportar.add(fila);
            }
            PrimeFaces.current().ajax().update("formularioImportar");
        } catch (IOException e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "subirUsuarios()", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void quitarUsuarioImportar() {
        try {
            if (usuarioImportarSeleccionado != null) {
                listaUsuariosImportar.remove(usuarioImportarSeleccionado);
            } else {
                mostrarMensajeError("Aún no ha seleccionado un registro");
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "quitarUsuarioImportar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public void importarUsuarios() {
        try {
            if (listaUsuariosImportar.size() == 0) {
                mostrarMensajeInfo("La lista aún no esta cargada, cargue la lista y proceda con la importación");
                return;
            }
            List<Usuario> oListUsuario = new ArrayList<>();
            Map<String, Object> permisos = new HashMap<>();
            Usuario oUsuario;
            for (Map<String, Object> fila : listaUsuariosImportar) {
                oUsuario = new Usuario();
                oUsuario.setUsername(fila.get("usuario").toString().trim());
                oUsuario.setNombres(fila.get("nombres").toString().trim());
                oUsuario.setApellidos(fila.get("apellidos").toString().trim());
                oUsuario.setEmail(fila.get("email").toString().trim());
                oUsuario.setPassword(oSeguridadService.encode(fila.get("password").toString().trim()));
                oUsuario.setCreadoPor(this.getUsuarioActual());
                oUsuario.setCreadoEl(this.getTimeNow());
                oUsuario.setCreadoEnIp(this.getRemoteIp());
                oUsuario.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oUsuario.setPasivo(false);
                oUsuario.setAuditarNavegacion(true);
                oUsuario.setVerAuditoria(true);
                oUsuario.setSuperAdministrador(true);
                oUsuario.setVerAuditoria(true);
                oUsuario.setVerInfoPantalla(true);
                oUsuario.setPasswordTemporal(Objects.equals(fila.get("temporal").toString().trim(), "true"));
                oUsuario.setIntentosFallidos(0);
                oUsuario.setAuditarInicioSesion(true);
                oUsuario.setVerInfoErrores(true);
                oUsuario.setCantidadInicioSesion(0);
                oUsuario.setTelefono(fila.get("telefono").toString().trim().equals("") ? "" : fila.get("telefono").toString().trim());
                oUsuario.setReferencia(fila.get("ref").toString());
                permisos.put(oUsuario.getUsername(), fila.get("permiso").toString().trim());
                oListUsuario.add(oUsuario);
            }
            oUsuarioService.agregarList(oListUsuario, permisos);
            mostrarMensajeSuccess("El proceso de importación de usuarios ha concluido con éxito");
            cargarLista();
            PrimeFaces.current().ajax().update("formLista");
            PrimeFaces.current().executeScript("PF('wvDlgAgregarUsuarios').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "importarUsuarios()", MessagesResults.ERROR_GUARDAR, e);
        }
    }
}