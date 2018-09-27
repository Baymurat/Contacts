package com.itechart.contacts.core.service;

import com.itechart.contacts.core.dao.JDBCAttachmentDao;
import com.itechart.contacts.core.dao.JDBCContactDao;
import com.itechart.contacts.core.dao.JDBCPhonesDao;
import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.ConnectionPool;
import com.itechart.contacts.core.utils.CustomUtils;

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

            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            contactDao = new JDBCContactDao(connection);
            attachmentsDao = new JDBCAttachmentDao(connection);
            phonesDao = new JDBCPhonesDao(connection);

            contactDao.update(contact);

            for (Phone p : phones) {
                p.setPersons_id(contact.getId());
                phonesDao.update(p);
            }

            for (Attachment a : attachments) {
                a.setPersons_id(contact.getId());
                attachmentsDao.update(a);
            }

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            CustomUtils.closeConnection(connection);
        }
    }

    public void deleteAttachment(Attachment attachment) {
        JDBCAttachmentDao attachmentDao;

        ConnectionPool connectionPool = new ConnectionPool();
        Savepoint savepoint = null;
        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            attachmentDao = new JDBCAttachmentDao(connection);

            attachmentDao.delete(attachment.getId());

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            CustomUtils.closeConnection(connection);
        }
    }

    public void deletePhone(Phone phone) {
        JDBCPhonesDao phonestDao;

        ConnectionPool connectionPool = new ConnectionPool();
        Savepoint savepoint = null;
        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            phonestDao = new JDBCPhonesDao(connection);

            phonestDao.delete(phone.getId());

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            CustomUtils.closeConnection(connection);
        }
    }

    public void deleteContact(Contact contact) {
        JDBCContactDao contactDao;

        ConnectionPool connectionPool = new ConnectionPool();
        Savepoint savepoint = null;
        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            contactDao = new JDBCContactDao(connection);

            contactDao.delete(contact.getId());

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            CustomUtils.closeConnection(connection);
        }
    }

    public void addRecord(Contact contact) {
        JDBCContactDao contactDao;
        JDBCPhonesDao phonesDao;
        JDBCAttachmentDao attachmentsDao;

        ConnectionPool connectionPool = new ConnectionPool();
        Savepoint savepoint = null;
        try {
            List<Phone> phones = contact.getPhones();
            List<Attachment> attachments = contact.getAttachments();

            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            contactDao = new JDBCContactDao(connection);
            attachmentsDao = new JDBCAttachmentDao(connection);
            phonesDao = new JDBCPhonesDao(connection);

            contactDao.insert(contact);

            for (Phone p : phones) {
                p.setPersons_id(contact.getId());
                phonesDao.insert(p);
            }

            for (Attachment a : attachments) {
                a.setPersons_id(contact.getId());
                attachmentsDao.insert(a);
            }

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            CustomUtils.closeConnection(connection);
        }
    }

    public ArrayList<Contact> getContacts(int from) {

        JDBCContactDao contactDao;
        JDBCAttachmentDao attachmentDao;
        JDBCPhonesDao phonesDao;

        ConnectionPool connectionPool = new ConnectionPool();

        HashMap<Integer, Contact> resultContactsMap;
        HashMap<Integer, Attachment> resultAttachmentMap;
        HashMap<Integer, Phone> resultPhoneMap;

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();

            contactDao = new JDBCContactDao(connection);
            attachmentDao = new JDBCAttachmentDao(connection);
            phonesDao = new JDBCPhonesDao(connection);

            resultContactsMap = contactDao.getRecords(from);
            resultAttachmentMap = attachmentDao.getRecords(from);
            resultPhoneMap = phonesDao.getRecords(from);

            bindPhonesAndContacts(resultPhoneMap, resultContactsMap);
            bindAttachmentsAndContacts(resultAttachmentMap, resultContactsMap);

            return new ArrayList<>(resultContactsMap.values());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeConnection(connection);
        }

        return null;
    }

    private void bindPhonesAndContacts(HashMap<Integer, Phone> phoneMap, HashMap<Integer, Contact> contactMap) {
        Iterator iterator = phoneMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            Phone tepmPhoneObj = (Phone)pair.getValue();
            contactMap.get(tepmPhoneObj.getPersons_id()).getPhones().add(tepmPhoneObj);
        }
    }

    private void bindAttachmentsAndContacts(HashMap<Integer, Attachment> attachmentMap, HashMap<Integer, Contact> contactMap) {
        Iterator iterator = attachmentMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            Attachment tempAttachmentObj = (Attachment)pair.getValue();
            contactMap.get(tempAttachmentObj.getPersons_id()).getAttachments().add(tempAttachmentObj);
        }
    }
}
