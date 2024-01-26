package ni.gob.inss.barista.model.dao.infraestructura;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Entidad</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface EntidadDAO extends BaseGenericDAO<Entidad, Integer> {
    List<Entidad> obtenerEntidadPorUsuario(Integer usuario_id);
}