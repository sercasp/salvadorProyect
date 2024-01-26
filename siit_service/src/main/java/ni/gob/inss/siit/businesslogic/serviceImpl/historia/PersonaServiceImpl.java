package ni.gob.inss.siit.businesslogic.serviceImpl.historia;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.catalogos.CatalogoDAO;
import ni.gob.inss.barista.model.dao.catalogos.DivisionPoliticaDAO;
import ni.gob.inss.barista.model.dao.catalogos.LocalidadDAO;
import ni.gob.inss.barista.model.dao.catalogos.PaisesDAO;
import ni.gob.inss.barista.model.dao.infraestructura.EntidadDAO;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.DivisionPolitica;
import ni.gob.inss.barista.model.entity.catalogo.Localidad;
import ni.gob.inss.barista.model.entity.catalogo.Pais;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.siit.businesslogic.service.historia.PersonaService;
import ni.gob.inss.siit.model.dao.historia.PersonaDAO;
import ni.gob.inss.siit.model.entity.historia.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Creado por  jvillanueva1 el  01/12/2015.
 * Modificado por jvillanueva 02/01/2018.
 * Modificado por jvillanueva el 20/06/2020.
 * Modificado por jvillanueva 16/07/2020.
 * Modificado por jvillanueva 04/02/2021.
 */


@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class PersonaServiceImpl implements PersonaService {
    /**
     * **********************************************************************************
     * DEPENDENCIAS
     * **********************************************************************************
     */
    private final PersonaDAO oPersonaDAO;
    private final PaisesDAO oPaisDAO;
    private final DivisionPoliticaDAO oDivisionPoliticaDAO;
    private final CatalogoDAO oCatalogoDAO;
    private final LocalidadDAO oLocalidadDAO;
    private final EntidadDAO oEntidadDAO;

    @Autowired
    public PersonaServiceImpl(final PersonaDAO oPersonaDAO,
                              final PaisesDAO oPaisDAO,
                              final DivisionPoliticaDAO oDivisionPoliticaDAO,
                              final CatalogoDAO oCatalogoDAO,
                              final LocalidadDAO oLocalidadDAO,
                              final EntidadDAO oEntidadDAO) {
        this.oPersonaDAO = oPersonaDAO;
        this.oPaisDAO = oPaisDAO;
        this.oDivisionPoliticaDAO = oDivisionPoliticaDAO;
        this.oCatalogoDAO = oCatalogoDAO;
        this.oLocalidadDAO = oLocalidadDAO;
        this.oEntidadDAO = oEntidadDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    public Entidad obtenerEntidadPorId(int id) throws EntityNotFoundException {
        return oEntidadDAO.find(id);
    }

    public Persona obtenerPersonaPorId(int id) throws EntityNotFoundException {
        return oPersonaDAO.find(id);
    }

    @Override
    public void agregar(Persona oPersona) throws BusinessException, DAOException {
        validar_guardar(oPersona);
        oPersonaDAO.save(oPersona);
    }


    @Override
    public void actualizar(Persona oPersona) throws BusinessException, DAOException {
        validar_guardar(oPersona);
        oPersonaDAO.update(oPersona);
    }


    @Override
    public void eliminar(Persona oPersona) throws BusinessException, DAOException {
        oPersonaDAO.remove(oPersona);
    }

    @Override
    public List buscar(String prBuscar) {
        return oPersonaDAO.buscarPersona(prBuscar);
    }

    @Override
    public List buscarPersonaLazy(String prBuscar, Integer limite, Integer pagina) {
        return oPersonaDAO.buscarPersonaLazy(prBuscar, limite, pagina);
    }

    @Override
    public List<Pais> obtenerPais(String codigoAlfa) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigoAlfa2", codigoAlfa);
        oSearch.addSortAsc("nombre");
        return oPaisDAO.search(oSearch);
    }

    @Override
    public Pais obtenerPais306(String codigoAlfa) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigoAlfa2", codigoAlfa);
        oSearch.addSortAsc("nombre");
        List<Pais> obtenerPais = oPaisDAO.search(oSearch);
        return obtenerPais.size() > 0 ? obtenerPais.get(0) : null;
    }

    @Override
    public List<Persona> buscarPersonaByDni(String dni) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("dni", dni);
        return oPersonaDAO.search(oSearch);
    }

    @Override
    public List<DivisionPolitica> obtenerMunicipio(String codigo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigo);
        oSearch.addSortAsc("nombre");
        return oDivisionPoliticaDAO.search(oSearch);
    }

    @Override
    public List<Catalogo> obtenerCatalogo(String codigo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigo);
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addSortAsc("nombre");
        return oCatalogoDAO.search(oSearch);
    }

    @Override
    public List<Localidad> obtenerLocalidad(int localidadId) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("id", localidadId);
        return oLocalidadDAO.search(oSearch);
    }

    @Override
    public List<Localidad> buscarLocalidades() {
        Search oSearch = new Search();
        oSearch.addSortAsc("nombre");
        return oLocalidadDAO.search(oSearch);
    }

    @Override
    public List<Catalogo> buscarGruposSanguineos() {
        Search oSearch = new Search();
        oSearch.addFilterEqual("refTipoCatalogo", "014");
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addSortAsc("nombre");
        return oCatalogoDAO.search(oSearch);
    }

    @Override
    public List<Catalogo> buscarEscolaridades() {
        Search oSearch = new Search();
        oSearch.addFilterEqual("refTipoCatalogo", "012");
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addSortAsc("nombre");
        return oCatalogoDAO.search(oSearch);
    }

    @Override
    public List<Catalogo> buscarEtnias() {
        Search oSearch = new Search();
        oSearch.addFilterEqual("refTipoCatalogo", "013");
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addSortAsc("nombre");
        return oCatalogoDAO.search(oSearch);
    }

    @Override
    public List<Catalogo> buscarEstadosCiviles() {
        Search oSearch = new Search();
        oSearch.addFilterEqual("refTipoCatalogo", "011");
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addSortAsc("nombre");
        return oCatalogoDAO.search(oSearch);
    }

    @Override
    public List<Pais> buscarPaises() {
        Search oSearch = new Search();
        oSearch.addSortAsc("nombre");
        return oPaisDAO.search(oSearch);
    }

    @Override
    public List<Pais> buscarPaisesExtranjeros() {
        Search oSearch = new Search();
        oSearch.addFilterNotEqual("codigoAlfa2", "NI");
        oSearch.addSortAsc("nombre");
        return oPaisDAO.search(oSearch);
    }

    @Override
    public List<DivisionPolitica> buscarDepartamentos() {
        return oPersonaDAO.buscarDepartamentos();
    }

    @Override
    public List<DivisionPolitica> buscarMunicipios(int codigoDepartamento) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("divisionesPoliticas.id", codigoDepartamento);
        oSearch.addSortAsc("nombre");
        return oDivisionPoliticaDAO.search(oSearch);
    }

    @Override
    public List<DivisionPolitica> obtenerDepartamentoPorMunicipio(String codigoMunicipio) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigoMunicipio);
        oSearch.addSortAsc("nombre");
        return oDivisionPoliticaDAO.search(oSearch);
    }

    private void validar_guardar(Persona oPersona) throws BusinessException {
        //Validando si es un nuevo registro
        if (oPersona == null) {

            Search oSearch = new Search();
            oSearch.addFilterEqual("dni", oPersona != null ? oPersona.getDni() : null);
            List<Persona> lista = oPersonaDAO.search(oSearch);

            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Esta Cédula ya se encuentra registrada");
            }

        }
        //Validando si se esta editando el registro
        else {

            Search oSearch = new Search();
            oSearch.addFilterEqual("dni", oPersona.getDni());
            oSearch.addFilterNotEqual("id", oPersona.getId());
            List<Persona> lista = oPersonaDAO.search(oSearch);

            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Esta Cédula ya se encuentra registrada");
            }

        }
    }

    @Override
    public List<Pais> buscarPais(String codigo) {
        Search oSearch = new Search();
        oSearch.addFilterNotEqual("codigoAlfa2", codigo);
        oSearch.addSortAsc("nombre");
        return oPaisDAO.search(oSearch);
    }

    @Override
    public List<Catalogo> buscarProfesion() {
        Search oSearch = new Search();
        oSearch.addFilterEqual("refTipoCatalogo", "020");
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addSortAsc("nombre");
        return oCatalogoDAO.search(oSearch);
    }

}