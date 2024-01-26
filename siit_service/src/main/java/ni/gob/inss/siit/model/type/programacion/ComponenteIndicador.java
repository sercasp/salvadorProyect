package ni.gob.inss.siit.model.type.programacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Creado por jvillanueva1 el 08/03/2016.
 * Modificado por jvillanueva 18/11/2020.
 */
public class ComponenteIndicador implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private String nombreComponente;

    private List<ListaProyectoIndicador> listaProyectoIndicador;

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public ComponenteIndicador(String nombreComponente) {
        this.nombreComponente = nombreComponente;
        listaProyectoIndicador = new ArrayList<>();
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

    public List<ListaProyectoIndicador> getListaProyectoIndicador() {
        return listaProyectoIndicador;
    }

    public void setListaProyectoIndicador(List<ListaProyectoIndicador> listaProyectoIndicador) {
        this.listaProyectoIndicador = listaProyectoIndicador;
    }
}
