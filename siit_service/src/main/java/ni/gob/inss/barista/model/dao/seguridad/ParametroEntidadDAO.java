package ni.gob.inss.barista.model.dao.seguridad;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.ParametroEntidad;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Parametros Entidades</br>
 *
 * @author VIRGINIA ELIZABETH MORA MUNGUIA
 * @version 1.0, 03/06/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface ParametroEntidadDAO extends BaseGenericDAO<ParametroEntidad, Integer> {

    ParametroEntidad buscarPorParametroEntidad(String Parametro);

    List<Modulo> obtenerModulos();

    List<ParametroEntidad> obtenerRepetidos(String prCodigo, Integer prIdEntidad);

    List<ParametroEntidad> obtenerRepetido(String prCodigo, Integer prIdEntidad);

    List<ParametroEntidad> obtenerRepetidos(Integer prId, String prCodigo, Integer prIdEntidad);
}