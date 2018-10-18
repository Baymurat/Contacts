package com.itechart.contacts.web;

import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.service.SimpleService;
import com.itechart.contacts.core.utils.email.CustomMessageHolder;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 10000)
    public void task() throws Exception {
        SimpleService simpleService = new SimpleService();
        Date date = new Date(System.currentTimeMillis());

        List<Contact> contacts = simpleService.getContactsByDateBirth(date);
        if (contacts != null && contacts.size() > 0) {
            StringBuilder receivers = new StringBuilder();

            for (Contact c : contacts) {
                receivers.append(c.getEmail());
                receivers.append("  ");
            }

            List<String> admin = new ArrayStack<>();
            admin.add("ADMIN_EMAIL");

            CustomMessageHolder messageHolder = new CustomMessageHolder();
            messageHolder.setMessageTheme("Birthday");
            messageHolder.setMessageText(receivers.toString());
            messageHolder.setReceivers(admin);

            simpleService.sendEmail(messageHolder);
        }
    }
}
