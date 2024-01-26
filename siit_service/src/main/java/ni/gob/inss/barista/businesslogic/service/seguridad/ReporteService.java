package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.serviceImpl.seguridad.ReporteServiceImpl;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.daoImpl.seguridad.ReporteDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Reporte;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;
import java.util.Map;

/**
 * @author jjrivera
 * @version 1.0
 * @since 5/10/2016
 */
public interface ReporteService {
    /**
     * {@link ReporteServiceImpl#guardar(Reporte)}
     */
    void guardar(Reporte oReporte) throws BusinessException, DAOException;

    /**
     * {@link ReporteDAOImpl#obtenerModulos()}
     */
    List<Map<String, Object>> obtenerModulos();

    /**
     * {@link ReporteDAOImpl#obtenerReportes()}
     */
    List<Map<String, Object>> obtenerReportes();

    /**
     * {@link ReporteServiceImpl#obtenerReportePorId(Integer)}
     */
    Reporte obtenerReportePorId(Integer id) throws EntityNotFoundException;

    /**
     * {@link ReporteServiceImpl#actualizar(Reporte)}
     */
    void actualizar(Reporte oReporte) throws BusinessException, DAOException;

    /**
     * {@link ReporteServiceImpl#eliminar(Reporte)}
     */
    void eliminar(Reporte oReporte) throws BusinessException, DAOException;

    /**
     * {@link ReporteDAOImpl#obtenerUsuariosPorReporte(Integer)}
     */
    List<Usuario> obtenerUsuariosPorReporte(Integer reporteId);

    /**
     * {@link ReporteDAOImpl#obtenerRolesPorReporte(Integer)}
     */
    List obtenerRolesPorReporte(Integer prReporteId);
}