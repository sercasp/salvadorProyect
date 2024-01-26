package ni.gob.inss.siit.model.type.preinversion;

/**
 * Creado por jvillanueva1 el 23/11/2015.
 */
public class BeneficiariosDetalleType {

    private Integer Id;
    private String txtBeneficiario;
    private Integer txtCantidad;
    private String txtTipoBeneficiario;
    private String txtCodigoTipoBeneficiario;

    public String getTxtBeneficiario() {
        return txtBeneficiario;
    }

    public void setTxtBeneficiario(String txtBeneficiario) {
        this.txtBeneficiario = txtBeneficiario;
    }

    public Integer getTxtCantidad() {
        return txtCantidad;
    }

    public void setTxtCantidad(Integer txtCantidad) {
        this.txtCantidad = txtCantidad;
    }

    public String getTxtTipoBeneficiario() {
        return txtTipoBeneficiario;
    }

    public void setTxtTipoBeneficiario(String txtTipoBeneficiario) {
        this.txtTipoBeneficiario = txtTipoBeneficiario;
    }

    public String getTxtCodigoTipoBeneficiario() {
        return txtCodigoTipoBeneficiario;
    }

    public void setTxtCodigoTipoBeneficiario(String txtCodigoTipoBeneficiario) {
        this.txtCodigoTipoBeneficiario = txtCodigoTipoBeneficiario;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
