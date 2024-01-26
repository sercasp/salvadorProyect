package ni.gob.inss.barista.model.dao.seguridad;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.daoImpl.seguridad.UsuarioDAOImpl;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Usuarios</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface UsuarioDAO extends BaseGenericDAO<Usuario, Integer> {
    Usuario buscarPorUsuario(String usuario);

    List<Map<String, Object>> obtenerRoles(Usuario oUsuario, Entidad oEntidad);

    List<Map<String, Object>> obtenerEntidadesRoles(Usuario oUsuario);

    List<Entidad> obtenerEntidadesSinAgregar(Usuario oUsuario);

    List obtenerUsuariosPorMenu(Integer menuId);

    List<Role> obtenerAgregarRoles(Usuario oUsuario, Entidad oEntidad);

    /**
     * Ver más de este metodo {@link UsuarioDAOImpl#verificarPermisosUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarPermisosUsuario(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioDAOImpl#verificarParametroUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarParametroUsuario(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioDAOImpl#verificarSesionUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarSesionUsuario(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioDAOImpl#verificarSesionUsuarioModulo(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarSesionUsuarioModulo(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioDAOImpl#verificarNavegacionUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarNavegacionUsuario(Integer prIdUsuario);

    /**
     * Ver más de este metodo {@link UsuarioDAOImpl#verificarRecursoUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    List<Usuario> verificarRecursoUsuario(Integer prIdUsuario);
}