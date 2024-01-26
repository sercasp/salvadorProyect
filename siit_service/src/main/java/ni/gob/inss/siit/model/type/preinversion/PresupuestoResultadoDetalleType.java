package ni.gob.inss.siit.model.type.preinversion;


import java.math.BigDecimal;

/**
 * Creado por mgzelaya el 15/01/2015.
 */
public class PresupuestoResultadoDetalleType {

    private Integer id;
    private Integer ideaPorIdeaId;
    private Integer perfilPorPerfilId;
    private String descripcionResultado;
    private String refMonedaPorMonedaCodigo;
    private BigDecimal costoResultado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdeaPorIdeaId() {
        return ideaPorIdeaId;
    }

    public void setIdeaPorIdeaId(Integer ideaPorIdeaId) {
        this.ideaPorIdeaId = ideaPorIdeaId;
    }

    public Integer getPerfilPorPerfilId() {
        return perfilPorPerfilId;
    }

    public void setPerfilPorPerfilId(Integer perfilPorPerfilId) {
        this.perfilPorPerfilId = perfilPorPerfilId;
    }

    public String getDescripcionResultado() {
        return descripcionResultado;
    }

    public void setDescripcionResultado(String descripcionResultado) {
        this.descripcionResultado = descripcionResultado;
    }

    public String getRefMonedaPorMonedaCodigo() {
        return refMonedaPorMonedaCodigo;
    }

    public void setRefMonedaPorMonedaCodigo(String refMonedaPorMonedaCodigo) {
        this.refMonedaPorMonedaCodigo = refMonedaPorMonedaCodigo;
    }

    public BigDecimal getCostoResultado() {
        return costoResultado;
    }

    public void setCostoResultado(BigDecimal costoResultado) {
        this.costoResultado = costoResultado;
    }
}
