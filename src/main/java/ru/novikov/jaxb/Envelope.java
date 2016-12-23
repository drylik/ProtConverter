package ru.novikov.jaxb;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Envelope")
//@XmlType(propOrder = {"Body"})
public class Envelope {

    @XmlElementWrapper(name = "Body")
    @XmlElement(name ="sendPayment", namespace = "wsapi:Payment")
    private List<Payment> body;

    public List<Payment> getBody() {
        return body;
    }

    public void setBody(List<Payment> body) {
        this.body = body;
    }

    public Envelope() {
        body = new ArrayList<>();
    }

    public void add(Payment payment) {
        body.add(payment);
     }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Envelope envelope = (Envelope) o;

        return body != null ? body.equals(envelope.body) : envelope.body == null;
    }
}
