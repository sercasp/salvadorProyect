package ni.gob.inss.siit.view.utils.tree;

import java.io.Serializable;

/**
 * Created by jmendoza on 8/11/2015.
 */
public class TreeTableSimulacro implements Serializable {

    private Integer id;
    private String texto;
    private String evalauacion;

    public TreeTableSimulacro(Integer id, String texto, String evalauacion){
        this.id = id;
        this.texto = texto;
        this.evalauacion = evalauacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getEvalauacion() {
        return evalauacion;
    }

    public void setEvalauacion(String evalauacion) {
        this.evalauacion = evalauacion;
    }
}
