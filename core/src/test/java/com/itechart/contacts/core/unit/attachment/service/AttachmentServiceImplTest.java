package com.itechart.contacts.core.unit.attachment.service;

import com.itechart.contacts.core.utils.testutil.TestUtil;
import com.itechart.contacts.core.attachment.dto.AttachmentDto;
import com.itechart.contacts.core.attachment.dto.SaveAttachmentDto;
import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.attachment.repository.AttachmentRepository;
import com.itechart.contacts.core.attachment.service.AttachmentServiceImpl;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.person.repository.PersonRepository;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class AttachmentServiceImplTest {

    @InjectMocks
    private AttachmentServiceImpl attachmentService;

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private PersonRepository personRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private TestUtil testUtil = new TestUtil();

    @Test
    public void createAttachmentPositiveTest() {
        Person person = new Person();
        person.setId(1L);

        Attachment expected = testUtil.initAttachment("TestString");
        expected.setId(1L);

        when(attachmentRepository.save(expected)).thenReturn(expected);
        when(personRepository.getOne(1L)).thenReturn(person);

        SaveAttachmentDto saveAttachmentDto = ObjectMapperUtils.map(expected, SaveAttachmentDto.class);
        saveAttachmentDto.setPersonId(1L);

        Attachment actual = ObjectMapperUtils.map(attachmentService.create(saveAttachmentDto), Attachment.class);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void createAttachmentNegativeTest() {
        Person person = new Person();
        person.setId(1L);

        Attachment expected = testUtil.initAttachment("TestString");
        Attachment unexpected = testUtil.initAttachment("Another TestString");

        when(attachmentRepository.save(expected)).thenReturn(expected);
        when(personRepository.getOne(1L)).thenReturn(person);

        SaveAttachmentDto saveAttachmentDto = ObjectMapperUtils.map(expected, SaveAttachmentDto.class);
        saveAttachmentDto.setPersonId(1L);

        Attachment actual = ObjectMapperUtils.map(attachmentService.create(saveAttachmentDto), Attachment.class);

        assertNotNull(actual);
        assertNotEquals(unexpected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAttachmentNegativeTestIllArgExc() {
        attachmentService.create(null);
    }

    @Test(expected = NullPointerException.class)
    public void createAttachmentNegativeTestNPE() {
        Person person = new Person();
        person.setId(1L);

        Attachment attachment = new Attachment();

        SaveAttachmentDto saveAttachmentDto = ObjectMapperUtils.map(attachment, SaveAttachmentDto.class);

        when(attachmentRepository.save(attachment)).thenReturn(attachment);

        AttachmentDto attachmentDto = attachmentService.create(saveAttachmentDto);

        // Should generate NullPointerException
        String comments = attachmentDto.getComments();

        assertNull(attachmentDto.getLoadDate());
        assertNull(attachmentDto.getFileName());
        assertEquals(attachmentDto.getId(), 0);
    }


    @Test
    public void readAttachmentPositiveTest() {
        Attachment expected = testUtil.initAttachment("TestString");

        when(attachmentRepository.findById(1L)).thenReturn(java.util.Optional.of(expected));

        AttachmentDto attachmentDto = attachmentService.getAttachment(1L);
        Attachment actual = ObjectMapperUtils.map(attachmentDto, Attachment.class);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void readAttachmentNegativeTest() {
        Attachment expected = testUtil.initAttachment("TestString");
        Attachment unexpected = testUtil.initAttachment("Another TestString");

        when(attachmentRepository.findById(1L)).thenReturn(java.util.Optional.of(expected));

        AttachmentDto attachmentDto = attachmentService.getAttachment(1L);
        Attachment actual = ObjectMapperUtils.map(attachmentDto, Attachment.class);

        assertNotNull(attachmentDto);
        assertNotEquals(unexpected, actual);
    }

    @Test
    public void updateAttachmentPositiveTest() {
        Attachment prevAttachment = testUtil.initAttachment("TestString");
        Attachment expected = testUtil.initAttachment("Updated TestString");

        when(attachmentRepository.findById(1L)).thenReturn(java.util.Optional.of(prevAttachment));

        AttachmentDto attachmentDto = attachmentService.update(1L, ObjectMapperUtils.map(expected, SaveAttachmentDto.class));
        Attachment actual = ObjectMapperUtils.map(attachmentDto, Attachment.class);

        assertNotNull(attachmentDto);
        assertEquals(expected, actual);
    }

    @Test
    public void updateAttachmentNegativeTest() {
        Attachment prevAttachment = testUtil.initAttachment("TestString");
        Attachment expected = testUtil.initAttachment("Updated TestString");
        Attachment unexpected = testUtil.initAttachment("Another TestString");

        when(attachmentRepository.findById(1L)).thenReturn(java.util.Optional.of(prevAttachment));

        AttachmentDto attachmentDto = attachmentService.update(1L, ObjectMapperUtils.map(expected, SaveAttachmentDto.class));
        Attachment actual = ObjectMapperUtils.map(attachmentDto, Attachment.class);

        assertNotNull(actual);
        assertNotEquals(unexpected, actual);
    }
}