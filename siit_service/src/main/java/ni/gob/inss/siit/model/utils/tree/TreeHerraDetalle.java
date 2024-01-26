package ni.gob.inss.siit.model.utils.tree;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abenavidez on 24/8/2018.
 * Modificado por jvillanueva el 09/06/2020.
 * Modificado por jvillanueva 18/11/2020.
 * Modificado por scastillo 15/2/2021: buscarNodoImpl
 */
public class TreeHerraDetalle {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static TreeNode nodeIndex;
    private static Boolean encontrado = false;
    private static List<Map<String, Object>> listaDuplicado = new ArrayList<>();
    private static List<Integer> listaDuplicadoNodoId = new ArrayList<>();
    private TreeNode root = new DefaultTreeNode("base", null);
    private String nodo_id;
    private String nodo_max;
    private String herramienta_id;
    private String id;
    private String padre;
    private String padre_nivel1;
    private String padre_nivel2;
    private String padre_nivel3;
    private String nivel;
    private String orden;
    private String orden_concatenado;
    private String nombre;
    private String color_id;
    private String codigo_rgb;
    private String tipo;
    private String pregunta_configuracion_id;
    private String pregunta_configuracion_codigo;
    private String ref_tipo_seleccion;
    private String ref_tipo_pregunta;
    private String tiene_subpregunta;
    private String nivel_abona;
    private String parcial;
    private String valor;
    private String respuesta_c;
    private String respuesta_cp;
    private String respuesta_nc;
    private String respuesta_na;
    private String comentario;
    private String activo_comentario;
    private String deshabilitar_na;
    private String puntaje;
    private String accion;
    private String inspeccion_detalle_id;
    private String respuesta;
    private List<Map<String, Object>> registros;
    private List<NodeHerraDetalle> nodos = new ArrayList<>();
    private List<NodeHerraDetalle> nodoHoja = new ArrayList<>();
    private List<NodeHerraDetalle> nodosOpcionUnica = new ArrayList<>();

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public TreeHerraDetalle(String nodo_id, String nodo_max, String herramienta_id, String id, String padre, String padre_nivel1, String padre_nivel2, String padre_nivel3, String nivel, String orden, String orden_concatenado, String nombre, String color_id, String codigo_rgb, String tipo, String pregunta_configuracion_id,
                            String pregunta_configuracion_codigo, String ref_tipo_seleccion, String ref_tipo_pregunta, String tiene_subpregunta, String nivel_abona, String parcial, String valor, String respuesta_c, String respuesta_cp, String respuesta_nc,
                            String respuesta_na, String comentario, String activo_comentario, String deshabilitar_na, String puntaje, String accion, String inspeccion_detalle_id, String respuesta) {
        this.nodo_id = nodo_id;
        this.nodo_max = nodo_max;
        this.herramienta_id = herramienta_id;
        this.id = id;
        this.padre = padre;
        this.padre_nivel1 = padre_nivel1;
        this.padre_nivel2 = padre_nivel2;
        this.padre_nivel3 = padre_nivel3;
        this.nivel = nivel;
        this.orden = orden;
        this.orden_concatenado = orden_concatenado;
        this.nombre = nombre;
        this.color_id = color_id;
        this.codigo_rgb = codigo_rgb;
        this.tipo = tipo;
        this.pregunta_configuracion_id = pregunta_configuracion_id;
        this.pregunta_configuracion_codigo = pregunta_configuracion_codigo;
        this.ref_tipo_seleccion = ref_tipo_seleccion;
        this.ref_tipo_pregunta = ref_tipo_pregunta;
        this.tiene_subpregunta = tiene_subpregunta;
        this.nivel_abona = nivel_abona;
        this.parcial = parcial;
        this.valor = valor;
        this.respuesta_c = respuesta_c;
        this.respuesta_cp = respuesta_cp;
        this.respuesta_nc = respuesta_nc;
        this.respuesta_na = respuesta_na;
        this.comentario = comentario;
        this.activo_comentario = activo_comentario;
        this.deshabilitar_na = deshabilitar_na;
        this.puntaje = puntaje;
        this.accion = accion;
        this.inspeccion_detalle_id = inspeccion_detalle_id;
        this.respuesta = respuesta;
    }

    public static TreeNode buscarNodo(NodeHerraDetalle obj, TreeNode obgTrenode) {
        nodeIndex = null;
        return buscarNodoImpl(obj, obgTrenode);
    }

    public static TreeNode buscarNodoImpl(NodeHerraDetalle obj, TreeNode obgTrenode) {
        List<TreeNode> listTreenode = obgTrenode.getChildren();
        NodeHerraDetalle nodeHerraDetalle;
        for (TreeNode n : listTreenode) {
            nodeHerraDetalle = (NodeHerraDetalle) n.getData();
            if (nodeHerraDetalle.getNivel().equals(obj.getNivel()) &&
                    (nodeHerraDetalle.getNodo_id() != null && obj.getNodo_id() != null ? nodeHerraDetalle.getNodo_id().equals(obj.getNodo_id()) : true) &&
                    (nodeHerraDetalle.getPadre() != null && obj.getPadre() != null ? nodeHerraDetalle.getPadre().equals(obj.getPadre()) : true) &&
                    (nodeHerraDetalle.getTipo() != null && obj.getTipo() != null ? nodeHerraDetalle.getTipo().equals(obj.getTipo()) : true) &&
                    (nodeHerraDetalle.getId() != null && obj.getId() != null ? nodeHerraDetalle.getId().equals(obj.getId()) : true) &&
                    (nodeHerraDetalle.getOrden() != null && obj.getOrden() != null ? nodeHerraDetalle.getOrden().equals(obj.getOrden()) : true)) {
                nodeIndex = n;
                break;
            } else {
                nodeIndex = buscarNodo(obj, n);
                if (nodeIndex != null)
                    break;
            }
        }
        return nodeIndex;
    }

    public static void compararExpandirReducirArbol(TreeNode treeNodeAux, TreeNode treeNode) {
        encontrado = false;
        for (TreeNode n : treeNodeAux.getChildren()) {
            if (!n.isLeaf()) {
                compararExpandirReducirArbol2(n, treeNode);
                compararExpandirReducirArbol(n, treeNode);
            }
        }
    }

    public static void compararExpandirReducirArbol2(TreeNode treeNodeAux, TreeNode treeNode) {
        NodeHerraDetalle nodeHerraDetalle = (NodeHerraDetalle) treeNodeAux.getData();
        for (TreeNode n2 : treeNode.getChildren()) {
            NodeHerraDetalle nodeHerraDetalle2 = (NodeHerraDetalle) n2.getData();
            if (!encontrado && nodeHerraDetalle.getNivel().equals(nodeHerraDetalle2.getNivel()) && nodeHerraDetalle.getId().equals(nodeHerraDetalle2.getId()) &&
                    Integer.valueOf(nodeHerraDetalle.getPadre_nivel1() == null ? 0 : nodeHerraDetalle.getPadre_nivel1()).equals(nodeHerraDetalle2.getPadre_nivel1() == null ? 0 : nodeHerraDetalle2.getPadre_nivel1()) &&
                    Integer.valueOf(nodeHerraDetalle.getPadre_nivel2() == null ? 0 : nodeHerraDetalle.getPadre_nivel2()).equals(nodeHerraDetalle2.getPadre_nivel2() == null ? 0 : nodeHerraDetalle2.getPadre_nivel2()) &&
                    Integer.valueOf(nodeHerraDetalle.getPadre_nivel3() == null ? 0 : nodeHerraDetalle.getPadre_nivel3()).equals(nodeHerraDetalle2.getPadre_nivel3() == null ? 0 : nodeHerraDetalle2.getPadre_nivel3())) {
                if (n2.isExpanded())
                    treeNodeAux.setExpanded(true);
                encontrado = true;
                break;
            } else {
                if (!n2.isLeaf())
                    compararExpandirReducirArbol2(treeNodeAux, n2);
                if (encontrado)
                    break;
            }
        }
    }

    public static List<Map<String, Object>> duplicadoOrden(TreeNode treeNode) {
        for (TreeNode n1 : treeNode.getChildren()) {
            NodeHerraDetalle nodeHerraDetalle = (NodeHerraDetalle) n1.getData();
            for (TreeNode n2 : treeNode.getChildren()) {
                NodeHerraDetalle nodeHerraDetalle2 = (NodeHerraDetalle) n2.getData();
                if (!nodeHerraDetalle.getNodo_id().equals(nodeHerraDetalle2.getNodo_id()) && nodeHerraDetalle.getOrden() != null && nodeHerraDetalle2.getOrden() != null &&
                        nodeHerraDetalle.getOrden().equals(nodeHerraDetalle2.getOrden()) && listaDuplicadoNodoId.indexOf(nodeHerraDetalle2.getNodo_id()) == -1) {
                    Map<String, Object> map = new HashMap<>();
                    if (nodeHerraDetalle2.getNivel().equals(0)) {
                        map.put("orden", nodeHerraDetalle2.getOrden());
                        map.put("seccion", nodeHerraDetalle2.getNombre());
                        map.put("subseccion", null);
                        map.put("pregunta", null);
                        map.put("subpregunta", null);
                    }
                    if (nodeHerraDetalle2.getNivel().equals(1)) {
                        NodeHerraDetalle seccion = (NodeHerraDetalle) n2.getParent().getData();
                        map.put("orden", seccion.getOrden() + "." + nodeHerraDetalle2.getOrden());
                        map.put("seccion", seccion.getNombre());
                        map.put("subseccion", nodeHerraDetalle2.getNombre());
                        map.put("pregunta", null);
                        map.put("subpregunta", null);
                    }
                    if (nodeHerraDetalle2.getNivel().equals(2)) {
                        NodeHerraDetalle seccion = (NodeHerraDetalle) n2.getParent().getParent().getData();
                        NodeHerraDetalle subseccion = (NodeHerraDetalle) n2.getParent().getData();
                        map.put("orden", seccion.getOrden() + "." + subseccion.getOrden() + "." + nodeHerraDetalle2.getOrden());
                        map.put("seccion", seccion.getNombre());
                        map.put("subseccion", subseccion.getNombre());
                        map.put("pregunta", nodeHerraDetalle2.getNombre());
                        map.put("subpregunta", null);
                    }
                    if (nodeHerraDetalle2.getNivel().equals(3)) {
                        NodeHerraDetalle seccion = (NodeHerraDetalle) n2.getParent().getParent().getParent().getData();
                        NodeHerraDetalle subseccion = (NodeHerraDetalle) n2.getParent().getParent().getData();
                        NodeHerraDetalle pregunta = (NodeHerraDetalle) n2.getParent().getData();
                        map.put("orden", seccion.getOrden() + "." + subseccion.getOrden() + "." + pregunta.getOrden() + "." + nodeHerraDetalle2.getOrden());
                        map.put("seccion", seccion.getNombre());
                        map.put("subseccion", subseccion.getNombre());
                        map.put("pregunta", pregunta.getNombre());
                        map.put("subpregunta", nodeHerraDetalle2.getNombre());
                    }
                    listaDuplicado.add(map);
                    listaDuplicadoNodoId.add(nodeHerraDetalle2.getNodo_id());
                }
            }
            if (!n1.isLeaf())
                duplicadoOrden(n1);
        }
        return listaDuplicado;
    }

    public static void setListaDuplicado(List<Map<String, Object>> listaDuplicado) {
        TreeHerraDetalle.listaDuplicado = listaDuplicado;
    }

    public static void setListaDuplicadoNodoId(List<Integer> listaDuplicadoNodoId) {
        TreeHerraDetalle.listaDuplicadoNodoId = listaDuplicadoNodoId;
    }

    private void parseNodes() {
        //Recorriendo nodos principales y agregandolos al root
        for (Map<String, Object> i : registros) {
            nodos.add(new NodeHerraDetalle(
                    (Integer) i.get(nodo_id),
                    (Integer) i.get(nodo_max),
                    (Integer) i.get(herramienta_id),
                    (Integer) i.get(id),
                    (Integer) i.get(padre),
                    (Integer) i.get(padre_nivel1),
                    (Integer) i.get(padre_nivel2),
                    (Integer) i.get(padre_nivel3),
                    (Integer) i.get(nivel),
                    (Integer) i.get(orden),
                    (String) i.get(orden_concatenado),
                    (String) i.get(nombre),
                    (Integer) i.get(color_id),
                    (String) i.get(codigo_rgb),
                    (String) i.get(tipo),
                    (Integer) i.get(pregunta_configuracion_id),
                    (String) i.get(pregunta_configuracion_codigo),
                    (String) i.get(ref_tipo_seleccion),
                    (String) i.get(ref_tipo_pregunta),
                    (Boolean) i.get(tiene_subpregunta),
                    (String) i.get(nivel_abona),
                    (BigDecimal) i.get(parcial),
                    (BigDecimal) i.get(valor),
                    (Boolean) i.get(respuesta_c),
                    (Boolean) i.get(respuesta_cp),
                    (Boolean) i.get(respuesta_nc),
                    (Boolean) i.get(respuesta_na),
                    (String) i.get(comentario),
                    (Boolean) i.get(activo_comentario),
                    (Boolean) i.get(deshabilitar_na),
                    (BigDecimal) i.get(puntaje),
                    (String) i.get(accion),
                    (Integer) i.get(inspeccion_detalle_id),
                    (String) i.get(respuesta)));
        }
    }

    public void setRegistros(List<Map<String, Object>> registros) {
        this.registros = registros;
        parseNodes();
    }

    public TreeNode generateTree() {
        //Recorriendo nodos principales y agregandolos al root
        for (NodeHerraDetalle n : nodos) {
            if (n.getPadre() == null) {
                //Comprlbando si tiene hijos
                if (haveChildrens(n.getNodo_id(), n.getNivel() + 1)) {
                    TreeNode node = new DefaultTreeNode(n, root);
                    searchChildrens(n.getNodo_id(), (n.getNivel() + 1), node);
                } else {
                    new DefaultTreeNode(n, root);
                    nodoHoja.add(n);
                }
            }
        }
        return root;
    }

    private boolean haveChildrens(Integer id, Integer nivel) {
        boolean result = false;
        for (NodeHerraDetalle n : nodos) {
            if (n.getPadre() != null && n.getNivel().equals(nivel)) {
                if ((n.getPadre()).equals(id)) {
                    result = true;
                }
            }
            if (result)
                break;
        }
        return result;
    }

    private void searchChildrens(Integer parentId, Integer nivel, TreeNode parentNode) {
        //Buscando hijos
        for (NodeHerraDetalle n : nodos) {
            if (n.getPadre() != null && n.getNivel().equals(nivel)) {
                if ((n.getPadre()).equals(parentId)) {
                    if (haveChildrens(n.getNodo_id(), (n.getNivel() + 1))) {
                        TreeNode node = new DefaultTreeNode(n, parentNode);
                        searchChildrens(n.getNodo_id(), (n.getNivel() + 1), node);
                    } else {
                        new DefaultTreeNode(n, parentNode);
                        nodoHoja.add(n);
                        if (nivel == 3 && n.getRef_tipo_seleccion() != null && n.getRef_tipo_seleccion().equals("171|01")) {
                            nodosOpcionUnica.add(n);
                        }
                    }
                }
            }
        }
    }

    public List<NodeHerraDetalle> getNodosOpcionUnica() {
        return nodosOpcionUnica;
    }

    public List<NodeHerraDetalle> getNodoHoja() {
        return nodoHoja;
    }
}
