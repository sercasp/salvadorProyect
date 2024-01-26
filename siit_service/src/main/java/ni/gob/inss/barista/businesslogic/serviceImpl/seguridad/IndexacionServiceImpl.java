package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import ni.gob.inss.barista.businesslogic.service.seguridad.IndexacionService;
import ni.gob.inss.barista.model.dao.seguridad.IndexacionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <b>SPA</b></br>
 * <b>Copyright (c) 2017 MI FAMILIA.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para Indexación de entidades</br>
 *
 * @author HERNALDO JOSE MAYORGA HERNANDEZ, ENOC EZEQUIEL JIMENEZ MIRANDA
 * @version 1.0, 03/03/2017
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Service
public class IndexacionServiceImpl implements IndexacionService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final IndexacionDAO oIndexacionDAO;

    @Autowired
    public IndexacionServiceImpl(IndexacionDAO oIndexacionDAO) {
        this.oIndexacionDAO = oIndexacionDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    @Override
    public List<Map<String, Object>> cargarListaEntidades() {
        return oIndexacionDAO.cargarListaEntidades();
    }

    @Transactional
    @Override
    public void indexarEntidad(Class entidad) throws InterruptedException {
        oIndexacionDAO.indexarEntidad(entidad);
    }
}