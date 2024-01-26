package ni.gob.inss.siit.view.utils.tree;

import lombok.Data;

import java.io.Serializable;

@Data
public class nodo implements Serializable, Comparable<nodo> {

    private String name;
    private Object id;
    private String type;

    public nodo(String name, Object id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public int compareTo(nodo nodo) {
        return this.getName().compareTo(nodo.getName());
    }
}
