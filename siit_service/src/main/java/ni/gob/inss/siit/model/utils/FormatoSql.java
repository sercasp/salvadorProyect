package ni.gob.inss.siit.model.utils;

import java.text.Normalizer;

/**
 * Created by jjrivera on 11/30/2016.
 * Modificado por jvillanueva 02/01/2018.
 * Modificado por mzelaya 30/07/2018.
 * Modificado por jvillanueva 18/11/2020.
 */
public class FormatoSql {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private String campos;

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public FormatoSql(String campos) {
        this.campos = campos.toLowerCase();
    }

    public FormatoSql removerAcentos() {
        campos = Normalizer.normalize(campos, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        return this;
    }

    public FormatoSql removerEspacios() {
        campos = campos.replace(" ", "_");
        return this;
    }

    public FormatoSql removerCaracteresEspeciales() {
        campos = campos.replaceAll("[^a-zA-Z,0-9_]+", "");
        return this;
    }

    public String build() {
        return campos;
    }
}
