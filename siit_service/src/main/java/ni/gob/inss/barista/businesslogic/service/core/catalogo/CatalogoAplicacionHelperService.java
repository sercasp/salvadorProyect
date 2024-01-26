package ni.gob.inss.barista.businesslogic.service.core.catalogo;

import ni.gob.inss.barista.businesslogic.service.ServiceException;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de servicio para catálogos de la aplicación</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/03/2015
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface CatalogoAplicacionHelperService {

    CatalogoAplicacion obtenerCatalogo(String codigo) throws ServiceException;

    CatalogoAplicacionItem obtenerCatalogoItem(String codigoCatalogo, String codigoCatalogoItem) throws ServiceException;
}