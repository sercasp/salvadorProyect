package ni.gob.inss.barista.view.bean.backbean.seguridad.roleReporte;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ReporteService;
import ni.gob.inss.barista.businesslogic.service.seguridad.RoleReporteService;
import ni.gob.inss.barista.businesslogic.service.seguridad.RoleService;
import ni.gob.inss.barista.model.entity.seguridad.Reporte;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.RoleReporte;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
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
 * Representa BackBean para pantalla ListaRoleReporte
 *
 * @author Hernaldo José Mayorga Hernández
 * @since 07/10/2016
 * Modificado el 08/08/2023 por jvillanueva
 */

@Data
@Named
@Scope("view")
public class ListaRoleReporteBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    List<String> arrayNodesAgregar = new ArrayList<>();
    AuditoriaService oDialogAuditoriaService;
    private String txtBuscar;
    private String txtNombre;
    private String txtBuscarRol;
    private String regExpNombre;
    private Role selectedRole;
    private Integer hfId;
    private Integer index = 0;
    private TreeNode root;
    private TreeNode rootNoAgregado;
    private TreeNode[] selectedNodes;
    private Boolean rbPasivo;
    private Boolean nuevo_registro;
    private Boolean btnGuardarVisible;
    private Boolean btnElminiarVisible;
    private Boolean btnAuditoriaVisible;
    private List listaAuditoria;
    private List<Role> listaRoles;
    private List<Usuario> listaUsuarios;
    private RoleService oRoleService;
    private ReporteService oReporteService;
    private RoleReporteService oRoleReporteService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaRoleReporteBackBean(RoleService oRoleService, ReporteService oReporteService, AuditoriaService oDialogAuditoriaService, RoleReporteService oRoleReporteService) {
        this.oRoleService = oRoleService;
        this.oReporteService = oReporteService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
        this.oRoleReporteService = oRoleReporteService;
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
            this.listaRoles = oRoleService.buscar(txtBuscar, "R");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaRoles()", MessagesResults.ERROR_OBTENER_LISTA, e);
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
    }

    public void tree(Role oRole) {
        List<Map<String, Object>> listaResult = oRoleService.obtenerModulosReportePorRole(oRole);
        Tree oTree = new Tree("Reportes", "id", "nombre", "reporteId", "moduloId", "estado");
        oTree.setRegistros(listaResult);
        root = oTree.generateTree();
    }

    public void treeNoAgregado(Role oRole) {
        List<Map<String, Object>> listaResult = oRoleService.obtenerModulosReportePorRoleNoAgregado(oRole);
        Tree oTree = new Tree("Reportes", "id", "nombre", "reporteId", "moduloId", "estado");
        oTree.setRegistros(listaResult);
        rootNoAgregado = oTree.generateTreeConEstado();
    }

    public void onRowSelect(SelectEvent event) {
        cargarRole(((Role) event.getObject()).getId());
    }

    public void editar() {
        cargarRole(selectedRole.getId());
    }

    public void modalAgregarReportes() {
        try {
            Role oRole = oRoleService.obtener(this.hfId);
            treeNoAgregado(oRole);
            arrayNodesAgregar.removeAll(arrayNodesAgregar);
            index = 0;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "modalAgregarReportes()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void agregarReportes() {
        try {
            RoleReporte oRoleReporte;
            Role oRole = oRoleService.obtener(hfId);
            if (!arrayNodesAgregar.isEmpty()) {
                for (String menusAgregados : arrayNodesAgregar) {
                    oRoleReporte = new RoleReporte();
                    oRoleReporte.setReporteByReporteId(oReporteService.obtenerReportePorId(Integer.parseInt(menusAgregados)));
                    oRoleReporte.setRoleByRoleId(oRoleService.obtener(hfId));
                    oRoleReporte.setCreadoPor(this.getUsuarioActual());
                    oRoleReporte.setCreadoEl(this.getTimeNow());
                    oRoleReporte.setCreadoEnIp(this.getRemoteIp());
                    oRoleReporte.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                    oRoleReporteService.guardar(oRoleReporte);
                }
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                tree(oRole);
                arrayNodesAgregar.removeAll(arrayNodesAgregar);
                PrimeFaces.current().executeScript("PF('modalReportes').hide()");
            } else {
                mostrarMensajeInfo("Debe seleccionar al menos un Reporte.");
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "agregarReportes()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public void eliminarReportes() {
        try {
            Role oRole = oRoleService.obtener(hfId);
            if (!arrayNodesAgregar.isEmpty()) {
                for (String reportesExtraer : arrayNodesAgregar) {
                    Reporte oReporte = oReporteService.obtenerReportePorId(Integer.parseInt(reportesExtraer));
                    oRoleReporteService.quitarReporte(oReporte, oRole);
                }
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                arrayNodesAgregar.removeAll(arrayNodesAgregar);
                tree(oRole);
            } else {
                mostrarMensajeInfo("Debe seleccionar al menos un Reporte.");
            }
            index = 0;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminarReportes()", MessagesResults.ERROR_GUARDAR, e);
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
            this.cargarListaUsuarios();
            this.tree(oRole);
            this.treeNoAgregado(oRole);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarRole(Integer roleId)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void eliminar() {
        try {
            Role oRole = oRoleService.obtener(this.hfId);
            oRoleService.borrar(oRole);
            limpiar();
            cargarListaRoles();
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
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
                oRole.setTipo("R");
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
}