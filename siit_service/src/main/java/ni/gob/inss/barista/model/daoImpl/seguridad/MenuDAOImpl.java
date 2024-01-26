package ni.gob.inss.barista.model.daoImpl.seguridad;

import ni.gob.inss.barista.model.dao.seguridad.MenuDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Menu;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de acceso a datos para Menús</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
@Repository
public class MenuDAOImpl extends BaseGenericDAOImpl<Menu, Integer> implements MenuDAO {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Override
    public List<Modulo> obtenerModulos() {
        return sessionFactory.getCurrentSession().createQuery("from Modulo where bloqueado=false order by nombre asc").list();
    }

    @Override
    public List<Recurso> obtenerRecursos() {
        return sessionFactory.getCurrentSession().createQuery("from Recurso where pasivo = false order by nombre asc").list();
    }

    public Menu buscarPorMenu(String Menu) {
        Menu oMenu = null;
        List<Menu> listaMenu = sessionFactory.getCurrentSession()
                .createQuery("from Menu where nombre = :prNombre")
                .setParameter("prNombre", Menu).list();
        if (listaMenu.size() > 0) {
            oMenu = listaMenu.get(0);
        }
        return oMenu;
    }

    @Override
    public List<Menu> obtenerRepetido(Integer prId, Integer prOrden, Integer prMod, Integer prMenuId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select m from Menu m " +
                "where m.orden = :prOrden AND m.modulosByModuloId.id= :prMod AND m.id not in(:prId) and m.menusByMenuId.id= :prMenuId");
        query.setParameter("prOrden", prOrden);
        query.setParameter("prId", prId);
        query.setParameter("prMod", prMod);
        query.setParameter("prMenuId", prMenuId);
        return query.list();
    }

    @Override
    public List<Menu> obtenerRepetido2(Integer prId, Integer prOrden, Integer prMod) {
        Query query = sessionFactory.getCurrentSession().createQuery("select m from Menu m " +
                "where m.orden = :prOrden AND m.modulosByModuloId.id= :prMod AND m.id not in(:prId) and m.menusByMenuId.id is null");
        query.setParameter("prOrden", prOrden);
        query.setParameter("prId", prId);
        query.setParameter("prMod", prMod);
        return query.list();
    }

    @Override
    public List<Menu> obtenerRepetidover(Integer prOrden, Integer prMod, Integer prMenuId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select m from Menu m " +
                "where m.orden = :prOrden AND  m.modulosByModuloId.id = :prMod and m.menusByMenuId.id= :prMenuId");
        query.setParameter("prOrden", prOrden);
        query.setParameter("prMod", prMod);
        query.setParameter("prMenuId", prMenuId);
        return query.list();
    }

    @Override
    public List<Menu> obtenerRepetidos(Integer prOrden) {
        Query query = sessionFactory.getCurrentSession().createQuery("select m from Menu m " +
                "where m.orden = :prOrden ");
        query.setParameter("prOrden", prOrden);
        return query.list();
    }

    @Override
    public List obtenerMenuPorUsuarioModulo(Integer usuarioId, Integer entidadId, Integer moduloId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select distinct " +
                "menus.id as id,menus.nombre as nombre, menus.menusByMenuId.id as parent_id, " +
                "re.url as url, menus.orden as orden " +
                "from Recurso re " +
                "join re.menusesById as menus join menus.rolesMenusesById roles join roles.rolesByRolId rol " +
                "join rol.permisosesById per join menus.modulosByModuloId modulo " +
                "where per.entidad.id = :entidadId and modulo.id = :moduloId and per.usuariosByUsuarioId.id = :usuarioId " +
                "and modulo.bloqueado = false and re.tipo = 'P' " +
                "and menus.pasivo = false  and rol.pasivo = false and re.pasivo = false " +
                "order by menus.menusByMenuId.id asc, menus.orden asc");
        query.setParameter("usuarioId", usuarioId);
        query.setParameter("entidadId", entidadId);
        query.setParameter("moduloId", moduloId);
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.getResultList();
    }

    @Override
    public List obtenerMenuPorUsuarioModuloMobile(Integer usuarioId, Integer entidadId, Integer moduloId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select distinct menus.id as id,menus.nombre as nombre," +
                "menus.menusByMenuId.id as parent_id,recursos.url as url,menus.orden as orden from Recurso as recursos " +
                "join recursos.menusesById as menus join menus.rolesMenusesById as roles_menus " +
                "join roles_menus.rolesByRolId as roles join roles.permisosesById as permisos " +
                "join menus.modulosByModuloId as modulos WHERE permisos.usuariosByUsuarioId.id = :usuarioId " +
                "and permisos.entidad.id= :entidadId and modulos.bloqueado=false and recursos.tipo='M' " +
                "and menus.pasivo=false and roles.pasivo =false and recursos.pasivo= false " +
                "and modulos.id =:moduloId ORDER BY menus.menusByMenuId.id asc,menus.orden asc,menus.id asc");
        query.setParameter("usuarioId", usuarioId);
        query.setParameter("entidadId", entidadId);
        query.setParameter("moduloId", moduloId);
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    public List obtenerMenuPorModuloTree(Integer moduloId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select menu.id as id, menu.nombre as text , parent.id as parentId from Menu as menu " +
                "left join menu.menusByMenuId as parent left join menu.modulosByModuloId as modulo where modulo.id = :moduloId ORDER BY menu.orden asc");
        query.setParameter("moduloId", moduloId);
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    /**
     * Este metodo verifica si el menu seleccionado esta relacionado con la tabla rol menu
     * @param prIdMenu parametro de busqueda
     */
    @Override
    public List<Menu> verificarMenuEnRolMenu(Integer prIdMenu) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT m.id AS id, m.nombre AS nombre " +
                "FROM RoleMenu AS rm " +
                "JOIN rm.menusByMenuId AS m " +
                "WHERE m.id = :prIdMenu");
        query.setParameter("prIdMenu", prIdMenu);
        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Menu.class));
        return query.list();
    }

    @Override
    public List obtenerMenuPorModuloTree() {
        Query query = sessionFactory.getCurrentSession().createQuery("select menu.id as id , menu.nombre as nombre , parent.id as menu_id, menu.modulosByModuloId.id + 10000 as modulo_id from Menu as menu left join menu.menusByMenuId as parent ORDER BY menu.menusByMenuId.id asc,menu.orden asc,menu.id asc");
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List obtenerModulosTree() {
        Query query = sessionFactory.getCurrentSession().createQuery("select modulo.id + 10000 as id , modulo.nombre as nombre  from Modulo as modulo order by modulo.nombre asc");
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public String obtenerBreadcrum(Integer menuId) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("WITH RECURSIVE menu AS ( " +
                "select id,concat('<li>',nombre,'</li>') as nombre,menu_id, cast(1 as integer) as orden " +
                "from seguridad.menu " +
                "where id = :menuId " +
                " UNION " +
                "SELECT " +
                "m.id,concat('<li>',m.nombre,'</li>') as nombre,m.menu_id, s.orden + 1  " +
                "FROM " +
                "seguridad.menu m " +
                "INNER JOIN menu s ON s.menu_id = m.id) " +
                "SELECT " +
                "array_to_string(array_agg(nombre ORDER BY orden desc), ' <li><i class=\"material-icons\">chevron_right</i></li> ') as bread " +
                "FROM menu;");
        query.setParameter("menuId", menuId);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        HashMap<String, String> result = (HashMap<String, String>) query.list().iterator().next();
        return result.get("bread");
    }
}