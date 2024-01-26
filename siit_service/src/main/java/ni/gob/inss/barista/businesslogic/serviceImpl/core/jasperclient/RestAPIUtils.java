package ni.gob.inss.barista.businesslogic.serviceImpl.core.jasperclient;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 31/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public class RestAPIUtils {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    protected HttpRequestBase httpReqCE;
    protected HttpRequestBase tempHttpReq;
    protected HttpResponse httpRes;
    private CloseableHttpClient httpClient;
    private CookieStore cookieStore;
    private HttpClientContext httpContext;
    private String host;
    private int port;

    public RestAPIUtils() {
        httpClient = HttpClients.createDefault();
        cookieStore = new BasicCookieStore();
        httpContext = HttpClientContext.create();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    protected void putSampleFolder() throws Exception {
        tempHttpReq = new HttpPut();
        sendAndAssert(tempHttpReq, JasperRestConsts.RESOURCE + "/JUNIT_NEW_FOLDER", JasperRestConsts.RESOURCES_LOCAL_PATH
                + JasperRestConsts.SAMPLE_FOLDER_RD, HttpStatus.SC_CREATED);
    }

    protected void deleteSampleFolder() throws Exception {
        tempHttpReq = new HttpDelete();
        sendAndAssert(tempHttpReq, JasperRestConsts.RESOURCE + "/JUNIT_NEW_FOLDER", JasperRestConsts.RESOURCES_LOCAL_PATH
                + JasperRestConsts.SAMPLE_FOLDER_RD, HttpStatus.SC_CREATED);
    }

    public void loginToServer(String USER_NAME, String PASSWORD) {
        //building the request parameters
        List<NameValuePair> ce_qparams = new ArrayList<NameValuePair>();
        ce_qparams.add(new BasicNameValuePair(JasperRestConsts.PARAMETER_USERNAME, USER_NAME));
        ce_qparams.add(new BasicNameValuePair(JasperRestConsts.PARAM_PASSWORD, PASSWORD));

        try {
            httpReqCE = new HttpPost();
            httpRes = sendRequest(httpReqCE, JasperRestConsts.SERVICE_LOGIN, ce_qparams, true);

            //consuming the content to close the stream
            IOUtils.toString(httpRes.getEntity().getContent(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHTTPAuthentication(String USER_NAME, String PASSWORD) {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(USER_NAME, PASSWORD);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, creds);
        httpContext.setCredentialsProvider(credsProvider);
    }

    /* SERVICE FUNCTION */
    protected HttpResponse sendAndAssert(HttpRequestBase req, String service, String rdPath, int expectedStatus) throws Exception {
        //building the body
        BasicHttpEntity reqEntity = new BasicHttpEntity();
        //appending the file descriptor from a file
        reqEntity.setContent(new FileInputStream(rdPath));
        ((HttpEntityEnclosingRequestBase) req).setEntity(reqEntity);

        //executing the request
        return sendAndAssert(req, service, expectedStatus);
    }

    protected HttpResponse sendAndAssert(HttpRequestBase req, String service, int expectedStatus) throws Exception {
        httpRes = sendRequest(req, service, null, true);
        //Assert.assertTrue("basic response check did not pass", isValidResposnse(expectedStatus));
        return httpRes;
    }

    // send a request to the CE server
    protected HttpResponse sendRequest(HttpRequestBase req, String service, List<NameValuePair> qparams,
                                       boolean isV2) throws Exception {
        if (!isV2) {
            req.setURI(createURI(JasperRestConsts.BASE_REST_URL + service, qparams));
        } else {
            req.setURI(createURI(JasperRestConsts.BASE_REST_URL_V2 + service, qparams));
        }
        httpRes = httpClient.execute(req, httpContext);
        return httpRes;
    }

    private URI createURI(String path, List<NameValuePair> qparams) throws Exception {
        URI uri;
        if (qparams != null) {
            uri = new URIBuilder()
                    .setScheme(JasperRestConsts.SCHEME)
                    .setHost(host)
                    .setPort(port)
                    .setPath(path)
                    .setParameters(qparams)
                    .setFragment(null)
                    .build();
            //uri = URIUtils.createURI(JasperRestConsts.SCHEME, host, port, path, URLEncodedUtils.format(qparams, "UTF-8"), null);
        } else {
            uri = (new URL(JasperRestConsts.SCHEME, host, port, path)).toURI();
        }
        return uri;
    }

    protected boolean isValidResposnse() throws Exception {
        return isValidResposnse(HttpStatus.SC_OK);
    }

    protected boolean isValidResposnse(int expected_respose_code) throws Exception {
        return httpRes.getStatusLine().getStatusCode() == expected_respose_code;
    }

    protected boolean isValidResposnse(HttpResponse res, int expected_respose_code) throws Exception {
        return httpRes.getStatusLine().getStatusCode() == expected_respose_code;
    }

    public void releaseConnection(HttpResponse res) throws Exception {
        InputStream is = res.getEntity().getContent();
        is.close();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}