package ni.gob.inss.barista.model.dao.seguridad;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.daoImpl.seguridad.ReporteDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Reporte;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;
import java.util.Map;

/**
 * @author jjrivera
 * @version 1.0
 * @since 5/10/2016
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface ReporteDAO extends BaseGenericDAO<Reporte, Integer> {
    /**
     * {@link ReporteDAOImpl#obtenerModulos()}
     */
    List<Map<String, Object>> obtenerModulos();

    /**
     * {@link ReporteDAOImpl#obtenerReportes()}
     */
    List<Map<String, Object>> obtenerReportes();

    /**
     * {@link ReporteDAOImpl#obtenerUsuariosPorReporte(Integer)}
     */
    List<Usuario> obtenerUsuariosPorReporte(Integer prReporteId);

    /**
     * {@link ReporteDAOImpl#obtenerRolesPorReporte(Integer)}
     */
    List obtenerRolesPorReporte(Integer prReporteId);

    List<Reporte> verificarRolReporte(Integer prIdReporte);

    List<Reporte> obtenerRepetidos(Integer prOrden);

    List<Reporte> obtenerRepetido(Integer prId, Integer prOrden);
}