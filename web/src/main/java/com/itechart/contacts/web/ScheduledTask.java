package com.itechart.contacts.web;

import com.itechart.contacts.core.email.service.EmailService;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.email.dto.MessageDto;
import com.itechart.contacts.core.person.service.PersonService;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PersonService personService;

    @Scheduled(fixedRate = 10000)
    public void task() throws Exception {
        Date date = new Date(System.currentTimeMillis());

        List<Person> people = personService.findByBirthDate(date);
        if (people != null && people.size() > 0) {
            StringBuilder receivers = new StringBuilder();

            for (Person c : people) {
                receivers.append(c.getEmail());
                receivers.append("  ");
            }

            List<String> admin = new ArrayStack<>();
            admin.add("ADMIN_EMAIL");

            MessageDto messageHolder = new MessageDto();
            messageHolder.setMessageSubject("Birthday");
            messageHolder.setMessageText(receivers.toString());
            messageHolder.setEmailOfReceivers(admin);

            emailService.sendMessage(messageHolder);
        }
    }
}
