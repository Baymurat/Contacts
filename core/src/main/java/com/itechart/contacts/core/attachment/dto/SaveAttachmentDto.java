package com.itechart.contacts.core.attachment.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class SaveAttachmentDto {

    private long id;

    @Size(min = 3, max = 50)
    private String fileName;
    private String comments;
    private Date loadDate;

    @Min(0)
    private long personId;

    public SaveAttachmentDto() {
    }
}
