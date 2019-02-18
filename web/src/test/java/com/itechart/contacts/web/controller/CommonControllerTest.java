package com.itechart.contacts.web.controller;

import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.person.entity.Person;
import com.itechart.contacts.core.phone.entity.Phone;
import com.itechart.contacts.core.utils.testutil.TestUtil;
import com.itechart.contacts.web.Application;
import com.itechart.contacts.web.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, WebConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CommonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void getContactsTest() throws Exception {
        mockMvc.perform(get("/contacts?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void addRecordTest() throws Exception {
        Person person = TestUtil.initPerson("Person");
        Attachment attachment = TestUtil.initAttachment("Attachment");
        Phone phone = TestUtil.initPhone("Phone", 222);
        MultipartFile[] files = TestUtil.initMultiFiles();

        person.setAttachments(new ArrayList<>(Collections.singletonList(attachment)));
        person.setPhones(new ArrayList<>(Collections.singletonList(phone)));

        HttpEntity<Person> entity = new HttpEntity<>(person, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/deleteContacts"),
                HttpMethod.POST, entity, String.class);

    }

    @Test
    public void deleteRecordTest() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(Arrays.toString(new long[]{1, 2}), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/deleteContacts"),
                HttpMethod.POST, entity, String.class);

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}