package ni.gob.inss.barista.view.notification;

import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Establecimientos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 23/07/2014
 * @since 1.0 *
 */
public abstract class UsuarioNotificacion {
    private static Map<String, Integer> listaUsuario = new HashMap<>();

    public static Map<String, Integer> getListaUsuario() {
        return listaUsuario;
    }

    public static void ingresarUsuario(Usuario oUsuario) {
        if (listaUsuario.containsKey(oUsuario.obtenerUsernameNombreCompleto())) {
            listaUsuario.put(oUsuario.obtenerUsernameNombreCompleto(),
                    listaUsuario.get(oUsuario.obtenerUsernameNombreCompleto()) + 1);
        } else {
            listaUsuario.put(oUsuario.obtenerUsernameNombreCompleto(), 1);
        }
    }

    public static void removerUsuario(Usuario oUsuario) {
        if (listaUsuario.containsKey(oUsuario.obtenerUsernameNombreCompleto())) {
            int cantidadConexion = listaUsuario.get(oUsuario.obtenerUsernameNombreCompleto());
            if (cantidadConexion > 1) {
                listaUsuario.put(oUsuario.obtenerUsernameNombreCompleto(), listaUsuario.get(oUsuario.obtenerUsernameNombreCompleto()) - 1);
            } else {
                listaUsuario.remove(oUsuario.obtenerUsernameNombreCompleto());
            }
        }
    }

    public static void removerUsuarioLista(Usuario oUsuario) {
        if (listaUsuario.containsKey(oUsuario.obtenerUsernameNombreCompleto())) {
            listaUsuario.remove(oUsuario.obtenerUsernameNombreCompleto());
        }
    }

    public static boolean usuarioConectado(Usuario oUsuario) {
        return listaUsuario.containsKey(oUsuario.obtenerUsernameNombreCompleto());
    }
}