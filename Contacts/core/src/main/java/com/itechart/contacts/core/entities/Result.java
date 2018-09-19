package com.itechart.contacts.core.entities;

import java.util.Collection;

/**
 * Created by Admin on 12.09.2018
 */
@Deprecated
public class Result {
    /*private Contact contactdsad;
    private Attachment attachments;
    private Phone phones;*/

    /** TRIAL */
    private Collection<Contact> contactCollection;
    private Collection<Attachment> attachmentCollection;
    private Collection<Phone> phoneCollection;
    /** TRIAL*/

    public Result() {

    }

    /*public Contact getContactdsad() {
        return contactdsad;
    }

    public void setContactdsad(Contact contact) {
        this.contactdsad = contact;
    }

    public Attachment getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachment attachments) {
        this.attachments = attachments;
    }

    public Phone getPhones() {
        return phones;
    }

    public void setPhones(Phone phones) {
        this.phones = phones;
    }*/

    /** TRIAL !*/
    public Collection<Contact> getContactCollection() {
        return contactCollection;
    }

    public void setContactCollection(Collection<Contact> contactCollection) {
        this.contactCollection = contactCollection;
    }

    public Collection<Attachment> getAttachmentCollection() {
        return attachmentCollection;
    }

    public void setAttachmentCollection(Collection<Attachment> attachmentCollection) {
        this.attachmentCollection = attachmentCollection;
    }

    public Collection<Phone> getPhoneCollection() {
        return phoneCollection;
    }

    public void setPhoneCollection(Collection<Phone> phoneCollection) {
        this.phoneCollection = phoneCollection;
    }
    /** TRIAL ^*/
}
