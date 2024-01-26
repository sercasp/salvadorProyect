package ni.gob.inss.barista.model.dao.seguridad;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.daoImpl.seguridad.ModuloDAOImpl;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Módulos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface ModuloDAO extends BaseGenericDAO<Modulo, Integer> {
    List<java.util.Map<String, Object>> obtenerModulosPorUsuario(Integer usuarioId, Integer entidadId, String nombreModulo, String orderBy);

    List<java.util.Map<String, Object>> obtenerModulosPorUsuarioMobile(Integer usuarioId, Integer entidadId, String nombreModulo, String orderBy);

    List<Modulo> obtenerModulos();

    /**
     * Ver más de este metodo {@link ModuloDAOImpl#verificarModuloEnReporte(Integer)}
     *
     * @param prIdModulo parametro de busqueda
     * @return retorna una lista de tipo modulo
     */
    List<Modulo> verificarModuloEnReporte(Integer prIdModulo);

    /**
     * Ver más de este metodo {@link ModuloDAOImpl#verificarModuloEnParametroEntidad(Integer)}
     *
     * @param prIdModulo parametro de busqueda
     * @return retorna una lista de tipo modulo
     */
    List<Modulo> verificarModuloEnParametroEntidad(Integer prIdModulo);

    /**
     * Ver más de este metodo {@link ModuloDAOImpl#verificarModuloEnSesionModulo(Integer)}
     *
     * @param prIdModulo parametro de busqueda
     * @return retorna una lista de tipo modulo
     */
    List<Modulo> verificarModuloEnSesionModulo(Integer prIdModulo);

    Modulo obtenerPorId(Integer integer) throws EntityNotFoundException;
}