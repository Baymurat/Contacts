package com.itechart.contacts.core.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 12.09.2018
 */
public class Attachment implements Serializable {
    private String fileName;
    private String comments;
    private Date loadDate;

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
}
