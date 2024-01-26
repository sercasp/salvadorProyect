package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
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
 */
public interface ParametroEntidadService {
    ParametroEntidad obtenerParametroEntidadPorId(int id) throws EntityNotFoundException;

    void guardar(ParametroEntidad oParametroEntidad) throws BusinessException, DAOException;

    void actualizar(ParametroEntidad oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(ParametroEntidad oTipoCatalogo);

    List<ParametroEntidad> buscar(String prBuscar);

    List<Modulo> obtenerModulos();

    List<Entidad> obtenerDelegaciones();
}