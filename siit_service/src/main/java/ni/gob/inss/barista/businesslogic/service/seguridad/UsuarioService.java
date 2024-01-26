package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.serviceImpl.seguridad.UsuarioServiceImpl;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Permiso;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Servicio para Usuarios</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
public interface UsuarioService {
    Usuario obtenerUsuarioPorId(int id) throws EntityNotFoundException;

    Usuario obtenerUsuarioPorUserName(String userName) throws BusinessException;

    void agregar(Usuario oUsuario) throws BusinessException, DAOException;

    void actualizar(Usuario oUsuario) throws BusinessException, DAOException;

    void eliminar(Usuario oUsuario);

    void agregarList(List<Usuario> oListUsuario, Map<String, Object> permisos) throws BusinessException, DAOException;

    List<Usuario> buscar(String prBuscar);

    Usuario login(String username, String password);

    List obtenerUsuariosPorMenu(int menuId);

    List<Map<String, Object>> listaEntidades(Usuario oUsuario);

    List<Entidad> listaEntidadesSinAgregar(Usuario oUsuario);

    List<Map<String, Object>> listaRoles(Usuario oUsuario, Entidad oEntidad);

    void agregarRol(Permiso oPermiso) throws DAOException;

    void quitarRol(Usuario oUsuario, Permiso oPermiso, Entidad oEntidad) throws BusinessException;

    List<Role> listaAgregarRoles(Usuario oUsuario, Entidad oEntidad);

    void quitarTodosRoles(Usuario oUsuario) throws BusinessException;

    List<Permiso> extraerEntidad(int usuarioId, int entidadId);

    List<Role> obtenerUsuarioFiltrado(String nombre);

    /**
     * Ver más de este metodo {@link UsuarioServiceImpl#verificarPermisosUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarPermisosUsuario(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioServiceImpl#verificarParametroUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarParametroUsuario(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioServiceImpl#verificarSesionUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarSesionUsuario(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioServiceImpl#verificarSesionUsuarioModulo(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarSesionUsuarioModulo(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioServiceImpl#verificarNavegacionUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarNavegacionUsuario(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioServiceImpl#verificarRecursoUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarRecursoUsuario(Integer prIdUsuario);
}