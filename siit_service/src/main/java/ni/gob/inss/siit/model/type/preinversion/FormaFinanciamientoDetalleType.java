package ni.gob.inss.siit.model.type.preinversion;

/**
 * Creado por jvillanueva1 el 21/01/2016.
 */
public class FormaFinanciamientoDetalleType {
    private Integer id;
    private String refFormaFinanciamiento;
    private String nombreFormaFinanciamiento;
    private Integer organismoId;
    private String nombreOrganismo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefFormaFinanciamiento() {
        return refFormaFinanciamiento;
    }

    public void setRefFormaFinanciamiento(String refFormaFinanciamiento) {
        this.refFormaFinanciamiento = refFormaFinanciamiento;
    }

    public String getNombreFormaFinanciamiento() {
        return nombreFormaFinanciamiento;
    }

    public void setNombreFormaFinanciamiento(String nombreFormaFinanciamiento) {
        this.nombreFormaFinanciamiento = nombreFormaFinanciamiento;
    }

    public Integer getOrganismoId() {
        return organismoId;
    }

    public void setOrganismoId(Integer organismoId) {
        this.organismoId = organismoId;
    }

    public String getNombreOrganismo() {
        return nombreOrganismo;
    }

    public void setNombreOrganismo(String nombreOrganismo) {
        this.nombreOrganismo = nombreOrganismo;
    }
}
