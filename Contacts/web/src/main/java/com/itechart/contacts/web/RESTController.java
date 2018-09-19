package com.itechart.contacts.web;

import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.service.SimpleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;



@RestController
public class RESTController {

    private SimpleService simpleService = new SimpleService();

    @RequestMapping("/fill-index")
    public Collection<Contact> getContacts() {
        String asd = "";
        return simpleService.getContacts();
    }

}
