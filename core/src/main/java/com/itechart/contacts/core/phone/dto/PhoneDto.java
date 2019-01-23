package com.itechart.contacts.core.phone.dto;

import com.itechart.contacts.core.person.dto.PersonDto;
import lombok.Data;

@Data
public class PhoneDto {

    private Long id;
    private int phoneNumber;
    private int codeOfCountry;
    private int codeOfOperator;
    private String type;
    private String comments;

    private PersonDto contact;

    public PhoneDto() {}
}
