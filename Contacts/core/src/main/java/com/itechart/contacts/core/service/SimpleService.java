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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 13.09.2018
 */

public class SimpleService {

    private Connection connection = null;

    public SimpleService() {
    }

    public void addRecord() {
        JDBCContactDao contactDao = null;
        JDBCPhonesDao phonesDao = null;
        JDBCAttachmentDao attachmentsDao = null;

        ConnectionPool connectionPool = new ConnectionPool();
    }

    public Map<Integer, Contact> getContacts(int from) {

        JDBCContactDao contactDao;

        ConnectionPool connectionPool = new ConnectionPool();

        Map<Integer, Contact> result ; new HashMap<>();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();

            contactDao = new JDBCContactDao(connection);

            result = contactDao.getRecords(from);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeConnection(connection);
        }

        return null;
    }
}
