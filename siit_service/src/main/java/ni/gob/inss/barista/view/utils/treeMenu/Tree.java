package ni.gob.inss.barista.view.utils.treeMenu;

import lombok.Data;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Filtro para expirar la sesión en peticiones push</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 10/09/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
public class Tree {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private TreeNode root;
    private TreeNode rootNode;
    private TreeNode selectedNode;
    private String idField;
    private String textField;
    private String parentField;
    private String superParentId;
    private String estado;
    private List<Map<String, Object>> registros;
    private boolean expandedRoot;
    private List<Node> nodos = new ArrayList<>();
    private List<Node> topNodes = new ArrayList<>();

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public Tree(String rootTitle) {
        root = new DefaultTreeNode("root", null);
    }

    public Tree(String rootTitle, String idField, String textField, String parentField, String superParentId, String estado) {
        root = new DefaultTreeNode("Root", null);
        this.idField = idField;
        this.textField = textField;
        this.parentField = parentField;
        this.superParentId = superParentId;
        this.estado = estado;
    }

    private void parseNodes() {
        //Recorriendo nodos principales y agregandolos al root
        for (Map<String, Object> i : registros) {
            if (i.get(superParentId) == null && i.get(parentField) == null) {
                topNodes.add(new Node(i.get(idField).toString(), (String) i.get(parentField), (String) i.get(textField), (String) i.get(superParentId), (String) i.get(estado)));
                nodos.add(new Node(i.get(idField).toString(), (String) i.get(parentField), (String) i.get(textField), (String) i.get(superParentId), (String) i.get(estado)));
            } else if (i.get(parentField) == null) {
                nodos.add(new Node(i.get(idField).toString(), (String) i.get(parentField), (String) i.get(textField), i.get(superParentId).toString(), (String) i.get(estado)));
                topNodes.add(new Node(i.get(idField).toString(), (String) i.get(parentField), (String) i.get(textField), i.get(superParentId).toString(), (String) i.get(estado)));
            } else {
                nodos.add(new Node(i.get(idField).toString(), i.get(parentField).toString(), (String) i.get(textField), i.get(superParentId).toString(), (String) i.get(estado)));
            }
        }
    }

    public TreeNode generateTree() {
        //Recorriendo nodos principales y agregandolos al root
        for (Node n : nodos) {
            if (n.getSuperParentId() == null && n.getParentId() == null) {
                //Comprobando si tiene hijos
                if (haveChildrens(n.getId(), 2)) {
                    TreeNode node = new DefaultTreeNode(n, root);
                    searchChildrens(n.getId(), node);
                }
            }
        }
        return root;
    }

    private void searchChildrens(String id, TreeNode parentNode) {
        //Buscando hijos
        for (Node n : nodos) {
            if (n.getSuperParentId() != null && n.getParentId() == null) {
                if ((n.getSuperParentId()).equals(id)) {
                    if (haveChildrens(n.getSuperParentId(), 2)) {
                        TreeNode node = new DefaultTreeNode(n, parentNode);
                        searchChildrens(n.getId(), node);
                    } else {
                        new DefaultTreeNode(n, parentNode);
                    }
                }
            } else if (n.getSuperParentId() != null && n.getParentId() != null) {
                if ((n.getParentId()).equals(id)) {
                    if (haveChildrens(n.getParentId(), 1)) {
                        TreeNode node = new DefaultTreeNode(n, parentNode);
                        searchChildrens(n.getId(), node);
                    } else {
                        new DefaultTreeNode(n, parentNode);
                    }
                }
            }
        }
    }

    private boolean haveChildrens(String id, Integer tipo) {
        boolean result = false;
        for (Node n : nodos) {
            if (n.getParentId() != null && tipo == 1) {
                if ((n.getParentId()).equals(id)) {
                    result = true;
                }
            } else if (n.getParentId() == null && n.getSuperParentId() != null && tipo == 2) {
                if ((n.getSuperParentId()).equals(id)) {
                    result = true;
                }
            }
            if (result)
                break;
        }
        return result;
    }

    public TreeNode generateTreeConEstado() {
        //Recorriendo nodos principales y agregandolos al root
        for (Node n : nodos) {
            if (n.getSuperParentId() == null && n.getParentId() == null) {
                //Comprobando si tiene hijos
                if (haveChildrens(n.getId(), 2)) {
                    TreeNode node = new DefaultTreeNode(n, root);
                    node.setSelected(n.getEstado().equals("AG"));
                    searchChildrensConEstado(n.getId(), node);
                }
            }
        }
        return root;
    }

    private void searchChildrensConEstado(String id, TreeNode parentNode) {
        //Buscando hijos
        for (Node n : nodos) {
            if (n.getSuperParentId() != null && n.getParentId() == null) {
                if ((n.getSuperParentId()).equals(id)) {
                    if (haveChildrens(n.getSuperParentId(), 2)) {
                        TreeNode node = new DefaultTreeNode(n, parentNode);
                        node.setSelected(n.getEstado().equals("AG"));
                        searchChildrensConEstado(n.getId(), node);
                    } else {
                        new DefaultTreeNode(n, parentNode);
                    }
                }
            } else if (n.getSuperParentId() != null && n.getParentId() != null) {
                if ((n.getParentId()).equals(id)) {
                    if (haveChildrens(n.getParentId(), 1)) {
                        TreeNode node = new DefaultTreeNode(n, parentNode);
                        node.setSelected(n.getEstado().equals("AG"));
                        searchChildrensConEstado(n.getId(), node);
                    } else {
                        new DefaultTreeNode(n, parentNode);
                    }
                }
            }
        }
    }
}