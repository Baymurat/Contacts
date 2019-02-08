package com.itechart.contacts.core.phone.dto;

import lombok.Data;

@Data
public class SavePhoneDto {

    private long id;
    private int phoneNumber;
    private int codeOfCountry;
    private int codeOfOperator;
    private String type;
    private String comments;
    private long personId;

    public SavePhoneDto() {}
}
