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
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Admin on 13.09.2018
 */

public class SimpleService {

    private Connection connection = null;

    public SimpleService() {
    }

    public void addRecord() {

    }

    public Collection<Contact> getContacts() {

        JDBCContactDao contactDao = null;
        JDBCPhonesDao phonesDao = null;
        JDBCAttachmentDao attachmentsDao = null;

        ConnectionPool connectionPool = new ConnectionPool();

        Collection<Contact> contacts = new ArrayList<>();
        Collection<Phone> phones = new ArrayList<>();
        Collection<Attachment> attachments = new ArrayList<>();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();

            contactDao = new JDBCContactDao(connection);
            phonesDao = new JDBCPhonesDao(connection);
            attachmentsDao = new JDBCAttachmentDao(connection);

            attachments = attachmentsDao.getAll();
            phones = phonesDao.getAll();
            contacts = contactDao.getAll();

            return contacts;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeConnection(connection);
        }

        return null;
    }
}
