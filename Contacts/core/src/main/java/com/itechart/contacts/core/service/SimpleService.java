package com.itechart.contacts.core.service;

import com.itechart.contacts.core.dao.JDBCAttachmentDao;
import com.itechart.contacts.core.dao.JDBCContactDao;
import com.itechart.contacts.core.dao.JDBCPhonesDao;
import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Message;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.ConnectionPool;
import com.itechart.contacts.core.utils.CustomUtils;
import com.itechart.contacts.core.utils.Result;

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

    public SimpleService() {
    }

    public void updateRecord(Contact contact) {
        JDBCContactDao contactDao;
        JDBCPhonesDao phonesDao;
        JDBCAttachmentDao attachmentsDao;

        ConnectionPool connectionPool = new ConnectionPool();
        Savepoint savepoint = null;
        try {
            List<Phone> phones = contact.getPhones();
            List<Attachment> attachments = contact.getAttachments();

            List<Integer> deletePhonesList = contact.getDeletePhonesList();
            List<Integer> deleteAttachmentsList = contact.getDeleteAttachmentsList();

            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            contactDao = new JDBCContactDao(connection);
            phonesDao = new JDBCPhonesDao(connection);
            attachmentsDao = new JDBCAttachmentDao(connection);

            contactDao.update(contact);

            if (phones != null) {
                for (Phone p : phones) {
                    if (!phonesDao.update(p)) {
                        p.setPersons_id(contact.getId());
                        phonesDao.insert(p);
                    }
                }
            }

            if (attachments != null) {
                for (Attachment a : attachments) {
                    if (!attachmentsDao.update(a)) {
                        a.setPersons_id(contact.getId());
                        attachmentsDao.insert(a);
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

    public void addRecord(Contact contact) {
        JDBCContactDao contactDao;
        JDBCPhonesDao phonesDao;
        JDBCAttachmentDao attachmentsDao;

        ConnectionPool connectionPool = new ConnectionPool();
        //Savepoint savepoint = null;
        try {
            List<Phone> phones = contact.getPhones();
            List<Attachment> attachments = contact.getAttachments();

            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            //savepoint = connection.setSavepoint();

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
                for (Attachment a : attachments) {
                    a.setPersons_id(contact.getId());
                    attachmentsDao.insert(a);
                }
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

    public void sendEmail(Message message) {
        if (message.getReceivers() != null) {
            for (String s : message.getReceivers()) {
                System.out.println(s);
            }
        }

        System.out.println(message.getMessageTheme());
        System.out.println(message.getMessageContent());
    }
}
