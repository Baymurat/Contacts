package com.itechart.contacts.core.TestPackage;

import com.itechart.contacts.core.entities.Contact;
import org.antlr.stringtemplate.StringTemplate;

public class Test {
    public static void main(String[] args) {
        /*String string = "SELECT * FROM persons WHERE     AND surname = ? AND middlename = ?";
        System.out.println(string);
        string = string.replace("WHERE+\\sAND", "WHERE");
        System.out.println(string);*/

        Contact contact = new Contact();
        contact.setName("SIMPLE CONTACT");
        String text = "text of $contact.name$ somthng else";

        StringTemplate template = new StringTemplate(text);
        template.setAttribute("name", contact);
        System.out.println(template.toString());
        System.out.println(contact.getName());
    }
}
