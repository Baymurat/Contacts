package com.itechart.contacts.core.phone.dto;

import com.itechart.contacts.core.person.dto.PersonDto;
import lombok.Data;

@Data
public class SavePhoneDto {

    private int phoneNumber;
    private int codeOfCountry;
    private int codeOfOperator;
    private String type;
    private String comments;

    private PersonDto contact;

    public SavePhoneDto() {}
}
