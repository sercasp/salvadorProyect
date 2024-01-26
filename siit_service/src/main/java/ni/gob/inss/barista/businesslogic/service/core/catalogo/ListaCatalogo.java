package ni.gob.inss.barista.businesslogic.service.core.catalogo;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Lista de Catálogos de aplicación</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 18/03/2015
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public class ListaCatalogo {
    private List<CatalogoAplicacion> catalogos = new ArrayList<CatalogoAplicacion>();

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    public ListaCatalogo() {
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public ListaCatalogo(List<CatalogoAplicacion> catalogos) {
        this.catalogos = catalogos;
    }

    public List<CatalogoAplicacion> getCatalogos() {
        return catalogos;
    }

    public void setCatalogos(List<CatalogoAplicacion> catalogos) {
        this.catalogos = catalogos;
    }
}