package com.itechart.contacts.core.entities;

/**
 * Created by Admin on 12.09.2018
 */
public class Phone {
    private String phoneNumber;
    private String type;
    private String comments;
    private String codeOfCountry;
    private String codeOfOperator;

    public Phone() {}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCodeOfCountry() {
        return codeOfCountry;
    }

    public void setCodeOfCountry(String codeOfCountry) {
        this.codeOfCountry = codeOfCountry;
    }

    public String getCodeOfOperator() {
        return codeOfOperator;
    }

    public void setCodeOfOperator(String codeOfOperator) {
        this.codeOfOperator = codeOfOperator;
    }
}
