package com.itechart.contacts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.service.SimpleService;
import com.itechart.contacts.core.utils.Result;
import com.itechart.contacts.core.utils.FileManageService;
import com.itechart.contacts.core.utils.email.CustomMessageHolder;
import com.itechart.contacts.core.utils.error.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomRestController {

    private SimpleService simpleService = new SimpleService();
    private FileManageService fileManageService = new FileManageService();

    @RequestMapping(value = "/get-contacts", method = RequestMethod.GET)
    public Result getContacts(@RequestParam(name = "from")  int from, @RequestParam(name = "range") int range) throws Exception {
        return simpleService.getContacts(from, range, null);
    }

    @RequestMapping(value = "/get-contact", method = RequestMethod.GET)
    public Contact getContact(@RequestParam(name = "id") int id) throws Exception {
        return simpleService.getContact(id);
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public void addRecord(@RequestParam("contact") String jsonRepresentation, @RequestPart("files") MultipartFile[] files,
                          @RequestPart(value = "photo", required = false) MultipartFile photo) throws Exception {
        Contact contact = parseToContact(jsonRepresentation);
        if (photo == null) {
            simpleService.addRecord(contact, getBytesAndExtOfFiles(files), null);
        } else {
            simpleService.addRecord(contact, getBytesAndExtOfFiles(files), getBytesAndExtOfFiles(new MultipartFile[]{photo}));
        }
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public void deleteRecord(@RequestBody int[] deleteContactsId) throws Exception {
        simpleService.deleteRecord(deleteContactsId);
    }

    @RequestMapping(value = "/update-record", method = RequestMethod.POST)
    public void updateRecord(@RequestParam("contact") String jsonRepresentation, @RequestPart("files") MultipartFile[] files,
                             @RequestPart(value = "photo", required = false) MultipartFile photo) throws Exception {
        Contact contact = parseToContact(jsonRepresentation);
        //simpleService.updateRecord(contact, getBytesAndExtOfFiles(files));
        if (photo == null) {
            simpleService.updateRecord(contact, getBytesAndExtOfFiles(files), null);
        } else {
            simpleService.updateRecord(contact, getBytesAndExtOfFiles(files), getBytesAndExtOfFiles(new MultipartFile[]{photo}));
        }
    }

    @RequestMapping(value = "/search-contact", method = RequestMethod.GET)
    public Result searchContacts(@RequestParam(name = "from")  int from, @RequestParam(name = "range") int range, @RequestParam(name = "like") String like) throws Exception {
        return simpleService.getContacts(from, range, like);
    }

    @RequestMapping(value = "/send-email", method = RequestMethod.POST)
    public void sendEmail(@RequestBody CustomMessageHolder message) {
        simpleService.sendEmail(message);
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.GET)
    public void met(@RequestParam(name = "person_id") int personId, @RequestParam(name = "attach_id") int attachId, HttpServletResponse response) throws Exception {

        try {
            File file = fileManageService.getFile(personId, attachId);
            if (file != null) {
                Attachment attachment = simpleService.getAttachment(attachId);

                String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                String fileExtension = file.getName().split("\\.")[1];
                String fileName = attachment.getFileName() + "." + fileExtension;

                response.setContentType(mimeType);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName +"\"");
                response.setContentLength((int)file.length());
                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                FileCopyUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public ResponseEntity getPhoto(@RequestParam(name = "id") int id) throws CustomException {
        File photo = fileManageService.getPhoto(id);
        String encodedFile = "";
        if (photo != null) {
            encodedFile = simpleService.encodeFileToBase64Binary(photo);
        }

        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(encodedFile);
    }

    @RequestMapping(value = "/advanced-search", method = RequestMethod.POST)
    public Result advancedSearh(@RequestBody Contact contact) throws Exception {
        return simpleService.advancedSearch(contact);
    }

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
