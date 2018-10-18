package com.itechart.contacts.core.service;

import com.itechart.contacts.core.dao.JDBCAttachmentDao;
import com.itechart.contacts.core.dao.JDBCContactDao;
import com.itechart.contacts.core.dao.JDBCPhonesDao;
import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.*;
import com.itechart.contacts.core.utils.email.CustomMessageHolder;
import com.itechart.contacts.core.utils.email.SendEmail;
import com.itechart.contacts.core.utils.error.CustomException;
import com.itechart.contacts.core.utils.error.CustomLogger;
import org.antlr.stringtemplate.StringTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.*;

/**
 * Created by Admin on 13.09.2018
 */

public class SimpleService {

    private Connection connection = null;
    private FileManageService fileManageService = new FileManageService();

    public SimpleService() {
    }

    public void updateRecord(Contact contact, List<Object> bytesAndExtensOfFiles, List<Object> photo) throws Exception {
        JDBCContactDao contactDao;
        JDBCPhonesDao phonesDao;
        JDBCAttachmentDao attachmentsDao;

        ConnectionPool connectionPool = new ConnectionPool();
        try {
            List<Phone> phones = contact.getPhones();
            List<Attachment> attachments = contact.getAttachments();

            List<Integer> deletePhonesList = contact.getDeletePhonesList();
            List<Integer> deleteAttachmentsList = contact.getDeleteAttachmentsList();

            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            contactDao = new JDBCContactDao(connection);
            phonesDao = new JDBCPhonesDao(connection);
            attachmentsDao = new JDBCAttachmentDao(connection);

            contactDao.update(contact);

            if (phones != null) {
                for (Phone p : phones) {
                    if (p.getId() == -1) {
                        p.setPersons_id(contact.getId());
                        phonesDao.insert(p);
                    } else {
                        phonesDao.update(p);
                    }
                }
            }

            if (attachments != null) {
                int step = 0;
                for (Attachment a : attachments) {
                    if (a.getId() == -1) {
                        a.setPersons_id(contact.getId());
                        attachmentsDao.insert(a);

                        byte[] bytes = (byte[]) bytesAndExtensOfFiles.get(step * 2);
                        String fileExtension = (String) bytesAndExtensOfFiles.get(step * 2 + 1);
                        fileManageService.uploadFile(contact.getId(), a.getId(), bytes, fileExtension);
                        step++;
                    } else {
                        attachmentsDao.update(a);
                    }
                }
            }

            if (deletePhonesList != null) {
                for (Integer i : deletePhonesList) {
                    phonesDao.delete(i);
                }
            }

            if (deleteAttachmentsList != null) {
                for (Integer i : deleteAttachmentsList) {
                    attachmentsDao.delete(i);
                }
                fileManageService.deleteFiles(contact.getId(), deleteAttachmentsList);
            }

            if (photo != null) {
                byte[] bytes = (byte[]) photo.get(0);
                String fileExtension = (String) photo.get(1);
                fileManageService.savePhoto(contact.getId(), bytes, fileExtension);
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                CustomLogger.logger.error("Error in rollback connection. Service class, updateRecord() method", e);
                throw new CustomException("", e);
            } finally {
                CustomUtils.closeConnection(connection);
            }

            CustomLogger.logger.error("Error in Service class, updateRecord() method", e);
            throw new CustomException("", e);
        }
    }

    public void deleteRecord(int[] deleteContactsId) throws Exception {

        JDBCContactDao contactDao;

        ConnectionPool connectionPool = new ConnectionPool();
        Savepoint savepoint = null;
        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            contactDao = new JDBCContactDao(connection);
            for (int i : deleteContactsId) {
                contactDao.delete(i);
            }

            connection.commit();

            fileManageService.deleteUsers(deleteContactsId);
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                CustomLogger.logger.error("Error rollback connection, in Service class, deleteRecord() method", e);
                throw new CustomException("", e);
            } finally {
                CustomUtils.closeConnection(connection);
            }

            CustomLogger.logger.error("Error in Service class, deleteRecord() method", e);
            throw new CustomException("", e);
        }
    }

    public void addRecord(Contact contact, List<Object> bytesAndExtensOfFiles, List<Object> photo) throws Exception {
        JDBCContactDao contactDao;
        JDBCPhonesDao phonesDao;
        JDBCAttachmentDao attachmentsDao;

        ConnectionPool connectionPool = new ConnectionPool();
        try {
            List<Phone> phones = contact.getPhones();
            List<Attachment> attachments = contact.getAttachments();

            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            contactDao = new JDBCContactDao(connection);
            attachmentsDao = new JDBCAttachmentDao(connection);
            phonesDao = new JDBCPhonesDao(connection);

            contactDao.insert(contact);

            if(phones != null) {
                for (Phone p : phones) {
                    p.setPersons_id(contact.getId());
                    phonesDao.insert(p);
                }
            }

            if (attachments != null) {
                for (int i = 0; i < attachments.size(); i++) {
                    Attachment currentAtt = attachments.get(i);
                    currentAtt.setPersons_id(contact.getId());
                    attachmentsDao.insert(currentAtt);

                    byte[] bytes = (byte[]) bytesAndExtensOfFiles.get(i * 2);
                    String fileExtension = (String) bytesAndExtensOfFiles.get(i * 2 + 1);
                    fileManageService.uploadFile(contact.getId(), currentAtt.getId(), bytes, fileExtension);
                }
            }

            if (photo != null) {
                byte[] bytes = (byte[]) photo.get(0);
                String fileExtension = (String) photo.get(1);
                fileManageService.savePhoto(contact.getId(), bytes, fileExtension);
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                CustomLogger.logger.error("Error rollback connection, in Service class, deleteRecord() method", e);
                throw new CustomException("", e);
            } finally {
                CustomUtils.closeConnection(connection);
            }

            CustomLogger.logger.error("Error in Service class, addRecord() method", e);
            throw new CustomException("", e);
        }
    }

    public Result getContacts(int from, int range, String searchDescription) throws Exception {
        Result result = new Result();

        JDBCContactDao contactDao;
        JDBCAttachmentDao attachmentDao;
        JDBCPhonesDao phonesDao;

        ConnectionPool connectionPool = new ConnectionPool();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();

            contactDao = new JDBCContactDao(connection);
            attachmentDao = new JDBCAttachmentDao(connection);
            phonesDao = new JDBCPhonesDao(connection);

            List<Contact> contactList = contactDao.getContacts(from, range, searchDescription);

            if (contactList != null) {
                for (Contact c : contactList) {
                    List<Attachment> attachmentList = attachmentDao.getContactAttachment(c.getId());
                    List<Phone> phoneList = phonesDao.getContactPhones(c.getId());

                    c.setAttachments(attachmentList);
                    c.setPhones(phoneList);
                }
            }

            int allElementsCount = contactDao.getAllElementsCount();

            result.setContactList(contactList);
            result.setAllElementsCount(allElementsCount);
        } catch (SQLException e) {
            CustomLogger.logger.error("Error in Service class, getContacts() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closeConnection(connection);
        }

        return result;
    }

    public Contact getContact(int id) throws Exception {
        Contact result = null;

        JDBCContactDao contactDao;
        JDBCAttachmentDao attachmentDao;
        JDBCPhonesDao phonesDao;

        ConnectionPool connectionPool = new ConnectionPool();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();

            contactDao = new JDBCContactDao(connection);
            attachmentDao = new JDBCAttachmentDao(connection);
            phonesDao = new JDBCPhonesDao(connection);

            List<Attachment> attachmentList = attachmentDao.getContactAttachment(id);
            List<Phone> phoneList = phonesDao.getContactPhones(id);

            result = contactDao.getEntityById(id);
            result.setAttachments(attachmentList);
            result.setPhones(phoneList);
        } catch (SQLException e) {
            CustomLogger.logger.error("Error in Service class, getContact() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closeConnection(connection);
        }

        return result;
    }

    public Attachment getAttachment(int id) throws Exception {
        JDBCAttachmentDao attachmentDao;
        ConnectionPool connectionPool = new ConnectionPool();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();

            attachmentDao = new JDBCAttachmentDao(connection);
            return attachmentDao.getEntityById(id);
        } catch (SQLException e) {
            CustomLogger.logger.error("Error in Service class, getAttachment() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closeConnection(connection);
        }
    }

    public void sendEmail(CustomMessageHolder messageHolder) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("email");

        //cont = getCOnEmail();

        SendEmail.SMTP_SERVER = resourceBundle.getString("server");
        SendEmail.SMTP_Port = resourceBundle.getString("port");
        SendEmail.EMAIL_FROM = resourceBundle.getString("from");
        SendEmail.SMTP_AUTH_USER = resourceBundle.getString("user");
        SendEmail.SMTP_AUTH_PWD = resourceBundle.getString("pass");

        for (String emailTo : messageHolder.getReceivers()) {
            if (!emailTo.isEmpty()) {
                StringTemplate template = new StringTemplate();
                Contact contact = new Contact();
                template.setAttribute("$name$", contact);
                //String string = temp.met("$name$", inst);
                //SendEmail se = new SendEmail(emailTo, templ);
                //se.sendMessage(messageHolder.getMessageText());
            }
        }
    }

    public List<Contact> getContactsByDateBirth(Date dateBirth) throws Exception {
        JDBCContactDao contactDao;
        ConnectionPool connectionPool = new ConnectionPool();
        List<Contact> contacts = new ArrayList<>();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            contactDao = new JDBCContactDao(connection);

            contacts = contactDao.getContactsByDateBirth(dateBirth);
        } catch (SQLException e) {
            CustomLogger.logger.error("Error in Service class, getContactsByDateBirth() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closeConnection(connection);
        }

        return contacts;
    }

    public String encodeFileToBase64Binary(File file) throws CustomException {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
        } catch (IOException e) {
            CustomLogger.logger.error("Error in Service class, encodeFileToBase64Binary() method", e);
            throw new CustomException("", e);
        }

        return encodedfile;
    }

    public Result advancedSearch(Contact contact) throws Exception {
        Result result = new Result();
        List<Contact> contactList = null;

        JDBCContactDao contactDao;
        JDBCAttachmentDao attachmentDao;
        JDBCPhonesDao phonesDao;

        ConnectionPool connectionPool = new ConnectionPool();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();

            contactDao = new JDBCContactDao(connection);
            attachmentDao = new JDBCAttachmentDao(connection);
            phonesDao = new JDBCPhonesDao(connection);

            contactList = contactDao.getByAdvancedSearch(contact);

            if (contactList != null) {
                for (Contact c : contactList) {
                    List<Attachment> attachmentList = attachmentDao.getContactAttachment(c.getId());
                    List<Phone> phoneList = phonesDao.getContactPhones(c.getId());

                    c.setPhones(phoneList);
                    c.setAttachments(attachmentList);
                }
            }

            int allElementsCount = contactDao.getAllElementsCount();

            result.setContactList(contactList);
            result.setAllElementsCount(allElementsCount);
        } catch (SQLException e) {
            CustomLogger.logger.error("Error in Service class, getContact() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closeConnection(connection);
        }

        return result;
    }
}
