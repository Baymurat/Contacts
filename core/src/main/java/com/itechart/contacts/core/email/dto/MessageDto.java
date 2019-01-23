package com.itechart.contacts.core.email.dto;

import lombok.Data;


import java.util.List;

@Data
public class MessageDto {
    private List<Integer> receivers;
    private String messageSubject;
    private String messageText;
    private List<String> emailOfReceivers;

    public MessageDto(){}
}