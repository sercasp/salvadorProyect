package ni.gob.inss.siit.view.bean.backbean.utils;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author jjrivera
 *         Clase utilitaria para retornar el DefaultStreamedContent de un archivo
 */
public class StreamedContentPersonal extends DefaultStreamedContent {

    /**
     * Agrega a la clase padres el stream copiado y el conten type de la imagen
     */
    public StreamedContentPersonal(InputStream stream, String contentType) throws IOException {
        super(copiarInputStream(stream), contentType);
    }

    /**
     * copia el input stream del archivo, obtiene los bytes del stream del archivo, cierra el stream de archivo y retorna la copia
     * del stream a partir de los bytes
     */
    public static InputStream copiarInputStream(InputStream stream) throws IOException {
        byte[] bytes = IOUtils.toByteArray(stream);
        stream.close();
        return new ByteArrayInputStream(bytes);
    }
}
