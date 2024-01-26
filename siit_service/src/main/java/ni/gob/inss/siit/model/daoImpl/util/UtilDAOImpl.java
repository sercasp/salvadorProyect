package ni.gob.inss.siit.model.daoImpl.util;

import com.google.gson.Gson;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.siit.model.dao.util.UtilDAO;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Alex Benavidez on 09/09/2019.
 * Modificado por jvillanueva el 09/06/2020.
 * Modificado por mbarrios el 06/01/2021. Corregir cuando se hagan los periodos de cierre
 * Modificado por jvillanueva el 24/05/2021.
 */
////@SuppressWarnings("unchecked")
@Repository
public class UtilDAOImpl extends BaseGenericDAOImpl<Object, Integer> implements UtilDAO {
    @Override
    public List<String> obtenerEncabezadoDesdeTipoSql(String nombreTipo) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select attname from pg_attribute where attrelid=(select typrelid from pg_type where typname=:nombreTipo) and attname!='Total_Registros' order by attnum");
        query.setParameter("nombreTipo", nombreTipo);
        return (List<String>) query.list();
    }

    @Override
    public Integer obtenerAnioActivo(Integer entidadId, Integer anio) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("SELECT cast(((coalesce(anio, date_part('year',now())))) + 1 as integer) as anio FROM  historial_empresa.empresas_turistica inner join catalogo.delegacion on delegacion.id=empresas_turistica.delegacion_id\n" +
                "where empresas_turistica.pasivo=false and delegacion.entidad_id=:entidadId order by anio desc LIMIT 1");
        query.setParameter("entidadId", entidadId);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String, Object>> lista = query.list();

        return lista != null && lista.size() > 0 ? (Integer) lista.get(0).get("anio") : anio;
    }

    @Override
    public String obtenerIp() {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select fn_obtener_ip as ip from general.fn_obtener_ip()");
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return ((HashMap) query.list().get(0)).get("ip").toString();
    }

    @Override
    public String separarCerosDeNumeros(){
        org.hibernate.Query query = sessionFactory.getCurrentSession().createSQLQuery("select to_char(coalesce(max(cast(numero_documento as integer)), 0) +1, '0000000000') from mcb.tb_ingresos_egresos_bancos");
        return (String) query.uniqueResult();
    }

    @Override
    public String separarCerosDeNumerosComprobante(Integer aumento, int anio){
        org.hibernate.Query query = sessionFactory.getCurrentSession().createSQLQuery("select to_char(coalesce(max(cast(comprobante as integer)), 0) + :aumento, '0000000000')\n" +
                "from mconta.tb_comprobante\n" +
                "where extract(year from fecha_proceso) = :anio");
        query.setMaxResults(1);
        query.setParameter("aumento",aumento);
        query.setParameter("anio",anio);
        return (String) query.uniqueResult();
    }

    @Override
    public String obtenerRefArea(Integer entidadId){
        org.hibernate.Query query = sessionFactory.getCurrentSession().createSQLQuery("select id_area from catalogo.tb_areas a\n" +
                " inner join infraestructura.entidad e on e.area_id = a.id\n" +
                " where e.id = :entidadId \n" +
                " and a.estado = 1");
        query.setParameter("entidadId", entidadId);
        return (String) query.uniqueResult();
    }

    @Override
    public Boolean obtenerArriendo(Integer entidadId){
        org.hibernate.Query query = sessionFactory.getCurrentSession().createSQLQuery("select es_arriendo from infraestructura.entidad where id = :entidadId");
        query.setParameter("entidadId",entidadId);
        return (Boolean) query.uniqueResult();
    }

    @Override
    public List<Map<String,Object>> obtenerAutorizacionesPorRoles(Integer usuarioId){
        Query query = sessionFactory.getCurrentSession().createNativeQuery(" SELECT a.codigo codigo_autoriza, ra.rol_id id_rol, p.usuario_id usuario\n" +
                "FROM seguridad.permiso as p\n" +
                "         INNER JOIN seguridad.rol as r ON p.rol_id = r.id\n" +
                "         INNER JOIN seguridad.rol_autorizacion as ra ON ra.rol_id = r.id\n" +
                "         INNER JOIN seguridad.autorizacion as a ON ra.autorizacion_id = a.id\n" +
                "WHERE p.usuario_id = :usuarioId");
        query.setParameter("usuarioId", usuarioId);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List<Map<String,Object>> listaDirecciones(){
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select * from bodega.fn_obtener_directores(0)");
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List<Map<String,Object>> direccionDelUsuario(int usuarioId){
        Query query = sessionFactory.getCurrentSession().createNativeQuery(" SELECT ee.id empleado_id,\n" +
                "       AD2.nombre direccion,\n" +
                "       p.primer_nombre || ' ' || p.segundo_nombre     as nombre,\n" +
                "       p.primer_apellido || ' ' || p.segundo_apellido as apellido ,\n" +
                "       pp.primer_nombre || ' ' || pp.segundo_nombre     as nombre_usuario,\n" +
                "       pp.primer_apellido || ' ' || pp.segundo_apellido as apellido_usuario,\n" +
                "       U.empleado_id as empleado_usuario_id\n" +
                "FROM mrrhh.empleados E\n" +
                "INNER JOIN seguridad.usuario U ON U.EMPLEADO_ID = E.id\n" +
                "INNER JOIN mrrhh.datos_laborales DL ON DL.ID = E.datos_laborales_id\n" +
                "INNER JOIN catalogo.cargos_administrativos AD ON AD.ID = DL.cargos_administrativos_id\n" +
                "INNER JOIN catalogo.cargos_administrativos AD2 ON AD2.ID = AD.CARGOS_ADMINISTRATIVOS_ID\n" +
                "INNER JOIN mrrhh.datos_laborales DL2 ON DL2.cargos_administrativos_id = AD2.ID\n" +
                "INNER JOIN mrrhh.empleados ee on ee.datos_laborales_id =  DL2.ID\n" +
                "INNER JOIN historia.persona p on p.id = ee.persona_id\n" +
                "inner join historia.persona pp on pp.id = E.persona_id\n" +
                "WHERE U.id  = :usuarioId");
        query.setParameter("usuarioId", usuarioId);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public String numeroLetras(BigDecimal numero, String monedaIso) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("SELECT * FROM public.fn_numero_letras(:numero, :monedaIso)");
        query.setParameter("numero", numero);
        query.setParameter("monedaIso", monedaIso);
        return (String) query.uniqueResult();
    }

    @Override
    public String getAppi(String uri, Object req) {
        String res = "";
       // String getAppi(String uri, Object req);

        try {
           /* Client client = ClientBuilder.newClient();
            WebTarget target = client.target(uri);
            Invocation.Builder solicitud = target.request();

            Gson gson = new Gson();
            String jsonString = gson.toJson(req);
            Response post = solicitud.post(Entity.json(jsonString));

            String responseJson = post.readEntity(String.class);

            switch (post.getStatus()){
                case 200 :
                    res= responseJson;
                    break;
                default: res = "Error";
                    break;
            }*/

        }catch (Exception e){
            res = e.toString();
        }

        return res;
    }
    
    public String convertirNumeroaLetras(Integer prNumero) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("SELECT * FROM catalogo.fn_numero_letra(:prNumero)");
        query.setParameter("prNumero", prNumero);
        return (String) query.uniqueResult();
    }

    @Override
    public List<Map<String, Object>> diasFeriados(String prBuscar) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select * from catalogo.fn_lista_dias_feriados(:prBuscar)");
        query.setParameter("prBuscar", prBuscar);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();

    }

    @Override
    public List<Map<String, Object>> cargarDiasFeriados(String prBuscar) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select * from public.fn_cargardiasferidos(:prBuscar)");
        query.setParameter("prBuscar", prBuscar);

        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();

    }

    @Override
    public Map<String, Object> insertAtty(String prnombre, Date prfecha, Integer prduracion, Integer prdelegacion) {

        Query query = sessionFactory.getCurrentSession()
                .createNativeQuery("select * from catalogo.fn_insert_att_holiday(:prnombre, :prfecha, :prduracion, :prdelegacion)");
        query.setParameter("prnombre", prnombre);
        query.setParameter("prfecha", prfecha);
        query.setParameter("prduracion", prduracion);
        query.setParameter("prdelegacion", prdelegacion);

        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return (Map<String, Object>) query.list().get(0);

    }

    @Override
    public Map<String, Object> EliminarRegistroAtty(Integer prRegistro) {
        Query query = sessionFactory.getCurrentSession()
                .createNativeQuery("select * from catalogo.fn_eliminar_registro_att_tabla_holiday(:prRegistro)");
        query.setParameter("prRegistro", prRegistro);

        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
       return (Map<String, Object>) query.list().get(0);

    }

}
