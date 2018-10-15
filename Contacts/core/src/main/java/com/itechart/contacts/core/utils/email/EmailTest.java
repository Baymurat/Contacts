package com.itechart.contacts.core.utils.email;

import java.util.ResourceBundle;

public class EmailTest {

    public static void main(String[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("email");

        SendEmail.SMTP_SERVER = resourceBundle.getString("server");
        SendEmail.SMTP_Port = resourceBundle.getString("port");
        SendEmail.EMAIL_FROM = resourceBundle.getString("from");
        SendEmail.SMTP_AUTH_USER = resourceBundle.getString("user");
        SendEmail.SMTP_AUTH_PWD = resourceBundle.getString("pass");

        String emailTo = resourceBundle.getString("to");
        String thema = resourceBundle.getString("thema");
        String text = resourceBundle.getString("text");

        SendEmail se = new SendEmail(emailTo, thema);
        System.out.println(se.sendMessage(text));
    }
}
