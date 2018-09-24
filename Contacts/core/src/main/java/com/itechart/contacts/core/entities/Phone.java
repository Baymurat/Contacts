package com.itechart.contacts.core.entities;

/**
 * Created by Admin on 12.09.2018
 */
public class Phone {
    private int phoneNumber;
    private int codeOfCountry;
    private int codeOfOperator;
    private String type;
    private String comments;

    public Phone() {}

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
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

    public int getCodeOfCountry() {
        return codeOfCountry;
    }

    public void setCodeOfCountry(int codeOfCountry) {
        this.codeOfCountry = codeOfCountry;
    }

    public int getCodeOfOperator() {
        return codeOfOperator;
    }

    public void setCodeOfOperator(int codeOfOperator) {
        this.codeOfOperator = codeOfOperator;
    }
}
