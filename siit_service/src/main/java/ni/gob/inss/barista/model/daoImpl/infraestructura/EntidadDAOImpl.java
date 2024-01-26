package ni.gob.inss.barista.model.daoImpl.infraestructura;

import ni.gob.inss.barista.model.dao.infraestructura.EntidadDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de acceso a datos para Entidades</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 */
@Repository
public class EntidadDAOImpl extends BaseGenericDAOImpl<Entidad, Integer> implements EntidadDAO {
    public List<Entidad> obtenerEntidadPorUsuario(Integer usuario_id) {
        return (List<Entidad>) sessionFactory.getCurrentSession().createQuery(" select distinct est from Entidad as est join est.permisos as p where est.pasivo = false and p.usuariosByUsuarioId.id = :usuario_id order by est.nombre asc")
                .setParameter("usuario_id", usuario_id).list();
    }
}