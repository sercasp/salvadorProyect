package ni.gob.inss.barista.businesslogic.serviceImpl.personal;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.personal.MiembroService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.personal.MiembroDAO;
import ni.gob.inss.barista.model.entity.personal.Miembro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para Catálogos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 04/08/2023
 */
@Service
public class MiembroServiceImpl implements MiembroService {
    /**
     * **********************************************************************************
     * DEPENDENCIAS
     * **********************************************************************************
     */
    private final MiembroDAO oMiembroDAO;

    @Autowired
    public MiembroServiceImpl(MiembroDAO oMiembroDAO) {
        this.oMiembroDAO = oMiembroDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Miembro obtenerMiembroPorId(int id) throws EntityNotFoundException {
        return oMiembroDAO.find(id);
    }

    @Transactional
    @Override
    public void agregar(Miembro oMiembro) throws BusinessException, DAOException {
        oMiembroDAO.save(oMiembro);
    }

    @Transactional
    @Override
    public void actualizar(Miembro oMiembro) throws BusinessException, DAOException {
        oMiembroDAO.updateUpper(oMiembro);
    }

    @Transactional
    @Override
    public void eliminar(Miembro oMiembro) {
        oMiembroDAO.remove(oMiembro);
    }

    @Transactional
    @Override
    public List<Miembro> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterILike("primerNombre", "%" + prBuscar + "%");
        oSearch.addSortAsc("primerNombre");
        return oMiembroDAO.search(oSearch);
    }
}