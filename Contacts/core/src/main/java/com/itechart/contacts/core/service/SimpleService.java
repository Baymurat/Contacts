package com.itechart.contacts.core.service;

import com.itechart.contacts.core.dao.JDBCAttachmentDao;
import com.itechart.contacts.core.dao.JDBCContactDao;
import com.itechart.contacts.core.dao.JDBCPhonesDao;
import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
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

    public void deleteRecord(int contactId) {

        JDBCContactDao contactDao;

        ConnectionPool connectionPool = new ConnectionPool();
        Savepoint savepoint = null;
        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            contactDao = new JDBCContactDao(connection);
            contactDao.delete(contactId);

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
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            CustomUtils.closeConnection(connection);
        }
    }

    public Result getContacts(int from, int count) {
        Result result = new Result();
        int allElementsCount = 0;

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

            resultContactsMap = contactDao.getRecords(from, count);
            resultAttachmentMap = attachmentDao.getRecords(from, count);
            resultPhoneMap = phonesDao.getRecords(from, count);

            allElementsCount = contactDao.getAllElementsCount();

            bindPhonesAndContacts(resultPhoneMap, resultContactsMap);
            bindAttachmentsAndContacts(resultAttachmentMap, resultContactsMap);

            result.setContactList(new ArrayList<>(resultContactsMap.values()));
            result.setAllElementsCount(allElementsCount);

            return result;
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
