package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.UsuarioService;
import ni.gob.inss.barista.businesslogic.serviceImpl.core.seguridad.SecurityUtils;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.infraestructura.EntidadDAO;
import ni.gob.inss.barista.model.dao.seguridad.PermisoDAO;
import ni.gob.inss.barista.model.dao.seguridad.RoleDAO;
import ni.gob.inss.barista.model.dao.seguridad.UsuarioDAO;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Permiso;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para Usuarios</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final UsuarioDAO oUsuarioDAO;
    private final PermisoDAO oPermisoDAO;
    private final RoleDAO oRoleDAO;
    private final SecurityUtils oSecurityUtils;
    private final EntidadDAO oEntidadDAO;

    @Autowired
    public UsuarioServiceImpl(UsuarioDAO oUsuarioDAO, PermisoDAO oPermisoDAO, RoleDAO oRoleDAO, SecurityUtils oSecurityUtils, EntidadDAO oEntidadDAO) {
        this.oUsuarioDAO = oUsuarioDAO;
        this.oPermisoDAO = oPermisoDAO;
        this.oRoleDAO = oRoleDAO;
        this.oSecurityUtils = oSecurityUtils;
        this.oEntidadDAO = oEntidadDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Usuario obtenerUsuarioPorId(int id) throws EntityNotFoundException {
        return oUsuarioDAO.find(id);
    }

    @Transactional
    public Usuario obtenerUsuarioPorUserName(String userName) throws BusinessException {
        Usuario usuario = oUsuarioDAO.buscarPorUsuario(userName);
        if (usuario == null) {
            throw new BusinessException("El usuario " + userName + " no ha sido registrado");
        }
        return usuario;
    }

    @Transactional
    @Override
    public void agregar(Usuario oUsuario) throws BusinessException, DAOException {

        Usuario oUsuarioExistente = oUsuarioDAO.buscarPorUsuario(oUsuario.getUsername());
        if (oUsuarioExistente != null) {
            throw new BusinessException("El username ya existe: " + oUsuarioExistente.obtenerUsernameNombreCompleto());
        }
        oUsuario.setNombres(oUsuario.getNombres().toUpperCase().trim());
        oUsuario.setApellidos(oUsuario.getApellidos().toUpperCase().trim());
        if (oUsuario.getReferencia() != null) {
            oUsuario.setReferencia(oUsuario.getReferencia().toUpperCase().trim());
        }
        oUsuarioDAO.save(oUsuario);
    }

    @Transactional
    @Override
    public void agregarList(List<Usuario> oListUsuario, Map<String, Object> permisos) throws BusinessException, DAOException {
        int index = -1;
        try {
            for (Usuario oUsuario : oListUsuario) {
                Usuario oUsuarioExistente = oUsuarioDAO.buscarPorUsuario(oUsuario.getUsername());
                if (oUsuarioExistente != null) {
                    throw new BusinessException("El username ya existe: " + oUsuarioExistente.obtenerUsernameNombreCompleto());
                }
                oUsuario.setNombres(oUsuario.getNombres().toUpperCase().trim());
                oUsuario.setApellidos(oUsuario.getApellidos().toUpperCase().trim());
                if (oUsuario.getReferencia() != null) {
                    oUsuario.setReferencia(oUsuario.getReferencia().toUpperCase().trim());
                }
                oUsuarioDAO.save(oUsuario);
                if (!permisos.get(oUsuario.getUsername()).toString().trim().equals("")) {
                    String[] entidadRol = permisos.get(oUsuario.getUsername()).toString().replace("[", "").split("]");
                    for (String value : entidadRol) {
                        Entidad oEntidad = oEntidadDAO.find(Integer.parseInt(value.split("=>")[0].toString()));
                        String[] roles = value.split("=>")[1].split("-");
                        for (String rol : roles) {
                            Permiso oPermiso = new Permiso();
                            oPermiso.setEntidad(oEntidad);
                            oPermiso.setUsuariosByUsuarioId(oUsuario);
                            oPermiso.setRolesByRolId(oRoleDAO.find(Integer.parseInt(rol)));
                            oPermiso.setCreadoEl(oUsuario.getCreadoEl());
                            oPermiso.setCreadoEnIp(oUsuario.getCreadoEnIp());
                            oPermiso.setCreadoEnOrdenador(oUsuario.getCreadoEnOrdenador());
                            oPermiso.setCreadoPor(oUsuario.getCreadoPor());
                            oPermisoDAO.save(oPermiso);
                        }
                    }
                }
                index++;
            }
        } catch (Exception e) {
            Search oSearch;
            for (int i = 0; i <= index; i++) {
                oSearch = new Search();
                oSearch.addFilterEqual("usuariosByUsuarioId.username", oListUsuario.get(i).getUsername());
                List<Permiso> listPermiso = oPermisoDAO.search(oSearch);
                for (Permiso oPermiso : listPermiso) {
                    oPermisoDAO.remove(oPermiso);
                }
                oUsuarioDAO.removeId(oListUsuario.get(i).getId());
            }
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void actualizar(Usuario oUsuario) throws BusinessException, DAOException {
        oUsuario.setNombres(oUsuario.getNombres().toUpperCase().trim());
        oUsuario.setApellidos(oUsuario.getApellidos().toUpperCase().trim());
        if (oUsuario.getReferencia() != null) {
            oUsuario.setReferencia(oUsuario.getReferencia().toUpperCase().trim());
        }
        oUsuarioDAO.update(oUsuario);
    }

    @Transactional
    @Override
    public void eliminar(Usuario oUsuario) {
        oUsuarioDAO.remove(oUsuario);
    }

    @Transactional
    @Override
    public List<Usuario> buscar(String prBuscar) {

        Search oSearch = new Search();
        oSearch.addFilterOr(
                Filter.ilike("username", "%" + prBuscar + "%"),
                Filter.ilike("nombres", "%" + prBuscar + "%"),
                Filter.ilike("apellidos", "%" + prBuscar + "%"),
                Filter.ilike("referencia", "%" + prBuscar + "%")
        );
        oSearch.addSort("username", false);
        return oUsuarioDAO.search(oSearch);
        //        return oUsuarioDAO.fullTextSearch(prBuscar, "username", "nombres", "apellidos", "referencia");
    }

    /**
     * Inicia sesión en la aplicación
     */
    @Transactional
    public Usuario login(String username, String password) {
        Usuario oUsuario = this.oUsuarioDAO.buscarPorUsuario(username);

        if (oUsuario != null) {
            if (!oSecurityUtils.checkpw(password, oUsuario.getPassword()))
                oUsuario = null;
        }
        return oUsuario;
    }

    @Transactional
    public List obtenerUsuariosPorMenu(int menuId) {
        return oUsuarioDAO.obtenerUsuariosPorMenu(menuId);
    }

    @Transactional
    @Override
    public List<Map<String, Object>> listaEntidades(Usuario oUsuario) {
        return oUsuarioDAO.obtenerEntidadesRoles(oUsuario);
    }

    @Transactional
    @Override
    public List<Entidad> listaEntidadesSinAgregar(Usuario oUsuario) {
        return oUsuarioDAO.obtenerEntidadesSinAgregar(oUsuario);
    }

    @Transactional
    @Override
    public List<Map<String, Object>> listaRoles(Usuario oUsuario, Entidad oEntidad) {
        return oUsuarioDAO.obtenerRoles(oUsuario, oEntidad);
    }

    @Transactional
    @Override
    public void agregarRol(Permiso oPermiso) throws DAOException {
        oPermisoDAO.saveUpper(oPermiso);
    }

    @Transactional
    @Override
    public void quitarRol(Usuario oUsuario, Permiso oPermiso, Entidad oEntidad) throws BusinessException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("id", oPermiso.getId());
        List<Permiso> listaPermisos = oPermisoDAO.search(oSearch);
        if (listaPermisos.size() == 0) {
            throw new BusinessException("Permiso no encontrado");
        }
        oPermisoDAO.remove(listaPermisos.get(0));
    }

    @Transactional
    @Override
    public List<Role> listaAgregarRoles(Usuario oUsuario, Entidad oEntidad) {
        return oUsuarioDAO.obtenerAgregarRoles(oUsuario, oEntidad);
    }

    @Transactional
    @Override
    public void quitarTodosRoles(Usuario oUsuario) throws BusinessException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("usuariosByUsuarioId", oUsuario);
        List<Permiso> listaPermisos = oPermisoDAO.search(oSearch);

        for (Permiso oPermiso : listaPermisos) {
            oPermisoDAO.remove(oPermiso);
        }
    }

    @Transactional
    @Override
    public List<Permiso> extraerEntidad(int usuarioId, int entidadId) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("usuariosByUsuarioId.id", usuarioId);
        oSearch.addFilterEqual("entidad.id", entidadId);
        return oPermisoDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<Role> obtenerUsuarioFiltrado(String nombre) {
        Search oSearch = new Search();
        oSearch.addFilterILike("nombre", "%" + nombre + "%");
        oSearch.addSortAsc("nombre");
        return oRoleDAO.search(oSearch);
    }

    /**
     * Ver más de este metodo {@link UsuarioDAO#verificarPermisosUsuario(Integer)}
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    @Transactional
    public List<Usuario> verificarPermisosUsuario(Integer prIdUsuario) {
        return oUsuarioDAO.verificarPermisosUsuario(prIdUsuario);
    }

    /**
     * Ver más de este metodo {@link UsuarioDAO#verificarParametroUsuario(Integer)}
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    @Transactional
    public List<Usuario> verificarParametroUsuario(Integer prIdUsuario) {
        return oUsuarioDAO.verificarParametroUsuario(prIdUsuario);
    }

    /**
     * Ver más de este metodo {@link UsuarioDAO#verificarSesionUsuario(Integer)}
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    @Transactional
    public List<Usuario> verificarSesionUsuario(Integer prIdUsuario) {
        return oUsuarioDAO.verificarSesionUsuario(prIdUsuario);
    }

    /**
     * Ver más de este metodo {@link UsuarioDAO#verificarSesionUsuarioModulo(Integer)}
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    @Transactional
    public List<Usuario> verificarSesionUsuarioModulo(Integer prIdUsuario) {
        return oUsuarioDAO.verificarSesionUsuarioModulo(prIdUsuario);
    }

    /**
     * Ver más de este metodo {@link UsuarioDAO#verificarNavegacionUsuario(Integer)}
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    @Transactional
    public List<Usuario> verificarNavegacionUsuario(Integer prIdUsuario) {
        return oUsuarioDAO.verificarNavegacionUsuario(prIdUsuario);
    }

    /**
     * Ver más de este metodo {@link UsuarioDAO#verificarRecursoUsuario(Integer)}
     *
     * @param prIdUsuario parametro de busqueda
     */
    @Override
    @Transactional
    public List<Usuario> verificarRecursoUsuario(Integer prIdUsuario) {
        return oUsuarioDAO.verificarRecursoUsuario(prIdUsuario);
    }
}