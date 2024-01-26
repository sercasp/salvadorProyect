package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.RoleReporteService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.seguridad.RoleReporteDAO;
import ni.gob.inss.barista.model.entity.seguridad.Reporte;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.RoleReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2016 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para Roles Reportes</br>
 *
 * @author HERNALDO JOSÉ MAYORGA HERNÁNDEZ
 * @version 1.0, 07/10/2016
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
public class RoleReporteServiceImpl implements RoleReporteService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final RoleReporteDAO oRoleReporteDAO;

    @Autowired
    public RoleReporteServiceImpl(RoleReporteDAO oRoleReporteDAO) {
        this.oRoleReporteDAO = oRoleReporteDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public void guardar(RoleReporte oRoleReporte) throws DAOException {
        this.oRoleReporteDAO.saveUpper(oRoleReporte);
    }

    @Transactional
    public void borrar(RoleReporte oRoleReporte) {
        this.oRoleReporteDAO.remove(oRoleReporte);
    }

    @Transactional
    public void quitarReporte(Reporte oReporte, Role oRole) throws BusinessException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("roleByRoleId", oRole);
        oSearch.addFilterEqual("reporteByReporteId", oReporte);
        List<RoleReporte> listaReportes = oRoleReporteDAO.search(oSearch);
        if (listaReportes.size() == 0) {
            throw new BusinessException("Reporte no encontrado");
        }
        oRoleReporteDAO.remove(listaReportes.get(0));
    }
}