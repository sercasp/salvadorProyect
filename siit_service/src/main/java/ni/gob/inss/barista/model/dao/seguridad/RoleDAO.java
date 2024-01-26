package ni.gob.inss.barista.model.dao.seguridad;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Roles</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface RoleDAO extends BaseGenericDAO<Role, Integer> {
    List obtenerRolesPorMenu(Integer menuId);

    List<Autorizacion> obtenerListaAutorizacion(Role oRole, Entidad oEntidad);

    List<Autorizacion> obtenerListaUsuarios(Role oRole, Entidad oEntidad);

    List obtenerModulosMenuPorRole(Role oRole);

    List obtenerModulosMenuPorRoleNoAgregado(Role oRole);

    List<Autorizacion> autorizacionesPorRole(Integer rolId);

    List<Usuario> usuariosPorRole(Integer rolId, Integer entidadId);

    List<Autorizacion> agregarAutorizaciones(Integer rolId);

    List<Autorizacion> obtenerAutorizacionesFiltradasNoAgregadas(String txtBuscar, Integer rolId);

    List obtenerModulosReportePorRole(Role oRole);

    List obtenerModulosReportePorRoleNoAgregado(Role oRole);
}