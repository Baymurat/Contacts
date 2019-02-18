package com.itechart.contacts.core.phone.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class SavePhoneDto {

    private long id;

    @Min(0)
    private int phoneNumber;

    @Min(0)
    private int codeOfCountry;

    @Min(0)
    private int codeOfOperator;
    private String type;
    private String comments;

    @Min(0)
    private long personId;

    public SavePhoneDto() {
    }
}
