package com.itechart.contacts.core.person.service;

import com.itechart.contacts.core.person.dto.PersonDto;
import com.itechart.contacts.core.person.dto.PersonFilter;
import com.itechart.contacts.core.person.dto.PersonPreviewDto;
import com.itechart.contacts.core.person.dto.SavePersonDto;
import com.itechart.contacts.core.person.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface PersonService {

    PersonDto create(SavePersonDto person, MultipartFile[] files) throws IOException;

    PersonDto update(long id, SavePersonDto personDto, MultipartFile[] files) throws IOException;

    PersonDto getContact(Long id);

    Page<PersonPreviewDto> searchContact(PersonFilter personFilter, Pageable pageable);

    Page<PersonPreviewDto> getContacts(Pageable pageable);

    void delete(Long id);

    List<Person> findByBirthDate(Date date);
}
