package com.itechart.contacts.core.email.service;


import com.itechart.contacts.core.email.dto.MessageDto;

public interface EmailService {
    void sendMessage(MessageDto messageDto);
}
