package ni.gob.inss.barista.view.notification.channel;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/07/2014
 * @since 1.0 *
 */
//@Singleton
//TODO
//@PushEndpoint("/general")
public class CanalGeneral {

    //@OnMessage(encoders = {JSONEncoder.class})
    public String onMessage(String message) {
        return message;
    }


}