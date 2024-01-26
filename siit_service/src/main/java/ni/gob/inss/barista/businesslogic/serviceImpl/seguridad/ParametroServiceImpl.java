package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;


import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.ParametroService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.ParametroDAO;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para Parámetros</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
public class ParametroServiceImpl implements ParametroService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final ParametroDAO oParametroDAO;

    @Autowired
    public ParametroServiceImpl(ParametroDAO oParametroDAO) {
        this.oParametroDAO = oParametroDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Parametro obtenerParametroPorId(int id) throws EntityNotFoundException {
        return oParametroDAO.find(id);
    }

    @Transactional
    @Override
    public void agregar(Parametro oParametro) throws BusinessException, DAOException {
        if (validarGuardar(oParametro)) {
            throw new BusinessException("Este código ya se encuentra guardado para este parámetro");
        }
        oParametroDAO.saveUpper(oParametro);
    }

    @Transactional
    @Override
    public void actualizar(Parametro oParametro) throws BusinessException, DAOException {
        oParametroDAO.updateUpper(oParametro);
    }

    @Transactional
    @Override
    public void eliminar(Parametro oParametro) throws BusinessException, DAOException {
        validarEliminar(oParametro);
        oParametroDAO.remove(oParametro);
    }

    @Transactional
    public List<Modulo> obtenerModulos() {
        return oParametroDAO.obtenerModulos();
    }

    @Transactional
    @Override
    public List<Parametro> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterILike("descriptor", "%" + prBuscar + "%");
        oSearch.addSort("descriptor", false);
        return oParametroDAO.search(oSearch);
    }

    public Boolean validarGuardar(Parametro oParametro) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", oParametro.getCodigo());
        Parametro parametro = oParametroDAO.searchUnique(oSearch);
        if (parametro != null) {
            return true;
        }
        return false;
    }

    public void validarEliminar(Parametro oParametro) throws BusinessException {
        List<Parametro> listaParametrosUsuarios = oParametroDAO.obtenerParametroUsuario(oParametro.getId());
        if (listaParametrosUsuarios.size() > 0) {
            throw new BusinessException("No puede eliminar este parámetro, ya que está siendo utilizado");
        }
    }
}