package ni.gob.inss.siit.model.type.preinversion;

import java.math.BigDecimal;

/**
 * Creado por mgzelaya el 07/12/2015.
 */
public class GastosDetalleType {

    private Integer id;
    private Integer txtAnio;
    private Integer txtDescripcionAnio;
    private BigDecimal txtGastoOperativo;
    private BigDecimal txtGastoMantenimiento;
    private BigDecimal txtTotal;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTxtAnio() {
        return txtAnio;
    }

    public void setTxtAnio(Integer txtAnio) {
        this.txtAnio = txtAnio;
    }

    public Integer getTxtDescripcionAnio() {
        return txtDescripcionAnio;
    }

    public void setTxtDescripcionAnio(Integer txtDescripcionAnio) {
        this.txtDescripcionAnio = txtDescripcionAnio;
    }

    public BigDecimal getTxtGastoOperativo() {
        return txtGastoOperativo;
    }

    public void setTxtGastoOperativo(BigDecimal txtGastoOperativo) {
        this.txtGastoOperativo = txtGastoOperativo;
    }

    public BigDecimal getTxtGastoMantenimiento() {
        return txtGastoMantenimiento;
    }

    public void setTxtGastoMantenimiento(BigDecimal txtGastoMantenimiento) {
        this.txtGastoMantenimiento = txtGastoMantenimiento;
    }

    public BigDecimal getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(BigDecimal txtTotal) {
        this.txtTotal = txtTotal;
    }
}
