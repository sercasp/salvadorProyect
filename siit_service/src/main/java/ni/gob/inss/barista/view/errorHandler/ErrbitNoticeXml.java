package ni.gob.inss.barista.view.errorHandler;


import airbrake.AirbrakeNotice;
import airbrake.BacktraceLine;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.Map;
import java.util.Map.Entry;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * CONVIERTE AIRBRAKENOTICE A XML CON TODOS LOS VALORES POSIBLES</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/28/2014
 * @since 1.0 *
 */
public class ErrbitNoticeXml {


    private final StringBuilder stringBuilder = new StringBuilder();

    public ErrbitNoticeXml(ErrBitNotice oErrBitNotice) {
        AirbrakeNotice notice = oErrBitNotice.getAirbrakeNotice();
        String serverName = oErrBitNotice.getServerName();
        Usuario oUsuario = oErrBitNotice.getUsuario();

        notice("2.2");
        {
            apikey(notice);

            notifier();
            {
                name("airbrake-java");
                version("2.2");
                url("https://github.com/airbrake/airbrake-java");
            }
            end("notifier");

            error();
            {
                tag("class", notice.errorClass());
                tag("message", escapeXml(notice.errorMessage()));

                backtrace();
                {
                    for (final String backtrace : notice.backtrace()) {
                        line(backtrace);
                    }
                }
                end("backtrace");
            }
            end("error");

            if (notice.hasRequest()) {
                addRequest(notice);
            }

            server_environment();
            {
                tag("project-root", notice.projectRoot());
                tag("environment-name", notice.env());
                tag("app-version", "LINUS 1.0-SNAPSHOT - JAVA");
                tag("hostname", serverName);
            }
            end("server-environment");

            if (oUsuario != null) {
                tag("current-user");
                {
                    tag("id", String.valueOf(oUsuario.getId()));
                    tag("name", oUsuario.obtenerNombreCompleto());
                    tag("email", oUsuario.getEmail());
                    tag("username", oUsuario.getUsername());
                }
                end("current-user");
            }

        }
        end("notice");
    }

    public static String escapeXml(String string) {
        if (null == string)
            return "";
        boolean anyCharactersProtected = false;
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            boolean controlCharacter = ch < 32;
            boolean unicodeButNotAscii = ch > 126;
            boolean characterWithSpecialMeaningInXML = ch == '<' || ch == '&'
                    || ch == '>';
            if (characterWithSpecialMeaningInXML || unicodeButNotAscii
                    || controlCharacter) {
                stringBuffer.append("&#").append((int) ch).append(";");
                anyCharactersProtected = true;
            } else {
                stringBuffer.append(ch);
            }
        }
        if (!anyCharactersProtected)
            return string;
        return stringBuffer.toString();
    }

    private void addRequest(AirbrakeNotice notice) {
        request();
        {
            tag("url", notice.url());
            tag("component", notice.component());
            vars("params", notice.request());
            vars("session", notice.session());
            vars("cgi-data", notice.environment());
        }
        end("request");
    }

    private void apikey(AirbrakeNotice notice) {
        tag("api-key");
        {
            append(notice.apiKey());
        }
        end("api-key");
    }

    private void append(String str) {
        stringBuilder.append(str);
    }

    private void backtrace() {
        tag("backtrace");
    }

    private void end(String string) {
        append("</" + string + ">");
    }

    private void error() {
        tag("error");
    }

    private void line(String backtrace) {
        append(new BacktraceLine(backtrace).toXml());
    }

    private void name(String name) {
        tag("name", name);
    }

    private void notice(String string) {
        append("<?xml version=\"1.0\"?>");
        append("<notice version=\"" + string + "\">");
    }

    private void notifier() {
        tag("notifier");
    }

    private void request() {
        tag("request");
    }

    private void server_environment() {
        tag("server-environment");
    }

    private ErrbitNoticeXml tag(String string) {
        append("<" + string + ">");
        return this;
    }

    private void tag(String string, String contents) {
        tag(string).text(contents).end(string);
    }

    private ErrbitNoticeXml text(String string) {
        append("<![CDATA[");
        append(string);
        append("]]>");
        return this;
    }

    public String toString() {
        return stringBuilder.toString();
    }

    private void url(String url) {
        tag("url", url);
    }

    private void vars(String sectionName, Map<String, Object> vars) {
        if (vars.isEmpty()) {
            return;
        }

        tag(sectionName);
        for (Entry<String, Object> var : vars.entrySet()) {
            append("<var key=\"" + var.getKey() + "\">");
            text(var.getValue().toString());
            append("</var>");
        }
        end(sectionName);
    }

    private void version(String version) {
        tag("version", version);
    }
}