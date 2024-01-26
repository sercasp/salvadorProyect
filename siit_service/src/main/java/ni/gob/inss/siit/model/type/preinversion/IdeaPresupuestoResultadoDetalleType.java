package ni.gob.inss.siit.model.type.preinversion;

import java.math.BigDecimal;

/**
 * Creado por jvillanueva1 el 15/01/2016.
 */
public class IdeaPresupuestoResultadoDetalleType {

    private Integer id;
    private String descripcion;
    private BigDecimal costo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

}
