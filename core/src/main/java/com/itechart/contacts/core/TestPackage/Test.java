package com.itechart.contacts.core.TestPackage;

import com.itechart.contacts.core.entities.Contact;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

public class Test {
    public static void main(String[] args) {
        /*String string = "SELECT * FROM persons WHERE     AND surname = ? AND middlename = ?";
        System.out.println(string);
        string = string.replace("WHERE+\\sAND", "WHERE");
        System.out.println(string);*/

        Contact contact = new Contact();
        contact.setName("SIMPLE CONTACT");
        contact.setSurName("SURNAME");
        contact.setMiddleName("MIDDLENAME");

        StringTemplateGroup group = new StringTemplateGroup("myGroup", "C:\\Users\\Student\\IdeaProjects\\Shared\\" +
                "Contacts\\core\\src\\main\\java\\com\\itechart\\contacts\\core\\templates", DefaultTemplateLexer.class);

        StringTemplate template = group.getInstanceOf("demotemplate");
        template.setAttribute("contact", contact);

        System.out.println(template.toString());
    }
}
