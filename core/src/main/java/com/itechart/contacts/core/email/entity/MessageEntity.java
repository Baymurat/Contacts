package com.itechart.contacts.core.email.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageEntity {

    private String theme;
    private String content;

    public MessageEntity() {}
}
