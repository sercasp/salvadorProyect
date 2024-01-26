package ni.gob.inss.siit.model.type.programacion;

/**
 * Creado por  jvillanueva1 el 09/03/2016.
 * Modificado por jvillanueva 18/11/2020.
 */
public class ListaProyectoSupuesto {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private Integer id;
    private String nombreSupuesto;

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public ListaProyectoSupuesto(Integer id, String nombreSupuesto) {
        this.id = id;
        this.nombreSupuesto = nombreSupuesto;
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

    public String getNombreSupuesto() {
        return nombreSupuesto;
    }

    public void setNombreSupuesto(String nombreSupuesto) {
        this.nombreSupuesto = nombreSupuesto;
    }
}
