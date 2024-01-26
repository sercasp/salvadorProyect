package ni.gob.inss.barista.view.bean.backbean.seguridad.role;

import lombok.*;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.seguridad.*;
import ni.gob.inss.barista.model.entity.seguridad.*;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.treeMenu.Node;
import ni.gob.inss.barista.view.utils.treeMenu.Tree;
import ni.gob.inss.barista.view.utils.validation.EntityValidationField;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Representa BackBean para pantalla ListaRole
 *
 * @author Juan Evangelista Fletes Garcia
 * @author Sergio López
 * @since 30/04/2013
 * Modificación de pantalla rol
 * Corrección de errores
 * @since 2016-10-07
 * Modificado el 08/08/2023 por jvillanueva
 */

@Data
@Named
@Scope("view")
public class ListaRoleBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    List<String> arrayNodesAgregar = new ArrayList<String>();
    AuditoriaService oDialogAuditoriaService;
    private Role selectedRole;
    private Autorizacion selectedAutorizacion;
    private String txtBuscar;
    private String txtBuscarRol;
    private String txtBuscarRoles;
    private String txtNombre;
    private String regExpNombre;
    private Integer hfId;
    private Integer index = 0;
    private boolean rbPasivo;
    private Boolean nuevo_registro;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private TreeNode root;
    private TreeNode rootNoAgregado;
    private TreeNode[] selectedNodes;
    private List listaAuditoria;
    private List<Role> listaRoles;
    private List<Autorizacion> listaAutorizaciones;
    private List<Autorizacion> listaAutorizacionesNoAgregadas;
    private List<Usuario> listaUsuarios;
    private RoleService oRoleService;
    private MenuService oMenuService;
    private AutorizacionService oAutorizacionService;
    private RoleMenuService oRoleMenuService;
    private RoleAutorizacionService oRoleAutorizacionService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaRoleBackBean(RoleService oRoleService, MenuService oMenuService, AutorizacionService oAutorizacionService, RoleMenuService oRoleMenuService, RoleAutorizacionService oRoleAutorizacionService, AuditoriaService oDialogAuditoriaService) {
        this.oRoleService = oRoleService;
        this.oMenuService = oMenuService;
        this.oAutorizacionService = oAutorizacionService;
        this.oRoleMenuService = oRoleMenuService;
        this.oRoleAutorizacionService = oRoleAutorizacionService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarListaRoles();
        cargarValidaciones();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private void cargarValidaciones() {
        try {
            regExpNombre = EntityValidationField.getRegExpresionPattern(Role.class, "Nombre");
        } catch (NoSuchMethodException e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Role.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cargarListaRoles() {
        try {
            this.listaRoles = oRoleService.buscar(txtBuscar, "P");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaRoles()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarListaAutorizaciones() {
        try {
            this.listaAutorizaciones = oRoleService.autorizacionesPorRole(hfId);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaAutorizaciones()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarListaUsuarios() {
        try {
            this.listaUsuarios = oRoleService.usuariosPorRole(hfId, getEntidadActual().getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaUsuarios()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode node = event.getTreeNode();
        boolean isSelected = node.isSelected();
        processAllChildNodes(node, isSelected, 1);
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        TreeNode node = event.getTreeNode();
        boolean isSelected = node.isSelected();
        processAllChildNodes(node, isSelected, 0);
    }

    private void processAllChildNodes(TreeNode node, boolean selectValue, Integer select) {
        List<TreeNode> childNodes = node.getChildren();
        if (childNodes.isEmpty()) {
            if (select == 1) {
                node.setSelected(selectValue);
                Node oNode = (Node) node.getData();
                if (!arrayNodesAgregar.contains(oNode.getId()) && oNode.getParentId() == null) {
                    arrayNodesAgregar.add(oNode.getId());
                } else {
                    arrayNodesAgregar.add(oNode.getId());
                    if (oNode.getParentId() != null)
                        arrayNodesAgregar.add(oNode.getParentId());
                }
            } else if (select == 0) {
                node.setSelected(selectValue);
                Node oNode = (Node) node.getData();
                removeElements(arrayNodesAgregar, oNode.getId().toString());
            }
        } else {
            if (select == 1) {
                node.setSelected(selectValue);
                Node oNode = (Node) node.getData();
                if (!arrayNodesAgregar.contains(oNode.getId())) {
                    if (containsOnlyNumbers(oNode.getId())) {
                        arrayNodesAgregar.add(oNode.getId());
                    }
                }
            } else if (select == 0) {
                node.setSelected(selectValue);
                Node oNode = (Node) node.getData();
                removeElements(arrayNodesAgregar, oNode.getId().toString());
            }

        }
        for (TreeNode childNode : childNodes) {
            if (select == 1) {
                childNode.setSelected(selectValue);
                processAllChildNodes(childNode, selectValue, 1);
                Node oNode = (Node) childNode.getData();
                if (!arrayNodesAgregar.contains(oNode.getId())) {
                    arrayNodesAgregar.add(oNode.getId());
                }
            } else if (select == 0) {
                childNode.setSelected(selectValue);
                processAllChildNodes(childNode, selectValue, 0);
                Node oNode = (Node) childNode.getData();
                removeElements(arrayNodesAgregar, oNode.getId().toString());
            }
        }
    }

    /**
     * Selecciones para el tab view de menu
     **/

    public void onNodeSelecte(NodeSelectEvent event) {
        TreeNode node = event.getTreeNode();
        boolean isSelected = node.isSelected();
        processAllChildNodess(node, isSelected, 1);
    }

    public void onNodeUnselecte(NodeUnselectEvent event) {
        TreeNode node = event.getTreeNode();
        boolean isSelected = node.isSelected();
        processAllChildNodess(node, isSelected, 0);
    }

    private void processAllChildNodess(TreeNode node, boolean selectValue, Integer select) {
        List<TreeNode> childNodes = node.getChildren();
        if (childNodes.isEmpty()) {
            if (select == 1) {
                node.setSelected(selectValue);
                Node oNode = (Node) node.getData();
                if (!arrayNodesAgregar.contains(oNode.getId())) {
                    arrayNodesAgregar.add(oNode.getId());
                }

            } else if (select == 0) {
                node.setSelected(selectValue);
                Node oNode = (Node) node.getData();
                removeElements(arrayNodesAgregar, oNode.getId().toString());
            }
        } else {
            if (select == 1) {
                node.setSelected(selectValue);
                Node oNode = (Node) node.getData();

                if (!arrayNodesAgregar.contains(oNode.getId())) {
                    if (containsOnlyNumbers(oNode.getId())) {
                        arrayNodesAgregar.add(oNode.getId());
                    }
                }

            } else if (select == 0) {
                node.setSelected(selectValue);
                Node oNode = (Node) node.getData();
                removeElements(arrayNodesAgregar, oNode.getId().toString());
            }

        }
        for (TreeNode childNode : childNodes) {
            if (select == 1) {
                childNode.setSelected(selectValue);
                processAllChildNodess(childNode, selectValue, 1);
                Node oNode = (Node) childNode.getData();
                if (!arrayNodesAgregar.contains(oNode.getId())) {
                    arrayNodesAgregar.add(oNode.getId());
                }
            } else if (select == 0) {
                childNode.setSelected(selectValue);
                processAllChildNodess(childNode, selectValue, 0);
                Node oNode = (Node) childNode.getData();
                removeElements(arrayNodesAgregar, oNode.getId().toString());
            }
        }
    }

    public boolean containsOnlyNumbers(String str) {
        //It can't contain only numbers if it's null or empty...
        if (str == null || str.length() == 0)
            return false;

        for (int i = 0; i < str.length(); i++) {
            //If we find a non-digit character we return false.
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }

        return true;
    }

    public List<String> removeElements(List<String> input, String deleteMe) {
        if (input != null) {
            for (int i = 0; i < input.size(); i++) {
                if (arrayNodesAgregar.get(i).equals(deleteMe)) {
                    arrayNodesAgregar.remove(i);
                }
            }
            return arrayNodesAgregar;
        } else {
            return arrayNodesAgregar;
        }
    }

    public void limpiar() {
        nuevo_registro = true;
        btnElminiarVisible = false;
        btnAuditoriaVisible = false;
        btnGuardarVisible = this.isServidorReplicacion();
        txtBuscar = "";
        txtNombre = null;
        rbPasivo = false;
        hfId = null;
        selectedRole = null;
    }

    public void tree(Role oRole) {
        List<Map<String, Object>> listaResult = oRoleService.obtenerModulosMenuPorRole(oRole);
        Tree oTree = new Tree("Menus", "id", "nombre", "menuId", "moduloId", "estado");
        oTree.setRegistros(listaResult);
        root = oTree.generateTree();
    }

    public void treeNoAgregado(Role oRole) {
        List<Map<String, Object>> listaResult = oRoleService.obtenerModulosMenuPorRoleNoAgregado(oRole);
        Tree oTree = new Tree("Menus", "id", "nombre", "menuId", "moduloId", "estado");
        oTree.setRegistros(listaResult);
        rootNoAgregado = oTree.generateTreeConEstado();
    }

    public void onRowSelect(SelectEvent event) {
        cargarRole(((Role) event.getObject()).getId());
    }

    public void editar() {
        if (selectedRole != null) {
            cargarRole(selectedRole.getId());
        } else {
            mostrarMensajeInfo("Seleccione un registro");
        }
    }

    public void modalAgregarMenus() {
        try {
            Role oRole = oRoleService.obtener(this.hfId);
            treeNoAgregado(oRole);
            arrayNodesAgregar.removeAll(arrayNodesAgregar);
            index = 0;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "agregarMenus()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void modalAgregarAutorizaciones() {
        try {
            listaAutorizacionesNoAgregadas = oRoleService.agregarAutorizaciones(hfId);
            selectedAutorizacion = null;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "modalAgregarAutorizaciones()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void actualizarListaAutorizaciones() {
        try {
            if (txtBuscarRoles.equals("") || txtBuscarRoles == null) {
                modalAgregarAutorizaciones();
            } else {
                listaAutorizacionesNoAgregadas.clear();
                listaAutorizacionesNoAgregadas = oRoleService.obtenerAutorizacionesFiltradasNoAgregadas(txtBuscarRoles, hfId);
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "actualizarListaAutorizaciones()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void actualizarListaAutorizacionesAgregadas() {
        try {
            if (txtBuscarRol.equals("") || txtBuscarRol == null) {
                cargarListaAutorizaciones();
            } else {
                listaAutorizaciones.clear();
                listaAutorizaciones = oRoleService.obtenerAutorizacionesFiltradas(txtBuscarRol, hfId);
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "actualizarListaAutorizaciones()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void agregarMenus() {
        try {
            RoleMenu oRoleMenu;
            Role oRole = oRoleService.obtener(hfId);
            oRoleMenu = new RoleMenu();
            if (!arrayNodesAgregar.isEmpty()) {
                for (String menusAgregados : arrayNodesAgregar) {
                    oRoleMenu.setMenusByMenuId(oMenuService.obtenerMenuPorId(Integer.parseInt(menusAgregados)));
                    oRoleMenu.setRolesByRolId(oRoleService.obtener(hfId));
                    oRoleMenu.setCreadoPor(this.getUsuarioActual());
                    oRoleMenu.setCreadoEl(this.getTimeNow());
                    oRoleMenu.setCreadoEnIp(this.getRemoteIp());
                    oRoleMenu.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                    oRole.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                    oRoleMenuService.guardar(oRoleMenu, oMenuService.obtenerMenuPorId(Integer.parseInt(menusAgregados)), oRole);
                }
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                tree(oRole);
                arrayNodesAgregar.removeAll(arrayNodesAgregar);
                PrimeFaces.current().executeScript("PF('modalMenus').hide()");
            } else {
                mostrarMensajeInfo("Debe seleccionar al menos un Menú.");
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "agregarMenus()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public void cerrarModal() {
        txtBuscarRoles = null;
    }

    public void agregarAutorizaciones() {
        try {
            txtBuscarRoles = null; //limpiar la busqueda del modal
            if (selectedAutorizacion != null) {
                RoleAutorizacion oRoleAutorizacion;
                Role oRole = oRoleService.obtener(hfId);
                oRoleAutorizacion = new RoleAutorizacion();
                oRoleAutorizacion.setAutorizacionesByAutorizacionId(oAutorizacionService.obtenerAutorizacionPorId(Integer.parseInt(selectedAutorizacion.getId().toString())));
                oRoleAutorizacion.setRolesByRolId(oRole);
                oRoleAutorizacion.setCreadoPor(this.getUsuarioActual());
                oRoleAutorizacion.setCreadoEl(this.getTimeNow());
                oRoleAutorizacion.setCreadoEnIp(this.getRemoteIp());
                oRoleAutorizacion.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oRoleAutorizacionService.guardar(oRoleAutorizacion);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                cargarListaAutorizaciones();
                index = 1;
                selectedAutorizacion = null;
                PrimeFaces.current().executeScript("PF('modalAutorizaciones').hide()");
            } else {
                mostrarMensajeInfo("Debe seleccionar una Autorización.");
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "agregarAutorizaciones()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public void eliminarMenus() {
        try {
            Role oRole = oRoleService.obtener(hfId);

            if (!arrayNodesAgregar.isEmpty()) {
                for (String menusExtraer : arrayNodesAgregar) {
                    Menu oMenu = oMenuService.obtenerMenuPorId(Integer.parseInt(menusExtraer));
                    if (menusExtraer.equals("")) {
                        mostrarMensajeError("vacio");
                        break;
                    } else {
                        oRoleMenuService.quitarMenu(oMenu, oRole);
                    }
                }
                mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
                arrayNodesAgregar.removeAll(arrayNodesAgregar);
                tree(oRole);
            } else {
                mostrarMensajeInfo("Debe seleccionar al menos un Menú.");
            }
            index = 0;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminarMenus()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public void quitarAutorizaciones() {
        try {
            Role oRole = oRoleService.obtener(hfId);
            if (selectedAutorizacion != null) {
                oRoleAutorizacionService.quitarAutorizacion(selectedAutorizacion, oRole);
                mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
                cargarListaAutorizaciones();
                //selectedAutorizacion = null;
            } else {
                mostrarMensajeInfo("Debe seleccionar una Autorización.");
            }
            index = 1;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "quitarAutorizaciones()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    private void cargarRole(Integer roleId) {
        try {
            Role oRole = oRoleService.obtener(roleId);
            this.hfId = oRole.getId();
            this.txtNombre = oRole.getNombre();
            this.rbPasivo = oRole.getPasivo();
            this.nuevo_registro = false;
            this.btnElminiarVisible = this.isServidorReplicacion();
            this.btnAuditoriaVisible = this.isVerInfoAuditoria();
            this.cargarListaRoles();
            this.cargarListaAutorizaciones();
            this.cargarListaUsuarios();
            this.tree(oRole);
            this.treeNoAgregado(oRole);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarRole(Integer roleId)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void elminar() {
        try {
            Role oRole = oRoleService.obtener(this.hfId);
            oRoleService.borrar(oRole);
            limpiar();
            cargarListaRoles();
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "elminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void guardar() {
        try {
            Role oRole;
            if (this.nuevo_registro) {
                oRole = new Role();
                oRole.setNombre(this.txtNombre);
                oRole.setPasivo(this.rbPasivo);
                oRole.setCreadoPor(this.getUsuarioActual());
                oRole.setCreadoEl(this.getTimeNow());
                oRole.setCreadoEnIp(this.getRemoteIp());
                oRole.setPasivo(false);
                oRole.setTipo("P");
                oRole.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oRoleService.guardar(oRole);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
            } else {
                oRole = oRoleService.obtener(this.hfId);
                oRole.setNombre(txtNombre);
                oRole.setPasivo(rbPasivo);
                oRole.setModificadoPor(this.getUsuarioActual());
                oRole.setModificadoEl(this.getTimeNow());
                oRole.setModificadoEnIp(this.getRemoteIp());
                oRole.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oRoleService.actualizar(oRole);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargarRole(oRole.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public TreeNode getTree() {
        return root;
    }

    public TreeNode getTreeNoAgregado() {
        return rootNoAgregado;
    }
}