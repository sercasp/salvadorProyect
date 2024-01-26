package ni.gob.inss.barista.businesslogic.service.core;

import ni.gob.inss.barista.model.entity.catalogo.Catalogo;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de servicio para catálogos generales</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 28/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface CatalogoGeneralService {
    List<Catalogo> obtenerCatalogo(String refTipoCatalogo);

    List<Catalogo> obtenerCatalogo(String refTipoCatalogo, String codigoCatalogo);
}