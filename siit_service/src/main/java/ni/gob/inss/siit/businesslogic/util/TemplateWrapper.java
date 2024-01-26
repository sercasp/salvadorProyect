package ni.gob.inss.siit.businesslogic.util;

import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Creado por  mzelaya el 01/01/2018.
 * Modificado por jvillanueva 02/01/2018.
 * Modificado por jvillanueva 16/07/2020.
 */

public class TemplateWrapper {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private static final TemplateWrapper INSTANCE = new TemplateWrapper();

    private final Configuration cfg;

    private TemplateWrapper() {
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        WebappTemplateLoader templateLoader = new WebappTemplateLoader(servletContext, "WEB-INF/plantillas");
        templateLoader.setURLConnectionUsesCaches(false);
        templateLoader.setAttemptFileAccess(false);

        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setTemplateLoader(templateLoader);

    }

    public static TemplateWrapper getInstance() {
        return INSTANCE;
    }

    public void setTemplateFolder(String pathToTemplateFolder) throws IOException {
        cfg.setDirectoryForTemplateLoading(new File(pathToTemplateFolder));
    }

    public String processToString(String templateName, Map<String, Object> model)
            throws IOException, TemplateException {
        Template tpl = cfg.getTemplate(templateName);
        Writer out = new StringWriter();
        tpl.process(model, out);
        return out.toString();
    }
}
