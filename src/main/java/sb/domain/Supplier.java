package sb.domain;

import java.sql.Date;

public class Supplier {

    private int id;
    private String name;
    private String address;
    private int payerNumber;
    private int registrationCertificateNumber;
    private Date registrationDate;
    private String phoneNumber;

    public Supplier() {
    }

    public Supplier(int id, String name, String address, int payerNumber, int registrationCertificateNumber, Date registrationDate, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.payerNumber = payerNumber;
        this.registrationCertificateNumber = registrationCertificateNumber;
        this.registrationDate = registrationDate;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPayerNumber() {
        return payerNumber;
    }

    public void setPayerNumber(int payerNumber) {
        this.payerNumber = payerNumber;
    }

    public int getRegistrationCertificateNumber() {
        return registrationCertificateNumber;
    }

    public void setRegistrationCertificateNumber(int registrationCertificateNumber) {
        this.registrationCertificateNumber = registrationCertificateNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
