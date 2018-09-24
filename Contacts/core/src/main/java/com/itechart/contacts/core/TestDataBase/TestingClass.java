package com.itechart.contacts.core.TestDataBase;

import com.itechart.contacts.core.dao.JDBCAttachmentDao;
import com.itechart.contacts.core.dao.JDBCContactDao;
import com.itechart.contacts.core.dao.JDBCPhonesDao;
import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.ConnectionPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Admin on 18.09.2018
 */
public class TestingClass {
    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            Connection connection = dataSource.getConnection();

            JDBCContactDao contactDao = new JDBCContactDao(connection);
            JDBCAttachmentDao attachmentDao = new JDBCAttachmentDao(connection);
            JDBCPhonesDao phonesDao = new JDBCPhonesDao(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
