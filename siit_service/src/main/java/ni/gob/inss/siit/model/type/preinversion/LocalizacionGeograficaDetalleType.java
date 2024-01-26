package ni.gob.inss.siit.model.type.preinversion;

import java.math.BigDecimal;

/**
 * Creado por jvillanueva1 el 23/11/2015.
 */
public class LocalizacionGeograficaDetalleType {

    private Integer Id;
    private String txtRefMunicipio;
    private String txtNombreMunicipio;
    private String txtNombreDepartamento;
    private String txtComunidad;
    private BigDecimal txtLongitud;
    private BigDecimal txtLatitud;
    private String txtRefDepartamento;

    public String getTxtRefDepartamento() {
        return txtRefDepartamento;
    }

    public void setTxtRefDepartamento(String txtRefDepartamento) {
        this.txtRefDepartamento = txtRefDepartamento;
    }

    public String getTxtRefMunicipio() {
        return txtRefMunicipio;
    }

    public void setTxtRefMunicipio(String txtRefMunicipio) {
        this.txtRefMunicipio = txtRefMunicipio;
    }

    public String getTxtComunidad() {
        return txtComunidad;
    }

    public void setTxtComunidad(String txtComunidad) {
        this.txtComunidad = txtComunidad;
    }

    public BigDecimal getTxtLongitud() {
        return txtLongitud;
    }

    public void setTxtLongitud(BigDecimal txtLongitud) {
        this.txtLongitud = txtLongitud;
    }

    public BigDecimal getTxtLatitud() {
        return txtLatitud;
    }

    public void setTxtLatitud(BigDecimal txtLatitud) {
        this.txtLatitud = txtLatitud;
    }

    public String getTxtNombreMunicipio() {
        return txtNombreMunicipio;
    }

    public void setTxtNombreMunicipio(String txtNombreMunicipio) {
        this.txtNombreMunicipio = txtNombreMunicipio;
    }

    public String getTxtNombreDepartamento() {
        return txtNombreDepartamento;
    }

    public void setTxtNombreDepartamento(String txtNombreDepartamento) {
        this.txtNombreDepartamento = txtNombreDepartamento;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
