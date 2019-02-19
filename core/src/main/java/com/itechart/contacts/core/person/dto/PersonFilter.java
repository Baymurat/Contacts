package com.itechart.contacts.core.person.dto;

import lombok.Data;

@Data
public class PersonFilter {

    private String firstAndLastName;
    private String currentJob;
    private long phoneNumber;
}
