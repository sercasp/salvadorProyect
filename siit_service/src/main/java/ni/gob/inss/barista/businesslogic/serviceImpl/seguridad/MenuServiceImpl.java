package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.MenuService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.MenuDAO;
import ni.gob.inss.barista.model.entity.seguridad.Menu;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Lógica de Negocio de la Pantalla de Menú</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 05/06/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final MenuDAO oMenuDAO;

    @Autowired
    public MenuServiceImpl(MenuDAO oMenuDAO) {
        this.oMenuDAO = oMenuDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Menu obtener(int id) throws EntityNotFoundException {
        return oMenuDAO.find(id);
    }

    @Transactional
    @Override
    public Menu obtenerMenuPorId(int id) throws EntityNotFoundException {
        return oMenuDAO.find(id);
    }

    @Transactional
    @Override
    public void agregar(Menu oMenu) throws BusinessException, DAOException {
        validar_guardar(oMenu);
        oMenuDAO.save(oMenu);
    }

    @Transactional
    @Override
    public void actualizar(Menu oMenu) throws BusinessException, DAOException {
        validar_actualizar(oMenu);
        oMenuDAO.update(oMenu);
    }

    @Transactional
    @Override
    public void eliminar(Menu oMenu) throws BusinessException, DAOException {
        validar_eliminar(oMenu);
        oMenuDAO.remove(oMenu);
    }

    @Transactional
    public List<Modulo> obtenerModulos() {
        return oMenuDAO.obtenerModulos();
    }

    @Transactional
    public List obtenerMenuPorModuloTree() {
        return oMenuDAO.obtenerMenuPorModuloTree();
    }

    @Transactional
    public List obtenerModuloTree() {
        return oMenuDAO.obtenerModulosTree();
    }

    @Transactional
    public List obtenerMenuPorModuloTree(int moduloId) {
        return oMenuDAO.obtenerMenuPorModuloTree(moduloId);
    }

    /**
     * Ver más de este metodo {@link MenuDAO#verificarMenuEnRolMenu(Integer)}
     * @param prIdMenu parametro de busqueda
     */
    @Override
    @Transactional
    public List<Menu> verificarMenuEnRolMenu(Integer prIdMenu) {
        return oMenuDAO.verificarMenuEnRolMenu(prIdMenu);
    }

    public void validar_eliminar(Menu oMenu) throws BusinessException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("menusByMenuId", oMenu);
        List<Menu> lista = oMenuDAO.search(oSearch);
        //Validando que el código no este registrado
        if (lista.size() > 0) {
            throw new BusinessException("Este es Menú es Padre, no puede eliminarse");
        }
    }

    public void validar_actualizar(Menu oMenu) throws BusinessException {
        if (oMenu.getId() != null) {
            //Validando si se esta editando el registro
            if (oMenu.getMenusByMenuId() != null) {
                if (oMenu.getId().equals(oMenu.getMenusByMenuId().getId())) {
                    throw new BusinessException("El padre de un menú ítem no puede ser él mismo");
                }
            }
            if (oMenu.getOrden() != 0) {
                List<Menu> datoRepetido = oMenuDAO.obtenerRepetidos(oMenu.getOrden());
                //Validando que el código no este registrado
                if (datoRepetido.size() > 0) {
                    if (oMenu.getMenusByMenuId() != null) {
                        List<Menu> dato = oMenuDAO.obtenerRepetido(oMenu.getId(), oMenu.getOrden(), oMenu.getModulosByModuloId().getId(), oMenu.getMenusByMenuId().getId());
                        if (dato.size() != 0) {
                            throw new BusinessException("Este orden ya existe");
                        }
                    } else {
                        List<Menu> dato = oMenuDAO.obtenerRepetido2(oMenu.getId(), oMenu.getOrden(), oMenu.getModulosByModuloId().getId());
                        if (dato.size() != 0) {
                            throw new BusinessException("Este orden ya existe");
                        }
                    }
                }
            }
        }
    }

    public void validar_guardar(Menu oMenu) throws BusinessException {
        List<Menu> datoRepetido = oMenuDAO.obtenerRepetidover(oMenu.getOrden(), oMenu.getModulosByModuloId().getId(), oMenu.getMenusByMenuId().getId());
        //Validando que el código no este registrado
        if (datoRepetido.size() > 0) {
            throw new BusinessException("Este orden ya existe");
        }
    }

    @Transactional
    @Override
    public String obtenerBreadcrum(Integer menuId) {
        return oMenuDAO.obtenerBreadcrum(menuId);
    }
}