package ni.gob.inss.barista.businesslogic.serviceImpl.notificacion;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.notificacion.MensajeNotificacionService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.notificacion.MensajeDAO;
import ni.gob.inss.barista.model.entity.notificacion.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
public class MensajeNotificacionServiceImpl implements MensajeNotificacionService {
    /**
     * **********************************************************************************
     * DEPENDENCIAS
     * **********************************************************************************
     */
    private final MensajeDAO oMensajeDAO;

    @Autowired
    public MensajeNotificacionServiceImpl(MensajeDAO oMensajeDAO) {
        this.oMensajeDAO = oMensajeDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    @Transactional
    @Override
    public Mensaje obtener(int id) throws EntityNotFoundException {
        return oMensajeDAO.find(id);
    }

    @Transactional
    @Override
    public void agregar(Mensaje oMensaje) throws DAOException, BusinessException {
        oMensajeDAO.save(oMensaje);
    }

    @Transactional
    @Override
    public void actualizar(Mensaje oMensaje) throws DAOException {
        oMensajeDAO.update(oMensaje);
    }

    @Transactional
    @Override
    public void eliminar(Mensaje oMensaje) {
        oMensajeDAO.remove(oMensaje);
    }

    @Transactional
    @Override
    public List<Mensaje> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterOr(
                Filter.ilike("titulo", "%" + prBuscar + "%"),
                Filter.ilike("mensaje", "%" + prBuscar + "%")
        );
        oSearch.addSortDesc("id");
        return oMensajeDAO.search(oSearch);
    }
}