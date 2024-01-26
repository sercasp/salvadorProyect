package ni.gob.inss.barista.model.daoImpl.auditoria;

import ni.gob.inss.barista.model.dao.auditoria.AuditTrailDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrail;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.hibernate.query.Query;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de interfaz de acceso a datos para AuditTrail</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/10/2014
 * @since 1.0 *
 */
@Repository
public class AuditTrailDAOImpl extends BaseGenericDAOImpl<AuditTrail,Integer> implements AuditTrailDAO {


    @Override
    public List<AuditTrail> obtenerAuditoria(String schema, String table, Object id) {

        Query query = sessionFactory.getCurrentSession().createQuery("select audit.id as id,audit.fecha as fecha," +
                "audit.operacion as operacion,concat('(',user.username,') ',user.nombres,' ',user.apellidos) as nombreUsuario " +
                "from AuditTrail audit " +
                "join audit.usuario user " +
                "where audit.schema = :schema and audit.tabla = :tabla and audit.filaId = :filaId " +
                "order by audit.fecha desc");

        query.setParameter("schema",schema);
        query.setParameter("tabla",table);
        query.setParameter("filaId",id);
        ((QueryImpl)query).setResultTransformer(new AliasToBeanResultTransformer(AuditTrail.class));

        return (List<AuditTrail>)query.list();
    }

    @Override
    public List<AuditTrail> obtenerAuditoriaPorUsuario(Usuario oUsuario,Date fecha,Date fechaFin,String schema, String table) {

        java.sql.Date fechaConvertida = new java.sql.Date(fecha.getTime());
        java.sql.Date fechaConvertidaFin = null;
        Query query = null;
        if(fechaFin != null){
            fechaConvertidaFin = new java.sql.Date(fechaFin.getTime());
            if(table == null && schema != null){
                query = sessionFactory.getCurrentSession().createQuery("from AuditTrail audit " +
                        "where audit.usuario = :usuario and cast(audit.fecha as date) between :fecha and :fechaFin " +
                        "and audit.schema = :schema " +
                        "order by audit.fecha desc");

                query.setParameter("schema",schema);
            }
            else if(table== null && schema == null) {
                query = sessionFactory.getCurrentSession().createQuery("from AuditTrail audit " +
                        "where audit.usuario = :usuario  and cast(audit.fecha as date) between :fecha and :fechaFin " +
                        "order by audit.fecha desc");

            }
            else if(table != null && schema != null){
                query = sessionFactory.getCurrentSession().createQuery("from AuditTrail audit " +
                        "where audit.usuario = :usuario and cast(audit.fecha as date) between :fecha and :fechaFin " +
                        "and audit.schema = :schema and audit.tabla = :tabla " +
                        "order by audit.fecha desc");

                query.setParameter("schema",schema);
                query.setParameter("tabla",table);
            }

            query.setParameter("fechaFin",fechaConvertidaFin);
        }else{
            if(schema==null && table != null){
                query = sessionFactory.getCurrentSession().createQuery("from AuditTrail audit " +
                        "where audit.usuario = :usuario and cast(audit.fecha as date) = :fecha " +
                        "order by audit.fecha desc");
            }
            else if(table == null && schema != null){
                query = sessionFactory.getCurrentSession().createQuery("from AuditTrail audit " +
                        "where audit.usuario = :usuario and cast(audit.fecha as date) = :fecha " +
                        "and audit.schema = :schema " +
                        "order by audit.fecha desc");

                query.setParameter("schema",schema);
            }
            else if(table== null && schema == null) {
                query = sessionFactory.getCurrentSession().createQuery("from AuditTrail audit " +
                        "where audit.usuario = :usuario  and cast(audit.fecha as date) = :fecha " +
                        "order by audit.fecha desc");

            }
            else if(table != null && schema != null){
                query = sessionFactory.getCurrentSession().createQuery("from AuditTrail audit " +
                        "where audit.usuario = :usuario and cast(audit.fecha as date) = :fecha " +
                        "and audit.schema = :schema and audit.tabla = :tabla " +
                        "order by audit.fecha desc");

                query.setParameter("schema",schema);
                query.setParameter("tabla",table);
            }
        }
        query.setParameter("usuario",oUsuario);
        query.setParameter("fecha",fechaConvertida);
        return (List<AuditTrail>)query.list();
    }
}
