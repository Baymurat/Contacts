package com.itechart.contacts.core.utils.testutil;

import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.person.dto.PersonFilter;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.phone.entity.Phone;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;

public class TestUtil {

    public static Person initPerson(String testString) {
        Person person = new Person();
        person.setName(testString);
        person.setSurName(testString);
        person.setMiddleName(testString);
        person.setFamilyStatus(testString);
        person.setCurrentJob(testString);
        person.setStreetHouseApart(testString);
        person.setIndex(testString);
        person.setBirthDate(new Date(System.currentTimeMillis()));
        person.setWebSite(testString);
        person.setGender(testString);
        person.setCitizenship(testString);
        person.setEmail(testString);
        person.setCountry(testString);
        person.setCity(testString);

        return person;
    }

    public static Attachment initAttachment(String testString) {
        Attachment attachment = new Attachment();
        attachment.setLoadDate(new Date(System.currentTimeMillis()));
        attachment.setFileName(testString);
        attachment.setComments(testString);

        return attachment;
    }

    public static Phone initPhone(String testString, int testInteger) {
        Phone phone = new Phone();
        phone.setPhoneNumber(testInteger);
        phone.setCodeOfOperator(testInteger);
        phone.setCodeOfCountry(testInteger);
        phone.setType(testString);
        phone.setComments(testString + " phone");
        //phone.setPerson(person);

        return phone;
    }

    public static MultipartFile[] initMultiFiles() throws IOException {
        return new MultipartFile[]{new MockMultipartFile("FileName", "testFile.jpg",
                "image/jpg", Files.readAllBytes(new File("D:\\testFile.jpg").toPath()))};
    }

    public static PersonFilter initPersonFilter(String firstLastName, String currentJob, long phoneNum) {
        PersonFilter personFilter = new PersonFilter();
        personFilter.setFirstAndLastName(firstLastName);
        personFilter.setCurrentJob(currentJob);
        personFilter.setPhoneNumber(phoneNum);

        return personFilter;
    }
}
