package ni.gob.inss.barista.businesslogic.serviceImpl.infraestructura;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.infraestructura.EntidadService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.infraestructura.EntidadDAO;
import ni.gob.inss.barista.model.dao.infraestructura.EstablecimientoDAO;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.infraestructura.Establecimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para Entidades</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvilanueva el 08/08/2023
 */
@Service
public class EntidadServiceImpl implements EntidadService {

    private final EntidadDAO oEntidadDAO;

    private final EstablecimientoDAO oEstablecimientoDAO;

    @Autowired
    public EntidadServiceImpl(EntidadDAO oEntidadDAO, EstablecimientoDAO oEstablecimientoDAO) {
        this.oEntidadDAO = oEntidadDAO;
        this.oEstablecimientoDAO = oEstablecimientoDAO;
    }

    @Transactional
    public List<Entidad> obtenerEntidadesPorUsuario(Integer usuario_id) {
        List<Entidad> entidads;
        entidads = oEntidadDAO.obtenerEntidadPorUsuario(usuario_id);
        return entidads;
    }

    @Transactional
    public Entidad obtenerEntidad(Integer entidadId) throws EntityNotFoundException {
        return oEntidadDAO.find(entidadId);
    }

    @Transactional
    public Entidad obtenerEntidadPorId(int id) throws EntityNotFoundException {
        return oEntidadDAO.find(id);
    }

    @Transactional
    public Entidad obtenerEntidadPorCodigo(Integer codigo) throws BusinessException {
        Search search = new Search();
        search.addFilterEqual("codigo", codigo);
        List<Entidad> lista = oEntidadDAO.search(search);
        if (lista.size() == 0) {
            throw new BusinessException("No existe Entidad con el codigo: " + codigo);
        }
        return lista.get(0);
    }

    @Transactional
    @Override
    public void agregar(Entidad oEntidad) throws BusinessException, DAOException {
        validar_guardar(oEntidad);
        oEntidadDAO.saveUpper(oEntidad);
    }

    @Transactional
    @Override
    public void actualizar(Entidad oEntidad) throws BusinessException, DAOException {
        validar_guardar(oEntidad);
        oEntidadDAO.updateUpper(oEntidad);
    }


    @Transactional
    @Override
    public List<Entidad> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterILike("nombre", "%" + prBuscar + "%");
        oSearch.addSortAsc("nombre");
        return oEntidadDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<Establecimiento> obtenerEstablecimientos() {
        Search oSearch = new Search();
        oSearch.addSortAsc("nombre");
        oSearch.addFilterEqual("pasivo", false);
        return oEstablecimientoDAO.search(oSearch);
    }

    @Transactional
    public List<Entidad> obtenerEntidadesCombo(int id) {
        Search oSearch = new Search();
        oSearch.addSortAsc("nombre");
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addFilterNotEqual("id", id);
        return oEntidadDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<Entidad> obtenerEntidadesCombo() {
        Search oSearch = new Search();
        oSearch.addSortAsc("nombre");
        oSearch.addFilterEqual("pasivo", false);
        return oEntidadDAO.search(oSearch);
    }

    private void validar_guardar(Entidad oEntidad) throws BusinessException {

        //Validando si es un nuevo registro
        if (oEntidad.getId() == null) {

            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oEntidad.getCodigo());
            List<Entidad> lista = oEntidadDAO.search(oSearch);

            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este CÓDIGO ya se encuentra registrado.");
            }

            //Validando el Nombre
            Search oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oEntidad.getNombre().trim());
            List<Entidad> listaValidacionNombre = oEntidadDAO.search(oSearchValidacionNombre);

            //Validando que el código no este registrado
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este NOMBRE ya se encuentra registrado.");
            }

            //Validando la Siglas
            Search oSearchValidacionSigla = new Search();
            oSearchValidacionSigla.addFilterILike("siglas", oEntidad.getSiglas().trim());
            List<Entidad> listaValidacionSigla = oEntidadDAO.search(oSearchValidacionSigla);

            //Validando que el código no este registrado
            if (listaValidacionSigla.size() > 0) {
                throw new BusinessException("Esta SIGLA ya se encuentra registrada.");
            }


        }
        //Validando si se esta editando el registro
        else {

            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oEntidad.getCodigo());
            oSearch.addFilterNotEqual("id", oEntidad.getId());
            List<Entidad> lista = oEntidadDAO.search(oSearch);

            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este CÓDIGO ya se encuentra registrado.");
            }

            //Validando el Nombre
            Search oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oEntidad.getNombre().trim());
            oSearchValidacionNombre.addFilterNotEqual("id", oEntidad.getId());
            List<Entidad> listaValidacionNombre = oEntidadDAO.search(oSearchValidacionNombre);

            //Validando que el código no este registrado
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este NOMBRE ya se encuentra registrado.");
            }

            //Validando la Sigla
            Search oSearchValidacionSigla = new Search();
            oSearchValidacionSigla.addFilterILike("siglas", oEntidad.getSiglas().trim());
            oSearchValidacionSigla.addFilterNotEqual("id", oEntidad.getId());
            List<Entidad> listaValidacionSigla = oEntidadDAO.search(oSearchValidacionSigla);

            //Validando que el código no este registrado
            if (listaValidacionSigla.size() > 0) {
                throw new BusinessException("Esta SIGLA ya se encuentra registrada.");
            }
        }
    }

}
