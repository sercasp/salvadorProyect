package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.entity.seguridad.Menu;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.RoleMenu;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Establecimientos</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 16/07/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
public interface RoleMenuService {
    void guardar(RoleMenu oRole, Menu prIdMenu, Role prIdRol) throws DAOException, BusinessException;

    void borrar(RoleMenu oRole);

    void quitarMenu(Menu oMenu, Role oRole) throws BusinessException;
}