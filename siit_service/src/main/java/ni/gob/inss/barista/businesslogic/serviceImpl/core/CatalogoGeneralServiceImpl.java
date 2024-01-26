package ni.gob.inss.barista.businesslogic.serviceImpl.core;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.core.CatalogoGeneralService;
import ni.gob.inss.barista.model.dao.catalogos.CatalogoDAO;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de interfaz de servicio para catálogos generales</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 28/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class CatalogoGeneralServiceImpl implements CatalogoGeneralService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    final CatalogoDAO oCatalogoDAO;

    @Autowired
    public CatalogoGeneralServiceImpl(CatalogoDAO oCatalogoDAO) {
        this.oCatalogoDAO = oCatalogoDAO;
    }

    /**
     * Devuelve una lista de catálogos activos
     *
     * @param refTipoCatalogo código de tipo de cátalogo
     * @return lista de catálogos por tipo
     * <p>
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */

    @Override
    public List<Catalogo> obtenerCatalogo(String refTipoCatalogo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addFilterEqual("refTipoCatalogo", refTipoCatalogo);
        return oCatalogoDAO.search(oSearch);
    }

    /**
     * Devuelve una lista de catálogos activos y el del código que se pasa como parámetro aunque este pasivo
     *
     * @param refTipoCatalogo código de tipo de cátalogo
     * @param codigoCatalogo  código de catálogo
     * @return lista de catálogos
     */
    @Override
    public List<Catalogo> obtenerCatalogo(String refTipoCatalogo, String codigoCatalogo) {
        Search oSearch = new Search();
        oSearch.addFilterOr(
                Filter.equal("pasivo", false),
                Filter.equal("codigo", codigoCatalogo)
        );
        oSearch.addFilterEqual("refTipoCatalogo", refTipoCatalogo);
        return oCatalogoDAO.search(oSearch);
    }
}