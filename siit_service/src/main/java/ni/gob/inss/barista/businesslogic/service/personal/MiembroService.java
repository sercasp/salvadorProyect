package ni.gob.inss.barista.businesslogic.service.personal;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.personal.Miembro;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Servicio para Miembros</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface MiembroService {
    Miembro obtenerMiembroPorId(int id) throws EntityNotFoundException;

    void agregar(Miembro oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Miembro oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(Miembro oTipoCatalogo);

    List<Miembro> buscar(String prBuscar);
}