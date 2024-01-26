package ni.gob.inss.barista.view.bean.session;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <b>LINUS</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Lista de usuarios con sesion</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 20/08/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
public abstract class SessionList implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;

    private static List<Map<String, Object>> userList = new ArrayList<>();

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public static void addUser(String userName, String referencia, String telefono, String nombre, HttpSession session) {
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("username", userName);
        usuario.put("referencia", referencia);
        usuario.put("telefono", telefono);
        usuario.put("sesion", session);
        usuario.put("nombre", nombre);
        userList.add(usuario);
    }

    public static void remove(String userName) {
        userList.removeIf(map -> map.get("username").toString().equals(userName));
    }

    public static boolean contains(String userName) {
        return userList.stream().filter(map -> map.get("username").toString().equals(userName))
                .collect(Collectors.toList()).size() > 0;
    }

    public static HttpSession getSession(String userName) {
        return (HttpSession) userList.stream().filter(map -> map.get("username").toString().equals(userName))
                .collect(Collectors.toList()).get(0).get("sesion");
    }

    public static String getSessionId(String userName) {
        return ((HttpSession) userList.stream().filter(map -> map.get("username").toString().equals(userName))
                .collect(Collectors.toList()).get(0).get("sesion")).getId();
    }

    public static List<Map<String, Object>> getUserList() {
        return userList;
    }
}