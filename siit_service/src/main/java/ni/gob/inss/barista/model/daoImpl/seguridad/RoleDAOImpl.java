package ni.gob.inss.barista.model.daoImpl.seguridad;

import ni.gob.inss.barista.model.dao.seguridad.RoleDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.hibernate.query.Query;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de acceso a datos para Roles</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */

@Repository
public class RoleDAOImpl extends BaseGenericDAOImpl<Role, Integer> implements RoleDAO {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public List obtenerRolesPorMenu(Integer menuId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select role.id as id , role.nombre as nombre from RoleMenu roleMenu join roleMenu.rolesByRolId as role where roleMenu.menusByMenuId.id = :menuId ORDER BY role.nombre asc");
        query.setParameter("menuId", menuId);
        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Role.class));
        return query.list();
    }

    @Override
    public List<Autorizacion> obtenerListaAutorizacion(Role oRole, Entidad oEntidad) {
        return null;
    }

    @Override
    public List<Autorizacion> obtenerListaUsuarios(Role oRole, Entidad oEntidad) {
        return null;
    }

    @Override
    public List obtenerModulosMenuPorRole(Role oRole) {
        Query query = sessionFactory.getCurrentSession().createQuery("select modulos.id as moduloId, " +
                "modulos.nombre as moduloNombre, menus.id as menuId,menus.nombre as menuNombre, " +
                "menus.menusByMenuId.id as menuParentId, menus.orden as orden, 'AG' as estado " +
                "from Menu as menus join menus.rolesMenusesById as rolesMenus " +
                "join menus.modulosByModuloId as modulos WHERE  rolesMenus.rolesByRolId = :role " +
                "GROUP BY modulos.id,modulos.nombre,menus.menusByMenuId.id,menus.id,menus.nombre,menus.orden " +
                "ORDER BY modulos.nombre asc,menus.orden asc,menus.id asc");

        query.setParameter("role", oRole);
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List obtenerModulosMenuPorRoleNoAgregado(Role oRole) {
        Query query = sessionFactory.getCurrentSession().createQuery("select modulos.id as moduloId, " +
                "modulos.nombre as moduloNombre, menus.id as menuId,menus.nombre as menuNombre, " +
                "menus.menusByMenuId.id as menuParentId, menus.orden as orden, 'NA' as estado " +
                "from Menu as menus left join menus.rolesMenusesById as rolesMenus " +
                "left join menus.modulosByModuloId as modulos WHERE menus.id not in (" +
                "select menus.id " +
                "from Menu as menus join menus.rolesMenusesById as rolesMenus " +
                "join menus.modulosByModuloId as modulos WHERE  rolesMenus.rolesByRolId = :role)" +
                "GROUP BY modulos.id,modulos.nombre,menus.menusByMenuId.id,menus.id,menus.nombre,menus.orden " +
                "ORDER BY modulos.nombre asc,menus.orden asc,menus.id asc");
        query.setParameter("role", oRole);
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    public List<Autorizacion> autorizacionesPorRole(Integer rolId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select auto.id as id ,auto.codigo as codigo, " +
                "auto.nombre as nombre, auto.descripcion as descripcion from RoleAutorizacion as roleAuto " +
                "left join roleAuto.autorizacionesByAutorizacionId as auto where roleAuto.rolesByRolId.id = :rolId");

        query.setParameter("rolId", rolId);
        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Autorizacion.class));
        return query.list();
    }

    public List<Usuario> usuariosPorRole(Integer rolId, Integer entidadId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select usuario.username as username, " +
                "usuario.nombres as nombres, usuario.apellidos as apellidos " +
                "from Permiso as permiso left join permiso.usuariosByUsuarioId as usuario where permiso.rolesByRolId.id = :rolId " +
                "and permiso.entidad.id = :entidadId");

        query.setParameter("rolId", rolId);
        query.setParameter("entidadId", entidadId);
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    public List<Autorizacion> agregarAutorizaciones(Integer rolId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select auto.id as id, auto.codigo as codigo," +
                "auto.nombre as nombre, auto.descripcion as descripcion from Autorizacion as auto where auto.id not in " +
                "(select roleAuto.autorizacionesByAutorizacionId.id " +
                "from RoleAutorizacion as roleAuto where roleAuto.rolesByRolId.id = :rolId)");

        query.setParameter("rolId", rolId);
        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Autorizacion.class));
        return query.list();
    }

    public List<Autorizacion> obtenerAutorizacionesFiltradasNoAgregadas(String txtBuscar, Integer rolId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select auto.id as id, auto.codigo as codigo," +
                "auto.nombre as nombre, auto.descripcion as descripcion " +
                "from Autorizacion as auto where auto.id not in " +
                "(select roleAuto.autorizacionesByAutorizacionId.id " +
                "from RoleAutorizacion as roleAuto where roleAuto.rolesByRolId.id = :rolId) and " +
                "(auto.nombre like :prBuscar or auto.descripcion like :prBuscar)");

        query.setParameter("rolId", rolId);
        query.setParameter("prBuscar", "%" + txtBuscar.toUpperCase() + "%");
        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Autorizacion.class));
        return query.list();
    }

    @Override
    public List obtenerModulosReportePorRole(Role oRole) {
        Query query = sessionFactory.getCurrentSession().createQuery("select " +
                "  m.id      as moduloId," +
                "  m.nombre  as moduloNombre,\n" +
                "  rp.id     as reporteId,\n" +
                "  rp.nombre as reporteNombre,\n" +
                "  rp.orden  as orden,\n" +
                "  cast('AG' as string)     AS  estado \n" +
                "from RoleReporte rr " +
                "join rr.reporteByReporteId rp join rp.moduloPorModuloId m " +
                "where rr.roleByRoleId.id = :role");
        query.setParameter("role", oRole.getId());
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List obtenerModulosReportePorRoleNoAgregado(Role oRole) {
        Query query = sessionFactory.getCurrentSession().createQuery("select " +
                "  mE.id      as moduloId," +
                "  mE.nombre  as moduloNombre,\n" +
                "  rpE.id     as reporteId,\n" +
                "  rpE.nombre as reporteNombre,\n" +
                "  rpE.orden  as orden,\n" +
                "  cast('NA' as string)     AS  estado \n" +
                "from RoleReporte rrE " +
                " right join rrE.reporteByReporteId rpE " +
                " right join rpE.moduloPorModuloId mE " +
                "where rpE.id not in (select rp.id from RoleReporte rr " +
                "                    join rr.reporteByReporteId rp " +
                "                    join rp.moduloPorModuloId m where rr.roleByRoleId.id = :role)");
        query.setParameter("role", oRole.getId());
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }
}