package ni.gob.inss.barista.model.daoImpl.seguridad;

import ni.gob.inss.barista.model.dao.seguridad.UsuarioDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.hibernate.query.Query;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de acceso a datos para Usuarios</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */

@Repository
public class UsuarioDAOImpl extends BaseGenericDAOImpl<Usuario, Integer> implements UsuarioDAO {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public Usuario buscarPorUsuario(String usuario) {
        Usuario oUsuario = null;
        List<Usuario> listaUsuario = sessionFactory.getCurrentSession()
                .createQuery("from Usuario where upper(username) = upper(:prUsername)")
                .setParameter("prUsername", usuario).list();
        if (listaUsuario.size() > 0) {
            oUsuario = listaUsuario.get(0);
        }
        return oUsuario;
    }

    public List obtenerUsuariosPorMenu(Integer menuId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select usuarios.id as id, usuarios.username as username , usuarios.nombres as nombres , usuarios.apellidos as apellidos from RoleMenu roleMenu join roleMenu.rolesByRolId as rol join rol.permisosesById as permiso join permiso.usuariosByUsuarioId as usuarios join roleMenu.menusByMenuId as menus where menus.pasivo = false and menus.id = :menuId");
        query.setParameter("menuId", menuId);
        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));
        return query.list();
    }

    @Override
    public List<Map<String, Object>> obtenerRoles(Usuario oUsuario, Entidad oEntidad) {
        Query query = sessionFactory.getCurrentSession().createQuery("select permisos.id as id, role.nombre as nombre, " +
                "entidad.nombre as entidadnombre, role.tipo as tipo  from Role as role " +
                "join role.permisosesById as permisos join permisos.entidad as entidad " +
                "where permisos.usuariosByUsuarioId = :usuario order by entidad.nombre, role.nombre");

        query.setParameter("usuario", oUsuario);
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        return query.list();
    }

    @Override
    public List<Map<String, Object>> obtenerEntidadesRoles(Usuario oUsuario) {
        Query query = sessionFactory.getCurrentSession().createQuery("select role.nombre as nombreRol, entidad.id as id," +
                "entidad.codigo as codigo,entidad.nombre as nombre " +
                "from Role as role join role.permisosesById as permisos join permisos.entidad as entidad " +
                "where permisos.usuariosByUsuarioId = :usuario order by entidad.nombre ");

        query.setParameter("usuario", oUsuario);
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        return query.list();

    }

    @Override
    public List<Entidad> obtenerEntidadesSinAgregar(Usuario oUsuario) {
        Query query = sessionFactory.getCurrentSession().createQuery("select entidad.id as id, entidad.codigo as codigo,entidad.nombre as nombre " +
                "from Entidad as entidad order by entidad.nombre");
//        query.setParameter("usuario",oUsuario);
        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Entidad.class));

        return (List<Entidad>) query.list();
    }

    @Override
    public List<Role> obtenerAgregarRoles(Usuario oUsuario, Entidad oEntidad) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Role r where r.id not in " +
                "(select role.id from Role as role " +
                "join role.permisosesById as permisos join permisos.entidad as entidad " +
                "where permisos.usuariosByUsuarioId = :usuario and permisos.entidad = :entidad) order by r.nombre");
        query.setParameter("usuario", oUsuario);
        query.setParameter("entidad", oEntidad);
        return query.list();
    }

    /**
     * Este metodo se encarga de buscar si un usuario tiene relacionado con un permiso
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    public List<Usuario> verificarPermisosUsuario(Integer prIdUsuario) {
        Query query = sessionFactory.getCurrentSession().createQuery("select p.usuariosByUsuarioId " +
                "from Permiso p join p.usuariosByUsuarioId us " +
                "where us.id = :prIdUsuario");
        query.setParameter("prIdUsuario", prIdUsuario);
        return query.list();
    }

    /**
     * Este metodo se encarga de buscar si un usuario esta relacionado a la tabla parametro usuario
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    public List<Usuario> verificarParametroUsuario(Integer prIdUsuario) {
        Query query = sessionFactory.getCurrentSession().createQuery("select pus.usuarioIdByUsuarioId " +
                "from ParametroUsuario pus " +
                "join pus.usuarioIdByUsuarioId us where us.id = :prIdUsuario");

        query.setParameter("prIdUsuario", prIdUsuario);
        return query.list();
    }

    /**
     * Este metodo se encarga de buscar si un usuario esta relacionado a la tabla Sesion usuario
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    public List<Usuario> verificarSesionUsuario(Integer prIdUsuario) {
        Query query = sessionFactory.getCurrentSession().createQuery("select " +
                "(select usi from Usuario usi where usi.id = su.usuarioId) " +
                "from SesionUsuario su " +
                "where su.usuarioId = :prIdUsuario");
        query.setParameter("prIdUsuario", prIdUsuario);
        return query.list();
    }

    /**
     * Este metodo se encarga de buscar si un usuario tiene relacionado un modulo en la tabla
     * sesion modulo
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    public List<Usuario> verificarSesionUsuarioModulo(Integer prIdUsuario) {
        Query query = sessionFactory.getCurrentSession().createQuery("select " +
                " (select usi from Usuario usi where usi.id = sm.usuarioId)" +
                "from SesionModulo sm where sm.usuarioId = :prIdUsuario");
        query.setParameter("prIdUsuario", prIdUsuario);
        return query.list();
    }

    /**
     * Este metodo se encarga de buscar si un usuario esta relacionado a la tabla navegacion usuario
     * sesion modulo
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    public List<Usuario> verificarNavegacionUsuario(Integer prIdUsuario) {
        Query query = sessionFactory.getCurrentSession().createQuery("select " +
                "(select usi from Usuario usi where usi.id = nu.usuarioId)" +
                "from NavegacionUsuario nu " +
                "where nu.usuarioId = :prIdUsuario");
        query.setParameter("prIdUsuario", prIdUsuario);
        return query.list();
    }

    /**
     * Este metodo se encarga de buscar si un usuario esta relacionado a la tabla recurso usuario
     * sesion modulo
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    public List<Usuario> verificarRecursoUsuario(Integer prIdUsuario) {
        Query query = sessionFactory.getCurrentSession().createQuery("select " +
                "(select usi from Usuario usi where usi.id = ru.usuarioId)" +
                "from RecursoUsuario ru " +
                "where ru.usuarioId = :prIdUsuario");
        query.setParameter("prIdUsuario", prIdUsuario);
        return query.list();
    }
}