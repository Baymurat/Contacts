package com.itechart.contacts.core.attachment.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class AttachmentDto {

    private long id;
    private String fileName;
    private String comments;
    private Date loadDate;
    private Long personId;

    public AttachmentDto() {}
}
