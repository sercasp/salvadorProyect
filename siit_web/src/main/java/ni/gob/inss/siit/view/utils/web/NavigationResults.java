package ni.gob.inss.siit.view.utils.web;


public class NavigationResults {

    private NavigationResults(){}
	
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



}
