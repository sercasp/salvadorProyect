package ni.gob.inss.siit.model.type.seguimiento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Creado por mgzelaya el 22/03/2016.
 */
public class ProyectoDesembolsoEjecucionType implements Serializable {

    private Integer idProgramacion;
    private Integer idDesembolso;
    private Integer numeroDesembolso;
    private Date fechaDesembolsoProgramado;
    private BigDecimal montoDesembolsadoProgramado;
    private Date fechaDesembolsoEjecucion;
    private Timestamp fechaRegistro;
    private BigDecimal montoDesembolsadoEjecucion;
    private String observacion;
    private String noCalendario;

    public Integer getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Integer idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public Integer getIdDesembolso() {
        return idDesembolso;
    }

    public void setIdDesembolso(Integer idDesembolso) {
        this.idDesembolso = idDesembolso;
    }

    public Integer getNumeroDesembolso() {
        return numeroDesembolso;
    }

    public void setNumeroDesembolso(Integer numeroDesembolso) {
        this.numeroDesembolso = numeroDesembolso;
    }

    public Date getFechaDesembolsoProgramado() {
        return fechaDesembolsoProgramado;
    }

    public void setFechaDesembolsoProgramado(Date fechaDesembolsoProgramado) {
        this.fechaDesembolsoProgramado = fechaDesembolsoProgramado;
    }

    public BigDecimal getMontoDesembolsadoProgramado() {
        return montoDesembolsadoProgramado;
    }

    public void setMontoDesembolsadoProgramado(BigDecimal montoDesembolsadoProgramado) {
        this.montoDesembolsadoProgramado = montoDesembolsadoProgramado;
    }

    public Date getFechaDesembolsoEjecucion() {
        return fechaDesembolsoEjecucion;
    }

    public void setFechaDesembolsoEjecucion(Date fechaDesembolsoEjecucion) {
        this.fechaDesembolsoEjecucion = fechaDesembolsoEjecucion;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public BigDecimal getMontoDesembolsadoEjecucion() {
        return montoDesembolsadoEjecucion;
    }

    public void setMontoDesembolsadoEjecucion(BigDecimal montoDesembolsadoEjecucion) {
        this.montoDesembolsadoEjecucion = montoDesembolsadoEjecucion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNoCalendario() {
        return noCalendario;
    }

    public void setNoCalendario(String noCalendario) {
        this.noCalendario = noCalendario;
    }
}
