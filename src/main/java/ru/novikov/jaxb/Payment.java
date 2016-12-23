package ru.novikov.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sendPayment", namespace = "wsapi:Payment")
public class Payment {

    @XmlElement
    private String token;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @XmlElement
    private String cardNumber;
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @XmlElement
    private String requestId;
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @XmlElement
    private String amount;
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @XmlElement
    private String currency;
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement(namespace = "wsapi:Utils")
    private List<Account> account;
    public List<Account> getAccount() {
        return account;
    }
    public void setAccount(List<Account> account) {
        this.account = account;
    }

    @XmlElement
    private int page;
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }

    @XmlElement
    private List<Field> field;
    public List<Field> getField() {
        return field;
    }
    public void setField(List<Field> field) {
        this.field = field;
    }

    public Payment() {
        account = new ArrayList<>();
        field = new ArrayList<>();
    }

    public Payment(String token, String cardNumber, String requestId, String amount, String currency, List<Account> account, int page, List<Field> field) {
        this.token = token;
        this.cardNumber = cardNumber;
        this.requestId = requestId;
        this.amount = amount;
        this.currency = currency;
        this.account = account;
        this.page = page;
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (page != payment.page) return false;
        if (token != null ? !token.equals(payment.token) : payment.token != null) return false;
        if (cardNumber != null ? !cardNumber.equals(payment.cardNumber) : payment.cardNumber != null) return false;
        if (requestId != null ? !requestId.equals(payment.requestId) : payment.requestId != null) return false;
        if (amount != null ? !amount.equals(payment.amount) : payment.amount != null) return false;
        if (currency != null ? !currency.equals(payment.currency) : payment.currency != null) return false;
        if (account != null ? !account.equals(payment.account) : payment.account != null) return false;
        return field != null ? field.equals(payment.field) : payment.field == null;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + page;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        return result;
    }
}
