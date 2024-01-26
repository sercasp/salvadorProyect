package ni.gob.inss.barista.businesslogic.service.core.catalogo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Catálogo de aplicación</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 18/03/2015
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Data
public class CatalogoAplicacion {

    private String codigo;
    private String descripcion;
    private List<CatalogoAplicacionItem> items = new ArrayList<CatalogoAplicacionItem>();

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    public CatalogoAplicacion() {
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public CatalogoAplicacion(String codigo, String descripcion, List<CatalogoAplicacionItem> items) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.items = items;
    }
}