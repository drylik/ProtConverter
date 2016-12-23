package ru.novikov.jaxb.parse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.OutputStream;

public class JaxbParser implements Parser {

    private static final Logger log = LogManager.getLogger(JaxbParser.class);

    public Object getObject(InputStream in, Class c) {
        JAXBContext context;
        Object object;
        try {
            context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            object = unmarshaller.unmarshal(in);
            return object;
        } catch (JAXBException e) {
            log.log(Level.ERROR, "Error during unmarshalling", e);
            return null;
        }
    }

    public void saveObject(OutputStream out, Object o) {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(o, out);
        } catch (JAXBException e) {
            log.log(Level.ERROR, "Error during marshalling", e);
        }
    }
}