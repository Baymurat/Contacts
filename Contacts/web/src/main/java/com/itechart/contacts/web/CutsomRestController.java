package com.itechart.contacts.web;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.service.SimpleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class CutsomRestController {

    private SimpleService simpleService = new SimpleService();

    @RequestMapping("/fill-index")
    public List<Contact> getContacts() {
        return simpleService.getContacts(0);
    }

    @RequestMapping("/delete-record")
    public void delteRecord(@RequestParam(name = "index")int i) {
        simpleService.delete(i);
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public void addRecord(@RequestBody Contact contact) {
        simpleService.addRecord(contact);
    }
}
