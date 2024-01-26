package ni.gob.inss.barista.model.daoImpl.seguridad;

import ni.gob.inss.barista.model.dao.seguridad.IndexacionDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.hibernate.search.FullTextSession;
import org.springframework.stereotype.Repository;

import javax.persistence.metamodel.EntityType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jjrivera on 2/16/2018.
 * Modificación de jvillanueva el 12/07/2023
 * Modificación de jvillanueva el 12/07/2023
 */
@Repository
public class IndexacionDAOImpl extends BaseGenericDAOImpl<Usuario, Integer> implements IndexacionDAO {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Override
    public List<Map<String, Object>> cargarListaEntidades() {
        List<Map<String, Object>> lstEntidades = new ArrayList<>();
        Set<EntityType<?>> entidades = sessionFactory.getMetamodel().getEntities();
        List<Map<String, Object>> classes = entidades.stream()
                .map(p -> {
                    Map<String, Object> mapa = new HashMap<>();
                    mapa.put("nombre", p.getJavaType().getSimpleName());
                    mapa.put("id", p.getJavaType());
                    return mapa;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        lstEntidades.addAll(classes);
        return lstEntidades;
    }

    @Override
    public void indexarEntidad(Class entidad) throws InterruptedException {
        FullTextSession oFullTextSession = org.hibernate.search.Search.getFullTextSession(sessionFactory.getCurrentSession());
        oFullTextSession.createIndexer(entidad).startAndWait();
        oFullTextSession.flushToIndexes();
    }
}