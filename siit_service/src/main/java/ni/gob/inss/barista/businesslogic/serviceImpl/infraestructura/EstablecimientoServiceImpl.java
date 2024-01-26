package ni.gob.inss.barista.businesslogic.serviceImpl.infraestructura;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.infraestructura.EstablecimientoService;
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
 * DESCRIPCIÓN</br>
 *
 * @author JAIRO HELÍ MENDOZA AGUIRRE
 * @version 1.0, 05/08/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
public class EstablecimientoServiceImpl implements EstablecimientoService {

    private EstablecimientoDAO oEstablecimientoDAO;
    private EntidadDAO oEntidadDAO;

    @Autowired
    public EstablecimientoServiceImpl(EstablecimientoDAO oEstablecimientoDAO, EntidadDAO oEntidadDAO) {
        this.oEstablecimientoDAO = oEstablecimientoDAO;
        this.oEntidadDAO = oEntidadDAO;
    }

    @Transactional
    public Establecimiento obtenerEstablecimientoPorId(int id) throws EntityNotFoundException {
        return oEstablecimientoDAO.find(id);
    }

    @Transactional
    @Override
    public void agregar(Establecimiento oEstablecimiento) throws BusinessException, DAOException {
        validar_guardar(oEstablecimiento);
        oEstablecimientoDAO.saveUpper(oEstablecimiento);
    }

    @Transactional
    @Override
    public void actualizar(Establecimiento oEstablecimiento) throws BusinessException, DAOException {
        validar_guardar(oEstablecimiento);
        oEstablecimientoDAO.updateUpper(oEstablecimiento);
    }

    @Transactional
    @Override
    public void eliminar(Establecimiento oEstablecimiento) throws BusinessException, DAOException {
        validar_eliminar(oEstablecimiento);
        oEstablecimientoDAO.remove(oEstablecimiento);
    }

    @Transactional
    @Override
    public List<Establecimiento> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterILike("nombre", "%" + prBuscar + "%");
        oSearch.addSortAsc("nombre");
        return oEstablecimientoDAO.search(oSearch);
    }

    public void validar_eliminar(Establecimiento oEstablecimiento) throws BusinessException {

        Search oSearch = new Search();
        oSearch.addFilterEqual("establecimientoByEstablecimientoId", oEstablecimiento);
        List<Entidad> lista = oEntidadDAO.search(oSearch);

        //Validando que el Id de Establecimiento se encuentre en Entidad
        if (lista.size() > 0) {
            throw new BusinessException("El Establecimiento está asociado a una Entidad.");
        }
    }

    private void validar_guardar(Establecimiento oEstablecimiento) throws BusinessException {

        //Validando si es un nuevo registro
        if (oEstablecimiento.getId() == null) {

            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oEstablecimiento.getCodigo());
            List<Establecimiento> lista = oEstablecimientoDAO.search(oSearch);

            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este CÓDIGO ya se encuentra registrado.");
            }

            //Validando el Nombre
            Search oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oEstablecimiento.getNombre().trim());
            List<Establecimiento> listaValidacionNombre = oEstablecimientoDAO.search(oSearchValidacionNombre);

            //Validando que el nombre no este registrado
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este NOMBRE ya se encuentra registrado.");
            }

        }
        //Validando si se esta editando el registro
        else {

            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oEstablecimiento.getCodigo());
            oSearch.addFilterNotEqual("id", oEstablecimiento.getId());
            List<Establecimiento> lista = oEstablecimientoDAO.search(oSearch);

            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este CÓDIGO ya se encuentra registrado.");
            }

            //Validando el Nombre
            Search oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oEstablecimiento.getNombre().trim());
            oSearchValidacionNombre.addFilterNotEqual("id", oEstablecimiento.getId());
            List<Establecimiento> listaValidacionNombre = oEstablecimientoDAO.search(oSearchValidacionNombre);

            //Validando que el código no este registrado
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este NOMBRE ya se encuentra registrado.");
            }

        }
    }
}
