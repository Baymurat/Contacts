package com.itechart.properties;

import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.service.SimpleService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestClass {
    public static void main(String[] args) {
        SimpleService simpleService = new SimpleService();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String searchParam = dateFormat.format(new Date());
        /*System.out.println(searchParam);
        List<Contact> contacts = simpleService.getContactsByDateBirth(searchParam);

        for (Contact c : contacts) {
            System.out.println(c.getName() + "  " + c.getSurName());
        }*/

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        String s = date.toString();
        System.out.println(s);
    }
}


