package ni.gob.inss.barista.businesslogic.serviceImpl.notificacion;

import ni.gob.inss.barista.businesslogic.service.notificacion.NotificacionGeneralService;
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
public class NotificacionGeneralServiceImpl implements NotificacionGeneralService {
    /**
     * **********************************************************************************
     * DEPENDENCIAS
     * **********************************************************************************
     */
    private final MensajeDAO oMensajeDAO;

    @Autowired
    public NotificacionGeneralServiceImpl(MensajeDAO oMensajeDAO) {
        this.oMensajeDAO = oMensajeDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    @Override
    public List<Mensaje> obtenerMensajes() {
        return oMensajeDAO.findAll();
    }
}
