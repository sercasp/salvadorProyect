package ni.gob.inss.siit.view.bean.backbean.utils.tree;

import lombok.*;

import java.io.Serializable;

/**
 * Created by jmendoza on 8/11/2015.
 */
@Data
public class TreeTableSimulacro implements Serializable {
    private Integer id;
    private String texto;
    private String evalauacion;

    public TreeTableSimulacro(Integer id, String texto, String evalauacion){
        this.id = id;
        this.texto = texto;
        this.evalauacion = evalauacion;
    }

}
