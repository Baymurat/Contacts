package com.itechart.contacts.core.person.service;

import com.itechart.contacts.core.person.dto.PersonDto;
import com.itechart.contacts.core.person.dto.PersonPreviewDto;
import com.itechart.contacts.core.person.dto.SavePersonDto;
import com.itechart.contacts.core.person.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

public interface PersonService {

    PersonDto create(SavePersonDto person);

    PersonDto update(long id, SavePersonDto personDto);

    PersonDto getContact(Long id);

    Page<PersonPreviewDto> getContacts(PageRequest request);

    void delete(Long id);

    List<Person> findByBirthDate(Date date);
}
