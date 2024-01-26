package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.RoleAutorizacion;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Capa de Servicio para la tabla de Role Autorización</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 25/07/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */
public interface RoleAutorizacionService {
    void guardar(RoleAutorizacion oRoleAutorizacion) throws DAOException;

    void borrar(RoleAutorizacion oRoleAutorizacion);

    void quitarAutorizacion(Autorizacion oAutorizacion, Role oRole) throws BusinessException;
}