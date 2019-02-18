package com.itechart.contacts.core.unit.person.service;

import com.itechart.contacts.core.utils.testutil.TestUtil;
import com.itechart.contacts.core.person.dto.PersonDto;
import com.itechart.contacts.core.person.dto.SavePersonDto;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.person.repository.PersonRepository;
import com.itechart.contacts.core.person.service.PersonServiceImpl;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.*;

public class PersonServiceImplTest {

    @InjectMocks
    PersonServiceImpl personService;

    @Mock
    PersonRepository personRepository;

    private TestUtil testUtil = new TestUtil();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPersonPositiveTest() throws IOException {
        Person expected = testUtil.initPerson("TestString");

        Mockito.when(personRepository.save(expected)).thenReturn(expected);

        PersonDto personDto = personService.create(ObjectMapperUtils.map(expected, SavePersonDto.class), null);
        Person actual = ObjectMapperUtils.map(personDto, Person.class);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void createPersonNegativeTest() throws IOException {
        Person expected = testUtil.initPerson("TestString");
        Person unexpected = testUtil.initPerson("Another TestString");

        Mockito.when(personRepository.save(expected)).thenReturn(expected);

        PersonDto personDto = personService.create(ObjectMapperUtils.map(expected, SavePersonDto.class), null);
        Person actual = ObjectMapperUtils.map(personDto, Person.class);

        assertNotNull(actual);
        assertNotEquals(unexpected, actual);
    }


    @Test(expected = IllegalArgumentException.class)
    public void createPersonNegativeTestIllArgExc() throws IOException {
        personService.create(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void createPersonNegativeTestNPE() throws IOException {
        Person person = new Person();

        Mockito.when(personRepository.save(person)).thenReturn(person);

       personService.create(ObjectMapperUtils.map(person, SavePersonDto.class), null);
    }

    @Test
    public void readPersonPositiveTest() {
        Person expected = testUtil.initPerson("TestString");

        Mockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(expected));

        Person actual = ObjectMapperUtils.map( personService.getContact(1L), Person.class);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void readPersonNegativeTest() {
        Person expected = testUtil.initPerson("TestString");
        Person unexpected = testUtil.initPerson("Another TestString");

        Mockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(expected));

        Person actual = ObjectMapperUtils.map(personService.getContact(1L), Person.class);

        assertNotNull(actual);
        assertNotEquals(unexpected, actual);
    }

    @Test
    public void updatePersonPositiveTest() throws IOException {
        Person prevPerson = testUtil.initPerson("Prev TestString");
        Person updatedPerson = testUtil.initPerson("Updated TestString");

        Mockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(prevPerson));

        PersonDto personDto = personService.update(1L, ObjectMapperUtils.map(updatedPerson, SavePersonDto.class), null);
        Person actual = ObjectMapperUtils.map(personDto, Person.class);

        assertNotNull(actual);
        assertEquals(actual, updatedPerson);
    }

    @Test
    public void updatePersonNegativeTest() throws IOException {
        Person person = testUtil.initPerson("TestString");
        Person updatedPerson = testUtil.initPerson("Updated TestString");
        Person unexpected = testUtil.initPerson("Another TestString");

        Mockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(person));

        PersonDto personDto = personService.update(1L, ObjectMapperUtils.map(updatedPerson, SavePersonDto.class), null);
        Person actual = ObjectMapperUtils.map(personDto, Person.class);
        assertNotNull(actual);
        assertNotEquals(unexpected, actual);
    }

    @Test
    public void deletePersonPositiveTest() {
        //&&
    }

    @Test
    public void deletePersonNegativeTest() {
        //&&
    }
}