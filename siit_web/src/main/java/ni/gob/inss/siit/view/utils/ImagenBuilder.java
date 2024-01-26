package ni.gob.inss.siit.view.utils;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.StreamedContent;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 * Created by jjrivera on 12/2/2016.
 */
public class ImagenBuilder {

    public ImagenBuilder() {

    }

    public String getSrc(StreamedContent stream) throws IOException {
        byte[] bytes = IOUtils.toByteArray(stream.getStream());
        stream.getStream().close();
        String base64 = DatatypeConverter.printBase64Binary(bytes);
        return "data:" + stream.getContentType() + ";base64," + base64;
    }
}
