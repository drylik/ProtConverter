package ru.novikov.jaxb;

import javax.xml.bind.annotation.XmlElement;

public class Payment {

    @XmlElement
    private String token;
    public String getToken() {
        return token;
    }

    @XmlElement
    private int cardNumber;
    public int getCardNumber() {
        return cardNumber;
    }

    @XmlElement
    private int requestId;
    public int getRequestId() {
        return requestId;
    }

    @XmlElement
    private float amount;
    public float getAmount() {
        return amount;
    }

    @XmlElement
    private String currency;
    public String getCurrency() {
        return currency;
    }
}
