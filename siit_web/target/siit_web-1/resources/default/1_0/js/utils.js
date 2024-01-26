/**
 * @author Jairo Mendoza A.
 */
function isCedulaValida(txtcedula){
		var cedula = new ValidarCedula();
		cedula.setCedula(txtcedula);
		
		/*tempo = "Cédula a Validar: " + cedula.getCedula() + "\n";
        tempo+= "Prefijo Cédula a Validar: " + cedula.getPrefijoCedula()+ "\n";
        tempo+= "Fecha Cédula a Validar: " + cedula.getFechaCedula()+ "\n";
        tempo+= "Sufijo Cédula a Validar: " + cedula.getSufijoCedula()+ "\n";
        tempo+= "Letra Cédula a Validar: " + cedula.getLetraCedula()+ "\n";
        tempo+= "¿Es Cédula Válida?: " + (cedula.isCedulaValida()?"SI":"NO")+ "\n";
        tempo+= "¿Es Prefijo Válido?: " + (cedula.isPrefijoValido()?"SI":"NO")+ "\n";
        tempo+= "¿Es Fecha Válida?: " + (cedula.isFechaValida()?"SI":"NO")+ "\n";
        tempo+= "¿Es Sufijo Válido?: " + (cedula.isSufijoValido()?"SI":"NO")+ "\n";
        tempo+= "¿Es Letra Válida?: " + (cedula.isLetraValida()?"SI":"NO")+ "\n";*/
		
	if ($('#historia_persona_ref_pais_nacimiento').val() == 'NI' && $('#historia_persona_dni').val() == ''){}
	else if ($('#historia_persona_ref_pais_nacimiento').val() == 'NI' && $('#historia_persona_dni').val() == '______________'){}
	else if ($('#historia_persona_ref_pais_nacimiento').val() == 'NI' && $('#historia_persona_dni').val() != ''){
	  	if (cedula.isCedulaValida()?"SI":"NO" == 'SI'){
     	}
		else{
			tempo= "Cédula Invalida";	
			/*tempo+= "Prefijo: " + cedula.getPrefijoCedula() +" "+ (cedula.isPrefijoValido()?"Correcto":"Incorrecto") + "\n";
	        tempo+= "Fecha: " + cedula.getFechaCedula() +" "+  (cedula.isFechaValida()?"Correcto":"Incorrecto") + "\n" ;
	        tempo+= "Sufijo: " + cedula.getSufijoCedula() +" "+ (cedula.isSufijoValido()?"Correcto":"Incorrecto") + "\n";
	        tempo+= "Letra: " + cedula.getLetraCedula() +" "+ (cedula.isLetraValida()?"Correcto":"Incorrecto") + "\n";*/
	        mostrarMensaje('Error',tempo,'error');
		}
	  } 
	}
 /************************************************************************  
  *************************VALIDA CEDULA**********************************
  ************************************************************************/
	String.prototype.replaceAll=function(s1, s2) {return this.split(s1).join(s2)}
	String.prototype.ltrim=function() {return this.replace(/^\s+/, "");}
	String.prototype.rtrim=function() {return this.replace(/\s+$/, "");}
	String.prototype.trim=function() {return this.rtrim(this.ltrim(this));}

function ValidarCedula(){
    this.LETRAS = "ABCDEFGHJKLMNPQRSTUVWXY";
    this.cedula = null;
	
    /**
     * Fiajr el Número de Cédula
     * 
     * @param cedula Número de Cédula con o sin guiones
     */
    this.setCedula =  function(cedula) {
        this.cedula = cedula.trim().replaceAll("-","");
		
        if(this.cedula == null || this.cedula.length != 14)
            this.cedula = null;
        else
        this.cedula = this.cedula.toUpperCase();
    }
	
    /**
     * Retorna true si la cédula es válida
     * false en caso contrario
     * 
     * @return
     */
    this.isCedulaValida = function() {
        if(!this.isPrefijoValido())
            return false;
            
        if(!this.isFechaValida())
            return false;
            
        if(!this.isSufijoValido())
            return false;
        
        if(!this.isLetraValida())
            return false;
            
        return true;
    }
	
    /**
     * Retorna true si el prefijo de la cédula es válida
     * false en caso contrario
     * 
     * @return
     */
    this.isPrefijoValido = function() {
        var prefijo = this.getPrefijoCedula();
        
        if(prefijo==null) return false;
        
	var re = new RegExp("^[0-9]{3}$");
	if (prefijo.search(re) == -1)
            return false;
			
        return true;
    }
	
    /**
     * Retorna true si la fecha de la cédula es válida
     * false en caso contrario
     * 
     * @return
     */
    this.isFechaValida = function() {
        var fecha = this.getFechaCedula();
        
        if(fecha == null) return false;
        
	var re = new RegExp("^(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])([0-9]{2})$");
	if (fecha.search(re) == -1)
        return false;
            
        return true;
    }
	
    /**
     * Retorna true si el sufijo de la cédula es válida
     * false en caso contrario
     * 
     * @return
     */
    this.isSufijoValido = function() {
        var sufijo = this.getSufijoCedula();
        
        if(sufijo == null) return false;
        
	var re = new RegExp("^[0-9]{4}$");
	if (sufijo.search(re) == -1)
        return false;
        
        return true;
    }
	
    /**
     * Retorna true si la letra de la cédula es válida
     * false en caso contrario
     * 
     * @return
     */
    this.isLetraValida = function() {
        var letraValida = null;
        var letra = this.getLetraCedula();
        
        if(letra == null) return false;
        
        letraValida = this.calcularLetra();
        
        return (letraValida == letra);
	}
	
    /**
     * Retorna un entero que representa la posición en la cadena LETRAS 
     * de la letra final de una cédula
     * 
     * @return
     */
    this.getPosicionLetra = function() {
        var posicionLetra = 0;
        var cedulaSinLetra = this.getCedulaSinLetra();
        var numeroSinLetra = 0;
        
        if(cedulaSinLetra == null) return -1;
        
        numeroSinLetra =  cedulaSinLetra;
                
        posicionLetra = (numeroSinLetra - Math.floor(numeroSinLetra/23.0) * 23);
        
        return posicionLetra;
    }
    
    /**
     * <p>
     * Calcular la letra al final de la cédula nicaraguense.
     * </p><p>
     * Basado en el trabajo de: Arnoldo Suarez Bonilla - arsubo@yahoo.es
     * </p><p>
     * La letra se calcula con el siguiente algoritmo (yo se los envío en SQL).
     * <p>
     * <pre>
     * declare  @cedula      varchar(20),
     *          &#64;val         numeric(13, 0),
     *          &#64;letra       char(1),
     *          &#64;Letras      varchar(20)
     *          
     *          select @Letras = 'ABCDEFGHJKLMNPQRSTUVWXY'
     *          select @cedula = '0012510750012' --PARTE NUMERICA DE LA CEDULA SIN GUIONES
     *          --CALCULO DE LA LETRA DE LA CEDULA   
     *          select @val = convert(numeric(13, 0), @cedula) - floor(convert(numeric(13, 0), @cedula) / 23) * 23
     *          select @letra = SUBSTRING(@Letras, @val + 1, 1)
     *          select @letra
     * </pre> 
     * @return Letra válida de cédula dada
     */
    this.calcularLetra = function() {
        var posicionLetra = this.getPosicionLetra();
        
        if(posicionLetra <0 || posicionLetra >= this.LETRAS.length)
            return '?';
            
        return this.LETRAS.charAt(posicionLetra);
    }

    /**
     * Retorna el Número de Cédula
     * @return 
     */	
    this.getCedula = function() {
        return this.cedula;
    }
	
    /**
     * Retorna el prefijo de la cédula
     * @return
     */
    this.getPrefijoCedula = function() {
        if(this.cedula!=null)
            return this.cedula.substring(0,3);
        else 
            return null;
    }
    
    /**
     * Retorna la fecha en la cédula
     * @return
     */
    this.getFechaCedula = function() {
        if(this.cedula!=null)
            return this.cedula.substring(3,9);
        else 
            return null;
    }
    
    /**
     * Retorna el sufijo en la cédula
     * @return
     */
    this.getSufijoCedula = function() {
        if(this.cedula!=null)
            return this.cedula.substring(9,13);
        else 
            return null;
    }
    
    /**
     * Retorna la letra de la cédula
     * @return
     */
    this.getLetraCedula = function() {
        if(this.cedula!=null)
            return this.cedula.substring(13,14);
        else 
            return null;
    }
	
    /**
     * Retorna la cédula sin la letra de validación
     * @return
     */
    this.getCedulaSinLetra = function() {
        if(this.cedula!=null)
            return this.cedula.substring(0,13);
        else 
            return null;
    }
}


 /************************************************************************  
  *********************FIN VALIDA CEDULA**********************************
  ************************************************************************/

    /*
    Autor: HHERNANDEZ
    Fecha: 16-06-2015
    Descripción: Selecciona el idioma Español como lenguaje por defecto del control Calendar
    */

    PrimeFaces.locales['es'] = {
        closeText: 'Cerrar',
        prevText: 'Anterior',
        nextText: 'Siguiente',
        monthNames: ['Enero','Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun','Jul','Ago','Sep','Oct','Nov','Dic'],
        dayNames: ['Domingo','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado'],
        dayNamesShort: ['Dom','Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
        dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sa'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'Sólo hora',
        timeText: 'Tiempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Fecha actual',
        ampm: false,
        month: 'Mes',
        week: 'Semana',
        day: 'Día',
        allDayText : 'Todo el día'
    };

function grayImage(imageObj, canvas) {
    var context = canvas.getContext('2d');
    var x = 0;
    var y = 0;
    context.drawImage(imageObj, x, y);
    var imageData = context.getImageData(x, y, canvas.width, canvas.height);
    var data = imageData.data;
    for(var i = 0; i < data.length; i += 4) {
        var brightness = 0.34 * data[i] + 0.5 * data[i + 1] + 0.16 * data[i + 2];
        // red
        data[i] = brightness;
        // green
        data[i + 1] = brightness;
        // blue
        data[i + 2] = brightness;
    }
    return imageData;
}