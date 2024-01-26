package ni.gob.inss.barista.view.utils.treeMenu;

import lombok.Data;

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
public class Node {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private String id;
    private String parentId;
    private String text;
    private String superParentId;
    private String estado;

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public Node(String id, String parentId, String text, String superParentId, String estado) {
        this.id = id;
        this.parentId = parentId;
        this.text = text;
        this.superParentId = superParentId;
        this.estado = estado;
    }

}
