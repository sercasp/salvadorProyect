package ni.gob.inss.barista.view.bean.application;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Enumerador para valores de configuración</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/6/2014
 * @since 1.0 *
 */
public enum ConfigValue {
    YES(true), NO(false), BY_USER(true);

    private boolean value;

     ConfigValue(boolean b) {
         value = b;
    }

    public boolean getValue() {
        return value;
    }

    public static String toString(ConfigValue oConfigValue){
        String returnValue = "";

        if(oConfigValue == YES){
            returnValue="SI";
        }
        else if(oConfigValue == NO){
            returnValue="NO";
        }
        else if(oConfigValue == BY_USER){
            returnValue="Por Usuario";
        }
        return returnValue;
    }
}
