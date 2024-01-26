package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.ParametroUsuarioService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.ParametroDAO;
import ni.gob.inss.barista.model.dao.seguridad.ParametroUsuarioDAO;
import ni.gob.inss.barista.model.dao.seguridad.UsuarioDAO;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import ni.gob.inss.barista.model.entity.seguridad.ParametroUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sergio López
 * @version 1.0
 * @since 2016-10-12
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class ParametrosUsuariosServiceImpl implements ParametroUsuarioService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final ParametroUsuarioDAO oParametroUsuarioDAO;
    private final UsuarioDAO oUsuarioDAO;
    private final ParametroDAO oParametroDAO;

    @Autowired
    public ParametrosUsuariosServiceImpl(ParametroUsuarioDAO oParametroUsuarioDAO, ParametroDAO oParametroDAO, UsuarioDAO oUsuarioDAO) {
        this.oParametroUsuarioDAO = oParametroUsuarioDAO;
        this.oUsuarioDAO = oUsuarioDAO;
        this.oParametroDAO = oParametroDAO;
    }

    /**
     * Este metodo busca un objeto mediante su identificador
     *
     * @param id parametro de busqueda
     * @return retorna un objeto de tipo parametro usuario
     * @throws EntityNotFoundException arroja esta excepción ocurrió un error en el paso de parametro de la busqueda
     */
    @Override
    @Transactional
    public ParametroUsuario obtenerParametroUsuarioPorId(Integer id) throws EntityNotFoundException {
        return oParametroUsuarioDAO.find(id);
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     * <p>
     * Este metodo guarda un registro completo
     *
     * @param oParametroUsuario parametro a guardar
     * @throws BusinessException arroja esta excepción cuando ocurre un problema al guardar
     * @throws DAOException      arroja esta excepción cuando ocurre un problema al guardar
     */
    @Override
    @Transactional
    public void guardar(ParametroUsuario oParametroUsuario) throws BusinessException, DAOException {
        validarGuardar(oParametroUsuario);
        oParametroUsuarioDAO.save(oParametroUsuario);
    }

    /**
     * Este metodo actualiza un registro especifico
     *
     * @param oParametroUsuario parametro a actualizar
     * @throws BusinessException arroja esta excepción cuando ocurre un problema al actualizar
     * @throws DAOException      arroja esta excepción cuando ocurre un problema al actualizar
     */
    @Override
    @Transactional
    public void actualizar(ParametroUsuario oParametroUsuario) throws BusinessException, DAOException {
        validarGuardar(oParametroUsuario);
        oParametroUsuarioDAO.update(oParametroUsuario);
    }

    /**
     * Este metodo elimina un registro especifico
     *
     * @param oParametroUsuario parametro a eliminar
     * @throws BusinessException arroja esta excepción cuando ocurre un problema al eliminar
     * @throws DAOException      arroja esta excepción cuando ocurre un problema al eliminar
     */
    @Override
    @Transactional
    public void eliminar(ParametroUsuario oParametroUsuario) throws BusinessException, DAOException {
        oParametroUsuarioDAO.remove(oParametroUsuario);
    }

    /**
     * Este metodo obtiene todos los elementos que están almacenados en la tabla parametro usuarios
     *
     * @param prBusqueda parametro de busqueda
     * @return retorna una lista de tipo parametro usuario
     */
    @Override
    @Transactional
    public List<ParametroUsuario> obtenerListadoParametroUsuario(String prBusqueda) {
        Search oSearch = new Search();
        if (!prBusqueda.equals("")) {
            oSearch.addFilterOr(
                    Filter.ilike("usuarioIdByUsuarioId.username", "%" + prBusqueda + "%"),
                    Filter.ilike("parametroIdByParametroId.descriptor", "%" + prBusqueda + "%")
            );
        }
        return oParametroUsuarioDAO.search(oSearch);
    }

    /**
     * Este metodo obtiene todos los usuarios pertenecientes al sistema
     *
     * @param prUserName parametro de busqueda
     * @return retorna una lista de tipo usuario
     */
    @Override
    @Transactional
    public List<Usuario> obtenerListaUsuarios(String prUserName) {
        Search oSearch = new Search();
        if (!prUserName.equals("")) {
            oSearch.addFilterOr(
                    Filter.ilike("username", "%" + prUserName + "%"),
                    Filter.ilike("nombres", "%" + prUserName + "%"),
                    Filter.ilike("apellidos", "%" + prUserName + "%")
            );
        }
        return oUsuarioDAO.search(oSearch);
    }

    /**
     * Este metodo obtiene una lista de parametros
     *
     * @param prParametro parametro de busqueda
     * @return retorna una lista de tipo Parametro
     */
    @Override
    @Transactional
    public List<Parametro> obtenerListaParametros(String prParametro) {
        Search oSearch = new Search();
        if (!prParametro.equals("")) {
            oSearch.addFilterILike("descriptor", "%" + prParametro + "%");
        }
        return oParametroDAO.search(oSearch);
    }

    /**
     * Este metodo se encarga de verificar que no se guarden datos repetidos
     *
     * @param oParametroUsuario parametros a buscar
     */
    public void validarGuardar(ParametroUsuario oParametroUsuario) throws BusinessException {
        Search oSearch = new Search();
        oSearch.addFilterAnd(
                Filter.equal("usuarioIdByUsuarioId.id", oParametroUsuario.getUsuarioIdByUsuarioId().getId()),
                Filter.equal("parametroIdByParametroId.id", oParametroUsuario.getParametroIdByParametroId().getId())
        );
        List<ParametroUsuario> listaDatosRepetidos = oParametroUsuarioDAO.search(oSearch);
        if (listaDatosRepetidos.size() > 0) {
            throw new BusinessException("Ya existe este usuario y este parámetro");
        }
    }
}