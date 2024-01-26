package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.PermisoService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.PermisoDAO;
import ni.gob.inss.barista.model.entity.seguridad.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jmendoza on 6/24/2015.
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class PermisoServiceImpl implements PermisoService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final PermisoDAO oPermisoDAO;

    @Autowired
    public PermisoServiceImpl(PermisoDAO oPermisoDAO) {
        this.oPermisoDAO = oPermisoDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Permiso obtener(int id) throws EntityNotFoundException {
        return oPermisoDAO.find(id);
    }

    @Transactional
    public Permiso obtenerPermisoPorId(int id) throws EntityNotFoundException {
        return oPermisoDAO.find(id);
    }

    @Transactional
    @Override
    public void agregar(Permiso oPermiso) throws BusinessException, DAOException {
        oPermisoDAO.saveUpper(oPermiso);
    }

    @Transactional
    @Override
    public void actualizar(Permiso oPermiso) throws BusinessException, DAOException {
        oPermisoDAO.updateUpper(oPermiso);
    }

    @Transactional
    @Override
    public void eliminar(Permiso oPermiso) throws BusinessException, DAOException {
        oPermisoDAO.remove(oPermiso);
    }

    @Transactional
    @Override
    public List<Permiso> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterILike("nombre", "%" + prBuscar + "%");
        oSearch.addSortAsc("nombre");
        return oPermisoDAO.search(oSearch);
    }
}