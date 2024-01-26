package ni.gob.inss.barista.view.utils.tree;

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
public class TreeAll {
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
    private List<Map<String, Object>> registros;
    private boolean expandedRoot;
    private List<NodeAll> nodos = new ArrayList<>();
    private List<NodeAll> topNodes = new ArrayList<>();

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public TreeAll(String rootTitle) {
        root = new DefaultTreeNode("root", null);
        //rootNode = new DefaultTreeNode("rootType",rootTitle, root);
    }

    public TreeAll(String rootTitle, String idField, String textField, String parentField) {
        root = new DefaultTreeNode("Root", null);
        //rootNode = new DefaultTreeNode("rootType",rootTitle, root);
        this.idField = idField;
        this.textField = textField;
        this.parentField = parentField;
    }

    private void parseNodes() {
        //Recorriendo nodos principales y agregandolos al root
        for (Map<String, Object> i : registros) {
            if (i.get(parentField) == null) {
                nodos.add(new NodeAll((String) i.get(idField).toString(), (String) i.get(parentField), (String) i.get(textField)));
                topNodes.add(new NodeAll((String) i.get(idField).toString(), (String) i.get(parentField), (String) i.get(textField)));
            } else {
                nodos.add(new NodeAll((String) i.get(idField).toString(), (String) i.get(parentField).toString(), (String) i.get(textField)));
            }
        }
    }

    public TreeNode generateTree() {
        //Recorriendo nodos principales y agregandolos al root
        for (NodeAll n : nodos) {
            if (n.getParentId() == null) {
                //Comprlbando si tiene hijos
                if (haveChildrens(n.getId())) {
                    TreeNode node = new DefaultTreeNode(n, root);
                    searchChildrens(n.getId(), node);
                } else {
                    new DefaultTreeNode(n, root);
                }
            }
        }
        return root;
    }

    private void searchChildrens(String parentId, TreeNode parentNode) {
        //Buscando hijos
        for (NodeAll n : nodos) {
            if (n.getParentId() != null) {
                if ((n.getParentId()).equals(parentId)) {
                    if (haveChildrens(n.getId())) {
                        TreeNode node = new DefaultTreeNode(n, parentNode);
                        searchChildrens(n.getId(), node);
                    } else {
                        new DefaultTreeNode(n, parentNode);
                    }
                }
            }
        }
    }

    private boolean haveChildrens(String id) {
        boolean result = false;
        for (NodeAll n : nodos) {
            if (n.getParentId() != null) {
                if ((n.getParentId()).equals(id)) {
                    result = true;
                }
            }
            if (result)
                break;
        }
        return result;
    }
}