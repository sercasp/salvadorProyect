package ni.gob.inss.barista.view.bean.backbean.seguridad.menu;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.seguridad.*;
import ni.gob.inss.barista.model.entity.seguridad.*;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.tree.NodeAll;
import ni.gob.inss.barista.view.utils.tree.TreeAll;
import ni.gob.inss.barista.view.utils.treeMenu.Node;
import ni.gob.inss.barista.view.utils.treeMenu.Tree;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para ListaMenuBackBean</br>
 *
 * @author barista
 * @version 1.0, 05/06/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
@Scope("view")
public class ListaMenuBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    private Recurso selectedRow;
    private boolean nuevoRegistro;
    private Integer hfId;
    private String txtNombre;
    private Integer cmbModulo;
    private Integer cmbMenu;
    private Integer txtOrden;
    private Boolean rbPasivo;
    private String txtBuscar;
    private Integer mdlRecurso;
    private String mdlRecursoNombre;
    private Integer mdlMenuPadre;
    private String mdlMenuPadreNombre;
    private List<Menu> listaMenus;
    private List<Modulo> listaModulos;
    private List<Recurso> listaRecursos;
    private List<Role> listaRoles;
    private List<Usuario> listaUsuarios;
    private TreeNode root;
    private TreeNode rootMenuPadre;
    private String regExpCodigo;
    private String regExpDescriptor;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private List listaAuditoria;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final AuditoriaService oDialogAuditoriaService;
    private final MenuService oMenuService;
    private final ModuloService oModuloService;
    private final RecursoService oRecursoService;
    private final UsuarioService oUsuarioService;
    private final RoleService oRoleService;

    @Inject
    @Autowired
    public ListaMenuBackBean(MenuService oMenuService, ModuloService oModuloService, RecursoService oRecursoService, UsuarioService oUsuarioService, RoleService oRoleService, AuditoriaService oDialogAuditoriaService) {
        this.oMenuService = oMenuService;
        this.oModuloService = oModuloService;
        this.oRecursoService = oRecursoService;
        this.oUsuarioService = oUsuarioService;
        this.oRoleService = oRoleService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarValidaciones();
        cargarModulos();
        tree();
        cargarModalRecursos();
        cleanMenuPadre();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    public void tree() {
        mostrarMensajeInfo("Libre del barista");
        List<Map<String, Object>> menuListModulos = oMenuService.obtenerModuloTree();
        List<Map<String, Object>> menuListMenus = oMenuService.obtenerMenuPorModuloTree();
        List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> msd : menuListModulos) {
            Map<String, Object> lsitMapElement1 = new HashMap<String, Object>();
            lsitMapElement1.put("id", msd.get("id"));
            lsitMapElement1.put("nombre", msd.get("nombre").toString());
            lsitMapElement1.put("menu_id", msd.get("menu_id"));
            lsitMapElement1.put("modulo_id", msd.get("modulo_id"));
            menuList.add(lsitMapElement1);
        }
        for (Map<String, Object> msd : menuListMenus) {
            Map<String, Object> lsitMapElement2 = new HashMap<String, Object>();
            lsitMapElement2.put("id", msd.get("id"));
            lsitMapElement2.put("nombre", msd.get("nombre").toString());
            lsitMapElement2.put("menu_id", msd.get("menu_id"));
            lsitMapElement2.put("modulo_id", msd.get("modulo_id"));
            menuList.add(lsitMapElement2);
        }
        Tree oTree = new Tree("Menus", "id", "nombre", "menu_id", "modulo_id", "estado");
        oTree.setRegistros(menuList);
        root = oTree.generateTree();
    }

    public void cargarTreeMenuPadre() {
        if (cmbModulo != null) {
            List<Map<String, Object>> menuListPadre = oMenuService.obtenerMenuPorModuloTree(cmbModulo);

            if (menuListPadre.size() == 0) {
                mostrarMensajeInfo("Este menú no tiene dependientes");
                return;
            }
            TreeAll oTree = new TreeAll("Menus", "id", "text", "parentId");
            oTree.setRegistros(menuListPadre);
            rootMenuPadre = oTree.generateTree();
            PrimeFaces.current().executeScript("PF('modalMenus').show()");

        } else {
            rootMenuPadre = null;
            mostrarMensajeInfo("Debe seleccionar un Módulo.");
        }
    }

    public void cargarValidaciones() {
        try {
            regExpCodigo = RegExpresion.regExpSoloNumeros;
            regExpDescriptor = RegExpresion.regExpSoloLetras;
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Menu.class, hfId));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    public void cerrarModal() {
        txtBuscar = "";
        mdlRecursoNombre = null;
        cargarModalRecursos();
        selectedRow = null;
    }

    public void cargarModulos() {
        try {
            this.listaModulos = oMenuService.obtenerModulos();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarModulos()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarModalRecursos() {
        try {
            this.listaRecursos = oRecursoService.buscar(txtBuscar, "P");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarModalRecursos()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarUsuarios() {
        try {
            this.listaUsuarios = oUsuarioService.obtenerUsuariosPorMenu(hfId);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarUsuarios()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarRoles() {
        try {
            this.listaRoles = oRoleService.obtenerRolesPorMenu(hfId);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarRoles()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void agregarRecurso() {
        if (selectedRow != null) {
            mdlRecurso = selectedRow.getId();
            mdlRecursoNombre = selectedRow.getNombre();
            txtBuscar = "";
            cargarModulos();
            cargarModalRecursos();
            selectedRow = null;
            PrimeFaces.current().executeScript("PF('modalRecursos').hide()");
        } else {
            mostrarMensajeInfo("Debe seleccionar un Recurso.");
            PrimeFaces.current().executeScript("PF('modalRecursos').show()");
        }
    }

    public void selectedMenuPadre(NodeSelectEvent event) {
        NodeAll oNode = (NodeAll) event.getTreeNode().getData();
        mdlMenuPadre = Integer.parseInt(oNode.getId());
        mdlMenuPadreNombre = oNode.getText();
    }

    public void onNodeSelect(NodeSelectEvent event) {
        Node oNode = (Node) event.getTreeNode().getData();
        if (Integer.parseInt(oNode.getId()) >= 10000) {
            mostrarMensajeInfo("Debe seleccionar un Menú.");
        } else {
            cargar(Integer.parseInt(oNode.getId()));
        }
    }

    public void limpiar() {
        btnElminiarVisible = false;
        btnGuardarVisible = isServidorReplicacion();
        btnAuditoriaVisible = false;
        hfId = null;
        txtBuscar = "";
        mdlRecurso = null;
        mdlRecursoNombre = null;
        mdlMenuPadre = null;
        mdlMenuPadreNombre = null;
        txtNombre = null;
        listaMenus = null;
        cmbMenu = null;
        cmbModulo = null;
        txtOrden = null;
        rbPasivo = null;
        nuevoRegistro = true;
        cargarModalRecursos();
    }

    public void cleanMenuPadre() {
        mdlMenuPadre = null;
        mdlMenuPadreNombre = null;
    }

    private void cargar(Integer id) {
        try {
            Menu oMenu = oMenuService.obtenerMenuPorId(id);
            hfId = oMenu.getId();
            txtNombre = oMenu.getNombre();
            mdlRecurso = oMenu.getRecursosByRecursoId().getId();
            mdlRecursoNombre = oMenu.getRecursosByRecursoId().getNombre();
            mdlMenuPadre = oMenu.getMenusByMenuId() == null ? null : oMenu.getMenusByMenuId().getId();
            mdlMenuPadreNombre = oMenu.getMenusByMenuId() == null ? null : oMenu.getMenusByMenuId().getNombre();
            txtOrden = oMenu.getOrden();
            rbPasivo = oMenu.getPasivo();
            cmbModulo = oMenu.getModulosByModuloId().getId();
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnElminiarVisible = isServidorReplicacion();
            cargarRoles();
            cargarUsuarios();
            nuevoRegistro = false;
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void eliminar() {
        try {
            if (validarEliminar(hfId)) {
                return;
            }
            Menu oMenu = oMenuService.obtenerMenuPorId(hfId);
            oMenuService.eliminar(oMenu);
            limpiar();
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    /**
     * Este metodo verifica si el menú seleccionado se encuentra relacionado con alguna otra tabla
     *
     * @param prIdMenu parametro de busqueda
     * @return retorna un valor booleano
     */
    private Boolean validarEliminar(Integer prIdMenu) {
        List<Menu> listaMenuEnRolMenu = oMenuService.verificarMenuEnRolMenu(prIdMenu);
        if (listaMenuEnRolMenu.size() > 0) {
            mostrarMensajeError("No puede eliminar este menú, ya que está siendo utilizado");
            return true;
        }
        return false;
    }

    public void guardar() {
        try {
            Menu oMenu;
            if (nuevoRegistro) {
                oMenu = new Menu();
                oMenu.setNombre(txtNombre);
                oMenu.setRecursosByRecursoId(oRecursoService.obtenerRecursoPorId(mdlRecurso));
                oMenu.setModulosByModuloId(oModuloService.obtenerModuloPorId(cmbModulo));
                if (mdlMenuPadre == null || mdlMenuPadre == 0) {
                    oMenu.setMenusByMenuId((Menu) null);
                } else {
                    oMenu.setMenusByMenuId(oMenuService.obtenerMenuPorId(mdlMenuPadre));
                }
                oMenu.setOrden(txtOrden);
                oMenu.setPasivo(false);
                oMenu.setCreadoPor(this.getUsuarioActual());
                oMenu.setCreadoEl(this.getTimeNow());
                oMenu.setCreadoEnIp(this.getRemoteIp());
                oMenu.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                oMenuService.agregar(oMenu);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
            } else {
                oMenu = oMenuService.obtenerMenuPorId(hfId);
                oMenu.setNombre(txtNombre);
                oMenu.setRecursosByRecursoId(oRecursoService.obtenerRecursoPorId(mdlRecurso));
                if (mdlMenuPadre == null || mdlMenuPadre == 0) {
                    oMenu.setMenusByMenuId(null);
                } else {
                    oMenu.setMenusByMenuId(oMenuService.obtenerMenuPorId(mdlMenuPadre));
                }
                oMenu.setModulosByModuloId(oModuloService.obtenerModuloPorId(cmbModulo));
                oMenu.setOrden(txtOrden);
                oMenu.setPasivo(rbPasivo);
                oMenu.setModificadoPor(this.getUsuarioActual());
                oMenu.setModificadoEl(this.getTimeNow());
                oMenu.setModificadoEnIp(this.getRemoteIp());
                oMenu.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oMenuService.actualizar(oMenu);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oMenu.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    public TreeNode getTree() {
        return root;
    }

    public TreeNode getTreeMenuPadre() {
        return rootMenuPadre;
    }
}