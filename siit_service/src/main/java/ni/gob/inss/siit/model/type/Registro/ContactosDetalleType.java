package ni.gob.inss.siit.model.type.Registro;

/**
 * Created by mbarrios  25/04/2017.
 */
public class ContactosDetalleType {
    private Integer Id;
    private String txtNombreContacto;
    private String txtTipoContacto;
    private String txtTelefono;
    private String txtCelular;
    private String txtCorreo;
    private String txtCodigoTipoContacto;
    private String refSexo;

    public String getTxtNombreContacto() {
        return txtNombreContacto;
    }

    public void setTxtNombreContacto(String txtNombreContacto) {
        this.txtNombreContacto = txtNombreContacto;
    }

    public String getTxtTipoContacto() {
        return txtTipoContacto;
    }

    public void setTxtTipoContacto(String txtTipoContacto) {
        this.txtTipoContacto = txtTipoContacto;
    }

    public String getTxtCodigoTipoContacto() {
        return txtCodigoTipoContacto;
    }

    public void setTxtCodigoTipoContacto(String txtCodigoTipoContacto) {
        this.txtCodigoTipoContacto = txtCodigoTipoContacto;
    }

    public String getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(String txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public String getTxtCelular() {
        return txtCelular;
    }

    public void setTxtCelular(String txtCelular) {
        this.txtCelular = txtCelular;
    }

    public String getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(String txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public String getRefSexo() {
        return refSexo;
    }

    public void setRefSexo(String refSexo) {
        this.refSexo = refSexo;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}


