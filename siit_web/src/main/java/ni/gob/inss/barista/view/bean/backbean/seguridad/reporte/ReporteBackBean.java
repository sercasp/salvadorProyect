package ni.gob.inss.barista.view.bean.backbean.seguridad.reporte;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ModuloService;
import ni.gob.inss.barista.businesslogic.service.seguridad.RecursoService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ReporteService;
import ni.gob.inss.barista.model.entity.seguridad.*;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
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
import java.util.stream.Collectors;

/**
 * @author jjrivera
 * @version 1.0
 * @since 5/10/2016
 * Modificado el 08/08/2023 por jvillanueva
 */
@Data
@Named
@Scope("view")
public class ReporteBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    RecursoService oRecursoService;
    ModuloService oModuloService;
    ReporteService oReporteService;
    AuditoriaService oDialogAuditoriaService;
    private String itNombre;
    private String itNombreRecurso;
    private String txtBuscar;
    private Integer somModulo;
    private Integer itOrden;
    private Integer olIdReporte;
    private Boolean sbbPasivo;
    private Boolean nuevoRegistro;
    private List<Recurso> listaRecursos;
    private List<Role> listaRoles;
    private List<Usuario> listaUsuarios;
    private List listaAuditoria;
    private List<Modulo> listaModulos;
    private Recurso recursoSeleccionado;
    private TreeNode root;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ReporteBackBean(RecursoService oRecursoService, ModuloService oModuloService, ReporteService oReporteService, AuditoriaService oDialogAuditoriaService) {
        this.oRecursoService = oRecursoService;
        this.oModuloService = oModuloService;
        this.oReporteService = oReporteService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
    }

    @PostConstruct
    public void init() {
        cargarRecursos();
        cargarModulo();
        cargarArbol();
        nuevoRegistro = true;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     * carga la lista de recursos
     */
    public void cargarRecursos() {
        try {
            this.listaRecursos = oRecursoService.buscar(txtBuscar, "R");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarModalRecursos()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    /**
     * carga la lista de modulos
     */
    public void cargarModulo() {
        try {
            this.listaModulos = oModuloService.obtenerModulos();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarModulo()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    /**
     * Agrega un recurso
     */
    public void agregarRecurso() {
        if (recursoSeleccionado != null) {
            itNombreRecurso = recursoSeleccionado.getNombre();
            //recursoSeleccionado = null;
            txtBuscar = "";
            //cargarRecursos();
            PrimeFaces.current().executeScript("PF('modalRecursos').hide()");
        } else {
            mostrarMensajeInfo("Seleccione un recurso");
            PrimeFaces.current().executeScript("PF('modalRecursos').show()");
        }
    }

    /**
     * Guarda el Reporte
     */
    public void guardar() {
        try {
            if (nuevoRegistro) {
                if (recursoSeleccionado == null || recursoSeleccionado.equals("")) {
                    mostrarMensajeError("Seleccionar un recurso");
                    return;
                } else {
                    Reporte oReporte = new Reporte();
                    oReporte.setNombre(itNombre);
                    oReporte.setModuloPorModuloId(listaModulos
                            .stream()
                            .filter(modulo -> modulo.getId().equals(somModulo))
                            .collect(Collectors.toList()).get(0));
                    oReporte.setRecursoPorRecursoId(recursoSeleccionado);
                    oReporte.setOrden(itOrden);
                    oReporte.setPasivo(false);
                    oReporte.setCreadoEl(this.getTimeNow());
                    oReporte.setCreadoPor(this.getUsuarioActual());
                    oReporte.setCreadoEnIp(this.getRemoteIp());
                    oReporte.setCreadoEnOrdenador(WebUtils.getRemoteHostName());
                    oReporteService.guardar(oReporte);
                    mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
                    olIdReporte = oReporte.getId();
                    nuevoRegistro = false;
                }
            } else {
                Reporte oReporte = oReporteService.obtenerReportePorId(olIdReporte);
                oReporte.setNombre(itNombre);
                oReporte.setModuloPorModuloId(listaModulos
                        .stream()
                        .filter(modulo -> modulo.getId().equals(somModulo))
                        .collect(Collectors.toList()).get(0));
                oReporte.setRecursoPorRecursoId(recursoSeleccionado == null ? oReporte.getRecursoPorRecursoId() : recursoSeleccionado);
                oReporte.setOrden(itOrden);
                oReporte.setPasivo(sbbPasivo);
                oReporte.setModificadoEl(this.getTimeNow());
                oReporte.setModificadoPor(this.getUsuarioActual());
                oReporte.setModificadoEnIp(this.getRemoteIp());
                oReporte.setModificadoEnOrdenador(WebUtils.getRemoteHostName());
                oReporteService.actualizar(oReporte);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            cargarArbol();
            cargarRoles();
            cargarUsuarios();
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }

    /**
     * carga el arbol
     */
    public void cargarArbol() {
        List<Map<String, Object>> listaModulos = oReporteService.obtenerModulos();
        List<Map<String, Object>> listaReportes = oReporteService.obtenerReportes();
        List<Map<String, Object>> menuList = new ArrayList<>();
        for (Map<String, Object> msd : listaModulos) {
            Map<String, Object> lsitMapElement1 = new HashMap<String, Object>();
            lsitMapElement1.put("id", msd.get("id"));
            lsitMapElement1.put("nombre", msd.get("nombre").toString());
            lsitMapElement1.put("menu_id", msd.get("menu_id"));
            lsitMapElement1.put("modulo_id", msd.get("modulo_id"));
            menuList.add(lsitMapElement1);
        }

        for (Map<String, Object> msd : listaReportes) {
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

    /**
     * Captura el nodo seleccionado
     */
    public void onNodeSelect(NodeSelectEvent event) {
        try {
            Node oNode = (Node) event.getTreeNode().getData();
            if (Integer.parseInt(oNode.getId()) >= 10000) {
                mostrarMensajeInfo("Debe seleccionar un Reporte.");
            } else {
                Reporte oReporte = oReporteService.obtenerReportePorId(Integer.parseInt(oNode.getId()));
                itNombre = oReporte.getNombre();
                itNombreRecurso = oReporte.getRecursoPorRecursoId().getNombre();
                cargarModulo();
                somModulo = oReporte.getModuloPorModuloId().getId();
                itOrden = oReporte.getOrden();
                olIdReporte = oReporte.getId();
                nuevoRegistro = false;
                cargarUsuarios();
                cargarRoles();
                PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
            }
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "onNodeSelect()", MessagesResults.ERROR_OBTENER, e);
        }
    }

    /**
     * Limpia las variables
     */
    public void limpiar() {
        itNombreRecurso = null;
        itNombre = null;
        itOrden = null;
        somModulo = null;
        sbbPasivo = false;
        txtBuscar = null;
        nuevoRegistro = true;
        olIdReporte = null;
    }

    /**
     * Elimina el reporte
     */
    public void eliminar() {
        try {
            oReporteService.eliminar(oReporteService.obtenerReportePorId(olIdReporte));
            mostrarMensajeSuccess(MessagesResults.EXITO_ELIMINAR);
            cargarArbol();
            limpiar();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    /**
     * carga la lista de roles
     */
    public void cargarRoles() {
        try {
            this.listaRoles = oReporteService.obtenerRolesPorReporte(olIdReporte);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarRoles()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    /**
     * carga los usuarios
     */
    public void cargarUsuarios() {
        try {
            this.listaUsuarios = oReporteService.obtenerUsuariosPorReporte(olIdReporte);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarUsuarios()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void cargarAuditoria() {
        try {
            listaAuditoria = new ArrayList<>();
            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Menu.class, olIdReporte));
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarAuditoria()", MessagesResults.ERROR_OBTENER_AUDITORIA, e);
        }
    }

    /**
     * cerrar Modal
     */
    public void cerrarModal() {
        recursoSeleccionado = null;
        txtBuscar = "";
        cargarRecursos();
    }
}