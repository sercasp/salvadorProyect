package ni.gob.inss.barista.view.utils.web;

/**
 * <b>LINUS</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * CookieHelper</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 29/08/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
public class NavigationResults {
    /**
     * ******************************************************************************
     * CONSTANTES
     * ******************************************************************************
     **/
    public static final String FAILURE = "/public/error/500.html";
    public static final String LOGIN = "/login.html?faces-redirect=true";
    public static final String LOGIN_NO_REDIRECT = "/login.html";
    public static final String HOME = "/web/index.html?faces-redirect=true";
    public static final String HOME_NO_REDIRECT = "/web/index.html";
    public static final String MODULOS = "/web/views/home/modulos.xhtml";
    public static final String BLANK = "/web/views/home/blank.xhtml";
    public static final String SHOW_CONFIG = "/web/views/home/configuracion.xhtml";
    public static final String MENU_APP = "/menu.html?faces-redirect=true";
    public static final String MENU_APP_NO_REDIRECT = "/menu.html";
    public static final String HOME_MOBILE = "/mobile/index.html?faces-redirect=true";
    public static final String HOME_MOBILE_NO_REDIRECT = "/mobile/index.html";
    public static final String MODULOS_MOBILE = "pm:main?transition=slide";
    public static final String MENU_MOBILE = "pm:menu?transition=slide";
    public static final String PAGE_MOBILE = "pm:page?transition=slide";
    public static final String CHANGE_PASSWORD = "/changePassword.html?faces-redirect=true";
    public static final String CHANGE_PASSWORD_NO_REDIRECT = "/changePassword.html";
    public static final String INVALIDATE_SESSION = "/session.html?faces-redirect=true";
    public static final String SESSION_EXPIRED = "/web/sessionExpired.html??faces-redirect=true";

    private NavigationResults() {
    }
}