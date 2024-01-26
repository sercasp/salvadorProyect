package ni.gob.inss.siit.businesslogic.service.util;

import ni.gob.inss.barista.businesslogic.service.ServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * Created by abenavidez on 15/11/2018.
 * Modificado por jvillanueva 12/06/2020.
 */

////@SuppressWarnings("unchecked")
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JasperRestModificadoServiceImpl implements JasperRestModificadoService {
    private RestAPIUtilsModificado restUtils = new RestAPIUtilsModificado();
    private HttpResponse httpRes;
    private String USER_NAME;
    private String PASSWORD;
    private String HOST;
    private Integer PORT;

    public JasperRestModificadoServiceImpl() {
    }

    @PostConstruct
    public void init() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL urlResource = classLoader.getResource("config.properties");
        Properties props = new Properties();
        props.load(urlResource != null ? urlResource.openStream() : null);
        this.HOST = props.getProperty("jasperserver.Host");
        this.PORT = Integer.parseInt(props.getProperty("jasperserver.Port"));
        this.USER_NAME = props.getProperty("jasperserver.User");
        this.PASSWORD = props.getProperty("jasperserver.Password");
    }

    @Override
    public byte[] getReport(String resourceUri, String type, Map<String, String> resourceParameters) throws Exception {
        byte[] ioe;
        this.restUtils.setHost(this.HOST);
        this.restUtils.setPort(this.PORT);
        this.restUtils.setHTTPAuthentication(this.USER_NAME, this.PASSWORD);
        if (StringUtils.isBlank(resourceUri)) {
            throw new ServiceException("Debe proporcionar un recurso.");
        } else {
            ArrayList parametros = new ArrayList();
            resourceUri = resourceUri + "." + type;
            this.restUtils.loginToServer(this.USER_NAME, this.PASSWORD);
            if (resourceParameters != null) {

                for (Object o : resourceParameters.entrySet()) {
                    Map.Entry buffer = (Map.Entry) o;
                    parametros.add(new BasicNameValuePair((String) buffer.getKey(), (String) buffer.getValue()));
                }
            }
            this.httpRes = this.restUtils.sendRequest(new HttpGet(), resourceUri, parametros, true);
            InputStream is1 = this.httpRes.getEntity().getContent();
            ioe = IOUtils.toByteArray(is1);
            is1.close();
            this.restUtils.releaseConnection(this.httpRes);

            return ioe;
        }
    }
}
