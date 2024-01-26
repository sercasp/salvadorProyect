package ni.gob.inss.barista.businesslogic.serviceImpl.core.jasperclient;

import ni.gob.inss.barista.businesslogic.service.ServiceException;
import ni.gob.inss.barista.businesslogic.service.core.jasperclient.JasperRestService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Servicio de Utilidades para JasperServer</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 28/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Service
public class JasperRestServiceImpl implements JasperRestService {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private RestAPIUtils restUtils = new RestAPIUtils();
    private HttpResponse httpRes;
    private String USER_NAME;
    private String PASSWORD;
    private String HOST;
    private Integer PORT;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @PostConstruct
    public void init() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL urlResource = classLoader.getResource("config.properties");
        Properties props = new Properties();
        props.load(urlResource.openStream());
        HOST = props.getProperty("jasperserver.Host");
        PORT = Integer.parseInt(props.getProperty("jasperserver.Port"));
        USER_NAME = props.getProperty("jasperserver.User");
        PASSWORD = props.getProperty("jasperserver.Password");
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Deprecated
    @Override
    public byte[] getReport(String resourceUri, String type, Map<String, String> resourceParameters) throws Exception {
        if (resourceUri == null) {
            throw new ServiceException("Debe proporcionar un recurso");
        }
        List<NameValuePair> parametros = new ArrayList<>();
        resourceUri = resourceUri + "." + type;
        restUtils.loginToServer(USER_NAME, PASSWORD);

        //Remplazando parámetros para ejecutar consulta si se pasaron parametros
        if (resourceParameters != null) {
            for (Map.Entry<String, String> e : resourceParameters.entrySet()) {
                parametros.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }
        }

        //Enviando petición
        httpRes = restUtils.sendRequest(new HttpGet(), resourceUri, parametros, true);

        // Write binary content to output
        InputStream is = httpRes.getEntity().getContent();
        byte[] buffer = new byte[8 * 1024];

        int offset = 0;
        int numRead;
        while ((offset < buffer.length) && ((numRead = is.read(buffer, offset, buffer.length - offset)) >= 0)) {
            offset += numRead;
        }
        is.close();
        restUtils.releaseConnection(httpRes);
        return buffer;
    }

    @Override
    public void getReport(String host, Integer port, String user, String password, String resourceUri, final String type, final Map<String, String> resourceParameters, String fileName) throws Exception {
        restUtils.setHost(host);
        restUtils.setPort(port);
        restUtils.setHTTPAuthentication(user, password);
        getReportRequest(resourceUri, type, resourceParameters, fileName);
    }

    @Override
    public void getReport(String resourceUri, final String type, final Map<String, String> resourceParameters, String fileName) throws Exception {
        restUtils.setHost(HOST);
        restUtils.setPort(PORT);
        restUtils.setHTTPAuthentication(USER_NAME, PASSWORD);
        getReportRequest(resourceUri, type, resourceParameters, fileName);
    }

    @Override
    public void getReport(String resourceUri, String type, Map<String, String> resourceParameters, String fileName, HttpServletResponse response) throws Exception {
        this.restUtils.setHost(this.HOST);
        this.restUtils.setPort(this.PORT.intValue());
        this.restUtils.setHTTPAuthentication(this.USER_NAME, this.PASSWORD);
        this.getReportRequest(resourceUri, type, resourceParameters, fileName, response);
    }

    @Override
    public byte[] getReportBytes(String resourceUri, String type, Map<String, String> resourceParameters, String fileName) throws Exception {
        this.restUtils.setHost(this.HOST);
        this.restUtils.setPort(this.PORT.intValue());
        this.restUtils.setHTTPAuthentication(this.USER_NAME, this.PASSWORD);
        if (StringUtils.isBlank(resourceUri)) {
            throw new ServiceException("Debe proporcionar un recurso.");
        } else {
            resourceUri = resourceUri + "." + type;
            List<NameValuePair> parametros = new ArrayList();
            if (resourceParameters != null) {

                for (Map.Entry<String, String> stringStringEntry : resourceParameters.entrySet()) {
                    Map.Entry<String, String> e = (Map.Entry) stringStringEntry;
                    parametros.add(new BasicNameValuePair((String) e.getKey(), (String) e.getValue()));
                }
            }
            this.httpRes = this.restUtils.sendRequest(new HttpGet(), resourceUri, parametros, true);
            InputStream contentIS = this.httpRes.getEntity().getContent();
            BufferedInputStream bufferedInReporte = new BufferedInputStream(contentIS);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                byte[] buffer = new byte[8192];

                int length;
                while ((length = bufferedInReporte.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                out.flush();
                return out.toByteArray();
            } catch (IOException var11) {
                var11.printStackTrace();
            } finally {
                contentIS.close();
                bufferedInReporte.close();
                out.close();
            }
            return null;
        }
    }

    private void getReportRequest(String resourceUri, String type, Map<String, String> resourceParameters, String fileName, HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(resourceUri)) {
            throw new ServiceException("Debe proporcionar un recurso.");
        } else {
            resourceUri = resourceUri + "." + type;
            ArrayList parametros = new ArrayList();
            if (resourceParameters != null) {

                for (Map.Entry<String, String> stringStringEntry : resourceParameters.entrySet()) {
                    Map.Entry e = (Map.Entry) stringStringEntry;
                    parametros.add(new BasicNameValuePair((String) e.getKey(), (String) e.getValue()));
                }
            }
            Method oMethod = restUtils.getClass().getDeclaredMethod("sendRequest", HttpRequestBase.class, String.class, List.class, boolean.class);
            oMethod.setAccessible(true);
            httpRes = (HttpResponse) oMethod.invoke(restUtils, new HttpGet(), resourceUri, parametros, true);
            InputStream contentIS1 = this.httpRes.getEntity().getContent();
            this.generarReporte(contentIS1, fileName + "." + type, response);
        }
    }

    private void getReportRequest(String resourceUri, final String type, final Map<String, String> resourceParameters, String fileName) throws Exception {
        if (!StringUtils.isBlank(resourceUri)) {
            resourceUri = resourceUri + "." + type;
            List<NameValuePair> parametros = new ArrayList<>();
            if (resourceParameters != null) {
                for (Map.Entry<String, String> e : resourceParameters.entrySet()) {
                    parametros.add(new BasicNameValuePair(e.getKey(), e.getValue()));
                }
            }

            //Enviando petición
            httpRes = restUtils.sendRequest(new HttpGet(), resourceUri, parametros, true);
            InputStream contentIS = httpRes.getEntity().getContent();
            this.generarReporte(contentIS, fileName + "." + type);
        } else {
            throw new ServiceException("Debe proporcionar un recurso.");
        }
    }

    private void generarReporte(final InputStream inReporte, String fileName) {
        BufferedInputStream bufferedInReporte = new BufferedInputStream(inReporte);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setContentType(new Tika().detect(bufferedInReporte));
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            byte[] buffer = new byte[8 * 1024];
            int length;
            while ((length = bufferedInReporte.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inReporte);
            IOUtils.closeQuietly(bufferedInReporte);
            IOUtils.closeQuietly(out);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    private void generarReporte(InputStream inReporte, String fileName, HttpServletResponse response) {
        BufferedInputStream bufferedInReporte = new BufferedInputStream(inReporte);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setContentType((new Tika()).detect(bufferedInReporte));
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            byte[] ioe = new byte[8192];

            int length;
            while ((length = bufferedInReporte.read(ioe)) > 0) {
                out.write(ioe, 0, length);
            }
            out.flush();
            inReporte.close();
            response.getOutputStream().close();
        } catch (IOException var11) {
            var11.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inReporte);
            IOUtils.closeQuietly(bufferedInReporte);
            IOUtils.closeQuietly(out);
        }
    }
}