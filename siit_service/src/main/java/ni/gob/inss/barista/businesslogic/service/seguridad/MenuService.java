package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.serviceImpl.seguridad.MenuServiceImpl;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.seguridad.Menu;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Logica de Negocio para la pantalla de Menú</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 05/06/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface MenuService {
    Menu obtener(int id) throws EntityNotFoundException;

    Menu obtenerMenuPorId(int id) throws EntityNotFoundException;

    //public Menu obtenerMenuPorId2(int id) throws EntityNotFoundException;

    void agregar(Menu oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Menu oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(Menu oTipoCatalogo) throws BusinessException, DAOException;

    List<Modulo> obtenerModulos();

    List obtenerMenuPorModuloTree();

    List obtenerModuloTree();

    List obtenerMenuPorModuloTree(int moduloId);

    /**
     * Ver más de este metodo en {@link MenuServiceImpl#verificarMenuEnRolMenu(Integer)}
     *
     * @param prIdMenu parametro de busqueda
     * @return retorna una lista tipo menu
     * Modificación de jvillanueva el 12/07/2023
     */
    List<Menu> verificarMenuEnRolMenu(Integer prIdMenu);

    String obtenerBreadcrum(Integer menuId);
}