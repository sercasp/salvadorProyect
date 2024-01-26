package ni.gob.inss.siit.view.utils.reportes;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import ni.gob.inss.siit.model.utils.FormatoSql;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jjrivera on 5/5/2016.
 */
public class GenerarReporte implements Serializable {
    List<HashMap<String, Object>> valores;
    Collection<Map<String, ?>> dataOneReport;
    private String titulo;

    /**
     * The column names.
     */
    private List<String> columnNames;

    public GenerarReporte(String titulo) {
        this.titulo = titulo;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    public void builReport() throws JRException, ClassNotFoundException, IOException {
        FastReportBuilder drb = new FastReportBuilder();
        DynamicReport dr;
        dataOneReport = new LinkedList<>();
        for (HashMap<String, Object> mapa : valores) {
            Map<String, Object> nuevoMapa = mapa.entrySet().stream().collect(Collectors.toMap(map -> columnNames.stream()
                    .filter(p -> new FormatoSql(p).removerAcentos().removerCaracteresEspeciales().build().equals(map.getKey()))
                    .collect(Collectors.toList()).get(0), valor -> valor.getValue()));
            dataOneReport.add(nuevoMapa);

        }
        Style letra = new Style("letra");
        letra.setFont(new Font(12, "Calibri", true, false, false));
        Style columDetail = new Style("detalle");
        columDetail.setBorderTop(Border.THIN());
        columDetail.setBorderBottom(Border.THIN());

        Style headerStyle = new Style("header");
        headerStyle.setBorder(Border.THIN());
        headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);

        for (String name : columnNames) {
            AbstractColumn column = ColumnBuilder.getNew().setColumnProperty(name, Object.class.getName())
                    .setTitle(name)
                    .setWidth(200)
                    .setFixedWidth(false)
                    .setHeaderStyle(headerStyle)
                    .setStyle(columDetail)
                    .build();
            drb.addColumn(column);
        }
        drb.setTitle(titulo);
        drb.addStyle(letra);
        drb.setUseFullPageWidth(true);
        drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
        dr = drb.build();
        JRDataSource ds = new JRBeanCollectionDataSource(dataOneReport);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
        descargarReporte(jp);
    }

    private void descargarReporte(JasperPrint jp) throws IOException, JRException {
        String now = DateFormatUtils.format(WebUtils.now(), "-dd-MM-yyyy_HH_mm_SS");
        File outputFile = new File("reporte" + now + ".pdf");
        JRPdfExporter exporter = new JRPdfExporter();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse httpServletResponse = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + FilenameUtils.getName(outputFile.getAbsolutePath()));
        facesContext.responseComplete();
        exporter.exportReport();
    }

    public List<HashMap<String, Object>> getValores() {
        return valores;
    }

    public void setValores(List<HashMap<String, Object>> valores) {
        this.valores = valores;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}