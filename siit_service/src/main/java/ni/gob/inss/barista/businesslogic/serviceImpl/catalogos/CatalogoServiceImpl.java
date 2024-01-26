package ni.gob.inss.barista.businesslogic.serviceImpl.catalogos;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.catalogos.CatalogoService;
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
 * Implementación de servicio para Catálogos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Service
public class CatalogoServiceImpl implements CatalogoService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final TipoCatalogoDAO oTipoCatalogoDAO;
    private CatalogoDAO oCatalogoDAO;

    public CatalogoServiceImpl(CatalogoDAO oCatalogoDAO, TipoCatalogoDAO oTipoCatalogoDAO) {
        this.oCatalogoDAO = oCatalogoDAO;
        this.oTipoCatalogoDAO = oTipoCatalogoDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Autowired
    public CatalogoServiceImpl(TipoCatalogoDAO oTipoCatalogoDAO) {
        this.oTipoCatalogoDAO = oTipoCatalogoDAO;
    }

    @Transactional
    public Catalogo obtener(int id) throws EntityNotFoundException {
        return oCatalogoDAO.find(id);
    }

    @Transactional
    public void agregar(Catalogo oCatalogo) throws DAOException, BusinessException {
        //verificando si el codigo existe
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", oCatalogo.getCodigo());
        List<Catalogo> listaCatalogos = oCatalogoDAO.search(oSearch);
        if (listaCatalogos.size() > 0)
            throw new BusinessException("El código ya existe lo ocupa el catálogo: " +
                    listaCatalogos.get(0).getCodigo() + "-" + listaCatalogos.get(0).getNombre());
        oCatalogoDAO.saveUpper(oCatalogo);
    }

    @Transactional
    public void actualizar(Catalogo oCatalogo) throws DAOException {
        oCatalogoDAO.updateUpper(oCatalogo);
    }

    @Transactional
    public void eliminar(Catalogo oCatalogo) {
        oCatalogoDAO.remove(oCatalogo);
    }

    @Transactional
    @Override
    public List<Catalogo> buscar(String refTipoCatalogo, String prBuscar) {
        Search oSearch = new Search();
        //oSearch.addFilterILike("nombre", "%" + prBuscar + "%");
        oSearch.addFilterOr(
                Filter.ilike("nombre", "%" + prBuscar + "%"),
                Filter.ilike("codigo", "%" + prBuscar + "%")
        );
        oSearch.addSortAsc("nombre");
        if (refTipoCatalogo == null || refTipoCatalogo.equals("")) {
            return oCatalogoDAO.search(oSearch);
        } else {
            oSearch.addFilterEqual("refTipoCatalogo", refTipoCatalogo);
            return oCatalogoDAO.search(oSearch);
        }
    }

    @Transactional
    @Override
    public List<TiposCatalogo> obtenerTipoCatalogo() {
        Search oSearch = new Search();
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addSortAsc("nombre");
        return oTipoCatalogoDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<TiposCatalogo> obtenerTipoCatalogo(Catalogo oCatalogo) {
        Search oSearch = new Search();
        oSearch.addSortAsc("nombre");

        if (oCatalogo.getId() != null) {
            oSearch.addFilterOr(
                    Filter.equal("pasivo", false),
                    Filter.equal("codigo", oCatalogo.getRefTipoCatalogo())
            );
            return oTipoCatalogoDAO.search(oSearch);
        } else {
            oSearch.addFilterEqual("pasivo", false);
            return oTipoCatalogoDAO.search(oSearch);
        }
    }
}