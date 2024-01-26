package ni.gob.inss.siit.model.type.programacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Creado por  jvillanueva1 el 09/03/2016.
 * Modificado por jvillanueva 18/11/2020.
 */
public class ComponenteSupuesto implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private String nombreComponente;

    private List<ListaProyectoSupuesto> listaProyectoSupuesto;

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public ComponenteSupuesto(String nombreComponente) {
        this.nombreComponente = nombreComponente;
        listaProyectoSupuesto = new ArrayList<>();
    }

    /**
     * ***********************************************************************************
     * PROPIEDADES
     * ************************************************************************************
     */
    public String getNombreComponente() {
        return nombreComponente;
    }

    public void setNombreComponente(String nombreComponente) {
        this.nombreComponente = nombreComponente;
    }

    public List<ListaProyectoSupuesto> getListaProyectoSupuesto() {
        return listaProyectoSupuesto;
    }

    public void setListaProyectoSupuesto(List<ListaProyectoSupuesto> listaProyectoSupuesto) {
        this.listaProyectoSupuesto = listaProyectoSupuesto;
    }
}
