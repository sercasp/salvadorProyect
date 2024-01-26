package ni.gob.inss.barista.view.bean.application;

import lombok.Data;

import javax.inject.Named;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 22/07/2014
 * @since 1.0 *
 */
@Data
@Named
public class MessageNotification {
    String title;
    String message;
}
