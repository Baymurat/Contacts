package com.itechart.contacts.core.person.service;

import com.itechart.contacts.core.person.dto.PersonDto;
import com.itechart.contacts.core.person.dto.PersonPreviewDto;
import com.itechart.contacts.core.person.dto.SavePersonDto;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.person.repository.PersonRepository;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public PersonDto getContact(Long id) {
        Person person = personRepository.findById(id).orElseThrow(IllegalStateException::new);
        return ObjectMapperUtils.map(person, PersonDto.class);
    }

    public Page<PersonPreviewDto> getContacts(PageRequest request) {
        return personRepository.findAll(request)
                .map(person -> ObjectMapperUtils.map(person, PersonPreviewDto.class));
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public PersonDto create(SavePersonDto savePersonDto) {
        Person person = ObjectMapperUtils.map(savePersonDto, Person.class);
        person = personRepository.save(person);
        return ObjectMapperUtils.map(person, PersonDto.class);
    }

    @Transactional
    public PersonDto update(long id, SavePersonDto savePersonDto) {
        Person person = personRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        ObjectMapperUtils.map(savePersonDto, person);
        return ObjectMapperUtils.map(person, PersonDto.class);
    }

    public List<Person> findByBirthDate(Date date) {
        return personRepository.findAllByBirthDate(date);
    }
}
