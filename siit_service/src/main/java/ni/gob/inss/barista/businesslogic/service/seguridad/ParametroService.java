package ni.gob.inss.barista.businesslogic.service.seguridad;


import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Servicio para Parámetros</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
public interface ParametroService {
    Parametro obtenerParametroPorId(int id) throws EntityNotFoundException;

    void agregar(Parametro oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Parametro oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(Parametro oTipoCatalogo) throws BusinessException, DAOException;

    List<Parametro> buscar(String prBuscar);

    List<Modulo> obtenerModulos();
}