package ni.gob.inss.barista.businesslogic.service.notificacion;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.notificacion.Mensaje;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 24/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface MensajeNotificacionService {

    Mensaje obtener(int id) throws EntityNotFoundException;

    void agregar(Mensaje oMensaje) throws DAOException, BusinessException;

    void actualizar(Mensaje oMensaje) throws DAOException;

    void eliminar(Mensaje oMensaje);

    List<Mensaje> buscar(String prBuscar);
}