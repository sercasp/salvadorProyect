package ni.gob.inss.barista.model.daoImpl.seguridad;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.ModuloDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import org.hibernate.query.Query;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de acceso a datos para Módulos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificación de jvillanueva el 12/07/2023
 */
@Repository
public class ModuloDAOImpl extends BaseGenericDAOImpl<Modulo, Integer> implements ModuloDAO {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Override
    public List<Map<String, Object>> obtenerModulosPorUsuario(Integer usuarioId, Integer entidadId, String nombreModulo, String orderBy) {
        Query query;
        if (orderBy.equals("nombre"))
            query = sessionFactory.getCurrentSession().getNamedQuery("obtenerModulosPorUsuarioOrderNombre")
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("tipo", "P")
                    .setParameter("entidadId", entidadId)
                    .setParameter("nombreModulo", "%" + nombreModulo + "%");
        else if (orderBy.equals("cantidad"))
            query = sessionFactory.getCurrentSession().getNamedQuery("obtenerModulosPorUsuarioOrderCantidad")
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("tipo", "P")
                    .setParameter("entidadId", entidadId)
                    .setParameter("nombreModulo", "%" + nombreModulo + "%");
        else {
            query = sessionFactory.getCurrentSession().getNamedQuery("obtenerModulosPorUsuarioOrderNombre")
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("tipo", "P")
                    .setParameter("entidadId", entidadId)
                    .setParameter("nombreModulo", "%" + nombreModulo + "%");
        }
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List<Map<String, Object>> obtenerModulosPorUsuarioMobile(Integer usuarioId, Integer entidadId, String nombreModulo, String orderBy) {
        Query query;
        if (orderBy.equals("nombre"))
            query = sessionFactory.getCurrentSession().getNamedQuery("obtenerModulosPorUsuarioOrderNombre")
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("tipo", "M")
                    .setParameter("entidadId", entidadId)
                    .setParameter("nombreModulo", "%" + nombreModulo + "%");
        else if (orderBy.equals("cantidad"))
            query = sessionFactory.getCurrentSession().getNamedQuery("obtenerModulosPorUsuarioOrderCantidad")
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("tipo", "M")
                    .setParameter("entidadId", entidadId)
                    .setParameter("nombreModulo", "%" + nombreModulo + "%");
        else {
            query = sessionFactory.getCurrentSession().getNamedQuery("obtenerModulosPorUsuarioOrderNombre")
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("tipo", "M")
                    .setParameter("entidadId", entidadId)
                    .setParameter("nombreModulo", "%" + nombreModulo + "%");
        }
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List<Modulo> obtenerModulos() {
        return sessionFactory.getCurrentSession().createQuery("from Modulo where bloqueado=false order by nombre asc").list();
    }

    /**
     * Este metodo se encarga de verificar si el módulo que se quiere eliminar tiene relaciones con la tabla
     * reporte
     * @param prIdModulo parametro de busqueda
     * @return retorna una lista tipo modulo
     * Modificación de jvillanueva el 12/07/2023
     */
    @Override
    public List<Modulo> verificarModuloEnReporte(Integer prIdModulo) {
        Query query = sessionFactory.getCurrentSession().createQuery("select repo.moduloPorModuloId from Reporte repo " +
                "where repo.moduloPorModuloId.id = :prIdModulo");
        query.setParameter("prIdModulo", prIdModulo);
        return query.list();
    }

    /**
     * Este metodo se encarga de verificar si el módulo que se quiere eliminar tiene relaciones con la tabla
     * parametro entidad
     *
     * @param prIdModulo parametro de busqueda
     * @return retorna una lista tipo modulo
     * Modificación de jvillanueva el 12/07/2023
     */
    @Override
    public List<Modulo> verificarModuloEnParametroEntidad(Integer prIdModulo) {
        Query query = sessionFactory.getCurrentSession().createQuery("select pe.modulosByModuloId " +
                "from ParametroEntidad pe " +
                "where pe.modulosByModuloId.id = :prIdModulo");
        query.setParameter("prIdModulo", prIdModulo);
        return query.list();
    }

    /**
     * Este metodo se encarga de verificar si el módulo que se quiere eliminar tiene relaciones con la tabla
     * sesion modulo
     * @param prIdModulo parametro de busqueda
     * @return retorna una lista tipo modulo
     * Modificación de jvillanueva el 12/07/2023
     */
    @Override
    public List<Modulo> verificarModuloEnSesionModulo(Integer prIdModulo) {
        Query query = sessionFactory.getCurrentSession().createQuery("select sm.modulosByModuloId " +
                "from SesionModulo sm " +
                "where sm.modulosByModuloId.id = :prIdModulo");
        query.setParameter("prIdModulo", prIdModulo);
        return query.list();
    }

    public Modulo obtenerPorId(Integer integer) throws EntityNotFoundException {
        Query query = sessionFactory.getCurrentSession().createQuery("from Modulo where id = :id");
        query.setParameter("id", integer);
        return (Modulo) query.list().get(0);
    }
}