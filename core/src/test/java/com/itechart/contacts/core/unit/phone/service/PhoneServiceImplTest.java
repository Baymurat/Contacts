package com.itechart.contacts.core.unit.phone.service;

import com.itechart.contacts.core.utils.testutil.TestUtil;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.person.repository.PersonRepository;
import com.itechart.contacts.core.phone.dto.PhoneDto;
import com.itechart.contacts.core.phone.dto.SavePhoneDto;
import com.itechart.contacts.core.phone.entity.Phone;
import com.itechart.contacts.core.phone.repository.PhoneRepository;
import com.itechart.contacts.core.phone.service.PhoneServiceImpl;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class PhoneServiceImplTest {

    @InjectMocks
    PhoneServiceImpl phoneService;

    @Mock
    PhoneRepository phoneRepository;

    @Mock
    PersonRepository personRepository;

    private TestUtil testUtil = new TestUtil();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPhonePositiveTest() {
        Person person = testUtil.initPerson("Test Person");
        person.setId(1L);

        Phone expected = testUtil.initPhone( "TestString", 111);
        expected.setPerson(person);

        when(phoneRepository.save(expected)).thenReturn(expected);
        when(personRepository.getOne(1L)).thenReturn(person);

        SavePhoneDto savePhoneDto = ObjectMapperUtils.map(expected, SavePhoneDto.class);
        savePhoneDto.setPersonId(1L);

        Phone actual = ObjectMapperUtils.map(phoneService.create(savePhoneDto), Phone.class);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void createPhoneNegativeTest() {
        Person person = testUtil.initPerson("Test Person");
        person.setId(1L);

        Phone expected = testUtil.initPhone( "TestString", 111);
        Phone unexpected = testUtil.initPhone( "TestString", 222);

        when(phoneRepository.save(expected)).thenReturn(expected);
        when(personRepository.getOne(1L)).thenReturn(person);

        SavePhoneDto savePhoneDto = ObjectMapperUtils.map(expected, SavePhoneDto.class);
        savePhoneDto.setPersonId(1L);

        Phone actual = ObjectMapperUtils.map(phoneService.create(savePhoneDto), Phone.class);

        assertNotNull(actual);
        assertNotEquals(unexpected, actual);
    }

    @Test
    public void readPhonePositiveTest() {
        Phone expected = testUtil.initPhone( "TestString", 111);

        when(phoneRepository.findById(1L)).thenReturn(java.util.Optional.of(expected));

        PhoneDto phoneDto = phoneService.getPhone(1L);
        Phone actual = ObjectMapperUtils.map(phoneDto, Phone.class);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void readPhoneNegativeTest() {
        Phone phone = testUtil.initPhone( "TestString", 111);
        Phone anotherPhone = testUtil.initPhone( "TestString", 222);

        when(phoneRepository.findById(1L)).thenReturn(java.util.Optional.of(phone));

        PhoneDto phoneDto = phoneService.getPhone(1L);

        assertNotNull(phoneDto);
        assertNotEquals(ObjectMapperUtils.map(phoneDto, Phone.class), anotherPhone);
    }

    @Test
    public void updatePhonePositiveTest() {
        Phone phone = testUtil.initPhone( "TestString", 111);
        Phone updatedPhone = testUtil.initPhone( "TestString", 222);

        when(phoneRepository.findById(1L)).thenReturn(java.util.Optional.of(phone));

        SavePhoneDto savePhoneDto = ObjectMapperUtils.map(updatedPhone, SavePhoneDto.class);
        PhoneDto phoneDto = phoneService.update(1L, savePhoneDto);

        assertNotNull(phoneDto);
        assertEquals(ObjectMapperUtils.map(phoneDto, Phone.class), updatedPhone);
    }

    @Test
    public void updatePhoneNegativeTest() {
        Phone phone = testUtil.initPhone( "TestString", 111);
        Phone updatedPhone = testUtil.initPhone( "TestString", 222);
        Phone anotherPhone = testUtil.initPhone( "TestString", 333);

        when(phoneRepository.findById(1L)).thenReturn(java.util.Optional.of(phone));

        SavePhoneDto savePhoneDto = ObjectMapperUtils.map(updatedPhone, SavePhoneDto.class);
        PhoneDto phoneDto = phoneService.update(1L, savePhoneDto);

        assertNotNull(phoneDto);
        assertNotEquals(ObjectMapperUtils.map(phoneDto, Phone.class), anotherPhone);
    }
}