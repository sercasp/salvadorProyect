package ni.gob.inss.barista.businesslogic.service.seguridad;


import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.serviceImpl.seguridad.ModuloServiceImpl;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de servicio para modulo</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.1, 5/29/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface ModuloService {
    Modulo obtenerModuloPorId(int id) throws EntityNotFoundException;

    Modulo obtener(int id) throws EntityNotFoundException;

    void agregar(Modulo oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Modulo oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(Modulo oTipoCatalogo) throws BusinessException, DAOException;

    List<Modulo> buscar(String prBuscar);

    List<Modulo> obtenerModulos();

    /**
     * Ver más de este metodo {@link ModuloServiceImpl#verificarModuloEnReporte(Integer)}
     *
     * @param prIdModulo parametro de busqueda
     * @return retorna una lista de tipo modulo
     */
    List<Modulo> verificarModuloEnReporte(Integer prIdModulo);

    /**
     * Ver más de este metodo {@link ModuloServiceImpl#verificarModuloEnReporte(Integer)}
     *
     * @param prIdModulo parametro de busqueda
     * @return retorna una lista de tipo modulo
     */
    List<Modulo> verificarModuloEnParametroEntidad(Integer prIdModulo);

    /**
     * Ver más de este metodo {@link ModuloServiceImpl#verificarModuloEnReporte(Integer)}
     *
     * @param prIdModulo parametro de busqueda
     * @return retorna una lista de tipo modulo
     */
    List<Modulo> verificarModuloEnSesionModulo(Integer prIdModulo);
}