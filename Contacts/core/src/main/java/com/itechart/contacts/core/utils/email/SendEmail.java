package com.itechart.contacts.core.utils.email;

import com.itechart.contacts.core.utils.error.CustomLogger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendEmail {
    private Message message = null;
    public static String SMTP_SERVER = null;
    public static String SMTP_Port = null;
    public static String SMTP_AUTH_USER = null;
    public static String SMTP_AUTH_PWD = null;
    public static String EMAIL_FROM = null;

    public SendEmail(final String emailTo, final String thema) {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.port", SMTP_Port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        try {
            Authenticator auth = new EmailAuthenticator(SMTP_AUTH_USER,
                    SMTP_AUTH_PWD);
            Session session = Session.getDefaultInstance(properties, auth);
            session.setDebug(false);

            InternetAddress email_from = new InternetAddress(EMAIL_FROM);
            InternetAddress email_to = new InternetAddress(emailTo);
            message = new MimeMessage(session);
            message.setFrom(email_from);
            message.setRecipient(Message.RecipientType.TO, email_to);
            message.setSubject(thema);
        } catch (MessagingException e) {
            CustomLogger.logger.error("Error in SendEmail class constructor ", e);
        }
    }

    public boolean sendMessage(final String text) {
        boolean result = false;
        try {
            Multipart mmp = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);

            message.setContent(mmp);

            Transport.send(message);
            result = true;
        } catch (MessagingException e) {
            CustomLogger.logger.error("Error in Service class, sendMessage() method", e);
        }
        return result;
    }
}
