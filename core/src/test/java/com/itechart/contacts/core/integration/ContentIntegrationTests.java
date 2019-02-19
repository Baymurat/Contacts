package com.itechart.contacts.core.integration;
import com.itechart.contacts.core.person.dto.PersonFilter;
import com.itechart.contacts.core.person.dto.PersonPreviewDto;
import com.itechart.contacts.core.utils.testutil.TestUtil;
import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.attachment.service.AttachmentService;
import com.itechart.contacts.core.common.config.RootConfig;
import com.itechart.contacts.core.person.dto.PersonDto;
import com.itechart.contacts.core.person.dto.SavePersonDto;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.person.service.PersonService;
import com.itechart.contacts.core.phone.entity.Phone;
import com.itechart.contacts.core.phone.service.PhoneService;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(classes = {RootConfig.class})
@TestPropertySource(locations = {"classpath:application.properties"})
public class ContentIntegrationTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    PersonService personService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    PhoneService phoneService;

    @PersistenceContext
    private EntityManager entityManager;

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void createPersonRecordPositiveTest() throws IOException {
        int currentRowsCount = countRowsInTable("persons");

        Person expectedPerson = TestUtil.initPerson("Test");
        Attachment expectedAttachment = TestUtil.initAttachment("Test attachment");
        Phone expectedPhone = TestUtil.initPhone("Mobile", 333);
        MultipartFile[] files = TestUtil.initMultiFiles();

        expectedPerson.setPhones(new ArrayList<>(Collections.singletonList(expectedPhone)));
        expectedPerson.setAttachments(new ArrayList<>(Collections.singletonList(expectedAttachment)));

        PersonDto personDto = personService.create(ObjectMapperUtils.map(expectedPerson, SavePersonDto.class), files);
        flushAndClear();

        Person actualPerson = ObjectMapperUtils.map(personService.getContact(personDto.getId()), Person.class);
        Attachment actualAttachment = actualPerson.getAttachments().get(0);
        Phone actualPhone = actualPerson.getPhones().get(0);

        expectedPerson.setId(actualPerson.getId());

        assertNotNull(actualPerson);
        assertNotNull(actualAttachment);
        assertNotNull(actualPhone);

        assertEquals(actualPerson, expectedPerson);
        assertEquals(actualAttachment, expectedAttachment);
        assertEquals(actualPhone, expectedPhone);

        String filePath = "D:\\upload\\" +
                actualPerson.getId() +
                "\\upload_folder\\attachments\\" +
                actualAttachment.getId() +
                "\\testFile.jpg";

        assertTrue(new File(filePath).exists());
        assertEquals(currentRowsCount + 1, countRowsInTable("persons"));
    }

    @Test
    public void createPersonRecordNegativeTest() throws IOException {
        Person anotherPerson = TestUtil.initPerson("Another Test");
        Attachment anotherAttachment = TestUtil.initAttachment("Another Test attachment");
        Phone anotherPhone = TestUtil.initPhone("Another Mobile", 333);
        MultipartFile[] files = TestUtil.initMultiFiles();

        anotherPerson.setPhones(new ArrayList<>(Collections.singletonList(anotherPhone)));
        anotherPerson.setAttachments(new ArrayList<>(Collections.singletonList(anotherAttachment)));

        Person expectedPerson = TestUtil.initPerson("Test");
        Attachment expectedAttachment = TestUtil.initAttachment("Test attachment");
        Phone expectedPhone = TestUtil.initPhone("Mobile", 333);

        expectedPerson.setPhones(new ArrayList<>(Collections.singletonList(expectedPhone)));
        expectedPerson.setAttachments(new ArrayList<>(Collections.singletonList(expectedAttachment)));

        PersonDto personDto = personService.create(ObjectMapperUtils.map(expectedPerson, SavePersonDto.class), files);
        flushAndClear();

        Person actualPerson = ObjectMapperUtils.map(personService.getContact(personDto.getId()), Person.class);
        Attachment actualAttachment = actualPerson.getAttachments().get(0);
        Phone actualPhone = actualPerson.getPhones().get(0);

        assertNotNull(actualPerson);
        assertNotNull(actualAttachment);
        assertNotNull(actualPhone);

        assertNotEquals(actualPerson, anotherPerson);
        assertNotEquals(actualAttachment, anotherAttachment);
        assertNotEquals(actualPhone, anotherPhone);
    }

    @Test
    public void readPersonRecordNegativeTest() {
        int currentRowsCount = countRowsInTable("persons");
        Person person = ObjectMapperUtils.map(personService.getContact(10L), Person.class);

        assertNotNull(person);
        assertNotEquals(person.getName(), "Another");
        assertNotEquals(person.getSurName(), "Another");
        assertNotEquals(person.getMiddleName(), "Another");
        assertNotEquals(person.getCountry(), "Another");
        assertNotEquals(person.getCity(), "Another");
        assertNotEquals(person.getCitizenship(), "Another");
        assertNotEquals(person.getGender(), "Another");
        assertNotEquals(person.getCurrentJob(), "Another");
        assertNotEquals(person.getFamilyStatus(), "Another");
        assertEquals(currentRowsCount, countRowsInTable("persons"));
    }

    @Test(expected = IllegalStateException.class)
    public void readPersonRecordNegativeTestIllStateExc() {
        personService.getContact(999L);
    }

    @Test
    public void updatePersonRecordPositiveTest() throws IOException {
        int currentRowsCount = countRowsInTable("persons");

        Person expectedPerson = TestUtil.initPerson("Updated Person");
        Attachment expectedAttach = TestUtil.initAttachment("Updated Attachment");
        Phone expectedPhone = TestUtil.initPhone("Updated Phone", 999);
        MultipartFile[] files = TestUtil.initMultiFiles();

        expectedPerson.setAttachments(new ArrayList<>(Collections.singletonList(expectedAttach)));
        expectedPerson.setPhones(new ArrayList<>(Collections.singletonList(expectedPhone)));
        expectedPerson.setId(7L);

        personService.update(7L, ObjectMapperUtils.map(expectedPerson, SavePersonDto.class), files);
        flushAndClear();

        Person actualPerson = ObjectMapperUtils.map(personService.getContact(7L), Person.class);
        Attachment actualAttach = actualPerson.getAttachments().get(0);
        Phone actualPhone = actualPerson.getPhones().get(0);

        assertNotNull(actualPerson);
        assertNotNull(actualAttach);
        assertNotNull(actualPhone);

        assertEquals(actualPerson, expectedPerson);
        assertEquals(actualAttach, expectedAttach);
        assertEquals(actualPhone, expectedPhone);
        assertEquals(currentRowsCount, countRowsInTable("persons"));
    }

    @Test
    public void deletePersonRecordPositiveTest() throws IOException {
        int currentRowsCount = countRowsInTable("persons");

        Person expectedPerson = TestUtil.initPerson("Test");
        PersonDto personDto = personService.create(ObjectMapperUtils.map(expectedPerson, SavePersonDto.class), null);
        flushAndClear();
        assertEquals(currentRowsCount + 1, countRowsInTable("persons"));

        personService.delete(personDto.getId());
        flushAndClear();
        assertEquals(currentRowsCount, countRowsInTable("persons"));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deletePersonRecordNegativeTest() {
        personService.delete(999L);
    }

    @Test
    public void searchFunctionTestByName() throws IOException {
        Person expected = TestUtil.initPerson("Dima");

        personService.create(ObjectMapperUtils.map(expected, SavePersonDto.class), null);
        flushAndClear();

        PersonFilter filter = TestUtil.initPersonFilter("Dima", "", 0);

        Page<PersonPreviewDto> page = personService.searchContact(filter, PageRequest.of(0, 5));
        List<PersonPreviewDto> personPreviewDtoList = page.getContent();

        assertEquals(1, personPreviewDtoList.size());

        PersonPreviewDto actual = personPreviewDtoList.get(0);

        assertEquals("Dima", actual.getName());
    }

    @Test
    public void searchFunctionTestBySurname() throws IOException {
        Person expected = TestUtil.initPerson("Petrov");

        personService.create(ObjectMapperUtils.map(expected, SavePersonDto.class), null);
        flushAndClear();

        PersonFilter filter = TestUtil.initPersonFilter("Petrov", "", 0);

        Page<PersonPreviewDto> page = personService.searchContact(filter, PageRequest.of(0, 5));
        List<PersonPreviewDto> personPreviewDtoList = page.getContent();

        assertEquals(1, personPreviewDtoList.size());

        PersonPreviewDto actual = personPreviewDtoList.get(0);

        assertEquals("Petrov", actual.getSurName());
    }

    @Test
    public void searchFunctionTestByJob() throws IOException {
        Person expected = TestUtil.initPerson("driver");
        expected.setName("Vasya");

        personService.create(ObjectMapperUtils.map(expected, SavePersonDto.class), null);
        flushAndClear();

        PersonFilter filter = TestUtil.initPersonFilter("", "driver", 0);

        Page<PersonPreviewDto> page = personService.searchContact(filter, PageRequest.of(0, 5));
        List<PersonPreviewDto> personPreviewDtoList = page.getContent();

        assertEquals(1, personPreviewDtoList.size());
        assertEquals("Vasya", personPreviewDtoList.get(0).getName());
    }

    @Test
    public void searchFunctionTestByPhoneNum() throws IOException {
        Person expectedPerson = TestUtil.initPerson("Ivan");
        Phone expectedPhone = TestUtil.initPhone("mobile", 999);
        expectedPerson.setPhones(Collections.singletonList(expectedPhone));

        personService.create(ObjectMapperUtils.map(expectedPerson, SavePersonDto.class), null);
        flushAndClear();
        PersonFilter filter = TestUtil.initPersonFilter("", "", 999);

        Page<PersonPreviewDto> page = personService.searchContact(filter, PageRequest.of(0, 5));
        List<PersonPreviewDto> personPreviewDtoList = page.getContent();

        assertEquals(1, personPreviewDtoList.size());
        assertEquals("Ivan", personPreviewDtoList.get(0).getName());
    }
}