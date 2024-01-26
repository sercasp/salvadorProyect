package ni.gob.inss.barista.model.utils.sql;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Modificado por jvillanueva el 03/08/2023
 */
@Repository
public class SqlUtils {

    private static final Logger logger = Logger.getLogger(SqlUtils.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public SqlUtils(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Ejecuta un sql
     *
     * @return List
     */

    public List<Map<String, Object>> ejecutarSqlConParametros(String sqlScript, Map<String, String> parametros) {
        String sql = sqlScript;
        //Remplazando parámetros para ejecutar consulta si se pasaron parametros
        if (parametros != null) {
            for (Map.Entry<String, String> e : parametros.entrySet()) {
                sql = sql.replace(e.getKey(), e.getValue());
            }
        }

        Session oSession = sessionFactory.openSession();
        Query query = oSession.createNativeQuery(sql);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> aliasToValueMapList = query.list();

        oSession.close();
        return aliasToValueMapList;
    }

    public List<Map<String, Object>> ejecutarSql(String sqlScript) {
        return ejecutarSqlConParametros(sqlScript, null);

    }

    public Object findByObject(Class type, Serializable id) {
        return sessionFactory.getCurrentSession().get(type, id);
    }
}