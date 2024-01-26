package ni.gob.inss.barista.businesslogic.serviceImpl.core.catalogo;

import ni.gob.inss.barista.businesslogic.service.ServiceException;
import ni.gob.inss.barista.businesslogic.service.core.catalogo.CatalogoAplicacion;
import ni.gob.inss.barista.businesslogic.service.core.catalogo.CatalogoAplicacionHelperService;
import ni.gob.inss.barista.businesslogic.service.core.catalogo.CatalogoAplicacionItem;
import ni.gob.inss.barista.businesslogic.service.core.catalogo.ListaCatalogo;
import ni.gob.inss.barista.businesslogic.service.util.xml.XMLConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación de servicio para catálogos de la aplicación</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/03/2015
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Service
public class CatalogoAplicacionHelperServiceImpl implements CatalogoAplicacionHelperService {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    ListaCatalogo listaCatalogo;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Autowired
    private XMLConverter oXMLConverter;

    @PostConstruct
    public void init() throws IOException {
        ClassPathResource cpr = new ClassPathResource("catalogos.xml");
        FileInputStream catalogosXml = new FileInputStream(cpr.getFile());
        listaCatalogo = (ListaCatalogo) oXMLConverter.convertFromXMLFileToObject(catalogosXml);
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Override
    public CatalogoAplicacion obtenerCatalogo(String codigo) throws ServiceException {
        CatalogoAplicacion oCatalogoAplicacion = null;
        for (CatalogoAplicacion c : listaCatalogo.getCatalogos()) {
            if (c.getCodigo().equals(codigo.trim())) {
                oCatalogoAplicacion = c;
                break;
            }
        }
        if (oCatalogoAplicacion == null) {
            throw new ServiceException("El catálogo con código: " + codigo + " no fue encontrado.");
        }
        return oCatalogoAplicacion;
    }

    @Override
    public CatalogoAplicacionItem obtenerCatalogoItem(String codigoCatalogo, String codigoCatalogoItem) throws ServiceException {
        CatalogoAplicacionItem oCatalogoAplicacionItem = null;
        CatalogoAplicacion oCatalogoAplicacion = obtenerCatalogo(codigoCatalogo);

        for (CatalogoAplicacionItem ci : oCatalogoAplicacion.getItems()) {
            if (ci.getCodigo().equals(codigoCatalogoItem.trim())) {
                oCatalogoAplicacionItem = ci;
                break;
            }
        }

        if (oCatalogoAplicacionItem == null) {
            throw new ServiceException("El catálogo ítem con código: " + codigoCatalogoItem + " no fue encontrado.");
        }
        return oCatalogoAplicacionItem;
    }
}