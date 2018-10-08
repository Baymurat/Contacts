package com.itechart.contacts.web;

import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.service.SimpleService;
import com.itechart.contacts.core.utils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomRestController {

    private SimpleService simpleService = new SimpleService();

    @RequestMapping(value = "/get-contacts", method = RequestMethod.GET)
    public Result getContacts(@RequestParam(name = "from")  int from, @RequestParam(name = "range") int range) {
        return simpleService.getContacts(from, range, null);
    }

    @RequestMapping(value = "/get-contact", method = RequestMethod.GET)
    public Contact getContact(@RequestParam(name = "id") int id) {
        return simpleService.getContact(id);
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

    @RequestMapping(value = "/search-contact", method = RequestMethod.GET)
    public Result searchContacts(@RequestParam(name = "from")  int from, @RequestParam(name = "range") int range, @RequestParam(name = "like") String like) {
        return simpleService.getContacts(from, range, like);
    }
}
