package com.itechart.contacts.core.person.dto;

import com.itechart.contacts.core.attachment.dto.AttachmentDto;
import com.itechart.contacts.core.phone.dto.PhoneDto;
import lombok.Data;


import java.util.List;

@Data
public class PersonDto {

    private Long id;
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

    private List<PhoneDto> phones;
    private List<AttachmentDto> attachments;

    public PersonDto() {}

}
