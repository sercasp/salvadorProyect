package ni.gob.inss.siit.model.daoImpl.historia;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.catalogo.DivisionPolitica;
import ni.gob.inss.siit.model.dao.historia.PersonaDAO;
import ni.gob.inss.siit.model.entity.historia.Persona;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Creado por jvillanueva1 el 01/12/2015.
 * Modificado por jvillanueva 02/01/2018.
 * Modificado por jvillanueva el 08/02/2021.
 * Modificado por jvillanueva el 22/05/2021.
 */

@Repository
public class PersonaDAOImpl extends BaseGenericDAOImpl<Persona, Integer> implements PersonaDAO {

    @Override
    public List buscarPersona(String prBuscar) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select id,concat_ws(' ',primer_nombre," +
                "segundo_nombre,primer_apellido,segundo_apellido ) as nombrecompleto," +
                "dni,verificado,sexo,fecha_nacimiento as fechaNacimiento,fallecido, " +
                "total_registros from historia.fn_buscar_persona(:prBuscar,20000,0)  " +
                "WHERE  fallecido=false  " +
                "ORDER BY primer_nombre,segundo_nombre,primer_apellido,segundo_apellido");
        query.setParameter("prBuscar", prBuscar);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List<Map<String, Object>> buscarPersonaLazy(String buscar,
                                                       Integer limite,
                                                       Integer pagina) {
        Query query = sessionFactory.getCurrentSession()
                .createNativeQuery("select * from historia.fn_buscar_persona_lazy(:prBuscar," +
                        ":prLimite," +
                        ":prOffset)");

        query.setParameter("prBuscar", buscar);
        query.setParameter("prLimite", limite);
        query.setParameter("prOffset", pagina);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List<DivisionPolitica> buscarDepartamentos() {
        Query query = sessionFactory.getCurrentSession().createQuery("select dp.codigo as codigo,dp.nombre as nombre " +
                "from DivisionPolitica as dp where dp.divisionesPoliticas.id is null order by dp.nombre");

        ((NativeQueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(DivisionPolitica.class));

        return query.list();
    }
}
