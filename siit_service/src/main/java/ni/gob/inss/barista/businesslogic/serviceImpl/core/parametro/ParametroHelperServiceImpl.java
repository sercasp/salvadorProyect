package ni.gob.inss.barista.businesslogic.serviceImpl.core.parametro;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.core.parametro.ParametroHelperService;
import ni.gob.inss.barista.model.dao.seguridad.ParametroDAO;
import ni.gob.inss.barista.model.dao.seguridad.ParametroEntidadDAO;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import ni.gob.inss.barista.model.entity.seguridad.ParametroEntidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2015 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de interfaz de servicio para parámetros</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 10/03/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Service
public class ParametroHelperServiceImpl implements ParametroHelperService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final ParametroDAO oParametroDAO;
    private final ParametroEntidadDAO oParametroEntidadDAO;

    @Autowired
    public ParametroHelperServiceImpl(ParametroDAO oParametroDAO, ParametroEntidadDAO oParametroEntidadDAO) {
        this.oParametroDAO = oParametroDAO;
        this.oParametroEntidadDAO = oParametroEntidadDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    @Override
    public Parametro obtenerParametro(String codigo) {
        Parametro result = null;
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigo);
        List<Parametro> parametros = oParametroDAO.search(oSearch);

        if (parametros.size() > 0) {
            result = parametros.get(0);
        }
        return result;
    }

    @Transactional
    @Override
    public ParametroEntidad obtenerParametroEntidad(String codigo, Entidad oEntidad) {
        ParametroEntidad result = null;
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigo);
        oSearch.addFilterEqual("entidadId", oEntidad.getId());
        List<ParametroEntidad> parametros = oParametroEntidadDAO.search(oSearch);
        if (parametros.size() > 0) {
            result = parametros.get(0);
        }
        return result;
    }
}