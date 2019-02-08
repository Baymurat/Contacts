package com.itechart.contacts.core.person.service;

import com.itechart.contacts.core.attachment.dto.AttachmentDto;
import com.itechart.contacts.core.attachment.dto.SaveAttachmentDto;
import com.itechart.contacts.core.attachment.service.AttachmentService;
import com.itechart.contacts.core.filemanager.FileManageService;
import com.itechart.contacts.core.person.config.PersonSpecification;
import com.itechart.contacts.core.person.dto.PersonDto;
import com.itechart.contacts.core.person.dto.PersonPreviewDto;
import com.itechart.contacts.core.person.dto.SavePersonDto;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.person.repository.PersonRepository;
import com.itechart.contacts.core.phone.dto.SavePhoneDto;
import com.itechart.contacts.core.phone.service.PhoneService;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    private PersonRepository personRepository;
    private AttachmentService attachmentService;
    private PhoneService phoneService;
    private FileManageService fileManageService;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, AttachmentService attachmentService, PhoneService phoneService, FileManageService fileManageService) {
        this.personRepository = personRepository;
        this.attachmentService = attachmentService;
        this.phoneService = phoneService;
        this.fileManageService = fileManageService;
    }


    public PersonDto getContact(Long id) {
        Person person = personRepository.findById(id).orElseThrow(IllegalStateException::new);
        return ObjectMapperUtils.map(person, PersonDto.class);
    }

    @Override
    public Page<PersonPreviewDto> searchContact(String text, PageRequest request) {
        return personRepository.findAll(Specification.where(PersonSpecification.textInAllColumns(text)), request)
                .map(person -> ObjectMapperUtils.map(person, PersonPreviewDto.class));
    }

    public Page<PersonPreviewDto> getContacts(PageRequest request) {
        return personRepository.findAll(request)
                .map(person -> ObjectMapperUtils.map(person, PersonPreviewDto.class));
    }

    @Transactional
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public PersonDto create(SavePersonDto savePersonDto, MultipartFile[] files) throws IOException {
        Person person = ObjectMapperUtils.map(savePersonDto, Person.class);
        person = personRepository.save(person);

        List<SaveAttachmentDto> attachments = savePersonDto.getAttachments();
        List<SavePhoneDto> phones = savePersonDto.getPhones();

        long personId = person.getId();
        int i = 0;

        for (SaveAttachmentDto attachment : attachments) {
            attachment.setPersonId(personId);
            AttachmentDto attachmentDto = attachmentService.create(attachment);
            fileManageService.uploadFile(personId, attachmentDto.getId(), files[i++]);
        }

        for (SavePhoneDto phone : phones) {
            phone.setPersonId(personId);
            phoneService.create(phone);
        }

        return ObjectMapperUtils.map(person, PersonDto.class);
    }

    @Transactional
    public PersonDto update(long id, SavePersonDto savePersonDto, MultipartFile[] files) throws IOException {
        Person person = personRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        ObjectMapperUtils.map(savePersonDto, person);

        List<SaveAttachmentDto> attachments = savePersonDto.getAttachments();
        List<SavePhoneDto> phones = savePersonDto.getPhones();

        Long personId = savePersonDto.getId();
        int i = 0;

        for (SaveAttachmentDto attachment : attachments) {
            attachment.setPersonId(personId);
            long attId = attachment.getId();

            if (attId == 0) {
                AttachmentDto attachmentDto = attachmentService.create(attachment);
                fileManageService.uploadFile(personId, attachmentDto.getId(), files[i++]);
            } else {
                attachmentService.update(attId, attachment);
            }
        }

        for (SavePhoneDto phone : phones) {
            phone.setPersonId(personId);
            long phoneId = phone.getId();

            if (phoneId == 0) {
                phoneService.create(phone);
            } else {
                phoneService.update(phoneId, phone);
            }

        }

        return ObjectMapperUtils.map(person, PersonDto.class);
    }

    public List<Person> findByBirthDate(Date date) {
        return personRepository.findAllByBirthDate(date);
    }
}
