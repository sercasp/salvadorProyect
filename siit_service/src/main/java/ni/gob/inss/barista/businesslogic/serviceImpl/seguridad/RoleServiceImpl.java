package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.RoleService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.*;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para Roles</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
public class RoleServiceImpl implements RoleService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final RoleDAO oRoleDAO;
    private final RoleMenuDAO oRoleMenuDAO;
    private final RoleReporteDAO oRoleReporteDAO;
    private final RoleAutorizacionDAO oRoleAutorizacionDAO;
    private final PermisoDAO oPermisoDAO;
    private final AutorizacionDAO oAutorizacionDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO oRoleDAO, RoleMenuDAO oRoleMenuDAO, RoleReporteDAO oRoleReporteDAO, RoleAutorizacionDAO oRoleAutorizacionDAO, PermisoDAO oPermisoDAO, AutorizacionDAO oAutorizacionDAO) {
        this.oRoleDAO = oRoleDAO;
        this.oRoleMenuDAO = oRoleMenuDAO;
        this.oRoleReporteDAO = oRoleReporteDAO;
        this.oRoleAutorizacionDAO = oRoleAutorizacionDAO;
        this.oPermisoDAO = oPermisoDAO;
        this.oAutorizacionDAO = oAutorizacionDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Role obtener(Integer id) throws EntityNotFoundException {
        return oRoleDAO.find(id);
    }

    @Transactional
    public void guardar(Role oRole) throws DAOException {
        this.oRoleDAO.saveUpper(oRole);
    }

    @Transactional
    public void actualizar(Role oRole) throws DAOException {
        this.oRoleDAO.updateUpper(oRole);
    }

    @Transactional
    public void borrar(Role oRole) throws BusinessException, DAOException {
        validar_eliminar(oRole);
        this.oRoleDAO.remove(oRole);
    }

    @Transactional
    public List<Role> buscar(String prBuscar, String tipo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("tipo", tipo);
        oSearch.addFilterILike("nombre", "%" + prBuscar + "%");
        return oRoleDAO.search(oSearch);
    }

    @Transactional
    public List obtenerRolesPorMenu(int menuId) {
        return oRoleDAO.obtenerRolesPorMenu(menuId);
    }

    @Transactional
    @Override
    public List<Autorizacion> obtenerListaAutorizacion(Role oRole, Entidad oEntidad) {
        return null;
    }

    @Transactional
    @Override
    public List<Autorizacion> obtenerListaUsuarios(Role oRole, Entidad oEntidad) {
        return null;
    }

    @Transactional
    @Override
    public List obtenerModulosMenuPorRole(Role oRole) {
        List<Map<String, Object>> listaModuloMenu = oRoleDAO.obtenerModulosMenuPorRole(oRole);
        List<Map<String, String>> listaResult = new ArrayList<>();
        //Obteniendo Modulos
        for (Map<String, Object> r : listaModuloMenu) {
            boolean agregado = false;
            Map<String, String> row = new HashMap<>();
            String moduloId = "M" + r.get("moduloId").toString();
            String moduloNombre = r.get("moduloNombre").toString();
            String estado = r.get("estado").toString();
            //Verificando si no ha sido agregado
            for (Map<String, String> map : listaResult) {
                if (map.get("id").toString().equals(moduloId)) {
                    agregado = true;
                    break;
                }
            }
            if (agregado) {
                continue;
            }
            row.put("id", moduloId);
            row.put("nombre", moduloNombre);
            row.put("menuId", null);
            row.put("moduloId", null);
            row.put("estado", estado);
            listaResult.add(row);
        }
        //Obteniendo hijos y nietos
        for (Map<String, Object> r : listaModuloMenu) {
            Map<String, String> row = new HashMap<>();
            String menuId = r.get("menuId").toString();
            String menuNombre = r.get("menuNombre").toString();
            String moduloId = "M" + r.get("moduloId").toString();
            String menuParentId = r.get("menuParentId") == null ? null : r.get("menuParentId").toString();
            String estado = r.get("estado").toString();
            row.put("id", menuId);
            row.put("nombre", menuNombre);
            row.put("menuId", menuParentId);
            row.put("moduloId", moduloId);
            row.put("estado", estado);
            listaResult.add(row);
        }
        return listaResult;
    }

    @Transactional
    @Override
    public List obtenerModulosMenuPorRoleNoAgregado(Role oRole) {
        // Listado de Menus Agregados por Rol
        List<Map<String, Object>> listaModuloMenuAgregados = oRoleDAO.obtenerModulosMenuPorRole(oRole);
        // Listado de Menus No Agregados por Rol
        List<Map<String, Object>> listaModuloMenuNoAgregados = oRoleDAO.obtenerModulosMenuPorRoleNoAgregado(oRole);
        //Listado Todos los Menus( Agregado y No Agregados)
        List<Map<String, Object>> listaModuloMenu = new ArrayList<Map<String, Object>>();
        List<Map<String, String>> listaResult = new ArrayList<>();
        for (Map<String, Object> msd : listaModuloMenuAgregados) {
            Map<String, Object> lsitMapElement1 = new HashMap<String, Object>();
            lsitMapElement1.put("menuId", msd.get("menuId"));
            lsitMapElement1.put("menuNombre", msd.get("menuNombre").toString());
            lsitMapElement1.put("menuParentId", msd.get("menuParentId"));
            lsitMapElement1.put("moduloId", msd.get("moduloId"));
            lsitMapElement1.put("moduloNombre", msd.get("moduloNombre"));
            lsitMapElement1.put("estado", msd.get("estado"));
            listaModuloMenu.add(lsitMapElement1);
        }
        for (Map<String, Object> msd : listaModuloMenuNoAgregados) {
            Map<String, Object> lsitMapElement2 = new HashMap<String, Object>();
            lsitMapElement2.put("menuId", msd.get("menuId"));
            lsitMapElement2.put("menuNombre", msd.get("menuNombre").toString());
            lsitMapElement2.put("menuParentId", msd.get("menuParentId"));
            lsitMapElement2.put("moduloId", msd.get("moduloId"));
            lsitMapElement2.put("moduloNombre", msd.get("moduloNombre"));
            lsitMapElement2.put("estado", msd.get("estado"));
            listaModuloMenu.add(lsitMapElement2);
        }
        //Obteniendo Modulos
        for (Map<String, Object> r : listaModuloMenu) {
            boolean agregado = false;
            Map<String, String> row = new HashMap<>();
            String moduloId = "M" + r.get("moduloId").toString();
            String moduloNombre = r.get("moduloNombre").toString();
            String estado = r.get("estado").toString();
            //Verificando si no ha sido agregado
            for (Map<String, String> map : listaResult) {
                if (map.get("id").toString().equals(moduloId)) {
                    agregado = true;
                    break;
                }
            }
            if (agregado) {
                continue;
            }
            row.put("id", moduloId);
            row.put("nombre", moduloNombre);
            row.put("menuId", null);
            row.put("moduloId", null);
            row.put("estado", estado);
            listaResult.add(row);
        }
        //Obteniendo Menus
        for (Map<String, Object> r : listaModuloMenu) {
            Map<String, String> row = new HashMap<>();
            String menuId = r.get("menuId").toString();
            String menuNombre = r.get("menuNombre").toString();
            String moduloId = "M" + r.get("moduloId").toString();
            String menuParentId = r.get("menuParentId") == null ? null : r.get("menuParentId").toString();
            String estado = r.get("estado").toString();
            row.put("id", menuId);
            row.put("nombre", menuNombre);
            row.put("menuId", menuParentId);
            row.put("moduloId", moduloId);
            row.put("estado", estado);
            listaResult.add(row);
        }
        return listaResult;
    }

    @Transactional
    @Override
    public List<Autorizacion> autorizacionesPorRole(int rolId) {
        return oRoleDAO.autorizacionesPorRole(rolId);
    }

    @Transactional
    @Override
    public List<Usuario> usuariosPorRole(int rolId, int entidadId) {
        return oRoleDAO.usuariosPorRole(rolId, entidadId);
    }

    @Transactional
    @Override
    public List<Autorizacion> agregarAutorizaciones(int rolId) {
        return oRoleDAO.agregarAutorizaciones(rolId);
    }

    @Transactional
    @Override
    public List<Autorizacion> obtenerAutorizacionesFiltradas(String txtBuscar, Integer rolId) {
        Search oSearch = new Search();
        if (!txtBuscar.equals("")) {
            oSearch.addFilterOr(
                    Filter.ilike("nombre", "%" + txtBuscar + "%"),
                    Filter.ilike("codigo", "%" + txtBuscar + "%"),
                    Filter.ilike("descripcion", "%" + txtBuscar + "%")
            );
        }
        oSearch.addFilterEqual("rolesAutorizacionesesById.rolesByRolId.id", rolId);
        return oAutorizacionDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<Autorizacion> obtenerAutorizacionesFiltradasNoAgregadas(String txtBuscar, Integer rolId) {
        return oRoleDAO.obtenerAutorizacionesFiltradasNoAgregadas(txtBuscar, rolId);
    }

    @Transactional
    @Override
    public List obtenerModulosReportePorRole(Role oRole) {
        List<Map<String, Object>> listaModuloReporte = oRoleDAO.obtenerModulosReportePorRole(oRole);
        List<Map<String, String>> listaResult = new ArrayList<>();
        //Obteniendo Modulos
        for (Map<String, Object> r : listaModuloReporte) {
            boolean agregado = false;
            Map<String, String> row = new HashMap<>();
            String moduloId = "M" + r.get("moduloId").toString();
            String moduloNombre = r.get("moduloNombre").toString();
            String estado = r.get("estado").toString();
            //Verificando si no ha sido agregado
            for (Map<String, String> map : listaResult) {
                if (map.get("id").toString().equals(moduloId)) {
                    agregado = true;
                    break;
                }
            }
            if (agregado) {
                continue;
            }
            row.put("id", moduloId);
            row.put("nombre", moduloNombre);
            row.put("reporteId", null);
            row.put("moduloId", null);
            row.put("estado", estado);
            listaResult.add(row);
        }
        //Obteniendo Reportes
        for (Map<String, Object> r : listaModuloReporte) {
            Map<String, String> row = new HashMap<>();
            String reporteId = r.get("reporteId").toString();
            String reporteNombre = r.get("reporteNombre").toString();
            String moduloId = "M" + r.get("moduloId").toString();
            String estado = r.get("estado").toString();
            row.put("id", reporteId);
            row.put("nombre", reporteNombre);
            row.put("moduloId", moduloId);
            row.put("estado", estado);
            listaResult.add(row);
        }
        return listaResult;
    }

    @Transactional
    @Override
    public List obtenerModulosReportePorRoleNoAgregado(Role oRole) {
        List<Map<String, Object>> listaModuloReporteNoAgregados = oRoleDAO.obtenerModulosReportePorRoleNoAgregado(oRole);
        // Listado de Reportes Agregados por Rol
        List<Map<String, Object>> listaModuloReporteAgregados = oRoleDAO.obtenerModulosReportePorRole(oRole);
        // Listado de Reportes No Agregados por Rol
        //Listado Todos los Reportes( Agregado y No Agregados)
        List<Map<String, Object>> listaModuloReporte = new ArrayList<Map<String, Object>>();
        List<Map<String, String>> listaResult = new ArrayList<>();
        for (Map<String, Object> msd : listaModuloReporteAgregados) {
            Map<String, Object> lsitMapElement1 = new HashMap<String, Object>();
            lsitMapElement1.put("reporteId", msd.get("reporteId"));
            lsitMapElement1.put("reporteNombre", msd.get("reporteNombre").toString());
            lsitMapElement1.put("moduloId", msd.get("moduloId"));
            lsitMapElement1.put("moduloNombre", msd.get("moduloNombre"));
            lsitMapElement1.put("estado", msd.get("estado"));
            listaModuloReporte.add(lsitMapElement1);
        }
        for (Map<String, Object> msd : listaModuloReporteNoAgregados) {
            if (msd.get("reporteId") != null) {
                Map<String, Object> lsitMapElement2 = new HashMap<String, Object>();
                lsitMapElement2.put("reporteId", msd.get("reporteId"));
                lsitMapElement2.put("reporteNombre", msd.get("reporteNombre").toString());
                lsitMapElement2.put("moduloId", msd.get("moduloId"));
                lsitMapElement2.put("moduloNombre", msd.get("moduloNombre"));
                lsitMapElement2.put("estado", msd.get("estado"));
                listaModuloReporte.add(lsitMapElement2);
            }
        }

        //Obteniendo Modulos
        for (Map<String, Object> r : listaModuloReporte) {
            boolean agregado = false;
            Map<String, String> row = new HashMap<>();
            String moduloId = "M" + r.get("moduloId").toString();
            String moduloNombre = r.get("moduloNombre").toString();
            String estado = r.get("estado").toString();
            //Verificando si no ha sido agregado
            for (Map<String, String> map : listaResult) {
                if (map.get("id").toString().equals(moduloId)) {
                    agregado = true;
                    break;
                }
            }
            if (agregado) {
                continue;
            }
            row.put("id", moduloId);
            row.put("nombre", moduloNombre);
            row.put("reporteId", null);
            row.put("moduloId", null);
            row.put("estado", estado);
            listaResult.add(row);
        }
        //Obteniendo Reportes
        for (Map<String, Object> r : listaModuloReporte) {
            Map<String, String> row = new HashMap<>();
            String reporteId = r.get("reporteId").toString();
            String reporteNombre = r.get("reporteNombre").toString();
            String moduloId = "M" + r.get("moduloId").toString();
            String estado = r.get("estado").toString();
            row.put("id", reporteId);
            row.put("nombre", reporteNombre);
            row.put("moduloId", moduloId);
            row.put("estado", estado);
            listaResult.add(row);
        }
        return listaResult;
    }

    public void validar_eliminar(Role oRole) throws BusinessException {
        Search oSearch1 = new Search();
        oSearch1.addFilterEqual("rolesByRolId", oRole);
        List<RoleMenu> listaMenu = oRoleMenuDAO.search(oSearch1);
        //Validando que el Id de Establecimiento se encuentre en Entidad
        if (listaMenu.size() > 0) {
            throw new BusinessException("Este Rol tiene Menús relacionados.");
        }
        Search oSearch2 = new Search();
        oSearch2.addFilterEqual("roleByRoleId", oRole);
        List<RoleReporte> listaReporte = oRoleReporteDAO.search(oSearch2);
        //Validando que el Id de Establecimiento se encuentre en Entidad
        if (listaReporte.size() > 0) {
            throw new BusinessException("Este Rol tiene Reportes relacionados.");
        }
        Search oSearch3 = new Search();
        oSearch3.addFilterEqual("rolesByRolId", oRole);
        List<RoleAutorizacion> listaAutorizacion = oRoleAutorizacionDAO.search(oSearch3);
        //Validando que el Id de Establecimiento se encuentre en Entidad
        if (listaAutorizacion.size() > 0) {
            throw new BusinessException("Este Rol tiene Autorizaciones relacionados.");
        }
        Search oSearch4 = new Search();
        oSearch4.addFilterEqual("rolesByRolId", oRole);
        List<Permiso> listaPermisos = oPermisoDAO.search(oSearch4);
        //Validando que el Id de Establecimiento se encuentre en Entidad
        if (listaPermisos.size() > 0) {
            throw new BusinessException("Este Rol tiene Permisos relacionados.");
        }
    }
}