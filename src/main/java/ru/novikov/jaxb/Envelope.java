package ru.novikov.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Envelope")
@XmlType (propOrder = {"body"})
public class Envelope {

    @XmlElement(name ="sendPayment", namespace = "urn")
    @XmlElementWrapper
    private List<Payment> body;
    public List<Payment> getBody() {
        return body;
    }

    public Envelope() {
        body = new ArrayList<Payment>();
    }
}
