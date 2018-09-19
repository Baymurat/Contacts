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

            List<Contact> contacts = contactDao.getAll();
            List<Attachment> attachments = attachmentDao.getAll();
            List<Phone> phones = phonesDao.getAll();

            for (int i = 0; i < contacts.size(); i++) {
                System.out.print("Current contact has: " + contacts.get(i).getName() + "  " + contacts.get(i).getSurName() + "  " + contacts.get(i).getMiddleName());
                System.out.print("  " + attachments.get(i).getFileName() + "  ");
                System.out.println(phones.get(i).getCodeOfCountry() + phones.get(i).getCodeOfOperator() + phones.get(i).getPhoneNumber());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
