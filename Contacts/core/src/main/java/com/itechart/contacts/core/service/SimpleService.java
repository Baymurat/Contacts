package com.itechart.contacts.core.service;

import com.itechart.contacts.core.dao.JDBCAttachmentDao;
import com.itechart.contacts.core.dao.JDBCContactDao;
import com.itechart.contacts.core.dao.JDBCPhonesDao;
import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.ConnectionPool;
import com.itechart.contacts.core.utils.CustomUtils;
import com.itechart.contacts.core.utils.FileManageService;
import com.itechart.contacts.core.utils.Result;
import com.itechart.contacts.core.utils.email.CustomMessageHolder;
import com.itechart.contacts.core.utils.email.SendEmail;

import javax.sql.DataSource;
import java.sql.Connection;
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

    public void updateRecord(Contact contact, List<Object> bytesAndExtensOfFiles) {
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

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                CustomUtils.closeConnection(connection);
            }
        }
    }

    public void deleteRecord(int[] deleteContactsId) {

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
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                CustomUtils.closeConnection(connection);
            }
        }
    }

    public void addRecord(Contact contact, List<Object> bytesAndExtensOfFiles, List<Object> photo) {
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
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            CustomUtils.closeConnection(connection);
        }
    }

    public Result getContacts(int from, int range, String searchDescription) {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeConnection(connection);
        }

        return result;
    }

    public Contact getContact(int id) {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeConnection(connection);
        }

        return result;
    }

    public Attachment getAttachment(int id) {
        JDBCAttachmentDao attachmentDao;
        ConnectionPool connectionPool = new ConnectionPool();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();

            attachmentDao = new JDBCAttachmentDao(connection);
            return attachmentDao.getEntityById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void sendEmail(CustomMessageHolder messageHolder) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("email");

        SendEmail.SMTP_SERVER = resourceBundle.getString("server");
        SendEmail.SMTP_Port = resourceBundle.getString("port");
        SendEmail.EMAIL_FROM = resourceBundle.getString("from");
        SendEmail.SMTP_AUTH_USER = resourceBundle.getString("user");
        SendEmail.SMTP_AUTH_PWD = resourceBundle.getString("pass");

        for (String emailTo : messageHolder.getReceivers()) {
            if (!emailTo.isEmpty()) {
                SendEmail se = new SendEmail(emailTo, messageHolder.getMessageTheme());
                se.sendMessage(messageHolder.getMessageText());
            }
        }
    }

    public List<Contact> getContactsByDateBirth(String dateBirth) {
        JDBCContactDao contactDao;
        ConnectionPool connectionPool = new ConnectionPool();
        List<Contact> contacts = new ArrayList<>();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            contactDao = new JDBCContactDao(connection);

            contacts = contactDao.getContactsByDateBirth(dateBirth);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }
}
