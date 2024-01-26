package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.entity.seguridad.Reporte;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.RoleReporte;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2016 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Roles Reportes</br>
 *
 * @author HERNALDO JOSÉ MAYORGA HERNÁNDEZ
 * @version 1.0, 10/07/2016
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 */

public interface RoleReporteService {
    void guardar(RoleReporte oRole) throws DAOException;

    void borrar(RoleReporte oRole);

    void quitarReporte(Reporte oReporte, Role oRole) throws BusinessException;
}