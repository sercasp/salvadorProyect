package ni.gob.inss.barista.businesslogic.service.core.jasperclient;


import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Servicio para utilidades JasperServer</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 28/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface JasperRestService {
    @Deprecated
    byte[] getReport(String resourceUri, String type, Map<String, String> resourceParameters) throws Exception;

    void getReport(String host, Integer port, String user, String password, String resourceUri, String type, Map<String, String> resourceParameters, String fileName) throws Exception;

    void getReport(String resourceUri, String type, Map<String, String> resourceParameters, String fileName) throws Exception;

    void getReport(String resourceUri, String type, Map<String, String> resourceParameters, String fileName, HttpServletResponse response) throws Exception;

    byte[] getReportBytes(String resourceUri, String type, Map<String, String> resourceParameters, String fileName) throws Exception;
}