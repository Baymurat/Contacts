package com.itechart.contacts.core.email.service;

import com.itechart.contacts.core.email.dto.MessageDto;
import com.itechart.contacts.core.person.dto.PersonDto;
import com.itechart.contacts.core.person.service.PersonService;
import org.antlr.stringtemplate.StringTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private PersonService personService;
    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(PersonService personService, JavaMailSender emailSender) {
        this.personService = personService;
        this.emailSender = emailSender;
    }

    public void sendMessage(MessageDto messageDto) {

        if (messageDto.getReceivers() != null) {
            for (Integer i : messageDto.getReceivers()) {
                PersonDto currentPerson = personService.getContact((long)i);
                if (currentPerson != null) {
                    StringTemplate template = new StringTemplate(messageDto.getMessageText());
                    template.setAttribute("person", currentPerson);

                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(currentPerson.getEmail());
                    mailMessage.setSubject(messageDto.getMessageSubject());
                    mailMessage.setText(template.toString());
                    emailSender.send(mailMessage);
                }
            }
        }

        if (messageDto.getEmailOfReceivers() != null) {
            for (String email : messageDto.getEmailOfReceivers()) {
                if (!email.isEmpty()) {
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(email);
                    mailMessage.setSubject(messageDto.getMessageSubject());
                    mailMessage.setText(messageDto.getMessageText());
                    emailSender.send(mailMessage);
                }
            }
        }
    }
}
