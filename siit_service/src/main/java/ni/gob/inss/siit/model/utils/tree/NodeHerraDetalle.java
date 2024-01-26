package ni.gob.inss.siit.model.utils.tree;

import java.math.BigDecimal;

/**
 * Creaado por abenavidez en 20/04/2017.
 * Modificado por jvillanueva 18/11/2020.
 */
public class NodeHerraDetalle {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private Integer nodo_id;
    private Integer nodo_max;
    private Integer herramienta_id;
    private Integer id;
    private Integer padre;
    private Integer padre_nivel1;
    private Integer padre_nivel2;
    private Integer padre_nivel3;
    private Integer nivel;
    private Integer orden;
    private String orden_concatenado;
    private String nombre;
    private Integer color_id;
    private String codigo_rgb;
    private String tipo;
    private Integer pregunta_configuracion_id;
    private String pregunta_configuracion_codigo;
    private String ref_tipo_seleccion;
    private String ref_tipo_pregunta;
    private Boolean tiene_subpregunta;
    private String nivel_abona;
    private BigDecimal parcial;
    private BigDecimal valor;
    private Boolean respuesta_c;
    private Boolean respuesta_cp;
    private Boolean respuesta_nc;
    private Boolean respuesta_na;
    private String comentario;
    private Boolean activo_comentario;
    private Boolean deshabilitar_na;
    private BigDecimal puntaje;
    private String accion;
    private Integer inspeccion_detalle_id;
    private String respuesta;

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public NodeHerraDetalle(Integer nodo_id, Integer nodo_max, Integer herramienta_id, Integer id, Integer padre, Integer padre_nivel1, Integer padre_nivel2, Integer padre_nivel3, Integer nivel, Integer orden, String orden_concatenado, String nombre, Integer color_id, String codigo_rgb, String tipo, Integer pregunta_configuracion_id,
                            String pregunta_configuracion_codigo, String ref_tipo_seleccion, String ref_tipo_pregunta, Boolean tiene_subpregunta, String nivel_abona, BigDecimal parcial, BigDecimal valor, Boolean respuesta_c, Boolean respuesta_cp, Boolean respuesta_nc,
                            Boolean respuesta_na, String comentario, Boolean activo_comentario, Boolean deshabilitar_na, BigDecimal puntaje, String accion, Integer inspeccion_detalle_id, String respuesta) {
        this.nodo_id = nodo_id;
        this.nodo_max = nodo_max;
        this.herramienta_id = herramienta_id;
        this.id = id;
        this.padre = padre;
        this.padre_nivel1 = padre_nivel1;
        this.padre_nivel2 = padre_nivel2;
        this.padre_nivel3 = padre_nivel3;
        this.nivel = nivel;
        this.orden = orden;
        this.orden_concatenado = orden_concatenado;
        this.nombre = nombre;
        this.color_id = color_id;
        this.codigo_rgb = codigo_rgb;
        this.tipo = tipo;
        this.pregunta_configuracion_id = pregunta_configuracion_id;
        this.pregunta_configuracion_codigo = pregunta_configuracion_codigo;
        this.ref_tipo_seleccion = ref_tipo_seleccion;
        this.ref_tipo_pregunta = ref_tipo_pregunta;
        this.tiene_subpregunta = tiene_subpregunta;
        this.nivel_abona = nivel_abona;
        this.parcial = parcial;
        this.valor = valor;
        this.respuesta_c = respuesta_c;
        this.respuesta_cp = respuesta_cp;
        this.respuesta_nc = respuesta_nc;
        this.respuesta_na = respuesta_na;
        this.comentario = comentario;
        this.activo_comentario = activo_comentario;
        this.deshabilitar_na = deshabilitar_na;
        this.puntaje = puntaje;
        this.accion = accion;
        this.inspeccion_detalle_id = inspeccion_detalle_id;
        this.respuesta = respuesta;
    }

    public NodeHerraDetalle() {
    }

    /**
     * ***********************************************************************************
     * PROPIEDADES
     * ************************************************************************************
     */

    public Integer getHerramienta_id() {
        return herramienta_id;
    }

    public void setHerramienta_id(Integer herramienta_id) {
        this.herramienta_id = herramienta_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getColor_id() {
        return color_id;
    }

    public void setColor_id(Integer color_id) {
        this.color_id = color_id;
    }

    public String getCodigo_rgb() {
        return codigo_rgb;
    }

    public void setCodigo_rgb(String codigo_rgb) {
        this.codigo_rgb = codigo_rgb;
    }

    public BigDecimal getParcial() {
        return parcial;
    }

    public void setParcial(BigDecimal parcial) {
        this.parcial = parcial;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getPadre() {
        return padre;
    }

    public void setPadre(Integer padre) {
        this.padre = padre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getTiene_subpregunta() {
        return tiene_subpregunta;
    }

    public void setTiene_subpregunta(Boolean tiene_subpregunta) {
        this.tiene_subpregunta = tiene_subpregunta;
    }

    public Integer getNodo_id() {
        return nodo_id;
    }

    public void setNodo_id(Integer nodo_id) {
        this.nodo_id = nodo_id;
    }

    public Integer getNodo_max() {
        return nodo_max;
    }

    public void setNodo_max(Integer nodo_max) {
        this.nodo_max = nodo_max;
    }

    public Integer getPregunta_configuracion_id() {
        return pregunta_configuracion_id;
    }

    public void setPregunta_configuracion_id(Integer pregunta_configuracion_id) {
        this.pregunta_configuracion_id = pregunta_configuracion_id;
    }

    public String getPregunta_configuracion_codigo() {
        return pregunta_configuracion_codigo;
    }

    public void setPregunta_configuracion_codigo(String pregunta_configuracion_codigo) {
        this.pregunta_configuracion_codigo = pregunta_configuracion_codigo;
    }

    public String getRef_tipo_seleccion() {
        return ref_tipo_seleccion;
    }

    public void setRef_tipo_seleccion(String ref_tipo_seleccion) {
        this.ref_tipo_seleccion = ref_tipo_seleccion;
    }

    public String getRef_tipo_pregunta() {
        return ref_tipo_pregunta;
    }

    public void setRef_tipo_pregunta(String ref_tipo_pregunta) {
        this.ref_tipo_pregunta = ref_tipo_pregunta;
    }

    public String getNivel_abona() {
        return nivel_abona;
    }

    public void setNivel_abona(String nivel_abona) {
        this.nivel_abona = nivel_abona;
    }

    public Integer getPadre_nivel1() {
        return padre_nivel1;
    }

    public void setPadre_nivel1(Integer padre_nivel1) {
        this.padre_nivel1 = padre_nivel1;
    }

    public Integer getPadre_nivel2() {
        return padre_nivel2;
    }

    public void setPadre_nivel2(Integer padre_nivel2) {
        this.padre_nivel2 = padre_nivel2;
    }

    public Integer getPadre_nivel3() {
        return padre_nivel3;
    }

    public void setPadre_nivel3(Integer padre_nivel3) {
        this.padre_nivel3 = padre_nivel3;
    }

    public String getOrden_concatenado() {
        return orden_concatenado;
    }

    public void setOrden_concatenado(String orden_concatenado) {
        this.orden_concatenado = orden_concatenado;
    }

    public Boolean getRespuesta_c() {
        return respuesta_c;
    }

    public void setRespuesta_c(Boolean respuesta_c) {
        this.respuesta_c = respuesta_c;
    }

    public Boolean getRespuesta_cp() {
        return respuesta_cp;
    }

    public void setRespuesta_cp(Boolean respuesta_cp) {
        this.respuesta_cp = respuesta_cp;
    }

    public Boolean getRespuesta_nc() {
        return respuesta_nc;
    }

    public void setRespuesta_nc(Boolean respuesta_nc) {
        this.respuesta_nc = respuesta_nc;
    }

    public Boolean getRespuesta_na() {
        return respuesta_na;
    }

    public void setRespuesta_na(Boolean respuesta_na) {
        this.respuesta_na = respuesta_na;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Boolean getActivo_comentario() {
        return activo_comentario;
    }

    public void setActivo_comentario(Boolean activo_comentario) {
        this.activo_comentario = activo_comentario;
    }

    public Boolean getDeshabilitar_na() {
        return deshabilitar_na;
    }

    public void setDeshabilitar_na(Boolean deshabilitar_na) {
        this.deshabilitar_na = deshabilitar_na;
    }

    public BigDecimal getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(BigDecimal puntaje) {
        this.puntaje = puntaje;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Integer getInspeccion_detalle_id() {
        return inspeccion_detalle_id;
    }

    public void setInspeccion_detalle_id(Integer inspeccion_detalle_id) {
        this.inspeccion_detalle_id = inspeccion_detalle_id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
