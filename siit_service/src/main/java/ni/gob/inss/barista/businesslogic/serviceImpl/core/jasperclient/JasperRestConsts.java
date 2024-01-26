package ni.gob.inss.barista.businesslogic.serviceImpl.core.jasperclient;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Constantes para restService</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 31/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public class JasperRestConsts {
    static final boolean SHOW_SPEC_MODE = false;

    // SERVER PARAMETERS
    static final String SCHEME = "http";
    static final String SERVER_HANDLE = "/jasperserver";

    //Server paths
    static final String BASE_REST_URL = SERVER_HANDLE + "/rest";
    static final String BASE_REST_URL_V2 = SERVER_HANDLE + "/rest_v2";

    // login parameters
    static final String PARAMETER_USERNAME = "j_username";
    static final String PARAM_PASSWORD = "j_password";

    // SERVER ENTITIES
    static final String SERVICE_LOGIN = "/login";
    static final String RESOURCES_LOCAL_PATH = "resources/";
    static final String SAMPLE_FOLDER_RD = "folder_URI.SAMPLE_REST_FOLDER.xml";
    static final String SAMPLE_IMAGE_RD = "image_URI.JUNIT_NEW_FOLDER.JUNIT_IMAGE_FILE.xml";
    static final String SAMPLE_IMAGE_BIN = "jasperSoftLogo.jpg";
    static final String NEW_SAMPLE_IMAGE_BIN = "jasperSoftLogo_2.jpg";
    static final String REQUEST_PARAMENTER_RD = "ResourceDescriptor";
    static final String RESOURCE = "/resource";
    static final String LOG4J_PATH = "log4j.properties";
}