package ru.novikov.jaxb.parse;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.novikov.jaxb.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TestParsing {

    private static final String xml = "<Envelope xmlns:urn=\"wsapi:Payment\" xmlns:uts=\"wsapi:Utils\">\n" +
            "<Body>\n" +
            "<urn:sendPayment>\n" +
            "<token>001234</token>\n" +
            "<cardNumber>811626834823422</cardNumber>\n" +
            "<requestId>2255086658</requestId>\n" +
            "<amount>100000.00</amount>\n" +
            "<currency>RUB</currency>\n" +
            "<uts:account type=\"source\">009037269229</uts:account>\n" +
            "<uts:account type=\"destination\">088127269229</uts:account>\n" +
            "<page>1</page>\n" +
            "<field id=\"0\" value=\"0800\" />\n" +
            "<field id=\"11\" value=\"000001\" />\n" +
            "<field id=\"70\" value=\"301\" />\n" +
            "</urn:sendPayment>\n" +
            "</Body>\n" +
            "</Envelope>";
    private Envelope envelope;

    @Before
    public void initialize() {
        envelope = new Envelope();
        List<Account> acc = new ArrayList<>();
        acc.add(new Account("source", "009037269229"));
        acc.add(new Account("destination", "088127269229"));
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("0", "0800"));
        fields.add(new Field("11", "000001"));
        fields.add(new Field("70", "301"));
        envelope.add(new Payment("001234", "811626834823422", "2255086658", "100000.00", "RUB", acc, 1, fields));
    }

    @Test
    public void marshallingTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Parser parser = new JaxbParser();
        parser.saveObject(out, envelope);
        String str = new String(out.toByteArray());
        Assert.assertEquals(envelope, parser.getObject(IOUtils.toInputStream(str, StandardCharsets.UTF_8), Envelope.class));
    }

    @Test
    public void unmarshallingTest() throws IOException {
        InputStream in = IOUtils.toInputStream(xml, StandardCharsets.UTF_8);
        Parser parser = new JaxbParser();
        Envelope env = (Envelope) parser.getObject(in, Envelope.class);
        Assert.assertEquals(envelope, env);
    }
}
