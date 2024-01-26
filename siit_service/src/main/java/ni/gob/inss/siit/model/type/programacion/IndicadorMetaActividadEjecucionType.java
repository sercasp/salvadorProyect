package ni.gob.inss.siit.model.type.programacion;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Creado por mgzelaya el 22/03/2016.
 * Modificado por jvillanueva 18/11/2020.
 */
public class IndicadorMetaActividadEjecucionType implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private Integer id;
    private String indicador;
    private String periodo;
    private String actividad;
    private BigDecimal avancefisico;
    private BigDecimal avancefinanciero;
    private String avancecualitativo;
    private String causaincumplimiento;
    private String planmedida;

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

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public BigDecimal getAvancefisico() {
        return avancefisico;
    }

    public void setAvancefisico(BigDecimal avancefisico) {
        this.avancefisico = avancefisico;
    }

    public BigDecimal getAvancefinanciero() {
        return avancefinanciero;
    }

    public void setAvancefinanciero(BigDecimal avancefinanciero) {
        this.avancefinanciero = avancefinanciero;
    }

    public String getAvancecualitativo() {
        return avancecualitativo;
    }

    public void setAvancecualitativo(String avancecualitativo) {
        this.avancecualitativo = avancecualitativo;
    }

    public String getCausaincumplimiento() {
        return causaincumplimiento;
    }

    public void setCausaincumplimiento(String causaincumplimiento) {
        this.causaincumplimiento = causaincumplimiento;
    }

    public String getPlanmedida() {
        return planmedida;
    }

    public void setPlanmedida(String planmedida) {
        this.planmedida = planmedida;
    }
}
