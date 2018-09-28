package com.itechart.contacts.core.entities;

/**
 * Created by Admin on 12.09.2018
 */
public class Phone extends Entity {
    private int id;
    private int persons_id;
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

    public String toString() {
        return this.codeOfCountry + " " + this.codeOfOperator + " " + this.phoneNumber + "    " + this.comments;
    }

    public boolean equals(Object o) {
        if (o == null) return  false;
        if (o == this) return  true;
        if (!(o instanceof Phone)) return  false;

        Phone phone = (Phone)o;
        return this.codeOfCountry == phone.codeOfCountry &&
                this.codeOfOperator == phone.codeOfOperator &&
                this.phoneNumber == phone.getPhoneNumber();
    }

    public int hashCode() {
        String s1 = this.codeOfCountry + "";
        String s2 = this.codeOfOperator + "";
        String s3 = this.phoneNumber + "";

        return Integer.parseInt(s1 + s2 + s3);
    }

    public int getPersons_id() {
        return persons_id;
    }

    public void setPersons_id(int persons_id) {
        this.persons_id = persons_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
