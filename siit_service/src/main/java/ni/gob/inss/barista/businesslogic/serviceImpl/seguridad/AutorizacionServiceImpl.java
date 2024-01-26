package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.AutorizacionService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.AutorizacionDAO;
import ni.gob.inss.barista.model.dao.seguridad.RoleAutorizacionDAO;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;
import ni.gob.inss.barista.model.entity.seguridad.RoleAutorizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Establecimientos</br>
 *
 * @author barista
 * @version 1.0, 19/06/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class AutorizacionServiceImpl implements AutorizacionService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final AutorizacionDAO oAutorizacionDAO;
    private final RoleAutorizacionDAO oRoleAutorizacionDAO;

    @Autowired
    public AutorizacionServiceImpl(AutorizacionDAO oAutorizacionDAO, RoleAutorizacionDAO oRoleAutorizacionDAO) {
        this.oAutorizacionDAO = oAutorizacionDAO;
        this.oRoleAutorizacionDAO = oRoleAutorizacionDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Autorizacion obtener(int id) throws EntityNotFoundException {
        return oAutorizacionDAO.find(id);
    }

    @Transactional
    public Autorizacion obtenerAutorizacionPorId(int id) throws EntityNotFoundException {
        return oAutorizacionDAO.find(id);
    }

    @Transactional
    @Override
    public void agregar(Autorizacion oAutorizacion) throws BusinessException, DAOException {
        //validar(oAutorizacion);
        oAutorizacionDAO.saveUpper(oAutorizacion);
    }

    @Transactional
    @Override
    public void actualizar(Autorizacion oAutorizacion) throws BusinessException, DAOException {
        //validar(oAutorizacion);
        oAutorizacionDAO.updateUpper(oAutorizacion);
    }

    @Transactional
    @Override
    public void eliminar(Autorizacion oAutorizacion) throws BusinessException, DAOException {
        validar_eliminar(oAutorizacion);
        oAutorizacionDAO.remove(oAutorizacion);
    }

    @Transactional
    @Override
    public List<Autorizacion> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterOr(
                Filter.ilike("nombre", "%" + prBuscar + "%"),
                Filter.ilike("codigo", "%" + prBuscar + "%")
        );
        oSearch.addSortAsc("nombre");
        return oAutorizacionDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<Autorizacion> obtenerTodasLasAutorizaciones(String codigo) {
        Search oSearch = new Search();
        oSearch.addFilterILike("codigo", "%" + codigo + "%");
        oSearch.addSortAsc("codigo");
        return oAutorizacionDAO.search(oSearch);
    }

    public void validar_eliminar(Autorizacion oAutorizacion) throws BusinessException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("autorizacionesByAutorizacionId", oAutorizacion);
        List<RoleAutorizacion> lista = oRoleAutorizacionDAO.search(oSearch);

        //Validando que el código no este registrado
        if (lista.size() > 0) {
            throw new BusinessException("Esta Autorización no puede eliminarse, por que está relacionada con Permisos" +
                    " en la pantalla de Usuarios.");
        }
    }

    private void validar(Autorizacion oAutorizacion) throws BusinessException {
        //Validando si es un nuevo registro
        if (oAutorizacion.getId() == null) {
            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oAutorizacion.getCodigo());
            List<Autorizacion> lista = oAutorizacionDAO.search(oSearch);
            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este código ya se encuentra registrado");
            }
        }
        //Validando si se esta editando el registro
        else {
            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oAutorizacion.getCodigo());
            oSearch.addFilterNotEqual("id", oAutorizacion.getId());
            List<Autorizacion> lista = oAutorizacionDAO.search(oSearch);
            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este código ya se encuentra registrado");
            }
        }
    }
}