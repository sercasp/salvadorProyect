package ni.gob.inss.siit.view.bean.backbean.utils.tree;

import lombok.Data;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class TreeAll {
    private TreeNode root;
    private TreeNode rootNode;
    private TreeNode selectedNode;
    private String idField;
    private String textField;
    private String parentField;
    private String icons;
    private String color;
    private String origen;
    private List<Map<String, Object>> registros;
    private boolean expandedRoot;
    private List<NodeAll> nodos = new ArrayList<NodeAll>();
    private List<NodeAll> topNodes = new ArrayList<NodeAll>();

    public TreeAll(String rootTitle) {
        root = new DefaultTreeNode("root", null);
        //rootNode = new DefaultTreeNode("rootType",rootTitle, root);
    }

    public TreeAll(String rootTitle, String idField, String textField, String parentField, String icons, String color, String origen) {
        root = new DefaultTreeNode("Root", null);
        this.idField = idField;
        this.textField = textField;
        this.parentField = parentField;
        this.icons = icons;
        this.color = color;
        this.origen = origen;
    }

    private void parseNodes() {
        //Recorriendo nodos principales y agregandolos al root
        for (Map<String, Object> i : registros) {
            if (i.get(parentField) == null) {
                nodos.add(new NodeAll((String) i.get(idField).toString(), (String) i.get(parentField), (String) i.get(textField), (String) i.get(icons), (String) i.get(color), (String) i.get(origen)));
                topNodes.add(new NodeAll((String) i.get(idField).toString(), (String) i.get(parentField), (String) i.get(textField), (String) i.get(icons), (String) i.get(color), (String) i.get(origen)));
            } else {
                nodos.add(new NodeAll((String) i.get(idField).toString(), (String) i.get(parentField).toString(), (String) i.get(textField), (String) i.get(icons), (String) i.get(color), (String) i.get(origen)));
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
