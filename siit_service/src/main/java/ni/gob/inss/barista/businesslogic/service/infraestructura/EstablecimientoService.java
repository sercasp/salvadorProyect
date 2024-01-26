package ni.gob.inss.barista.businesslogic.service.infraestructura;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.infraestructura.Establecimiento;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * DESCRIPCIÓN</br>
 *
 * @author JAIRO HELÍ MENDOZA AGUIRRE
 * @version 1.0, 05/08/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface EstablecimientoService {
    Establecimiento obtenerEstablecimientoPorId(int id) throws EntityNotFoundException;

    void agregar(Establecimiento oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Establecimiento oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(Establecimiento oTipoCatalogo) throws BusinessException, DAOException;

    List<Establecimiento> buscar(String prBuscar);
}