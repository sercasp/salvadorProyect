package ni.gob.inss.barista.businesslogic.service.notificacion;

import ni.gob.inss.barista.model.entity.notificacion.Mensaje;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface NotificacionGeneralService {
    List<Mensaje> obtenerMensajes();
}