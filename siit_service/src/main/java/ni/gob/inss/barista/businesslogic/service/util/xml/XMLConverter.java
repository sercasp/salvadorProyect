package ni.gob.inss.barista.businesslogic.service.util.xml;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Utilidad para serializar xml a objeto y viceversa</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/03/2015
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public class XMLConverter {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public Marshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public void convertFromObjectToXML(Object object, String filepath)
            throws IOException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filepath);
            getMarshaller().marshal(object, new StreamResult(os));
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public Object convertFromXMLToObject(String xmlfile) throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(xmlfile);
            return getUnmarshaller().unmarshal(new StreamSource(is));
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public Object convertFromXMLFileToObject(FileInputStream xmlfile) throws IOException {
        FileInputStream is = xmlfile;
        try {
            return getUnmarshaller().unmarshal(new StreamSource(is));
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}