package ni.gob.inss.barista.businesslogic.service.core;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.entity.seguridad.SesionModulo;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de servicio para navegación</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/31/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface NavegacionService {

    List obtenerModulosPorUsuario(Integer usuarioId, Integer entidadId, String nombreModulo, String orderBy);

    List obtenerModulosPorUsuarioMobile(Integer usuarioId, Integer entidadId, String nombreModulo, String orderBy);

    List obtenerMenuPorUsuarioModulo(Integer usuarioId, Integer entidadId, Integer moduloId);

    List obtenerMenuPorUsuarioModuloMobile(Integer usuarioId, Integer entidadId, Integer moduloId);

    void guardarSesionModulo(SesionModulo oSesionModulo) throws DAOException;
}