package ni.gob.inss.barista.businesslogic.serviceImpl.core.seguridad;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * <b>LINUS</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Establecimientos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 19/11/2014
 * @since 1.0 *
 */
@Component
public class SecurityUtils {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private EncryptionMethod encryptionMethod;
    private String salt;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @PostConstruct
    private void init() throws IOException {
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL urlResource = classLoader.getResource("config.properties");

        if (urlResource != null) {
            props.load(urlResource.openStream());
            encryptionMethod = getEnumValueEncryptionMethod(props.getProperty("seguridad.metodoEncriptacion"));
            salt = props.getProperty("seguridad.salt");
        }
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private EncryptionMethod getEnumValueEncryptionMethod(String property) {
        return EncryptionMethod.valueOf(property.trim());
    }

    public Boolean checkpw(String plaintext, String hashed) {
        boolean result;
        if (encryptionMethod == EncryptionMethod.BCrypt) {
            result = BCrypt.checkpw(plaintext, hashed);
        } else if (encryptionMethod == EncryptionMethod.MD5) {
            Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
            result = passwordEncoder.isPasswordValid(plaintext, hashed, salt);
        } else if (encryptionMethod == EncryptionMethod.SHA) {
            ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
            result = passwordEncoder.isPasswordValid(plaintext, hashed, salt);
        } else {
            result = BCrypt.checkpw(plaintext, hashed);
        }
        return result;
    }

    public String encode(String plaintext) {
        String hashed;
        if (encryptionMethod == EncryptionMethod.BCrypt) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            hashed = passwordEncoder.encode(plaintext);
        } else if (encryptionMethod == EncryptionMethod.MD5) {
            Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
            hashed = passwordEncoder.encodePassword(plaintext, salt);
        } else if (encryptionMethod == EncryptionMethod.SHA) {
            ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
            hashed = passwordEncoder.encodePassword(plaintext, salt);
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            hashed = passwordEncoder.encode(plaintext);
        }
        return hashed;
    }

    public String generateKey() {
        //return KeyGenerators.string().generateKey();
        String pswd = "";
        String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 6; i++) {
            pswd += (CARACTERES.charAt((int) (Math.random() * CARACTERES.length())));
        }
        return pswd;
    }
}