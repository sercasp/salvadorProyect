package ni.gob.inss.siit.businesslogic.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Creado por  mzelaya el 01/01/2018.
 * Modificado por jvillanueva 02/01/2018.
 * Modificado por jvillanueva 11/06/2020.
 * Modificado por jvillanueva 16/07/2020.
 */
public class ParameterBuilder {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private final Map<String, Object> params;

    private ParameterBuilder(String key, Object value) {
        params = new HashMap<>();
        params.put(key, value);
    }

    public static ParameterBuilder with(String key, Object value) {
        return new ParameterBuilder(key, value);
    }

    public ParameterBuilder and(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return params;
    }
}