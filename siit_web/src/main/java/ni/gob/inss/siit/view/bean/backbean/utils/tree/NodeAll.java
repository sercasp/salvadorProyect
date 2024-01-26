package ni.gob.inss.siit.view.bean.backbean.utils.tree;


import lombok.*;

@Data
public class NodeAll {
    private String id;
    private String parentId;
    private String text;
    private String icon;
    private String color;
    private String origen;

    public NodeAll(String id, String parentId, String text, String icon,String color, String origen){
        this.id = id;
        this.parentId= parentId;
        this.text = text;
        this.icon = icon;
        this.color = color;
        this.origen = origen;
    }
}
