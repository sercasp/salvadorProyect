package ni.gob.inss.siit.view.bean.backbean.utils.graficos;

import org.apache.poi.ss.usermodel.*;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: dcordero<br/>
 * Date: 06/08/2020<br/>
 * Time: 11:08<br/>
 * To change this template use File | Settings | File Templates.
 */

public class Graficos {
    //Generar color de gráfico
    public String generarColorGrafico() {
        Random rand = new Random();
        int value = rand.nextInt(38);
        String[] color = {"d50000", "c51162", "aa00ff", "6200ea", "304ffe", "2962ff", "0091ea", "00b8d4", "00bfa5", "00c853", "64dd17", "aeea00", "ffd600", "ffab00", "ff6d00", "dd2c00", "3e2723", "212121", "263238", "ff1744", "f50057", "d500f9", "651fff", "3d5afe", "2979ff", "00b0ff", "00e5ff", "1de9b6", "00e676", "76ff03", "c6ff00", "ffea00", "ffc400", "ff9100", "ff3d00", "4e342e", "424242", "37474f"};
        return color[value];
    }

    public String nombreMesesSeleccionados(List<Integer> mesSeleccionado) {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        StringBuilder strMesesSeleccionados = new StringBuilder();

        for (int i = 0; i < mesSeleccionado.size(); i++) {
            System.out.println(mesSeleccionado.get(i));
        }
        /*String str;
        int[] mesArray = mesSeleccionado.stream().mapToInt(i->i).toArray();

        for(int i=0;i<mesArray.length;i++){
            str = meses[mesArray[i] - 1];
            strMesesSeleccionados.append(str).append(", ");
        }*/

        /*for (int i = 0; i < mesSeleccionado.size(); i++) {
            str = mesSeleccionado.get(i).toString();
            System.out.println(intMes.toString());
            str = meses[Integer.valueOf(mesSeleccionado.get(i).toString()) - 1];
            strMesesSeleccionados.append(str).append(", ");
        }*/


        return "";//strMesesSeleccionados.substring(0, strMesesSeleccionados.length()-2);
    }

    public boolean noHayDatos(Boolean hayDatos, BarChartModel grafico, String titulo) {
        if (!hayDatos) {
            BarChartSeries serie = new BarChartSeries();
            serie.set("No Hay Datos", 0);
            grafico.addSeries(serie);
            grafico.setTitle(titulo);
            //mostrarMensajeInfo("No hay datos en el periodo seleccionado");
            return true;
        }
        return false;
    }

    public void tryCreaArchivoExcel(File archivo, Workbook workbook) {
        try {
            FileOutputStream salida = new FileOutputStream(archivo);
            workbook.write(salida);
            workbook.close();
            //mostrarMensajeInfo("Archivo guardado exitosamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CellStyle crearStilo(Workbook wb, String nombreFuente, short foreGround, short backGround, HorizontalAlignment hl, VerticalAlignment vl, BorderStyle bs, Boolean negrita, short tamano, boolean subrayado, boolean cursiva) {
        CellStyle style = wb.createCellStyle();
        Font fuente = wb.createFont();
        fuente.setBold(negrita);
        fuente.setFontName(nombreFuente);
        fuente.setFontHeightInPoints(tamano);
        fuente.setItalic(cursiva);
        fuente.setUnderline(subrayado ? Font.U_SINGLE : Font.U_NONE);
        style.setFillForegroundColor(foreGround);
        style.setFont(fuente);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillBackgroundColor(foreGround);
        style.setFillForegroundColor(backGround);
        style.setVerticalAlignment(vl);
        style.setAlignment(hl);
        style.setBorderBottom(bs);
        style.setBorderTop(bs);
        style.setBorderLeft(bs);
        style.setBorderRight(bs);
        return style;
    }


    public Integer llenarDatosHojaExcel(String[] listaCampos, Workbook wb, Sheet pagina, Integer j, List<?> lista, CellStyle estilo) {
        Row fila;
        Integer i;
        Cell celda;
        CellStyle estiloTexto = wb.createCellStyle();
        CellStyle estiloFecha = wb.createCellStyle();
        CellStyle estiloEntero = wb.createCellStyle();
        CellStyle estiloDecimales = wb.createCellStyle();
        estiloTexto.cloneStyleFrom(estilo);
        estiloFecha.cloneStyleFrom(estilo);
        estiloEntero.cloneStyleFrom(estilo);
        estiloDecimales.cloneStyleFrom(estilo);

        if (lista == null)
            return 0;
        for (Object item : lista) {
            //System.out.println(k++);
            if (pagina.getRow(j) == null)
                fila = pagina.createRow(j++);
            else
                fila = pagina.getRow(j++);
            for (i = 0; i < listaCampos.length; i++) {
                //System.out.println("------->> " + i);
                celda = fila.createCell(i);


                //if (i.equals(0))
                if (listaCampos[i].substring(0, 1).equals("+") || ((HashMap) item).get(listaCampos[i]) != null) {
                    int tipoDato;
                    if (listaCampos[i].substring(0, 1).equals("+"))
                        tipoDato = 5;
                    else if (((HashMap) item).get(listaCampos[i]) instanceof Date)
                        tipoDato = 3;
                    else if (((HashMap) item).get(listaCampos[i]) instanceof Integer || ((HashMap) item).get(listaCampos[i]) instanceof BigInteger)
                        tipoDato = 1;
                    else if (((HashMap) item).get(listaCampos[i]) instanceof BigDecimal)
                        tipoDato = 4;
                    else if (((HashMap) item).get(listaCampos[i]) instanceof char[] || ((HashMap) item).get(listaCampos[i]) instanceof String)
                        tipoDato = 2;
                    else
                        tipoDato = 0;

                    switch (tipoDato) {
                        case 1: //Entero
                            celda.setCellStyle(estiloEntero);
                            celda.getCellStyle().setDataFormat(wb.createDataFormat().getFormat("#,##0"));
                            celda.getCellStyle().setAlignment(HorizontalAlignment.RIGHT);
                            celda.setCellValue(Integer.valueOf(((HashMap) item).get(listaCampos[i]).toString()));
                            break;
                        case 2: //Texto
                            celda.setCellStyle(estiloTexto);
                            celda.getCellStyle().setAlignment(HorizontalAlignment.LEFT);
                            celda.setCellValue(((HashMap) item).get(listaCampos[i]).toString());
                            break;
                        case 3: //Fecha
                            try {
                                celda.setCellStyle(estiloFecha);
                                celda.getCellStyle().setDataFormat(wb.createDataFormat().getFormat("dd-MMM-yyyy"));
                                celda.getCellStyle().setAlignment(HorizontalAlignment.RIGHT);
                                celda.setCellValue(new SimpleDateFormat("yyyy-MM-dd").parse((((HashMap) item).get(listaCampos[i]).toString())));
                                break;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4: //BigDecimal
                            celda.setCellStyle(estiloDecimales);
                            celda.getCellStyle().setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
                            celda.getCellStyle().setAlignment(HorizontalAlignment.RIGHT);
                            celda.setCellValue(Double.valueOf(((HashMap) item).get(listaCampos[i]).toString()));
                            break;
                        case 5:
                            celda.setCellStyle(estiloEntero);
                            celda.getCellStyle().setDataFormat(wb.createDataFormat().getFormat("#,##0"));
                            celda.getCellStyle().setAlignment(HorizontalAlignment.RIGHT);
                            celda.setCellFormula(listaCampos[i].replace("[i]", j.toString()));
                    }
                } else { //Cualquier Otro
                    celda.setCellStyle(estiloTexto);
                    celda.getCellStyle().setAlignment(HorizontalAlignment.LEFT);
                    celda.setCellType(CellType.BLANK);
                }
            }
        }
        return j;
    }

    public StreamedContent descargarArchivo(File file) {
        try {
            InputStream stream = new FileInputStream(file);
            DefaultStreamedContent dsc = new DefaultStreamedContent(stream);
            dsc.setName(file.getName());
            return dsc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void borrarArchivo() {
        String nombreArchivo;
        nombreArchivo = "reporte.xlsx";
        File archivo = new File(nombreArchivo);
        try {
            Files.deleteIfExists(archivo.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
