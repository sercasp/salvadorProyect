package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Servicio para Roles</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
public interface RoleService {
    Role obtener(Integer id) throws EntityNotFoundException;

    void guardar(Role oRole) throws DAOException;

    void actualizar(Role oRole) throws DAOException;

    void borrar(Role oRole) throws BusinessException, DAOException;

    List<Role> buscar(String prBuscar, String tipo);

    List obtenerRolesPorMenu(int menuId);

    List<Autorizacion> obtenerListaAutorizacion(Role oRole, Entidad oEntidad);

    List<Autorizacion> obtenerListaUsuarios(Role oRole, Entidad oEntidad);

    List obtenerModulosMenuPorRole(Role oRole);

    List obtenerModulosMenuPorRoleNoAgregado(Role oRole);

    List<Autorizacion> autorizacionesPorRole(int rolId);

    List<Usuario> usuariosPorRole(int rolId, int entidadId);

    List<Autorizacion> agregarAutorizaciones(int rolId);

    List<Autorizacion> obtenerAutorizacionesFiltradas(String txtBuscar, Integer rolId);

    List<Autorizacion> obtenerAutorizacionesFiltradasNoAgregadas(String txtBuscar, Integer rolId);

    List obtenerModulosReportePorRole(Role oRole);

    List obtenerModulosReportePorRoleNoAgregado(Role oRole);
}