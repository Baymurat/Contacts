package com.itechart.contacts.web;

import com.itechart.contacts.core.entities.Contact;
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

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public void addRecord(@RequestBody Contact contact) {
        simpleService.addRecord(contact);
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.GET)
    public void deleteRecord(@RequestParam(name = "id") int id) {
        simpleService.deleteRecord(id);
    }

    @RequestMapping(value = "/update-record", method = RequestMethod.POST)
    public void updateRecord(@RequestBody Contact contact) {
        simpleService.updateRecord(contact);
    }
}
