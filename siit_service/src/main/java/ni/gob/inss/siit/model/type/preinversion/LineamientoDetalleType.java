package ni.gob.inss.siit.model.type.preinversion;

/**
 * Creado por jvillanueva1 el 21/01/2016.
 */
public class LineamientoDetalleType {

    private Integer id;
    private String refLineamiento;
    private String nombreLineamiento;
    private Integer organismoId;
    private String nombreOrganismo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefLineamiento() {
        return refLineamiento;
    }

    public void setRefLineamiento(String refLineamiento) {
        this.refLineamiento = refLineamiento;
    }

    public String getNombreLineamiento() {
        return nombreLineamiento;
    }

    public void setNombreLineamiento(String nombreLineamiento) {
        this.nombreLineamiento = nombreLineamiento;
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
