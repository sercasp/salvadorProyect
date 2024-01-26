package ni.gob.inss.siit.businesslogic.service.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
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

////@SuppressWarnings({"unchecked", "RedundantThrows"})
public class RestAPIUtilsModificado {
    protected HttpRequestBase httpReqCE;
    protected HttpRequestBase tempHttpReq;
    protected HttpResponse httpRes;
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private CookieStore cookieStore = new BasicCookieStore();
    private HttpClientContext httpContext = HttpClientContext.create();
    private String host;
    private int port;

    public RestAPIUtilsModificado() {
        this.httpContext.setAttribute("http.cookie-store", this.cookieStore);
    }

    protected void putSampleFolder() throws Exception {
        this.tempHttpReq = new HttpPut();
        this.sendAndAssert(this.tempHttpReq, "/resource/JUNIT_NEW_FOLDER", "resources/folder_URI.SAMPLE_REST_FOLDER.xml", 201);
    }

    protected void deleteSampleFolder() throws Exception {
        this.tempHttpReq = new HttpDelete();
        this.sendAndAssert(this.tempHttpReq, "/resource/JUNIT_NEW_FOLDER", "resources/folder_URI.SAMPLE_REST_FOLDER.xml", 201);
    }

    //    //@SuppressWarnings("ResultOfMethodCallIgnored")
    public void loginToServer(String USER_NAME, String PASSWORD) {
        ArrayList ce_qparams = new ArrayList();
        ce_qparams.add(new BasicNameValuePair("j_username", USER_NAME));
        ce_qparams.add(new BasicNameValuePair("j_password", PASSWORD));

        try {
            this.httpReqCE = new HttpPost();
            this.httpRes = this.sendRequest(this.httpReqCE, "/login", ce_qparams, true);
            IOUtils.toString(this.httpRes.getEntity().getContent());
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public void setHTTPAuthentication(String USER_NAME, String PASSWORD) {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(USER_NAME, PASSWORD);
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, creds);
        this.httpContext.setCredentialsProvider(credsProvider);
    }

    protected HttpResponse sendAndAssert(HttpRequestBase req, String service, String rdPath, int expectedStatus) throws Exception {
        BasicHttpEntity reqEntity = new BasicHttpEntity();
        reqEntity.setContent(new FileInputStream(rdPath));
        ((HttpEntityEnclosingRequestBase) req).setEntity(reqEntity);
        return this.sendAndAssert(req, service, expectedStatus);
    }

    protected HttpResponse sendAndAssert(HttpRequestBase req, String service, int expectedStatus) throws Exception {
        this.httpRes = this.sendRequest(req, service, null, true);
        return this.httpRes;
    }

    protected HttpResponse sendRequest(HttpRequestBase req, String service, List<NameValuePair> qparams, boolean isV2) throws Exception {
        if (!isV2) {
            req.setURI(this.createURI("/jasperserver/rest" + service, qparams));
        } else {
            req.setURI(this.createURI("/jasperserver/rest_v2" + service, qparams));
        }

        this.httpRes = this.httpClient.execute(req, this.httpContext);
        return this.httpRes;
    }

    private URI createURI(String path, List<NameValuePair> qparams) throws Exception {
        URI uri;
        if (qparams != null) {
            uri = (new URIBuilder()).setScheme("http").setHost(this.host).setPort(this.port).setPath(path).setParameters(qparams).setFragment(null).build();
        } else {
            uri = (new URL("http", this.host, this.port, path)).toURI();
        }

        return uri;
    }

    protected boolean isValidResposnse() throws Exception {
        return this.isValidResposnse(200);
    }

    protected boolean isValidResposnse(int expected_respose_code) throws Exception {
        return this.httpRes.getStatusLine().getStatusCode() == expected_respose_code;
    }

    protected boolean isValidResposnse(HttpResponse res, int expected_respose_code) throws Exception {
        return this.httpRes.getStatusLine().getStatusCode() == expected_respose_code;
    }

    public void releaseConnection(HttpResponse res) throws Exception {
        InputStream is = res.getEntity().getContent();
        is.close();
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}