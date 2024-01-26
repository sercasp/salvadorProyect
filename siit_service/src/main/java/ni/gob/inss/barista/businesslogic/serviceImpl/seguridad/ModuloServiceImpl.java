package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.ModuloService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.ModuloDAO;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Entidades</br>
 *
 * @author jfletes
 * @version 1.0, 5/29/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ModuloServiceImpl implements ModuloService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final ModuloDAO oModuloDAO;

    @Autowired
    public ModuloServiceImpl(ModuloDAO oModuloDAO) {
        this.oModuloDAO = oModuloDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public Modulo obtenerModuloPorId(int id) throws EntityNotFoundException {
        return oModuloDAO.find(id);
    }

    public Modulo obtener(int id) throws EntityNotFoundException {
        return oModuloDAO.find(id);
    }

    @Override
    public void agregar(Modulo oModulo) throws BusinessException, DAOException {
        validar(oModulo);
        oModuloDAO.save(oModulo);
    }

    @Override
    public void actualizar(Modulo oModulo) throws BusinessException, DAOException {
        validar(oModulo);
        oModuloDAO.update(oModulo);
    }

    @Override
    public void eliminar(Modulo oModulo) throws BusinessException, DAOException {
        validar_eliminar(oModulo);
        oModuloDAO.remove(oModulo);
    }

    @Override
    public List<Modulo> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterILike("nombre", "%" + prBuscar + "%");
        oSearch.addSortAsc("nombre");
        return oModuloDAO.search(oSearch);
    }

    @Override
    public List<Modulo> obtenerModulos() {
        return oModuloDAO.obtenerModulos();
    }

    /**
     * Ver más de este metodo {@link ModuloDAO#verificarModuloEnReporte(Integer)}
     * @param prIdModulo parametro de busqueda
     */
    @Override
    public List<Modulo> verificarModuloEnReporte(Integer prIdModulo) {
        return oModuloDAO.verificarModuloEnReporte(prIdModulo);
    }

    /**
     * Ver más de este metodo {@link ModuloDAO#verificarModuloEnParametroEntidad(Integer)}
     * @param prIdModulo parametro de busqueda
     */
    @Override
    public List<Modulo> verificarModuloEnParametroEntidad(Integer prIdModulo) {
        return oModuloDAO.verificarModuloEnParametroEntidad(prIdModulo);
    }

    /**
     * Ver más de este metodo {@link ModuloDAO#verificarModuloEnSesionModulo(Integer)}
     * @param prIdModulo parametro de busqueda
     */
    @Override
    public List<Modulo> verificarModuloEnSesionModulo(Integer prIdModulo) {
        return oModuloDAO.verificarModuloEnSesionModulo(prIdModulo);
    }

    private void validar_eliminar(Modulo oModulo) throws BusinessException {
        //Validando que el código no este registrado
        if (oModulo.getMenusesById().size() > 0) {
            throw new BusinessException("Esta Autorización no puede eliminarse, por que está relacionada con Permisos" +
                    " en la pantalla de Usuarios.");
        }
    }

    private void validar(Modulo oModulo) throws BusinessException {
        //Validando si es un nuevo registro
        if (oModulo.getId() == null) {
            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oModulo.getCodigo());
            List<Autorizacion> lista = oModuloDAO.search(oSearch);
            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este Código ya se encuentra registrado");
            }
        }
        //Validando si se esta editando el registro
        else {
            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oModulo.getCodigo());
            oSearch.addFilterNotEqual("id", oModulo.getId());
            List<Autorizacion> lista = oModuloDAO.search(oSearch);
            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este Código ya se encuentra registrado");
            }
        }
    }
}