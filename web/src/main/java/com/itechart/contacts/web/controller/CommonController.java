package com.itechart.contacts.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechart.contacts.core.attachment.dto.AttachmentDto;
import com.itechart.contacts.core.attachment.dto.SaveAttachmentDto;
import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.attachment.service.AttachmentService;
import com.itechart.contacts.core.email.service.EmailService;
import com.itechart.contacts.core.person.dto.PersonDto;
import com.itechart.contacts.core.person.dto.PersonPreviewDto;
import com.itechart.contacts.core.person.dto.SavePersonDto;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.person.service.PersonService;
import com.itechart.contacts.core.email.entity.MessageEntity;
import com.itechart.contacts.core.phone.dto.SavePhoneDto;
import com.itechart.contacts.core.phone.entity.Phone;
import com.itechart.contacts.core.phone.service.PhoneService;
import com.itechart.contacts.core.filemanager.FileManageServiceImpl;
import com.itechart.contacts.core.email.dto.MessageDto;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import com.itechart.contacts.core.utils.error.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

/**
 *
 */
@RestController
public class CommonController {

    private FileManageServiceImpl fileManageService = new FileManageServiceImpl();

    @Autowired
    private PersonService personService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private EmailService emailService;

    private int pageSize = 10;

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public Object getContacts(@RequestParam(name = "page")  int pageId) {
        return personService.getContacts(PageRequest.of(pageId, pageSize));
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public PersonDto getContact(@RequestParam(name = "id") long id) throws Exception {
        return personService.getContact(id);
    }

    @RequestMapping(value = "/receivers", method = RequestMethod.POST)
    public List<PersonDto> getReceivers(@RequestBody long[] receivers) throws Exception {
        List<PersonDto> people = new ArrayList<>();
        for (long i : receivers) {
            people.add(personService.getContact(i));
        }

        return people;
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public void addRecord(@RequestParam("person") String jsonRepresentation,
                          @RequestPart("files") MultipartFile[] files,
                          @RequestPart(value = "photo", required = false) MultipartFile photo) throws Exception {
        Person person = parseToContact(jsonRepresentation);
        SavePersonDto savePersonDto = ObjectMapperUtils.map(person, SavePersonDto.class);
        PersonDto personDto = personService.create(savePersonDto);

        List<Attachment> attachments = person.getAttachments();
        List<Phone> phones = person.getPhones();

        long id = personDto.getId();
        int i = 0;

        for (Attachment attachment : attachments) {
            attachment.setPerson(person);
            SaveAttachmentDto saveAttachmentDto = ObjectMapperUtils.map(attachment, SaveAttachmentDto.class);
            attachmentService.create(saveAttachmentDto);
            fileManageService.uploadFile(id, attachment.getId(), files[i++]);
        }

        for (Phone phone : phones) {
            phone.setPerson(person);
            SavePhoneDto savePhoneDto = ObjectMapperUtils.map(phone, SavePhoneDto.class);
            phoneService.create(savePhoneDto);
        }

        fileManageService.savePhoto(id, photo);
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public void deleteRecord(@RequestBody long[] deleteContactsId) throws Exception {
        for (long i : deleteContactsId) {
            attachmentService.deleteByContactId(i);
            phoneService.deleteByContactId(i);
            personService.delete(i);
        }
    }

    @RequestMapping(value = "/update-record", method = RequestMethod.POST)
    public void updateRecord(@RequestParam("person") String jsonRepresentation,
                             @RequestParam long[] deleteAttachments,
                             @RequestParam long[] deletePhones,
                             @RequestPart("files") MultipartFile[] files,
                             @RequestPart(value = "photo", required = false) MultipartFile photo) throws Exception {
        Person person = parseToContact(jsonRepresentation);
        SavePersonDto personDto = ObjectMapperUtils.map(person, SavePersonDto.class);

        List<Attachment> attachments = person.getAttachments();
        List<Phone> phones = person.getPhones();

        personService.update(person.getId(), personDto);

        Long personId = person.getId();
        int i = 0;

        for (Attachment attachment : attachments) {
            SaveAttachmentDto saveAttachmentDto = ObjectMapperUtils.map(attachment, SaveAttachmentDto.class);
            attachmentService.update(attachment.getId(), saveAttachmentDto);
            fileManageService.uploadFile(personId, attachment.getId(), files[i++]);
        }

        for (Phone phone : phones) {
            SavePhoneDto savePhoneDto = ObjectMapperUtils.map(phone, SavePhoneDto.class);
            phoneService.update(phone.getId(), savePhoneDto);
        }

        for (long attachmentId : deleteAttachments) {
            attachmentService.delete(attachmentId);
            fileManageService.deleteFiles(personId.intValue(), attachmentId);
        }

        for (long phoneId : deletePhones) {
            phoneService.delete(phoneId);
        }

        fileManageService.savePhoto(person.getId(), photo);
    }

    /*@RequestMapping(value = "/search-contact", method = RequestMethod.GET)
    public Object searchContacts(@RequestParam(name = "page") int pageId, @RequestParam(name = "like") String like) throws Exception {
        Page<Person> contacts = personService.searchContact(like, PageRequest.of(pageId - 1, pageSize));
        return contacts.map(PersonDto::new);
    }*/

    @RequestMapping(value = "/send-email", method = RequestMethod.POST)
    public void sendEmail(@RequestBody MessageDto messageDto) throws Exception {
        emailService.sendMessage(messageDto);
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.GET)
    public void met(@RequestParam(name = "person_id") int personId, @RequestParam(name = "attach_id") int attachId, HttpServletResponse response) throws Exception {

        try {
            File file = fileManageService.getFile(personId, attachId);
            if (file != null) {
                AttachmentDto attachment = attachmentService.getAttachment((long)attachId);

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
        String encodedFile = fileManageService.getPhoto(id);
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(encodedFile);
    }


    /*@RequestMapping(value = "/search-person-advanced", method = RequestMethod.POST)
    public Result advancedSearch(@RequestBody Person person) throws Exception {
        return simpleService.advancedSearch(person);
    }*/


    @RequestMapping(value = "/message-patterns", method = RequestMethod.GET)
    public List<MessageEntity> getPatterns() throws IOException {
        String path = "D:\\templates";
        System.out.println("CALLED");
        return fileManageService.getPatterns(path);
    }

    private Person parseToContact(String jsonRepresentation) {
        ObjectMapper mapper = new ObjectMapper();
        Person person = null;
        try {
            person = mapper.readValue(jsonRepresentation, Person.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return person;
    }
}
