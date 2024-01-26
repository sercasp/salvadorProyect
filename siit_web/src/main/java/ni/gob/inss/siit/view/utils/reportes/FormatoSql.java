package ni.gob.inss.siit.view.utils.reportes;

import java.text.Normalizer;

/**
 * Created by jjrivera on 11/30/2016.
 */
public class FormatoSql {

    private String campos;

    public FormatoSql(String campos){
        this.campos = campos.toLowerCase();
    }

    public FormatoSql removerAcentos(){
        campos = Normalizer.normalize(campos, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        return this;
    }

    public FormatoSql removerCaracteresEspeciales(){
        campos = campos.replaceAll("[^a-zA-Z,0-9]+","");
        return this;
    }

    public String build(){
        return campos;
    }
}
