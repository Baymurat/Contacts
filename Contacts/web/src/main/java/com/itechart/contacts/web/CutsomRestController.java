package com.itechart.contacts.web;

import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.service.SimpleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;


@RestController
public class CutsomRestController {

    private SimpleService simpleService = new SimpleService();

    @RequestMapping("/fill-index")
    public Map<Integer, Contact> getContacts() {
        return simpleService.getContacts(0);
    }

}
