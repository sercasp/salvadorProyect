package ni.gob.inss.siit.view.utils.tree;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
