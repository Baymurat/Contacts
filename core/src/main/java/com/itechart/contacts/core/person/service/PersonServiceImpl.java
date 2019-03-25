package com.itechart.contacts.core.person.service;


import com.itechart.contacts.core.attachment.dto.AttachmentDto;
import com.itechart.contacts.core.attachment.dto.SaveAttachmentDto;
import com.itechart.contacts.core.attachment.service.AttachmentService;
import com.itechart.contacts.core.filemanager.FileManageService;
import com.itechart.contacts.core.person.dto.PersonDto;
import com.itechart.contacts.core.person.dto.PersonFilter;
import com.itechart.contacts.core.person.dto.PersonPreviewDto;
import com.itechart.contacts.core.person.dto.SavePersonDto;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.person.repository.PersonRepository;
import com.itechart.contacts.core.phone.dto.SavePhoneDto;
import com.itechart.contacts.core.phone.service.PhoneService;
import com.itechart.contacts.core.user.repository.UserRepository;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private AttachmentService attachmentService;
    private PhoneService phoneService;
    private FileManageService fileManageService;
    private UserRepository userRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, AttachmentService attachmentService, PhoneService phoneService, FileManageService fileManageService, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.attachmentService = attachmentService;
        this.phoneService = phoneService;
        this.fileManageService = fileManageService;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public PersonDto getContact(Long id) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Person person = personRepository.findByIdAndUserId(id, userId);
        return ObjectMapperUtils.map(person, PersonDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PersonPreviewDto> searchContact(PersonFilter personFilter, Pageable pageable) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

        personFilter.setUserId(userId);
        return personRepository.search(personFilter, pageable)
                .map(person -> ObjectMapperUtils.map(person, PersonPreviewDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PersonPreviewDto> getContacts(Pageable pageable) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return personRepository.findAllByUserId(userId, pageable)
                .map(person -> ObjectMapperUtils.map(person, PersonPreviewDto.class));
    }

    @Transactional
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    @Override
    public PersonDto create(@Valid SavePersonDto savePersonDto, MultipartFile[] files) throws IOException {
        Person person = ObjectMapperUtils.map(savePersonDto, Person.class);

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

        person.setUser(userRepository.getOne(userId));
        person = personRepository.save(person);

        List<SaveAttachmentDto> attachments = savePersonDto.getAttachments();
        List<SavePhoneDto> phones = savePersonDto.getPhones();

        long personId = person.getId();
        int i = 0;

        if (attachments != null) {
            for (SaveAttachmentDto attachment : attachments) {
                attachment.setPersonId(personId);
                AttachmentDto attachmentDto = attachmentService.create(attachment);
                fileManageService.uploadFile(personId, attachmentDto.getId(), files[i++]);
            }
        }

        if (phones != null) {
            for (SavePhoneDto phone : phones) {
                phone.setPersonId(personId);
                phoneService.create(phone);
            }
        }

        return ObjectMapperUtils.map(person, PersonDto.class);
    }

    @Transactional
    @Override
    public PersonDto update(long id, @Valid SavePersonDto savePersonDto, MultipartFile[] files) throws IOException {

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Person person = personRepository.findByIdAndUserId(id,userId);
        ObjectMapperUtils.map(savePersonDto, person);

        List<SaveAttachmentDto> attachments = savePersonDto.getAttachments();
        List<SavePhoneDto> phones = savePersonDto.getPhones();

        long personId = savePersonDto.getId();
        int i = 0;

        if (attachments != null) {
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
        }

        if (phones != null) {
            for (SavePhoneDto phone : phones) {
                phone.setPersonId(personId);
                long phoneId = phone.getId();

                if (phoneId == 0) {
                    phoneService.create(phone);
                } else {
                    phoneService.update(phoneId, phone);
                }

            }
        }

        return ObjectMapperUtils.map(person, PersonDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> findByBirthDate(Date date) {
        return personRepository.findAllByBirthDate(date);
    }
}
