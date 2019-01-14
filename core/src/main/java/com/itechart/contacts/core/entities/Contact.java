package com.itechart.contacts.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 11.09.2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact implements Serializable{

    private String name;
    private String surName;
    private String middleName;
    private String gender;
    private String citizenship;
    private String familyStatus;
    private String webSite;
    private String email;
    private String currentJob;
    private String country;
    private String city;
    private String streetHouseApart;
    private String index;
    private int id;
    private String birthDate;


    private List<Phone> phones;
    private List<Attachment> attachments;

    private List<Integer> deletePhonesList;
    private List<Integer> deleteAttachmentsList;


    public Contact() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetHouseApart() {
        return streetHouseApart;
    }

    public void setStreetHouseApart(String streetHouseApart) {
        this.streetHouseApart = streetHouseApart;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return this.id + this.name + this.attachments;
    }

    public List<Integer> getDeletePhonesList() {
        return deletePhonesList;
    }

    public void setDeletePhonesList(List<Integer> deletePhonesList) {
        this.deletePhonesList = deletePhonesList;
    }

    public List<Integer> getDeleteAttachmentsList() {
        return deleteAttachmentsList;
    }

    public void setDeleteAttachmentsList(List<Integer> deleteAttachmentsList) {
        this.deleteAttachmentsList = deleteAttachmentsList;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}