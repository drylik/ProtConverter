package ru.novikov.jaxb.parse;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Parser {
    Object getObject(InputStream in, Class c) throws JAXBException;
    void saveObject(OutputStream out, Object o);
}
