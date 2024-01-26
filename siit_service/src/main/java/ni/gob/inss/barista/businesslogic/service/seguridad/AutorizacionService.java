package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Autorizaciones</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 19/06/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface AutorizacionService {

    Autorizacion obtener(int id) throws EntityNotFoundException;

    Autorizacion obtenerAutorizacionPorId(int id) throws EntityNotFoundException;

    void agregar(Autorizacion oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Autorizacion oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(Autorizacion oTipoCatalogo) throws BusinessException, DAOException;

    List<Autorizacion> buscar(String prBuscar);

    List<Autorizacion> obtenerTodasLasAutorizaciones(String codigo);
}