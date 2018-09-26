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
        int[] values = {23, 12, 13, 17, 23, 19};

        for (int i = 0; i < values.length - 1; i++) {
            if (values[i] > values[i + 1]) {
                int t = values[i];
                values[i] = values[i + 1];
                values[i + 1] = t;
            }
        }

        for (int i : values) {
            System.out.print(i + " ");
        }
    }
}
