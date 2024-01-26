package ni.gob.inss.barista.businesslogic.service.core.catalogo;

import lombok.Data;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Catálogo Item de la aplicación</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/03/2015
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Data
public class CatalogoAplicacionItem {
    private String codigo;
    private String valor;
    private int orden;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */

    public CatalogoAplicacionItem() {
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public CatalogoAplicacionItem(String codigo, String valor, int orden) {
        this.codigo = codigo;
        this.valor = valor;
        this.orden = orden;
    }
}