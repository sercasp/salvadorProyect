package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.serviceImpl.seguridad.ParametrosUsuariosServiceImpl;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import ni.gob.inss.barista.model.entity.seguridad.ParametroUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;

/**
 * @author Sergio López
 * @version 1.0
 * @since 2016-10-12
 * Modificación de jvillanueva el 12/07/2023
 */
public interface ParametroUsuarioService {
    /**
     * Ver más de este metodo en {@link ParametrosUsuariosServiceImpl#obtenerParametroUsuarioPorId(Integer)}
     *
     * @param id parametro de busqueda
     * @return retorna un objeto de tipo parametro usuario
     * @throws EntityNotFoundException arroja esta excepción ocurrió un error en el paso de parametro de la busqueda
     */
    ParametroUsuario obtenerParametroUsuarioPorId(Integer id) throws EntityNotFoundException;

    /**
     * Ver más de este metodo en {@link ParametrosUsuariosServiceImpl#guardar(ParametroUsuario)}
     *
     * @param oParametroUsuario parametro a guardar
     * @throws BusinessException arroja esta excepción cuando ocurre un problema al guardar
     * @throws DAOException      arroja esta excepción cuando ocurre un problema al guardar
     */
    void guardar(ParametroUsuario oParametroUsuario) throws BusinessException, DAOException;

    /**
     * Ver más de este metodo en {@link ParametrosUsuariosServiceImpl#guardar(ParametroUsuario)}
     *
     * @param oParametroUsuario parametro a actualizar
     * @throws BusinessException arroja esta excepción cuando ocurre un problema al actualizar
     * @throws DAOException      arroja esta excepción cuando ocurre un problema al actualizar
     */
    void actualizar(ParametroUsuario oParametroUsuario) throws BusinessException, DAOException;

    /**
     * Ver más de este metodo en {@link ParametrosUsuariosServiceImpl#guardar(ParametroUsuario)}
     *
     * @param oParametroUsuario parametro a eliminar
     * @throws BusinessException arroja esta excepción cuando ocurre un problema al eliminar
     * @throws DAOException      arroja esta excepción cuando ocurre un problema al eliminar
     */
    void eliminar(ParametroUsuario oParametroUsuario) throws BusinessException, DAOException;

    /**
     * Ver más de este metodo en {@link ParametrosUsuariosServiceImpl#obtenerListadoParametroUsuario(String)}
     *
     * @param prBusqueda parametro de busqueda
     * @return retorna una lista de tipo parametro usuario
     */
    List<ParametroUsuario> obtenerListadoParametroUsuario(String prBusqueda);

    /**
     * Ver más de este metodo en {@link ParametrosUsuariosServiceImpl#obtenerListaUsuarios(String)}
     *
     * @param prUserName parametro de busqueda
     * @return retorna una lista de tipo usuario
     */
    List<Usuario> obtenerListaUsuarios(String prUserName);

    /**
     * Ver más de este metodo en {@link ParametrosUsuariosServiceImpl#obtenerListaParametros(String)}
     *
     * @param prParametro parametro de busqueda
     * @return retorna una lista de tipo parametro Parametro
     */
    List<Parametro> obtenerListaParametros(String prParametro);
}