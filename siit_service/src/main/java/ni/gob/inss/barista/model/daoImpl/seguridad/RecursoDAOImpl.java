package ni.gob.inss.barista.model.daoImpl.seguridad;

import ni.gob.inss.barista.model.dao.seguridad.RecursoDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.personal.Miembro;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de acceso a datos para Recursos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
@Repository
public class RecursoDAOImpl extends BaseGenericDAOImpl<Recurso, Integer> implements RecursoDAO {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Override
    public List<Miembro> obtenerMiembros() {
        return sessionFactory.getCurrentSession().createQuery("from Miembro where pasivo=false order by " +
                "primerNombre,segundoNombre,primerApellido,segundoApellido asc").list();
    }

    @Override
    public List<Recurso> obtenerRecursosAuditables() {
        return sessionFactory.getCurrentSession().createQuery("from Recurso").list();
    }

    public Recurso buscarPorRecurso(String Recurso) {
        Recurso oRecurso = null;
        List<Recurso> listaRecurso = sessionFactory.getCurrentSession()
                .createQuery("from Recurso where nombre = :prNombre")
                .setParameter("prNombre", Recurso).list();
        if (listaRecurso.size() > 0) {
            oRecurso = listaRecurso.get(0);
        }
        return oRecurso;
    }

    @Override
    public List obtenerRecursosAuditablesPorUsuario(int usuarioId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select recurso.id as id, recurso.url as url," +
                "permisos.entidad.id as entidadId,recurso.tipo as tipo from Recurso as recurso " +
                "join recurso.menusesById as menus join menus.rolesMenusesById as roles_menus " +
                "join menus.modulosByModuloId as modulos " +
                "join roles_menus.rolesByRolId as roles join roles.permisosesById as permisos " +
                "WHERE permisos.usuariosByUsuarioId.id = :usuarioId and menus.pasivo=false " +
                "and roles.pasivo =false and modulos.bloqueado=false ");

        query.setParameter("usuarioId", usuarioId);
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List secuenciaCodigo() {
        Query query = sessionFactory.getCurrentSession().createQuery("select cast((max(cast(codigo as integer)) + 1) " +
                "as string) as codigo from Recurso where tipo = 'P'");
        ((QueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public Recurso verificarPermiso(Usuario oUsuario, Entidad oEntidad, String url) {
        Recurso recursoVerificado = null;
        Query query = sessionFactory.getCurrentSession().createQuery("select recurso.id as id, recurso.url as url," +
                "recurso.nombre as nombre , recurso.descripcion as descripcion, recurso.creadoEl as creadoEl, " +
                "recurso.urlAyuda as urlAyuda, recurso.miembrosByRecursoId.id as miembroId from Recurso as recurso " +
                "join recurso.menusesById as menus join menus.rolesMenusesById as roles_menus " +
                "join roles_menus.rolesByRolId as roles join roles.permisosesById as permisos " +
                "WHERE permisos.usuariosByUsuarioId = :usuario and permisos.entidad = :entidad " +
                "and recurso.url = :url and menus.pasivo=false " +
                "and roles.pasivo =false ");

        query.setParameter("usuario", oUsuario);
        query.setParameter("entidad", oEntidad);
        query.setParameter("url", url);

        ((QueryImpl) query).setResultTransformer(new AliasToBeanResultTransformer(Recurso.class));

        List<Recurso> listaRecurso = query.list();
        if (listaRecurso.size() > 0) {
            recursoVerificado = listaRecurso.get(0);
        }
        return recursoVerificado;
    }

    @Override
    public String obtenerBreadcrum(Integer menuId) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("WITH RECURSIVE menu AS ( " +
                "select id,concat('<li>',nombre,'</li>') as nombre,menu_id " +
                "from seguridad.menu " +
                "where id = :menuId" +
                " UNION " +
                "SELECT " +
                "m.id,concat('<li>',m.nombre,'</li>') as nombre,m.menu_id\n" +
                "FROM " +
                "seguridad.menu m " +
                "INNER JOIN menu s ON s.menu_id = m.id) " +
                "SELECT " +
                "array_to_string(array_agg(nombre), ' <li><i class=\"material-icons\">chevron_right</i></li> ')" +
                "FROM menu;");

        query.setParameter("menuId", menuId);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.uniqueResult().toString();
    }
}