package com.itechart.contacts.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechart.contacts.core.attachment.dto.AttachmentDto;
import com.itechart.contacts.core.attachment.dto.SaveAttachmentDto;
import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.attachment.service.AttachmentService;
import com.itechart.contacts.core.email.service.EmailService;
import com.itechart.contacts.core.person.dto.PersonDto;
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

    private int pageSize = 3;

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public Object getContacts(@RequestParam(name = "page") int pageId) {
        return personService.getContacts(PageRequest.of(pageId, pageSize));
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    public PersonDto getContact(@PathVariable(name = "id") long id) throws Exception {
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

    @RequestMapping(value = "/addRecord", method = RequestMethod.POST)
    public void addRecord(@RequestParam("person") String jsonRepresentation,
                          @RequestPart(value = "files", required = false) MultipartFile[] files,
                          @RequestPart(value = "photo", required = false) MultipartFile photo) throws Exception {
        SavePersonDto savePersonDto = parseToContact(jsonRepresentation);
        PersonDto personDto = personService.create(savePersonDto, files);

        if (photo != null) {
            fileManageService.savePhoto(personDto.getId(), photo);
        }

    }

    @RequestMapping(value = "/deleteContacts", method = RequestMethod.POST)
    public void deleteRecord(@RequestBody long[] deleteContactsId) throws Exception {
        for (long i : deleteContactsId) {
            attachmentService.deleteByContactId(i);
            phoneService.deleteByContactId(i);
            personService.delete(i);
        }

        fileManageService.deleteUsers(deleteContactsId);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public void deleteRecord(@PathVariable(name = "id") long id) {
        attachmentService.deleteByContactId(id);
        phoneService.deleteByContactId(id);
        personService.delete(id);
        fileManageService.deleteUsers(new long[]{id});
    }

    @RequestMapping(value = "/updateRecord/{id}", method = RequestMethod.POST)
    public void updateRecord(@PathVariable(name = "id") long id,
                             @RequestParam("person") String jsonRepresentation,
                             @RequestPart(value = "files", required = false) MultipartFile[] files,
                             @RequestPart(value = "photo", required = false) MultipartFile photo) throws Exception {
        SavePersonDto savePersonDto = parseToContact(jsonRepresentation);
        savePersonDto.setId(id);

        List<Long> deleteAttachments = savePersonDto.getDeleteAttaches();
        List<Long> deletePhones = savePersonDto.getDeletePhones();

        personService.update(id, savePersonDto, files);

        if (deleteAttachments.size() > 0) {
            for (long attachmentId : deleteAttachments) {
                attachmentService.delete(attachmentId);
                fileManageService.deleteFiles((int)savePersonDto.getId(), attachmentId);
            }
        }

        if (deletePhones.size() > 0) {
            for (long phoneId : deletePhones) {
                phoneService.delete(phoneId);
            }
        }

        if (photo != null) {
            fileManageService.savePhoto(savePersonDto.getId(), photo);
        }
    }

    @RequestMapping(value = "/getRecipientsEmail", method = RequestMethod.POST)
    public List<String> getRecipientsEmail(@RequestBody long[] recipientsId) {
        List<String> recipientsEmail = new ArrayList<>();

        if (recipientsId.length > 0) {
            for (long id : recipientsId) {
                PersonDto personDto = personService.getContact(id);
                recipientsEmail.add(personDto.getEmail());
            }
        }

        return recipientsEmail;
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public void sendEmail(@RequestBody MessageDto messageDto) throws Exception {
        emailService.sendMessage(messageDto);
    }

    @RequestMapping(value = "/attachment/{pId}/{attId}", method = RequestMethod.GET)
    public void met(@PathVariable(name = "pId") int personId,
                    @PathVariable(name = "attId") int attachId,
                    HttpServletResponse response) throws Exception {

        try {
            File file = fileManageService.getFile(personId, attachId);
            if (file != null) {
                AttachmentDto attachment = attachmentService.getAttachment((long) attachId);

                String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                String fileExtension = file.getName().split("\\.")[1];
                String fileName = attachment.getFileName() + "." + fileExtension;

                response.setContentType(mimeType);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                response.setContentLength((int) file.length());
                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                FileCopyUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/searchContact", method = RequestMethod.GET)
    public Object searchContacts(@RequestParam(name = "page") int pageId,
                                 @RequestParam(name = "text") String text) throws Exception {
        return personService.searchContact(text, PageRequest.of(pageId, pageSize));
    }

    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
    public ResponseEntity getPhoto(@PathVariable(name = "id") int id) throws CustomException {
        String encodedFile = fileManageService.getPhoto(id);
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(encodedFile);
    }


    /*@RequestMapping(value = "/search-person-advanced", method = RequestMethod.POST)
    public Result advancedSearch(@RequestBody Person person) throws Exception {
        return simpleService.advancedSearch(person);
    }*/


    @RequestMapping(value = "/messagePatterns", method = RequestMethod.GET)
    public List<MessageEntity> getPatterns() throws IOException {
        String path = "D:\\templates";
        return fileManageService.getPatterns(path);
    }

    private SavePersonDto parseToContact(String jsonRepresentation) {
        ObjectMapper mapper = new ObjectMapper();
        SavePersonDto savePersonDto = null;
        try {
            savePersonDto = mapper.readValue(jsonRepresentation, SavePersonDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savePersonDto;
    }
}
