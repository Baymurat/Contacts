package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.utils.CustomUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Admin on 12.09.2018
 */
public class JDBCContactDao implements DAO<Contact, Integer> {
    private ResultSet resultSet = null;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    public JDBCContactDao(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Contact getEntityById(Integer id) {
        return null;
    }

    @Override
    public Contact update(Contact contact) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean insert(Contact contact) {
        return false;
    }

    @Override
    public List<Contact> getAll() {
        List<Contact> result = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM user");
            resultSet = preparedStatement.executeQuery();
            simpleMethod(resultSet, result);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeRsultSet(resultSet);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return result;
    }

    private void simpleMethod(ResultSet resultSet, Collection<Contact> contacts) {
        try {
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setName(resultSet.getString("name"));
                contact.setSurName(resultSet.getString("surname"));
                contact.setMiddleName(resultSet.getString("middlename"));
                contact.setGender(resultSet.getString("gender"));
                contact.setCitizenship(resultSet.getString("citizenship"));
                contact.setFamilyStatus(resultSet.getString("familystatus"));
                contact.setWebSite(resultSet.getString("website"));
                contact.setCurrentJob(resultSet.getString("currentjob"));
                contact.setEmail(resultSet.getString("email"));

                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
