package ni.gob.inss.barista.businesslogic.serviceImpl.catalogos;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.catalogos.TipoCatalogoService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.catalogos.CatalogoDAO;
import ni.gob.inss.barista.model.dao.catalogos.TipoCatalogoDAO;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para Tipos de Catálogos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Service
public class TipoCatalogoServiceImpl implements TipoCatalogoService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final TipoCatalogoDAO oTipoCatalogoDAO;
    private final CatalogoDAO oCatalogoDAO;
   @Autowired
    public TipoCatalogoServiceImpl(TipoCatalogoDAO oTipoCatalogoDAO, CatalogoDAO oCatalogoDAO) {
        this.oTipoCatalogoDAO = oTipoCatalogoDAO;
        this.oCatalogoDAO = oCatalogoDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public TiposCatalogo obtener(int id) throws EntityNotFoundException {
        return oTipoCatalogoDAO.find(id);
    }

    @Transactional
    @Override
    public void agregar(TiposCatalogo oTiposCatalogo) throws BusinessException, DAOException {
        validar(oTiposCatalogo);
        oTipoCatalogoDAO.saveUpper(oTiposCatalogo);
    }

    @Transactional
    @Override
    public void actualizar(TiposCatalogo oTiposCatalogo) throws BusinessException, DAOException {
        validar(oTiposCatalogo);
        oTipoCatalogoDAO.updateUpper(oTiposCatalogo);
    }

    @Transactional
    @Override
    public void eliminar(TiposCatalogo oCatalogo) throws BusinessException {
        //Buscando registros relacionados
        Search oSearch = new Search();
        oSearch.addFilterEqual("refTipoCatalogo", oCatalogo.getCodigo());
        List listaCatalogos = oCatalogoDAO.search(oSearch);
        if (listaCatalogos.size() > 0) {
            throw new BusinessException("Tiene catálogos relacionados no puede ser eliminado");
        }
        oTipoCatalogoDAO.remove(oCatalogo);
    }

    @Transactional
    @Override
    public List<TiposCatalogo> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterILike("nombre", "%" + prBuscar + "%");
        oSearch.addSortAsc("nombre");
        return oTipoCatalogoDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<Catalogo> obtenerCatalogos(TiposCatalogo oTipoCatalogo) {
        //Buscando registros relacionados
        Search oSearch = new Search();
        oSearch.addFilterEqual("refTipoCatalogo", oTipoCatalogo.getCodigo());
        return oCatalogoDAO.search(oSearch);
    }

    private void validar(TiposCatalogo oTiposCatalogo) throws BusinessException {
        //Validando si es un nuevo registro
        if (oTiposCatalogo.getId() == null) {
            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oTiposCatalogo.getCodigo());
            List<TiposCatalogo> lista = oTipoCatalogoDAO.search(oSearch);
            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este código ya se encuentra registrado");
            }
            //Validando el nombre
            Search oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oTiposCatalogo.getNombre().trim());
            List<TiposCatalogo> listaValidacionNombre = oTipoCatalogoDAO.search(oSearchValidacionNombre);

            //Validando que el código no este registrado
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este nombre ya se encuentra registrado");
            }
        }
        //Validando si se esta editando el registro
        else {
            Search oSearch = new Search();
            oSearch.addFilterEqual("codigo", oTiposCatalogo.getCodigo());
            oSearch.addFilterNotEqual("id", oTiposCatalogo.getId());
            List<TiposCatalogo> lista = oTipoCatalogoDAO.search(oSearch);
            //Validando que el código no este registrado
            if (lista.size() > 0) {
                throw new BusinessException("Este código ya se encuentra registrado");
            }
            //Validando el nombre
            Search oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oTiposCatalogo.getNombre().trim());
            oSearchValidacionNombre.addFilterNotEqual("id", oTiposCatalogo.getId());
            List<TiposCatalogo> listaValidacionNombre = oTipoCatalogoDAO.search(oSearchValidacionNombre);
            //Validando que el código no este registrado
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este nombre ya se encuentra registrado");
            }
        }
    }
}