package ni.gob.inss.barista.businesslogic.serviceImpl.core;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.core.NavegacionService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.seguridad.MenuDAO;
import ni.gob.inss.barista.model.dao.seguridad.ModuloDAO;
import ni.gob.inss.barista.model.dao.seguridad.SesionModuloDAO;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.SesionModulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implentaón Interfaz de servicio para navegación</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/31/2014
 * @since 1.1 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class NavegacionServiceImpl implements NavegacionService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    final ModuloDAO oModuloDAO;
    final MenuDAO oMenuDAO;
    final SesionModuloDAO oSesionModuloDAO;

    @Autowired
    public NavegacionServiceImpl(ModuloDAO oModuloDAO, MenuDAO oMenuDAO, SesionModuloDAO oSesionModuloDAO) {
        this.oMenuDAO = oMenuDAO;
        this.oSesionModuloDAO = oSesionModuloDAO;
        this.oModuloDAO = oModuloDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    @Override
    public List obtenerModulosPorUsuario(Integer usuarioId, Integer entidadId, String nombreModulo, String orderBy) {
        return oModuloDAO.obtenerModulosPorUsuario(usuarioId, entidadId, nombreModulo, orderBy);
    }

    @Transactional
    @Override
    public List obtenerMenuPorUsuarioModulo(Integer usuarioId, Integer entidadId, Integer moduloId) {
        return oMenuDAO.obtenerMenuPorUsuarioModulo(usuarioId, entidadId, moduloId);
    }

    @Transactional
    @Override
    public List obtenerModulosPorUsuarioMobile(Integer usuarioId, Integer entidadId, String nombreModulo, String orderBy) {
        return oModuloDAO.obtenerModulosPorUsuarioMobile(usuarioId, entidadId, nombreModulo, orderBy);
    }

    @Transactional
    @Override
    public List obtenerMenuPorUsuarioModuloMobile(Integer usuarioId, Integer entidadId, Integer moduloId) {
        return oMenuDAO.obtenerMenuPorUsuarioModuloMobile(usuarioId, entidadId, moduloId);
    }

    @Transactional
    @Override
    public void guardarSesionModulo(SesionModulo oSesionModulo) throws DAOException {
        Entidad oEntidad = oSesionModulo.getEntidad();
        Integer usuarioId = oSesionModulo.getUsuarioId();
        Modulo oModulo = oSesionModulo.getModulosByModuloId();
        Search oSearch = new Search();
        oSearch.addFilterEqual("usuarioId", usuarioId);
        oSearch.addFilterEqual("entidad", oEntidad);
        oSearch.addFilterEqual("modulosByModuloId", oModulo);
        List<SesionModulo> sesionModulosList = oSesionModuloDAO.search(oSearch);

        if (sesionModulosList.size() > 0) {
            SesionModulo oSesionModuloOld = sesionModulosList.get(0);
            oSesionModuloOld.setCantidad(oSesionModuloOld.getCantidad() + 1);
            oSesionModuloOld.setFechaUltimaSesion(oSesionModulo.getFechaUltimaSesion());
            oSesionModuloDAO.updateUpper(oSesionModuloOld);
        } else {
            oSesionModulo.setCantidad(1);
            oSesionModuloDAO.saveUpper(oSesionModulo);
        }
    }
}