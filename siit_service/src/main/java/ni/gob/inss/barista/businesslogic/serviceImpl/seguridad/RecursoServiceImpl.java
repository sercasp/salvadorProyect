package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.ServiceException;
import ni.gob.inss.barista.businesslogic.service.seguridad.RecursoService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.MenuDAO;
import ni.gob.inss.barista.model.dao.seguridad.RecursoDAO;
import ni.gob.inss.barista.model.dao.seguridad.RecursoUsuarioDAO;
import ni.gob.inss.barista.model.dao.seguridad.ReporteDAO;
import ni.gob.inss.barista.model.entity.personal.Miembro;
import ni.gob.inss.barista.model.entity.seguridad.Menu;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.model.entity.seguridad.RecursoUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Reporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para Recursos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class RecursoServiceImpl implements RecursoService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final RecursoDAO oRecursoDAO;
    private final RecursoUsuarioDAO oRecursoUsuarioDAO;
    private final MenuDAO oMenuDAO;
    private final ReporteDAO oReporteDAO;

    @Autowired
    public RecursoServiceImpl(RecursoDAO oRecursoDAO, RecursoUsuarioDAO oRecursoUsuarioDAO, MenuDAO oMenuDAO, ReporteDAO oReporteDAO) {
        this.oRecursoDAO = oRecursoDAO;
        this.oRecursoUsuarioDAO = oRecursoUsuarioDAO;
        this.oMenuDAO = oMenuDAO;
        this.oReporteDAO = oReporteDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Recurso obtenerRecursoPorId(int id) throws EntityNotFoundException {
        return oRecursoDAO.find(id);
    }

    @Transactional
    public Recurso obtener(int id) throws EntityNotFoundException {
        return oRecursoDAO.find(id);
    }

    @Transactional
    @Override
    public void agregar(Recurso oRecurso) throws BusinessException, DAOException {
        validar_guardar(oRecurso);
        oRecursoDAO.save(oRecurso);
    }

    @Transactional
    @Override
    public void actualizar(Recurso oRecurso) throws BusinessException, DAOException {
        validar_guardar(oRecurso);
        oRecursoDAO.update(oRecurso);
    }

    @Transactional
    @Override
    public void eliminar(Recurso oRecurso) throws BusinessException, DAOException {
        validar_eliminar(oRecurso);
        oRecursoDAO.remove(oRecurso);
    }

    @Transactional
    public List<Miembro> obtenerMiembros() {
        return oRecursoDAO.obtenerMiembros();
    }

    @Transactional
    @Override
    public List<Recurso> buscar(String prBuscar, String tipo) {
        Search oSearch = new Search();
        prBuscar = prBuscar == null ? "" : prBuscar;

        if (!prBuscar.equals("")) {
            oSearch.addFilterAnd(
                    Filter.ilike("nombre", "%" + prBuscar + "%"),
                    Filter.equal("tipo", tipo)
            );
        } else {
            oSearch.addFilterEqual("tipo", tipo);
        }
        oSearch.addFilterILike("nombre", "%" + prBuscar + "%");
        oSearch.addFilterEqual("tipo", tipo);
        oSearch.addSort("nombre", false);
        return oRecursoDAO.search(oSearch);
    }

    @Override
    @Transactional
    public Recurso buscarPorCodigo(String codigo) throws ServiceException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigo);
        List<Recurso> listaRecursos = oRecursoDAO.search(oSearch);
        if (listaRecursos.size() == 0) {
            throw new ServiceException("Recurso no encontrado: " + codigo);
        }
        return listaRecursos.get(0);
    }

    @Transactional
    public List<Map<String, Object>> secuenciaCodigo() {
        return oRecursoDAO.secuenciaCodigo();
    }

    public void validar_eliminar(Recurso oRecurso) throws BusinessException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("recursosByRecursoId.id", oRecurso.getId());
        List<Menu> lista = oMenuDAO.search(oSearch);

        //Validando que el código no este registrado
        if (lista.size() > 0) {
            throw new BusinessException("Este recurso está asociado a un Menú y no puede eliminarse");
        }
        Search oSearchR = new Search();
        oSearchR.addFilterEqual("recursoPorRecursoId.id", oRecurso.getId());
        List<Reporte> listaReportesConAutorizacion = oReporteDAO.search(oSearchR);

        if (listaReportesConAutorizacion.size() > 0) {
            throw new BusinessException("Este recurso está asociado a un Reporte y no puede eliminarse");
        }

        Search oSearchRU = new Search();
        oSearchRU.addFilterEqual("recursoId", oRecurso.getId());
        List<RecursoUsuario> listaRecursonUsuarios = oRecursoUsuarioDAO.search(oSearchRU);
        if (listaRecursonUsuarios.size() > 0) {
            throw new BusinessException("Este recurso está asociado a un usuario y no puede eliminarse");
        }

        if (listaReportesConAutorizacion.size() > 0) {
            throw new BusinessException("Este recurso está asociado a un Reporte y no puede eliminarse");
        }
    }

    private void validar_guardar(Recurso oRecurso) throws BusinessException {
        //Validando si es un nuevo registro
        if (oRecurso.getId() == null) {
            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oRecurso.getCodigo());
            List<Recurso> lista = oRecursoDAO.search(oSearch);

            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este CÓDIGO ya se encuentra registrado");
            }

            //Validando el nombre
            Search oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oRecurso.getNombre().trim());
            List<Recurso> listaValidacionNombre = oRecursoDAO.search(oSearchValidacionNombre);

            //Validando que el código no este registrado
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este NOMBRE ya se encuentra registrado");
            }

            //Validando la URL
            Search oSearchValidacionUrl = new Search();
            oSearchValidacionUrl.addFilterILike("url", oRecurso.getUrl().trim());
            List<Recurso> listaValidacionUrl = oRecursoDAO.search(oSearchValidacionUrl);

            //Validando que el código no este registrado
            if (listaValidacionUrl.size() > 0) {
                throw new BusinessException("Esta URL ya se encuentra registrada en otro recurso");
            }
        }
        //Validando si se esta editando el registro
        else {
            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oRecurso.getCodigo());
            oSearch.addFilterNotEqual("id", oRecurso.getId());
            List<Recurso> lista = oRecursoDAO.search(oSearch);

            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este CÓDIGO ya se encuentra registrado");
            }
            //Validando el nombre
            Search oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oRecurso.getNombre().trim());
            oSearchValidacionNombre.addFilterNotEqual("id", oRecurso.getId());
            List<Recurso> listaValidacionNombre = oRecursoDAO.search(oSearchValidacionNombre);

            //Validando que el código no este registrado
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este NOMBRE ya se encuentra registrado");
            }

            //Validando la URL
            Search oSearchValidacionUrl = new Search();
            oSearchValidacionUrl.addFilterILike("url", oRecurso.getUrl().trim());
            oSearchValidacionUrl.addFilterNotEqual("id", oRecurso.getId());
            List<Recurso> listaValidacionUrl = oRecursoDAO.search(oSearchValidacionUrl);

            //Validando que el código no este registrado
            if (listaValidacionUrl.size() > 0) {
                throw new BusinessException("Esta URL ya se encuentra registrada en otro recurso");
            }
        }
    }
}