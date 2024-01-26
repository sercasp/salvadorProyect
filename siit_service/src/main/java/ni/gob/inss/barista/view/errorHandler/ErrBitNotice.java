package ni.gob.inss.barista.view.errorHandler;

import airbrake.AirbrakeNotice;
import airbrake.Backtrace;
import airbrake.BacktraceLine;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.barista.view.bean.application.ConfigApplicationBean;
import ni.gob.inss.barista.view.utils.web.FacesUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * ERROR PARA ENVIAR A ERRBIT</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/27/2014
 * @since 1.0 *
 */
@Named
public class ErrBitNotice {

    private static final Logger LOGGER = Logger.getLogger(ErrBitNotice.class);

    private AirbrakeNotice oAirbrakeNotice;
    private Usuario oUsuario;
    private Backtrace oBacktrace;
    private String enviromentName;
    private String apiKey;
    private String errbitHost;
    private String errorMessage;
    private String errorClass;
    private String targetErrorClass;
    private String projectRoot;
    private String errorUrl;

    /***************************************************************************************/
    // DEPENDENCIAS
    /***************************************************************************************/

    @Autowired
    ConfigApplicationBean oConfigApplicationBean;


    public void setErrorData(String targetErrorClass,String errorUrl,Usuario usuarioActual,Exception e){
        if(oConfigApplicationBean.isErrbitHabilitado()) {
            this.errbitHost = oConfigApplicationBean.getErrbitHostPort();
            this.apiKey = oConfigApplicationBean.getErrbitApiKey();
            this.projectRoot = oConfigApplicationBean.getProjectRoot();
            this.enviromentName = oConfigApplicationBean.getNombreEntorno();
            this.targetErrorClass = targetErrorClass;
            this.errorUrl = errorUrl;
            this.oUsuario = usuarioActual;
            errorMessage = e.getMessage();
            errorClass = e.getClass().getSimpleName();
            ConstruirBackTrace(e);
            getNotice();
        }

    }

    private void ConstruirBackTrace(Exception e){
        List<String> backTraceList = new ArrayList<String>();
        String line = "";

        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            line = toBacktrace(stackTraceElement.getClassName(), LinusFileName(stackTraceElement) ,stackTraceElement.getLineNumber(), stackTraceElement.getMethodName());
            backTraceList.add(line);
        }
        oBacktrace = new Backtrace(backTraceList);
    }



    private String LinusFileName(StackTraceElement stackTraceElement){

        String fileName;

        if(oConfigApplicationBean.getMavenModuleCount().equals("1")){
            if(stackTraceElement.getClassName().contains(oConfigApplicationBean.getProjectRoot())){
                fileName = "[PROJECT_ROOT]/src/main/java/" + stackTraceElement.getClassName().replaceAll("\\.","/") + ".java";

            }else{
                fileName = stackTraceElement.getFileName();
            }
        }else{
            if(stackTraceElement.getClassName().contains(oConfigApplicationBean.getProjectRoot()+".view")){
                fileName = "[PROJECT_ROOT]/"+oConfigApplicationBean.getProjectRoot()+"_web/src/main/java/" + stackTraceElement.getClassName().replaceAll("\\.","/") + ".java";
            }
            else if(stackTraceElement.getClassName().contains(oConfigApplicationBean.getProjectRoot()+".model") || stackTraceElement.getClassName().contains(oConfigApplicationBean.getProjectRoot()+".businesslogic")) {
                fileName = "[PROJECT_ROOT]/"+oConfigApplicationBean.getProjectRoot()+"_service/src/main/java/" + stackTraceElement.getClassName().replaceAll("\\.","/") + ".java";
            }else{
                fileName = stackTraceElement.getFileName();
            }
        }


        return fileName;
    }



    private String toBacktrace(final String className, final String fileName, final int lineNumber, final String methodName) {
        return new BacktraceLine(className, fileName, lineNumber, methodName).toString();
    }


    private AirbrakeNotice getNotice(){

        HttpServletRequest oRequest = FacesUtils.getServletRequest();

        //REQUEST
        Map<String, Object> request = new HashMap<>();

        Map<String, String[]> mapParameter =  oRequest.getParameterMap();
        Set set = mapParameter.entrySet();

        for (Object aSet : set) {
            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) aSet;
            request.put(entry.getKey(), entry.getValue()[0]);
        }
        //FIN REQUEST

        //SESSION
        Map<String, Object> session = new HashMap<>();
        if(oUsuario!=null){
            session.put("Usuario ID", oUsuario.getId());
            session.put("Usuario", oUsuario.obtenerUsernameNombreCompleto());
        }
        //FIN SESSION

        //ENVIROMENT
        Map<String, Object> enviroment = new HashMap<>();
        List<String> headerNames = Collections.list((Enumeration<String>)oRequest.getHeaderNames());
        for (String headerName : headerNames) {
            enviroment.put("HTTP_" + headerName.toUpperCase().replaceAll("\\-", "_"), Collections.list((Enumeration<String>) oRequest.getHeaders(headerName)).get(0));
        }

        enviroment.put("AUTH_TYPE", oRequest.getAuthType() == null ? "" : oRequest.getAuthType());
        enviroment.put("CONTENT_LENGTH", oRequest.getContentLength());
        enviroment.put("CONTENT_TYPE", oRequest.getContentType() == null ? "" : oRequest.getContentType());
        //enviroment.put("DOCUMENT_ROOT", oRequest.getRealPath("/") == null ? "" : oRequest.getRealPath("/"));
        enviroment.put("PATH_INFO", oRequest.getPathInfo() == null ? "" : oRequest.getPathInfo());
        enviroment.put("PATH_TRANSLATED", oRequest.getPathTranslated() == null ? "" : oRequest.getPathTranslated());
        enviroment.put("QUERY_STRING", oRequest.getQueryString() == null ? "" : oRequest.getQueryString());
        enviroment.put("REMOTE_ADDR", oRequest.getRemoteAddr() == null ? "" : oRequest.getRemoteAddr());
        enviroment.put("REMOTE_HOST", oRequest.getRemoteHost() == null ? "" : oRequest.getRemoteHost());
        enviroment.put("REMOTE_USER", oRequest.getRemoteUser() == null ? "" : oRequest.getRemoteUser());
        enviroment.put("REQUEST_METHOD", oRequest.getMethod() == null ? "" : oRequest.getMethod());
        enviroment.put("SCRIPT_NAME", oRequest.getServletPath() == null ? "" : oRequest.getServletPath());
        enviroment.put("SERVER_NAME", oRequest.getServerName() == null ? "" : oRequest.getServerName());
        enviroment.put("SERVER_PORT", oRequest.getServerPort());
        enviroment.put("SERVER_PROTOCOL", oRequest.getProtocol() == null ? "" : oRequest.getProtocol());

        //FIN ENVIROMENT

        List<String> enviromentfilter  = new ArrayList<String>();
        enviromentfilter.add("");

        oAirbrakeNotice = new AirbrakeNotice(apiKey,projectRoot,enviromentName, errorMessage,errorClass,oBacktrace, request, session, enviroment, enviromentfilter,true, errorUrl,targetErrorClass);

        return oAirbrakeNotice;

    }


    public String getServerName(){
        String serverName = "No especificado";
        try {
            serverName =  InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            LOGGER.error(e);
        }
        return serverName;
    }


    public AirbrakeNotice getAirbrakeNotice() {
        return oAirbrakeNotice;
    }

    public Usuario getUsuario() {
        return oUsuario;
    }

    public void sendError(){
        if(oConfigApplicationBean.isErrbitHabilitado()){
            ErrBitNotifier oErrBitNotifier = new ErrBitNotifier(errbitHost);
            oErrBitNotifier.notify(this);
        }
    }


}
