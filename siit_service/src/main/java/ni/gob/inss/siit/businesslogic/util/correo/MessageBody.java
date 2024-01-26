package ni.gob.inss.siit.businesslogic.util.correo;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.ws.rs.core.MediaType;

/**
 * Creado por  mzelaya el 01/01/2018.
 * Modificado por jvillanueva 02/01/2018.
 * Modificado por jvillanueva 16/07/2020.
 */
public class MessageBody {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private final String bodyContent;
    private final String bodyContentType;

    public MessageBody(String bodyContent, String bodyContentType) {
        this.bodyContent = bodyContent;
        this.bodyContentType = bodyContentType;
    }

    public static MessageBody newTextBody(String text) {
        return new MessageBody(text, MediaType.TEXT_PLAIN);
    }

    public static MessageBody newHtmlBody(String text) {
        return new MessageBody(text, MediaType.TEXT_HTML);
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public String getBodyContentType() {
        return bodyContentType;
    }

    public BodyPart toBodyPart() throws MessagingException {
        BodyPart body = new MimeBodyPart();
        body.setText(bodyContent);
        body.setDisposition(Part.INLINE);
        body.setHeader("Content-Type", bodyContentType);
        return body;
    }
}