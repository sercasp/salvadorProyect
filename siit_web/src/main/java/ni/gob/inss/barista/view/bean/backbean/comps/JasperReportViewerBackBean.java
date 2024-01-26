package ni.gob.inss.barista.view.bean.backbean.comps;

import ni.gob.inss.barista.businesslogic.service.core.jasperclient.JasperRestService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 31/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Named
public class JasperReportViewerBackBean {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    final JasperRestService oJasperRestService;

    @Inject
    @Autowired
    public JasperReportViewerBackBean(JasperRestService oJasperRestService) {
        this.oJasperRestService = oJasperRestService;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    public void downloadFile() throws Exception {
        /*
        <p:commandLink value="reporte" ajax="false" action="#{jasperReportViewerBackBean.downloadFile}"/>
         */
        Map<String, String> parametros = new HashMap<>();
        parametros.put("tittle", "Reporte de pruebas");
        HttpServletResponse response =
                (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=report.pdf");
        response.getOutputStream().write(oJasperRestService.getReport("/reports/reports/LivrosReport", "pdf", parametros));
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }
}
