package ni.gob.inss.siit.businesslogic.util.correo;

import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Creado por  mzelaya el 01/01/2018.
 * Modificado por jvillanueva 02/01/2018.
 * Modificado por jvillanueva 11/06/2020.
 * Modificado por jvillanueva 16/07/2020.
 */
public class MessageBuilder {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private static final String PDF_TYPE = "application/pdf";

    private final Session mailSession;
    private String from;
    private List<String> recipients;
    private String subject;
    private MessageBody messageBody;
    private List<BodyPart> attachments;

    public MessageBuilder(Session mailSession) {
        this.mailSession = mailSession;
//        this.from = CConstantes.getProperty("factura.recuperacion.correo.remitente");
        this.from = "notificacion_siit@intur.gob.ni";
        this.recipients = new ArrayList<>();
        this.attachments = new ArrayList<>();
    }

    public MessageBuilder from(String from) {
        this.from = from;
        return this;
    }

    public MessageBuilder addRecipient(String recipient) {
        if (StringUtils.isNotBlank(recipient)) {
            recipients.add(recipient);
        }
        return this;
    }

    public MessageBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public MessageBuilder body(MessageBody messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public MessageBuilder attachPdf(final byte[] pdfBytes, final String fileName) throws MessagingException {
        BodyPart pdfAttachment = new MimeBodyPart();
        pdfAttachment.setFileName(fileName);
        pdfAttachment.setDisposition(Part.ATTACHMENT);
        DataSource ds = new DataSource() {

            @Override
            public OutputStream getOutputStream() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(pdfBytes);
            }

            @Override
            public String getContentType() {
                return PDF_TYPE;
            }
        };
        pdfAttachment.setDataHandler(new DataHandler(ds));
        pdfAttachment.setHeader("Content-Type", ds.getContentType());
        pdfAttachment.setHeader("Content-Transfer-Encoding", "base64");
        attachments.add(pdfAttachment);
        return this;
    }

    public Optional<Message> build() throws AddressException, MessagingException {
        if (recipients.isEmpty()) {
            return Optional.empty();
        }

        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(from));
        for (String recipient : recipients) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }
        message.setSubject(subject);
        message.setSentDate(new Date());

        Multipart mp = new MimeMultipart();
        mp.addBodyPart(messageBody.toBodyPart());
        for (BodyPart attachment : attachments) {
            mp.addBodyPart(attachment);
        }
        message.setContent(mp);
        return Optional.of(message);
    }
}