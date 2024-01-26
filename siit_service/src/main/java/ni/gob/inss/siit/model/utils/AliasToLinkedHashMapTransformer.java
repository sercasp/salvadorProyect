package ni.gob.inss.siit.model.utils;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.util.LinkedHashMap;

/**
 * Created by jjrivera on 5/10/2016.
 * Modificado por jvillanueva 02/01/2018.
 */
public class AliasToLinkedHashMapTransformer extends AliasedTupleSubsetResultTransformer {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        LinkedHashMap result = new LinkedHashMap(tuple.length);

        for (int i = 0; i < tuple.length; ++i) {
            String alias = aliases[i];
            if (alias != null) {
                result.put(alias, tuple[i]);
            }
        }
        return result;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] strings, int i) {
        return false;
    }
}
