package ni.gob.inss.barista.model.daoImpl.seguridad;

import ni.gob.inss.barista.model.dao.seguridad.ReporteDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Reporte;
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
 * @author jjrivera
 * @version 1.0
 * @since 5/10/2016
 * Modificación de jvillanueva el 12/07/2023
 */
@Repository
public class ReporteDAOImpl extends BaseGenericDAOImpl<Reporte, Integer> implements ReporteDAO {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     * Devuelve los modulos para el arbol
     *
     * @return Lista de mapa que contiene el listado de modulos
     */
    @Override
    public List<Map<String, Object>> obtenerModulos() {
        Query query = sessionFactory.getCurrentSession().createQuery("select modulo.id + 10000 as id , modulo.nombre as nombre  from Modulo as modulo order by modulo.nombre asc");
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    /**
     * Devuelve el listado de reportes
     * @return Lista de mapa con el listado de reportes
     */
    @Override
    public List<Map<String, Object>> obtenerReportes() {
        Query query = sessionFactory.getCurrentSession().createQuery("select reporte.id as id, reporte.nombre as nombre, reporte.moduloPorModuloId.id + 10000 as modulo_id from Reporte reporte order by reporte.orden asc, reporte.id asc");
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    /**
     * Devuelve los usuarios que pueden acceder al reporte
     *
     * @param prReporteId el id del reporte
     * @return Lista de tipo Usuario con el listado de usuarios que pueden acceder al reporte
     */
    @Override
    public List<Usuario> obtenerUsuariosPorReporte(Integer prReporteId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select distinct usuarios.id as id, usuarios.username as username , usuarios.nombres as nombres , usuarios.apellidos as apellidos " +
                "from RoleReporte roleMenu join roleMenu.roleByRoleId as rol join rol.permisosesById as permiso " +
                "join permiso.usuariosByUsuarioId as usuarios join roleMenu.reporteByReporteId as menus " +
                "where menus.pasivo = false and menus.id = :menuId");

        query.setParameter("menuId", prReporteId);
        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));
        return query.list();
    }

    /**
     * Devuelve la lista de roles por reporte
     *
     * @param prReporteId el id del reporte
     * @return lista con los roles del reporte
     */
    @Override
    public List obtenerRolesPorReporte(Integer prReporteId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select role.id as id , role.nombre as nombre from RoleReporte roleReporte join roleReporte.roleByRoleId as role where roleReporte.reporteByReporteId.id = :reporte ORDER BY role.nombre asc");
        query.setParameter("reporte", prReporteId);
        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Role.class));
        return query.list();
    }

    @Override
    public List<Reporte> verificarRolReporte(Integer prIdReporte) {
        Query query = sessionFactory.getCurrentSession().createQuery("select distinct rr.reporteByReporteId " +
                "from RoleReporte rr " +
                "join rr.reporteByReporteId r where r.id = :prIdReporte");
        query.setParameter("prIdReporte", prIdReporte);
        return query.list();
    }

    @Override
    public List<Reporte> obtenerRepetidos(Integer prOrden) {
        Query query = sessionFactory.getCurrentSession().createQuery("select rr from Reporte rr " +
                "where rr.orden = :prOrden ");
        query.setParameter("prOrden", prOrden);
        return query.list();
    }

    @Override
    public List<Reporte> obtenerRepetido(Integer prId, Integer prOrden) {
        Query query = sessionFactory.getCurrentSession().createQuery("select rr from Reporte rr " +
                "where rr.orden = :prOrden AND rr.id = :prId");
        query.setParameter("prOrden", prOrden);
        query.setParameter("prId", prId);
        return query.list();
    }
}