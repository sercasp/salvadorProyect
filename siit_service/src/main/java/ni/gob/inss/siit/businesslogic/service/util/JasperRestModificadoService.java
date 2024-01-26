package ni.gob.inss.siit.businesslogic.service.util;

import java.util.Map;

/**
 * Created by abenavidez on 15/11/2018.
 */
public interface JasperRestModificadoService {
    byte[] getReport(String var1, String var2, Map<String, String> var3) throws Exception;
}
