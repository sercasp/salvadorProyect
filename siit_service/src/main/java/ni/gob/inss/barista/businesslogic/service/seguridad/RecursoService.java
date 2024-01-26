package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.ServiceException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.personal.Miembro;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;

import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Servicio para Recursos</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
public interface RecursoService {
    Recurso obtener(int id) throws EntityNotFoundException;

    Recurso obtenerRecursoPorId(int id) throws EntityNotFoundException;

    void agregar(Recurso oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Recurso oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(Recurso oTipoCatalogo) throws BusinessException, DAOException;

    List<Recurso> buscar(String prBuscar, String tipo);

    List<Miembro> obtenerMiembros();

    Recurso buscarPorCodigo(String codigo) throws ServiceException;

    List<Map<String, Object>> secuenciaCodigo();
}