package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.RoleAutorizacionService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.seguridad.RoleAutorizacionDAO;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.RoleAutorizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación del Servicio para la tabla de Role Autorización</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 25/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
public class RoleAutorizacionServiceImpl implements RoleAutorizacionService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final RoleAutorizacionDAO oRoleAutorizacionDAO;

    @Autowired
    public RoleAutorizacionServiceImpl(RoleAutorizacionDAO oRoleAutorizacionDAO) {
        this.oRoleAutorizacionDAO = oRoleAutorizacionDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public void guardar(RoleAutorizacion oRoleAutorizacion) throws DAOException {
        this.oRoleAutorizacionDAO.saveUpper(oRoleAutorizacion);
    }

    @Transactional
    public void borrar(RoleAutorizacion oRoleAutorizacion) {
        this.oRoleAutorizacionDAO.remove(oRoleAutorizacion);
    }

    @Transactional
    public void quitarAutorizacion(Autorizacion oAutorizacion, Role oRole) throws BusinessException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("rolesByRolId", oRole);
        oSearch.addFilterEqual("autorizacionesByAutorizacionId", oAutorizacion);
        List<RoleAutorizacion> listaAutorizaciones = oRoleAutorizacionDAO.search(oSearch);
        if (listaAutorizaciones.size() == 0) {
            throw new BusinessException("Autorización no encontrado");
        }
        oRoleAutorizacionDAO.remove(listaAutorizaciones.get(0));
    }
}