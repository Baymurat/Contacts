package com.itechart.contacts.web;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.service.SimpleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class CutsomRestController {

    private SimpleService simpleService = new SimpleService();

    @RequestMapping("/fill-index")
    public Map<Integer, Contact> getContacts() {
        return simpleService.getContacts(0);
    }

    @RequestMapping("/delete-record")
    public void delteRecord(@RequestParam(name = "index")int i) {
        simpleService.delete(i);
    }

    @RequestMapping("/add-record")
    public void addRecord(@RequestParam(name = "name") String name, @RequestParam(name = "number") int number, @RequestParam(name = "file") String fileName) {
        Contact contact = new Contact();
        Phone phone = new Phone();
        Attachment attachment = new Attachment();

        contact.setName(name);
        phone.setPhoneNumber(number);
        phone.setPersons_id(4);
        attachment.setFileName(fileName);
        attachment.setPersons_id(4);

        simpleService.addRecord(contact, attachment, phone);
    }
}
