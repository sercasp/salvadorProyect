package ni.gob.inss.siit.model.type.programacion;

import java.io.Serializable;

/**
 * Credo por jvillanueva1 el 08/03/2016.
 * Modificado por jvillanueva 18/11/2020.
 */
public class ListaProyectoIndicador implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private Integer id;
    private String nombreIndicador;
    private String medioVerificacion;

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public ListaProyectoIndicador(Integer id, String nombreIndicador, String medioVerificacion) {
        this.id = id;
        this.nombreIndicador = nombreIndicador;
        this.medioVerificacion = medioVerificacion;
    }

    /**
     * ***********************************************************************************
     * PROPIEDADES
     * ************************************************************************************
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreIndicador() {
        return nombreIndicador;
    }

    public void setNombreIndicador(String nombreIndicador) {
        this.nombreIndicador = nombreIndicador;
    }

    public String getMedioVerificacion() {
        return medioVerificacion;
    }

    public void setMedioVerificacion(String medioVerificacion) {
        this.medioVerificacion = medioVerificacion;
    }
}
