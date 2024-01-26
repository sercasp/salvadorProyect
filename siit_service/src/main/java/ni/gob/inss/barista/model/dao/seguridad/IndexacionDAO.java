package ni.gob.inss.barista.model.dao.seguridad;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;
import java.util.Map;

/**
 * Created by jjrivera on 2/16/2018.
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface IndexacionDAO extends BaseGenericDAO<Usuario, Integer> {
    List<Map<String, Object>> cargarListaEntidades();
    void indexarEntidad(Class entidad) throws InterruptedException;
}