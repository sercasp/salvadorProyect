package ni.gob.inss.barista.businesslogic.service.core.parametro;

import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import ni.gob.inss.barista.model.entity.seguridad.ParametroEntidad;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2015 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de servicio para parámetros</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 10/03/2015
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface ParametroHelperService {

    Parametro obtenerParametro(String codigo);

    ParametroEntidad obtenerParametroEntidad(String codigo, Entidad oEntidad);
}