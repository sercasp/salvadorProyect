package ni.gob.inss.barista.view.utils.tree;

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

public class NodeAll {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private String id;
    private String parentId;
    private String text;

    public NodeAll(String id, String parentId, String text) {
        this.id = id;
        this.parentId = parentId;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}