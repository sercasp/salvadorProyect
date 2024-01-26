package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.ReporteService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.ReporteDAO;
import ni.gob.inss.barista.model.daoImpl.seguridad.ReporteDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Reporte;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author jjrivera
 * @version 1.0
 * @since 5/10/2016
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
public class ReporteServiceImpl implements ReporteService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final ReporteDAO oReporteDAO;

    @Autowired
    public ReporteServiceImpl(ReporteDAO oReporteDAO) {
        this.oReporteDAO = oReporteDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     * Guarda un objeto de tipo Reporte
     *
     * @param oReporte el objeto Reporte a guardar
     */
    @Transactional
    @Override
    public void guardar(Reporte oReporte) throws BusinessException, DAOException {
        validar_guardar(oReporte);
        oReporteDAO.saveUpper(oReporte);
    }

    /**
     * {@link ReporteDAOImpl#obtenerModulos()}
     */
    @Transactional
    @Override
    public List<Map<String, Object>> obtenerModulos() {
        return oReporteDAO.obtenerModulos();
    }

    /**
     * {@link ReporteDAOImpl#obtenerReportes()}
     */
    @Transactional
    @Override
    public List<Map<String, Object>> obtenerReportes() {
        return oReporteDAO.obtenerReportes();
    }

    /**
     * Busca el reporte seleccionado en el arbol
     *
     * @param id el id del reporte
     * @return Devuelve el Objeto de tipo reporte encontrado
     */
    @Transactional
    @Override
    public Reporte obtenerReportePorId(Integer id) throws EntityNotFoundException {
        return oReporteDAO.find(id);
    }

    /**
     * Actualiza un reporte
     *
     * @param oReporte el objeto de tipo Reporte a actualizar
     */
    @Transactional
    @Override
    public void actualizar(Reporte oReporte) throws BusinessException, DAOException {
        validar_actualizar(oReporte);
        oReporteDAO.update(oReporte);
    }

    /**
     * Elimina un reporte
     *
     * @param oReporte el Objeto de tipo Reporte a eliminar
     */
    @Transactional
    @Override
    public void eliminar(Reporte oReporte) throws BusinessException, DAOException {
        if (validarEliminar(oReporte)) {
            throw new BusinessException("No puede eliminar este reporte, ya que está siendo utilizado");
        }
        oReporteDAO.remove(oReporte);
    }

    /**
     * {@link ReporteDAOImpl#obtenerUsuariosPorReporte(Integer)}
     */
    @Transactional
    @Override
    public List<Usuario> obtenerUsuariosPorReporte(Integer reporteId) {
        return oReporteDAO.obtenerUsuariosPorReporte(reporteId);
    }

    /**
     * {@link ReporteDAOImpl#obtenerRolesPorReporte(Integer)}
     */
    @Transactional
    @Override
    public List obtenerRolesPorReporte(Integer prReporteId) {
        return oReporteDAO.obtenerRolesPorReporte(prReporteId);
    }

    private Boolean validarEliminar(Reporte oReporte) {
        List<Reporte> listaRolesReportes = oReporteDAO.verificarRolReporte(oReporte.getId());
        return listaRolesReportes.size() > 0;
    }

    public void validar_guardar(Reporte oReporte) throws BusinessException {
        List<Reporte> datoRepetido = oReporteDAO.obtenerRepetidos(oReporte.getOrden());
        //Validando que el código no este registrado
        if (datoRepetido.size() > 0) {
            throw new BusinessException("Este orden ya existe");
        }
    }

    public void validar_actualizar(Reporte oReporte) throws BusinessException {
        if (oReporte.getId() != null) {

            if (oReporte.getOrden() != 0) {
                List<Reporte> datoRepetido = oReporteDAO.obtenerRepetidos(oReporte.getOrden());
                //Validando que el código no este registrado
                if (datoRepetido.size() > 0) {
                    List<Reporte> dato = oReporteDAO.obtenerRepetido(oReporte.getId(), oReporte.getOrden());
                    if (dato.size() != 1) {
                        throw new BusinessException("Este orden ya existe");
                    }
                }
            }
        }
    }
}