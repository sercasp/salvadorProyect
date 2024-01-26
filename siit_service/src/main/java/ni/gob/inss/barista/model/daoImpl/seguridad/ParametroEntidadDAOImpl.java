package ni.gob.inss.barista.model.daoImpl.seguridad;

import ni.gob.inss.barista.model.dao.seguridad.ParametroEntidadDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.ParametroEntidad;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Parametros Entidades</br>
 *
 * @author VIRGINIA ELIZABETH MORA MUNGUIA
 * @version 1.0, 03/06/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */

@Repository
public class ParametroEntidadDAOImpl extends BaseGenericDAOImpl<ParametroEntidad, Integer> implements ParametroEntidadDAO {
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
    public List<ParametroEntidad> obtenerRepetidos(String prCodigo, Integer prIdEntidad) {
        Query query = sessionFactory.getCurrentSession().createQuery("select distinct pe from ParametroEntidad pe " +
                "where pe.codigo = :prCodigo and pe.entidadIdByEntidadId.id = :prIdEntidad");
        query.setParameter("prCodigo", prCodigo);
        query.setParameter("prIdEntidad", prIdEntidad);
        return query.list();
    }

    @Override
    public List<ParametroEntidad> obtenerRepetido(String prCodigo, Integer prIdEntidad) {
        Query query = sessionFactory.getCurrentSession().createQuery("select pe from ParametroEntidad pe " +
                "where pe.codigo = :prCodigo and pe.entidadIdByEntidadId.id = :prIdEntidad");
        query.setParameter("prCodigo", prCodigo);
        query.setParameter("prIdEntidad", prIdEntidad);
        return query.list();
    }

    @Override
    public List<ParametroEntidad> obtenerRepetidos(Integer prId, String prCodigo, Integer prIdEntidad) {
        Query query = sessionFactory.getCurrentSession().createQuery("select pe from ParametroEntidad pe " +
                "where pe.codigo = :prCodigo and pe.entidadIdByEntidadId.id = :prIdEntidad and pe.id = :prId");
        query.setParameter("prCodigo", prCodigo);
        query.setParameter("prIdEntidad", prIdEntidad);
        query.setParameter("prId", prId);
        return query.list();
    }

    public ParametroEntidad buscarPorParametroEntidad(String ParametroEntidad) {
        ParametroEntidad oParametroEntidad = null;
        List<ParametroEntidad> listaParametroEntidad = sessionFactory.getCurrentSession()
                .createQuery("from ParametroEntidad where descriptor = :prDescriptor")
                .setParameter("prDescriptor", ParametroEntidad).list();
        if (listaParametroEntidad.size() > 0) {
            oParametroEntidad = listaParametroEntidad.get(0);
        }
        return oParametroEntidad;
    }
}