package com.itechart.contacts.core.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 12.09.2018
 */
public class Attachment implements Serializable {
    private int id;
    private String fileName;
    private String comments;
    private Date loadDate;
    private int persons_id;

    public Attachment() {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public String toString() {
        return this.fileName + "   " + this.comments;
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
