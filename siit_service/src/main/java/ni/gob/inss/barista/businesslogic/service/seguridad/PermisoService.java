package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.seguridad.Permiso;

import java.util.List;

/**
 * Created by jmendoza on 6/24/2015.
 * Modificación de jvillanueva el 12/07/2023
 */
public interface PermisoService {
    Permiso obtener(int id) throws EntityNotFoundException;

    Permiso obtenerPermisoPorId(int id) throws EntityNotFoundException;

    void agregar(Permiso oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Permiso oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(Permiso oTipoCatalogo) throws BusinessException, DAOException;

    List<Permiso> buscar(String prBuscar);
}