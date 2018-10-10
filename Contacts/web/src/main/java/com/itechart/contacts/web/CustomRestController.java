package com.itechart.contacts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Message;
import com.itechart.contacts.core.service.SimpleService;
import com.itechart.contacts.core.utils.Result;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public void addRecord(@RequestParam("contact") String jsonRepresentation, @RequestPart("files") MultipartFile[] files) {
        System.out.println(files.length);
        System.out.println(jsonRepresentation);
        ObjectMapper mapper = new ObjectMapper();
        Contact contact = null;
        try {
            contact = mapper.readValue(jsonRepresentation, Contact.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        simpleService.addRecord(contact);

    }


    /*@RequestMapping(value = "/add-record", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void addRecord(@RequestParam("contact") Contact contact) {
        simpleService.addRecord(contact);
    }*/


    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public void deleteRecord(@RequestBody int[] deleteContactsId) {
        simpleService.deleteRecord(deleteContactsId);
    }

    @RequestMapping(value = "/update-record", method = RequestMethod.POST)
    public void updateRecord(@RequestBody Contact contact) {
        simpleService.updateRecord(contact);
    }

    @RequestMapping(value = "/search-contact", method = RequestMethod.GET)
    public Result searchContacts(@RequestParam(name = "from")  int from, @RequestParam(name = "range") int range, @RequestParam(name = "like") String like) {
        return simpleService.getContacts(from, range, like);
    }

    @RequestMapping(value = "/send-email", method = RequestMethod.POST)
    public void sendEmail(@RequestBody Message message) {
        simpleService.sendEmail(message);
    }
}
