package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.RoleMenuService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.seguridad.RoleMenuDAO;
import ni.gob.inss.barista.model.entity.seguridad.Menu;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Establecimientos</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 16/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final RoleMenuDAO oRoleMenuDAO;

    @Autowired
    public RoleMenuServiceImpl(RoleMenuDAO oRoleMenuDAO) {
        this.oRoleMenuDAO = oRoleMenuDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public void guardar(RoleMenu oRoleMenu, Menu prIdMenu, Role prIdRol) throws DAOException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("rolesByRolId", prIdRol);
        oSearch.addFilterEqual("menusByMenuId", prIdMenu);
        List<RoleMenu> listaMenus = oRoleMenuDAO.search(oSearch);
        if (listaMenus.size() == 0) {
            this.oRoleMenuDAO.saveUpper(oRoleMenu);
        }
    }

    @Transactional
    public void borrar(RoleMenu oRoleMenu) {
        this.oRoleMenuDAO.remove(oRoleMenu);
    }

    @Transactional
    public void quitarMenu(Menu oMenu, Role oRole) throws BusinessException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("rolesByRolId", oRole);
        oSearch.addFilterEqual("menusByMenuId", oMenu);
        List<RoleMenu> listaMenus = oRoleMenuDAO.search(oSearch);
        if (listaMenus.size() == 0) {
            throw new BusinessException("Menú no encontrado");
        }
        oRoleMenuDAO.remove(listaMenus.get(0));
    }
}