package com.itechart.contacts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Message;
import com.itechart.contacts.core.service.SimpleService;
import com.itechart.contacts.core.utils.Result;
import com.itechart.contacts.core.utils.FileManageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomRestController {

    private SimpleService simpleService = new SimpleService();
    private FileManageService fileManageService = new FileManageService();

    @RequestMapping(value = "/get-contacts", method = RequestMethod.GET)
    public Result getContacts(@RequestParam(name = "from")  int from, @RequestParam(name = "range") int range) {
        return simpleService.getContacts(from, range, null);
    }

    @RequestMapping(value = "/get-contact", method = RequestMethod.GET)
    public Contact getContact(@RequestParam(name = "id") int id) {
        return simpleService.getContact(id);
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public void addRecord(@RequestParam("contact") String jsonRepresentation, @RequestPart("files") MultipartFile[] files ) {
        Contact contact = parseToContact(jsonRepresentation);
        simpleService.addRecord(contact, getBytesAndExtOfFiles(files));
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public void deleteRecord(@RequestBody int[] deleteContactsId) {
        simpleService.deleteRecord(deleteContactsId);
    }

    @RequestMapping(value = "/update-record", method = RequestMethod.POST)
    public void updateRecord(@RequestParam("contact") String jsonRepresentation, @RequestPart("files") MultipartFile[] files) {
        Contact contact = parseToContact(jsonRepresentation);
        simpleService.updateRecord(contact, getBytesAndExtOfFiles(files));
    }

    @RequestMapping(value = "/search-contact", method = RequestMethod.GET)
    public Result searchContacts(@RequestParam(name = "from")  int from, @RequestParam(name = "range") int range, @RequestParam(name = "like") String like) {
        return simpleService.getContacts(from, range, like);
    }

    @RequestMapping(value = "/send-email", method = RequestMethod.POST)
    public void sendEmail(@RequestBody Message message) {
        simpleService.sendEmail(message);
    }

    /*@RequestMapping(value = "/gyjhghjgh", method = RequestMethod.GET)
    public ResponseEntity met() {

        return ResponseEntity.status(200).header("file").body(byte[]);
    }*/

    private Contact parseToContact(String jsonRepresentation) {
        ObjectMapper mapper = new ObjectMapper();
        Contact contact = null;
        try {
            contact = mapper.readValue(jsonRepresentation, Contact.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contact;
    }

    private List<Object> getBytesAndExtOfFiles(MultipartFile[] files) {
        List<Object> result = new ArrayList<>();

        for (MultipartFile f : files) {
            try {
                String fileExtension = f.getOriginalFilename().split("\\.")[1];
                byte[] bytes = f.getBytes();

                result.add(bytes);
                result.add(fileExtension);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
