package com.itechart.contacts.core.person.dto;

import lombok.Data;

@Data
public class PersonPreviewDto {
    long id;
    private String surName;
    private String name;
    private String email;
    private String webSite;

    public PersonPreviewDto() {
    }
}
