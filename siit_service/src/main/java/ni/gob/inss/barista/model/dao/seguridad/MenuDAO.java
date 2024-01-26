package ni.gob.inss.barista.model.dao.seguridad;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.daoImpl.seguridad.MenuDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Menu;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Menús</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface MenuDAO extends BaseGenericDAO<Menu, Integer> {
    List obtenerMenuPorUsuarioModulo(Integer usuarioId, Integer entidadId, Integer moduloId);

    List obtenerMenuPorUsuarioModuloMobile(Integer usuarioId, Integer entidadId, Integer moduloId);

    Menu buscarPorMenu(String Menu);

    List<Modulo> obtenerModulos();

    List<Recurso> obtenerRecursos();

    List obtenerMenuPorModuloTree();

    List obtenerModulosTree();

    List obtenerMenuPorModuloTree(Integer moduloId);

    List<Menu> obtenerRepetido(Integer prId, Integer prOrden, Integer prMod, Integer prMenuId);

    List<Menu> obtenerRepetido2(Integer prId, Integer prOrden, Integer prMod);

    List<Menu> obtenerRepetidover(Integer prOrden, Integer prMod, Integer prMenuId);

    List<Menu> obtenerRepetidos(Integer prOrden);

    /**
     * Ver más de este metodo en {@link MenuDAOImpl#verificarMenuEnRolMenu(Integer)}
     *
     * @param prIdMenu parametro de busqueda
     * @return retorna una lista tipo menu
     */
    List<Menu> verificarMenuEnRolMenu(Integer prIdMenu);

    String obtenerBreadcrum(Integer menuId);
}