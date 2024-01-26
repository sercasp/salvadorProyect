package ni.gob.inss.barista.businesslogic.serviceImpl.administracion.log;

import ni.gob.inss.barista.businesslogic.service.administracion.log.LogService;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 08/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Service
public class LogServiceImpl implements LogService {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Override
    public Map<String, String> obtenerListaLogs() throws IOException {
        Map<String, String> logFiles = new LinkedHashMap<>();
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL urlResource = classLoader.getResource("config.properties");
        if (urlResource != null) {
            props.load(urlResource.openStream());
            String logFilePath = props.getProperty("general.rutaLog");
            File folder = new File(logFilePath);
            File[] files = folder.listFiles();
            if (files != null) {
                Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                for (int i = 0; i < files.length; i++) {
                    logFiles.put(files[i].getName(), files[i].getAbsolutePath());
                }
            }
        }
        return logFiles;
    }

    @Override
    public List<String> obtenerLogText(String logPath) throws IOException {
        if (logPath != null) {
            StringBuilder logText = new StringBuilder();
            FileInputStream fstream = new FileInputStream(logPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder Warnings = new StringBuilder();
            StringBuilder Errors = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("INFO")) {
                    logText.append("<div style='color:blue'>" + "<br>" + strLine + "</br>" + "</div>");
                } else if (strLine.contains("ERROR") || strLine.contains("FATAL") || strLine.contains("SEVERE")) {
                    logText.append("<div style='color:red'>" + "<br>" + strLine + "</br>" + "</div>");
                    Errors.append("<div style='color:red'>" + "<br>" + strLine + "</br>" + "</div>");
                } else if (strLine.contains("WARN")) {
                    logText.append("<div style='color:darkmagenta'>" + "<br>" + strLine + "</br>" + "</div>");
                    Warnings.append("<div style='color:darkmagenta'>" + "<br>" + strLine + "</br>" + "</div>");
                } else {
                    logText.append("<div style='padding-left:10px'>" + "<br>" + strLine + "</br>" + "</div>");
                }
            }
            List<String> result = new ArrayList<String>();
            result.add(0, logText.toString());
            result.add(1, Warnings.toString());
            result.add(2, Errors.toString());
            fstream.close();
            return result;
        } else {
            return null;
        }
    }
}