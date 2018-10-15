package com.itechart.contacts.web;

import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.service.SimpleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {

    /*@Scheduled(fixedRate = 1000)
    public void task() {
        SimpleService simpleService = new SimpleService();

        String pattern = "yyyy-mm-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String searchParam = dateFormat.format(new Date());

        List<Contact> contacts = simpleService.getContactsByDateBirth(searchParam);

        for (Contact c : contacts) {
            System.out.println(c.getName() + "  " + c.getSurName());
        }
    }*/
}
