package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.ParametroEntidadService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.infraestructura.EntidadDAO;
import ni.gob.inss.barista.model.dao.seguridad.ParametroEntidadDAO;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.ParametroEntidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Parametros Entidades</br>
 *
 * @author VIRGINIA ELIZABETH MORA MUNGUIA
 * @version 1.0, 03/06/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class ParametroEntidadServiceImpl implements ParametroEntidadService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final ParametroEntidadDAO oParametroEntidadDAO;
    private final EntidadDAO oEntidadDAO;

    @Autowired
    public ParametroEntidadServiceImpl(EntidadDAO oEntidadDAO, ParametroEntidadDAO oParametroEntidadDAO) {
        this.oParametroEntidadDAO = oParametroEntidadDAO;
        this.oEntidadDAO = oEntidadDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public ParametroEntidad obtenerParametroEntidadPorId(int id) throws EntityNotFoundException {
        return oParametroEntidadDAO.find(id);
    }

    @Override
    @Transactional
    public void guardar(ParametroEntidad oParametroEntidad) throws BusinessException, DAOException {
        validarGuardar(oParametroEntidad);
        oParametroEntidadDAO.save(oParametroEntidad);
    }

    @Transactional
    @Override
    public void actualizar(ParametroEntidad oParametroEntidad) throws BusinessException, DAOException {
        validarActualizar(oParametroEntidad);
        oParametroEntidadDAO.update(oParametroEntidad);
    }

    @Transactional
    @Override
    public void eliminar(ParametroEntidad oParametroEntidad) {
        oParametroEntidadDAO.remove(oParametroEntidad);
    }

    @Transactional
    public List<Modulo> obtenerModulos() {
        return oParametroEntidadDAO.obtenerModulos();
    }

    @Override
    @Transactional
    public List<Entidad> obtenerDelegaciones() {
        Search oSearch = new Search();
        return oEntidadDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<ParametroEntidad> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterILike("descriptor", "%" + prBuscar + "%");
        oSearch.addSort("descriptor", false);
        return oParametroEntidadDAO.search(oSearch);
    }

    private void validarGuardar(ParametroEntidad oParametroEntidad) throws BusinessException {
        List<ParametroEntidad> listaDatosRepetidos = oParametroEntidadDAO.obtenerRepetidos(oParametroEntidad.getCodigo(), oParametroEntidad.getEntidadIdByEntidadId().getId());
        if (listaDatosRepetidos.size() > 0) {
            throw new BusinessException("No puede guardar el mismo código y entidad");
        }
    }

    private void validarActualizar(ParametroEntidad oParametroEntidad) throws BusinessException {
        List<ParametroEntidad> datoRepetido = oParametroEntidadDAO.obtenerRepetido(oParametroEntidad.getCodigo(), oParametroEntidad.getEntidadIdByEntidadId().getId());

        //Validando que el código no este registrado
        if (datoRepetido.size() > 0) {
            List<ParametroEntidad> dato = oParametroEntidadDAO.obtenerRepetidos(oParametroEntidad.getId(), oParametroEntidad.getCodigo(), oParametroEntidad.getEntidadIdByEntidadId().getId());
            if (dato.size() != 1) {
                throw new BusinessException("Este orden ya existe");
            }
        }
    }
}