package ni.gob.inss.siit.model.type.preinversion;

/**
 * Creado por jvillanueva1 el 23/11/2015.
 */
public class ActoresInvolucradosDetalleType {

    private Integer Id;
    private String txtActorInvolucrado;
    private String txtCaracteristicaCapacidad;
    private String txtInteresExpectativa;
    private String txtRol;

    public String getTxtActorInvolucrado() {
        return txtActorInvolucrado;
    }

    public void setTxtActorInvolucrado(String txtActorInvolucrado) {
        this.txtActorInvolucrado = txtActorInvolucrado;
    }

    public String getTxtCaracteristicaCapacidad() {
        return txtCaracteristicaCapacidad;
    }

    public void setTxtCaracteristicaCapacidad(String txtCaracteristicaCapacidad) {
        this.txtCaracteristicaCapacidad = txtCaracteristicaCapacidad;
    }

    public String getTxtInteresExpectativa() {
        return txtInteresExpectativa;
    }

    public void setTxtInteresExpectativa(String txtInteresExpectativa) {
        this.txtInteresExpectativa = txtInteresExpectativa;
    }

    public String getTxtRol() {
        return txtRol;
    }

    public void setTxtRol(String txtRol) {
        this.txtRol = txtRol;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
