package com.itechart.contacts.web;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.service.SimpleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CutsomRestController {

    private SimpleService simpleService = new SimpleService();

    @RequestMapping("/fill-index")
    public List<Contact> getContacts() {
        return simpleService.getContacts(0);
    }

    @RequestMapping("/delete-contact")
    public void delteContact(@RequestBody Contact contact) {
        simpleService.deleteContact(contact);
    }

    @RequestMapping("/delete-phone")
    public void deltePhone(@RequestBody Phone phone) {
        simpleService.deletePhone(phone);
    }

    @RequestMapping("/delete-attachment")
    public void delteAttachment(@RequestBody Attachment attachment) {
        simpleService.deleteAttachment(attachment);
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public void addRecord(@RequestBody Contact contact) {
        simpleService.addRecord(contact);
    }

    @RequestMapping(value = "/update-record", method = RequestMethod.POST)
    public void updateRecord(@RequestBody Contact contact) {
        simpleService.updateRecord(contact);
    }
}
