package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.CustomUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 12.09.2018
 */
public class JDBCContactDao implements DAO<Contact, Integer> {
    private ResultSet resultSetWithPhones = null;
    private ResultSet resultSetWithAttachments = null;
    private ResultSet resultSetContacts = null;

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
    public HashMap<Integer, Contact> getRecords(int from) {
        int range = 11;
        HashMap<Integer, Contact> result = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM persons WHERE id > " + from + " && id < " + from + range);
            resultSetContacts = preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT persons.id AS id, countrycode, operatorcode, phonebumber, comments FROM persons JOIN phones ON (persons.id = phones.persons_id) WHERE persons.id > " + from + " && persons.id < " + (from + range) + ";");
            resultSetWithPhones = preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT persons.id AS id, filename, loaddate, comments FROM persons JOIN attachments ON (persons.id = attachments.persons_id) WHERE persons.id >" + from + " && persons.id < " + (from + range) +";");
            resultSetWithAttachments = preparedStatement.executeQuery();

            initResultMap(resultSetContacts, result);
            bindContactAndPhones(resultSetWithPhones, result);
            bindContactAndAttachments(resultSetWithAttachments, result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            CustomUtils.closeRsultSet(resultSetWithPhones);
            CustomUtils.closeRsultSet(resultSetWithAttachments);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return result;
    }

    private void bindContactAndPhones(ResultSet resultSet, HashMap<Integer, Contact> resultMap) {
        try {
            while (resultSet.next()) {
                int currentId = resultSet.getInt(1);

                Phone phone = new Phone();
                phone.setCodeOfCountry(resultSet.getInt("countrycode"));
                phone.setCodeOfOperator(resultSet.getInt("operatorcode"));
                phone.setPhoneNumber(resultSet.getInt("phonebumber"));
                phone.setComments(resultSet.getString("comments"));
                if (resultMap.get(currentId).getPhones() == null) {
                    resultMap.get(currentId).setPhones(new ArrayList<>());
                    resultMap.get(currentId).getPhones().add(phone);
                } else {resultMap.get(currentId).getPhones().add(phone);}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bindContactAndAttachments(ResultSet resultSet, HashMap<Integer, Contact> resultMap) {
        try {
            while (resultSet.next()) {
                int currentId = resultSet.getInt(1);

                Attachment attachment = new Attachment();
                attachment.setFileName(resultSet.getString("filename"));
                attachment.setComments(resultSet.getString("comments"));
                attachment.setLoadDate(resultSet.getDate("loaddate"));
                if (resultMap.get(currentId).getAttachments() == null) {
                    resultMap.get(currentId).setAttachments(new ArrayList<>());
                    resultMap.get(currentId).getAttachments().add(attachment);
                } else {resultMap.get(currentId).getAttachments().add(attachment);}

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initResultMap(ResultSet resultSet, HashMap<Integer, Contact> resultMap) {
        try {
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString("name"));
                contact.setSurName(resultSet.getString("surname"));
                contact.setMiddleName(resultSet.getString("middlename"));
                contact.setCitizenship(resultSet.getString("citizenship"));
                contact.setFamilyStatus(resultSet.getString("familystatus"));
                contact.setWebSite(resultSet.getString("website"));
                contact.setEmail(resultSet.getString("email"));
                contact.setCurrentJob(resultSet.getString("currentjob"));
                contact.setGender(resultSet.getString("gender"));
                contact.setBirthDate(resultSet.getDate("datebirth"));
                contact.setCountry(resultSet.getString("country"));
                contact.setCity(resultSet.getString("city"));
                contact.setStreetHouseApart(resultSet.getString("street_house_apart"));
                contact.setIndex(resultSet.getInt("p_index"));

                resultMap.put(contact.getId(), contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
