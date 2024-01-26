package ni.gob.inss.barista.view.bean.backbean.seguridad.parametroUsuario;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ParametroService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ParametroUsuarioService;
import ni.gob.inss.barista.businesslogic.service.seguridad.UsuarioService;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import ni.gob.inss.barista.model.entity.seguridad.ParametroUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.security.SystemSecurityException;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
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
 * @author Sergio López
 * @version 1.0
 * @since 2016-10-12
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
@Scope("view")
public class ListaParametroUsuarioBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private String itValor;
    private String itBuscar;
    private String itParametro;
    private String itNombreUsuario;
    private String itBuscarUsuario;
    private String itBuscarParametro;
    private Integer itId;
    private Integer idUsuario;
    private Integer idParametro;
    private Boolean nuevoRegistro;
    private Boolean btnAuditoriaVisible;
    private Usuario usuarioSeleccionado;
    private Parametro parametroSeleccionado;
    private ParametroUsuario parametroUsuarioSeleccionado;
    private List listaAuditoria;
    private List<Usuario> listaUsuarios;
    private List<Parametro> listaParametros;
    private List<ParametroUsuario> listaParametrosUsuarios;
    private ParametroUsuarioService oParametroUsuarioService;
    private UsuarioService oUsuarioService;
    private ParametroService oParametroService;
    private AuditoriaService oDialogAuditoriaService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaParametroUsuarioBackBean(ParametroUsuarioService oParametroUsuarioService, UsuarioService oUsuarioService, ParametroService oParametroService, AuditoriaService oDialogAuditoriaService) {
        this.oParametroUsuarioService = oParametroUsuarioService;
        this.oUsuarioService = oUsuarioService;
        this.oParametroService = oParametroService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
    }

    @PostConstruct
    public void init() throws SystemSecurityException {
        listaAuditoria = new ArrayList<>();
        nuevoRegistro = true;
        btnAuditoriaVisible = false;
        cargarLista();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     * Este metodo carga una lista con los elementos ya guardados de la pantalla parametro usuario
     **/
    public void cargarLista() {
        try {
            itBuscar = itBuscar == null ? "" : itBuscar;
            listaParametrosUsuarios = oParametroUsuarioService.obtenerListadoParametroUsuario(itBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(ParametroUsuario.class, itId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    /**
     * Este metodo carga una lista con los usuarios del sistema
     */
    public void cargarListaUsuarios() {
        try {
            itBuscarUsuario = itBuscarUsuario == null ? "" : itBuscarUsuario;
            listaUsuarios = oParametroUsuarioService.obtenerListaUsuarios(itBuscarUsuario);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaUsuarios()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    /**
     * Este metodo carga una lista con los parametros almacenados en la tabla parametros
     */
    public void cargarListaParametros() {
        try {
            itBuscarParametro = itBuscarParametro == null ? "" : itBuscarParametro;
            listaParametros = oParametroUsuarioService.obtenerListaParametros(itBuscarParametro);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaUsuarios()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    /**
     * Este metodo abre un dialogo donde están los usuarios a seleccionar
     */
    public void abrirModalUsuarios() {
        cargarListaUsuarios();
        PrimeFaces.current().executeScript("PF('wvDlgAgregarUsuarios').show()");
    }

    /**
     * Este metodo abre un dialogo donde están los parametros a seleccionar
     */
    public void abrirModalParametros() {
        cargarListaParametros();
        PrimeFaces.current().executeScript("PF('wvDlgAgregarParametros').show()");
    }

    /**
     * Este metodo llena las variables que corresponden a los datos del usuario
     */
    public void seleccionarUsuario() {
        if (usuarioSeleccionado == null) {
            mostrarMensajeInfo("Seleccione un usuario");
            PrimeFaces.current().executeScript("PF('wvDlgAgregarUsuarios').show()");
        } else {
            itNombreUsuario = usuarioSeleccionado.getUsername();
            idUsuario = usuarioSeleccionado.getId();
            usuarioSeleccionado = null;
            itBuscarUsuario = "";
            cargarListaUsuarios();
            PrimeFaces.current().executeScript("PF('wvDlgAgregarUsuarios').hide()");
        }
    }

    /**
     * Este metodo llena las variables que corresponden a los datos de los parametros
     */
    public void seleccionarParametro() {
        if (parametroSeleccionado == null) {
            mostrarMensajeInfo("Selecione un parametro");
            PrimeFaces.current().executeScript("PF('wvDlgAgregarParametros').show()");
            return;
        }
        itParametro = parametroSeleccionado.getDescriptor();
        idParametro = parametroSeleccionado.getId();
        itBuscarParametro = "";
        usuarioSeleccionado = null;
        cargarListaParametros();
        PrimeFaces.current().executeScript("PF('wvDlgAgregarParametros').hide()");
    }

    /**
     * Este metodo se encarga de volver las variables de la pantalla a su estado inicial
     */
    public void limpiar() {
        itValor = null;
        itBuscar = null;
        itParametro = null;
        itNombreUsuario = null;
        usuarioSeleccionado = null;
        parametroSeleccionado = null;
        parametroUsuarioSeleccionado = null;
        nuevoRegistro = true;
        btnAuditoriaVisible = false;
    }

    /**
     * Este metodo se encarga de tomar el valor del objeto seleccionado en el datatable principal y
     * utilizarlo para cargar las variables de la pantalla, bien sea para eliminar o editarlos
     */
    public void editarOEliminar() {
        if (parametroUsuarioSeleccionado == null) {
            mostrarMensajeInfo("Seleccione un registro");
            return;
        }
        cargarDatos();
    }

    /**
     * Este metodo llena las variables de la pantalla con los datos que contiene el objeto seleccionado
     */
    public void cargarDatos() {
        try {
            nuevoRegistro = false;
            btnAuditoriaVisible = true;
            itValor = parametroUsuarioSeleccionado.getValor();
            itNombreUsuario = parametroUsuarioSeleccionado.getUsuarioIdByUsuarioId().getUsername();
            itParametro = parametroUsuarioSeleccionado.getParametroIdByParametroId().getDescriptor();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatos()", MessagesResults.ERROR_OBTENER, e);
        }
    }

    /**
     * Este metodo se encarga de guardar o actualizar los datos ingresados por el usuario
     */
    public void guardar() {
        try {
            ParametroUsuario oParametroUsuario = new ParametroUsuario();
            if (nuevoRegistro) {
                if ((idUsuario == null || idUsuario.equals("")) && (idParametro == null || idParametro.equals(""))) {
                    mostrarMensajeError("Usuario y Parametro estan vacios");
                    return;
                } else if ((idUsuario == null || idUsuario.equals("")) && (idParametro != null || idParametro.equals("") == false)) {
                    mostrarMensajeError("Campo usuario vacio");
                    return;
                } else if ((idUsuario != null || idUsuario.equals("") == false) && (idParametro == null || idParametro.equals(""))) {
                    mostrarMensajeError("Campo parametro vacio");
                    return;
                } else {
                    oParametroUsuario.setUsuarioIdByUsuarioId(oUsuarioService.obtenerUsuarioPorId(idUsuario));
                    oParametroUsuario.setParametroIdByParametroId(oParametroService.obtenerParametroPorId(idParametro));
                    oParametroUsuario.setValor(itValor);
                    oParametroUsuarioService.guardar(oParametroUsuario);
                    mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                    parametroUsuarioSeleccionado = oParametroUsuario;
                    itId = parametroUsuarioSeleccionado.getId();
                    nuevoRegistro = false;
                    btnAuditoriaVisible = true;
                }
            } else {
                oParametroUsuario = parametroUsuarioSeleccionado;
                oParametroUsuario.setUsuarioIdByUsuarioId(oUsuarioService.obtenerUsuarioPorId(idUsuario));
                oParametroUsuario.setParametroIdByParametroId(oParametroService.obtenerParametroPorId(idParametro));
                oParametroUsuario.setValor(itValor);
                oParametroUsuarioService.actualizar(oParametroUsuario);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            cargarLista();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    /**
     * Este metodo se encarga de eliminar un registro
     */
    public void eliminar() {
        try {
            ParametroUsuario oParametroUsuario = parametroUsuarioSeleccionado;
            oParametroUsuarioService.eliminar(oParametroUsuario);
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            cargarLista();
            limpiar();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void cerrarModal() {
        itBuscarUsuario = "";
        usuarioSeleccionado = null;
        cargarListaUsuarios();
    }

    public void cerrarModalPara() {
        itBuscarParametro = "";
        usuarioSeleccionado = null;
        cargarListaParametros();
    }
}