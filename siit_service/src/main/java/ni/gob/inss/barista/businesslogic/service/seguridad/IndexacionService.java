package ni.gob.inss.barista.businesslogic.service.seguridad;

import java.util.List;
import java.util.Map;

/**
 * <b>SPA</b></br>
 * <b>Copyright (c) 2017 MI FAMILIA.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Servicio para Indexación de entidades</br>
 *
 * @author HERNALDO JOSE MAYORGA HERNANDEZ, ENOC EZEQUIEL JIMENEZ MIRANDA
 * @version 1.0, 03/03/2017
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface IndexacionService {
    List<Map<String, Object>> cargarListaEntidades();

    void indexarEntidad(Class entidad) throws InterruptedException;
}