package com.itechart.contacts.core.person.dto;

import com.itechart.contacts.core.attachment.dto.SaveAttachmentDto;
import com.itechart.contacts.core.phone.dto.SavePhoneDto;
import lombok.Data;

import java.util.List;

@Data
public class SavePersonDto {

    private long id;
    private String familyStatus;
    private String currentJob;
    private String streetHouseApart;
    private String index;
    private String birthDate;
    private String surName;
    private String middleName;
    private String name;
    private String gender;
    private String citizenship;
    private String webSite;
    private String email;
    private String country;
    private String city;

    List<SavePhoneDto> phones;
    List<SaveAttachmentDto> attachments;

    List<Long> deletePhones;
    List<Long> deleteAttaches;
    public SavePersonDto() {}
}
