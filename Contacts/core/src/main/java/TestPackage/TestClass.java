package TestPackage;

import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.service.SimpleService;

import java.sql.Date;
import java.util.List;

public class TestClass {
    public static void main(String[] args) {
        SimpleService simpleService = new SimpleService();
        java.sql.Date date = new Date(System.currentTimeMillis());

        List<Contact> contacts = simpleService.getContactsByDateBirth(date);

        for (Contact c : contacts) {
            System.out.println(c.getName() + "  " + c.getSurName());
        }
    }
}


