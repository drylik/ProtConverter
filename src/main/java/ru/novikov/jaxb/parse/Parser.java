package ru.novikov.jaxb.parse;

import java.io.InputStream;
import java.io.OutputStream;

public interface Parser {
    Object getObject(InputStream in, Class c);
    void saveObject(OutputStream out, Object o);
}
