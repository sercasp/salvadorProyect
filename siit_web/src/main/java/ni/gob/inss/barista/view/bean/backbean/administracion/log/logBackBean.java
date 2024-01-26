package ni.gob.inss.barista.view.bean.backbean.administracion.log;

import lombok.*;
import ni.gob.inss.barista.businesslogic.service.administracion.log.LogService;
import ni.gob.inss.barista.view.bean.application.ConfigApplicationBean;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 08/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Data
@Named
@Scope("view")
public class logBackBean extends BaseBackBean {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    Map<String, String> logFiles;
    List<String> logFileNames;
    String logSelected;
    Boolean registroSeleccionado;
    String logSelectedText;
    String tipoLOGSelected = null;
    List<String> listDetalleLOGSelected = null;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final LogService oLogService;
    private final ConfigApplicationBean oConfigApplicationBean;

    @Inject
    @Autowired
    public logBackBean(LogService oLogService, ConfigApplicationBean oConfigApplicationBean) {
        this.oLogService = oLogService;
        this.oConfigApplicationBean = oConfigApplicationBean;
    }

    @PostConstruct
    public void init() {
        try {
            registroSeleccionado = false;
            logFiles = oLogService.obtenerListaLogs();
            logFileNames = new ArrayList<String>();
            for (Map.Entry<String, String> e : logFiles.entrySet()) {
                logFileNames.add(e.getKey());
            }
        } catch (IOException e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "init()", MessagesResults.ERROR_OBTENER, e);
        }
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public void cargarLog() {
        tipoLOGSelected = null;
        if (logSelected != null && logSelected != "") {
            registroSeleccionado = true;
            try {
                listDetalleLOGSelected = oLogService.obtenerLogText(logFiles.get(logSelected));
                logSelectedText = listDetalleLOGSelected.get(0);
                PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
            } catch (IOException e) {
                mostrarMensajeError(this.getClass().getSimpleName(), "cargarLog()", MessagesResults.ERROR_OBTENER, e);
            }
        } else {
            mostrarMensajeError("Seleccione un registro");
        }
    }

    public void limpiar() {
        logFiles = null;
        logFileNames = null;
        logSelected = null;
        logSelectedText = null;
        tipoLOGSelected = null;
        listDetalleLOGSelected = null;
    }

    public void seleccionandoLOG(ValueChangeEvent event) {
        tipoLOGSelected = (String) event.getNewValue();
        if (tipoLOGSelected != null)
            switch (tipoLOGSelected) {
                case "CLR":
                    logSelectedText = "<div style='color:gray;width:99.5%;text-align:center;margin-top:300px;font-size:16px'><br>SELECCIONE UN ITEM DEL COMBO</br></div>";
                    tipoLOGSelected = null;
                    break;
                case "TODO":
                    logSelectedText = listDetalleLOGSelected != null ? listDetalleLOGSelected.get(0).toString() : null;
                    break;
                case "WARNING":
                    if (listDetalleLOGSelected.get(1).isEmpty() || listDetalleLOGSelected.get(2).toString() == null)
                        logSelectedText = "<div style='color:darkmagenta;width:99.5%;text-align:center;margin-top:300px;font-size:16px'>" + "<br>" + "NO HAY WARNINGS QUE MOSTRAR EN ESTE LOG" + "</br>" + "</div>";
                    else
                        logSelectedText = listDetalleLOGSelected.get(1).toString();
                    break;
                case "ERROR":
                    if (listDetalleLOGSelected.get(1).isEmpty() || listDetalleLOGSelected.get(2).toString() == null)
                        logSelectedText = "<div style='color:red;width:99.5%;text-align:center;margin-top:300px;font-size:16px'>" + "<br>" + "NO HAY ERRORES QUE MOSTRAR EN ESTE LOG" + "</br>" + "</div>";
                    else
                        logSelectedText = listDetalleLOGSelected.get(2).toString();
                    break;
            }
        else {
            registroSeleccionado = false;
            logSelected = null;
            logSelectedText = "<div style='color:gray;width:99.5%;text-align:center;margin-top:300px;font-size:16px'><br>SELECCIONE UN REGISTRO</br></div>";
            tipoLOGSelected = null;
            PrimeFaces.current().ajax().update("formLista");
            PrimeFaces.current().ajax().update("formDetalle");
        }
    }
}