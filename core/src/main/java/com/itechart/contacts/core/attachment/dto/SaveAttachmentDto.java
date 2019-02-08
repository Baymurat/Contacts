package com.itechart.contacts.core.attachment.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class SaveAttachmentDto {

    private long id;
    private String fileName;
    private String comments;
    private Date loadDate;
    private long personId;

    public SaveAttachmentDto() {}
}
