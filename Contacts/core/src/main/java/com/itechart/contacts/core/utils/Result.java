package com.itechart.contacts.core.utils;

import com.itechart.contacts.core.entities.Contact;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    private List<Contact> contactList;
    private int allElementsCount;
    /*private int currentPage;
    private boolean haveNext;
    private boolean havePrevious;*/

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

    /*public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isHaveNext() {
        return haveNext;
    }

    public void setHaveNext(boolean haveNext) {
        this.haveNext = haveNext;
    }

    public boolean isHavePrevious() {
        return havePrevious;
    }

    public void setHavePrevious(boolean havePrevious) {
        this.havePrevious = havePrevious;
    }*/
}
