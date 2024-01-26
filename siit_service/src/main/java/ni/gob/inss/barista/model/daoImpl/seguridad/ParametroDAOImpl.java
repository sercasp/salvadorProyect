package ni.gob.inss.barista.model.daoImpl.seguridad;

import ni.gob.inss.barista.model.dao.seguridad.ParametroDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de acceso a datos para Parámetros</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
@Repository
public class ParametroDAOImpl extends BaseGenericDAOImpl<Parametro, Integer> implements ParametroDAO {
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
    public List<Parametro> obtenerParametroUsuario(Integer prIdParametro) {
        // /Query /query = sessionFactory.getCurrentSession().createSQLQuery("SELECT DISTINCT\n" +
        //                "  p.id         AS id,\n" +
        //                "  p.descriptor AS descriptor\n" +
        //                "FROM seguridad.parametro_usuario pu\n" +
        //                "  INNER JOIN seguridad.parametro p ON pu.parametro_id = p.id\n" +
        //                "WHERE pu.parametro_id = :prIdParametro ");
        Query query = sessionFactory.getCurrentSession().createQuery("select distinct pu.parametroIdByParametroId " +
                "from ParametroUsuario pu " +
                "join pu.parametroIdByParametroId p " +
                "where pu.parametroIdByParametroId.id = :prIdParametro");
        query.setParameter("prIdParametro", prIdParametro);
        // query.setResultTransformer(new AliasToBeanResultTransformer(Parametro.class));
        return query.list();
    }

    public Parametro buscarPorParametro(String Parametro) {
        Parametro oParametro = null;
        List<Parametro> listaParametro = sessionFactory.getCurrentSession()
                .createQuery("from Parametro where descriptor = :prDescriptor")
                .setParameter("prDescriptor", Parametro).list();
        if (listaParametro.size() > 0) {
            oParametro = listaParametro.get(0);
        }
        return oParametro;
    }
}