package com.itechart.contacts.core.utils;

import com.itechart.contacts.core.entities.Contact;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    private List<Contact> contactList;
    private int allElementsCount;

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public int getAllElementsCount() {
        return allElementsCount;
    }

    public void setAllElementsCount(int allElementsCount) {
        this.allElementsCount = allElementsCount;
    }
}
