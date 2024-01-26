package ni.gob.inss.barista.view.bean.application;

import lombok.Data;

import javax.inject.Named;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * BEAN DE MENSAJES DE COMPONENTES DE DIALOGO</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/19/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
public class DialogText {
    private final String deleteConfirmationTextHeader = "Confimación";
    private final String deleteConfirmationTextContent = "Seguro de eliminar?";
    private final String deleteConfirmationTextYesButton = "Aceptar";
    private final String deleteConfirmationTextNoButton = "Cancelar";
    private final String desbloquearConfirmationTextContent = "¿Seguro que desea Desbloquear este Usuario?";
    private final String desbloquearConfirmationTextYesButton = "¿Seguro que desea Desbloquear este Usuario?";
    private final String desbloquearConfirmationTextNoButton = "¿Seguro que desea Desbloquear este Usuario?";
}
