package com.itechart.contacts.core.attachment.dto;

import com.itechart.contacts.core.person.dto.PersonDto;
import lombok.Data;

import java.sql.Date;

@Data
public class SaveAttachmentDto {

    private String fileName;
    private String comments;
    private Date loadDate;

    private PersonDto contact;

    public SaveAttachmentDto() {}
}
