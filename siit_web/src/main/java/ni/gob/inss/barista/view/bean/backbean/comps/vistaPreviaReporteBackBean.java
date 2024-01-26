package ni.gob.inss.barista.view.bean.backbean.comps;

import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Erania García Carranza
 * @version 1.0, 04/04/2018
 * @since 1.0 *
 */
@Named
@Scope("session")
public class vistaPreviaReporteBackBean extends BaseBackBean implements Serializable {

    private static final long serialVersionUID = 1L;
    StreamedContent streamPdf = null;
    @PostConstruct
    public void init() {

    }

    public StreamedContent getStreamPdf() {
        return streamPdf;
    }

    public void setStreamPdf(StreamedContent streamPdf) {
        this.streamPdf = streamPdf;
    }
}
