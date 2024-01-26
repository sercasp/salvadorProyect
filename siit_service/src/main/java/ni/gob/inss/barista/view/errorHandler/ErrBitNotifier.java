package ni.gob.inss.barista.view.errorHandler;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * CONSTRUYE NOTICE PARA ERRBIT</br>
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 */
public class ErrBitNotifier {

    private final String baseUrl;

    private static Logger LOGGER = Logger.getLogger(ErrBitNotifier.class);

    public ErrBitNotifier(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private void addingProperties(final HttpURLConnection connection) throws ProtocolException {
        connection.setDoOutput(true);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
        connection.setRequestProperty("Content-type", "text/xml");
        connection.setRequestProperty("Accept", "text/xml, application/xml");
        connection.setRequestMethod("POST");
    }

    private HttpURLConnection createConnection() throws IOException {
        return (HttpURLConnection) new URL(String.format("http://%s/notifier_api/v2/notices", baseUrl)).openConnection();
    }

    public int notify(final ErrBitNotice notice) {
        try {
            final HttpURLConnection toairbrake = createConnection();
            addingProperties(toairbrake);
            String toPost = new ErrbitNoticeXml(notice).toString();
            return send(toPost, toairbrake);
        } catch (final Exception e) {
            LOGGER.error(e);
        }
        return 0;
    }

    private int send(final String yaml, final HttpURLConnection connection) throws IOException {
        int statusCode;
        final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(yaml);
        writer.close();
        statusCode = connection.getResponseCode();
        return statusCode;
    }
}