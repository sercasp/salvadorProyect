package ni.gob.inss.barista.businesslogic.service.administracion.log;

import java.io.IOException;
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
 * Modificado por jvillanueva el 03/08/2023
 */
public interface LogService {

    Map<String, String> obtenerListaLogs() throws IOException;

    List<String> obtenerLogText(String logPath) throws IOException;
}