package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.CustomUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 12.09.2018
 */
public class JDBCContactDao implements DAO<Contact, Integer> {
    private ResultSet resultSetContacts = null;
    private PreparedStatement preparedStatement = null;

    private Connection connection;

    public JDBCContactDao(Connection connection) {
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
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM persons WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return false;
    }

    @Override
    public int insert(Contact contact) {
        try {
            boolean existId = true;

            preparedStatement = connection.prepareStatement("INSERT INTO persons VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            if (contact.getId() == 0) {
                preparedStatement.setString(1, null);
                existId = false;
            } else {
                preparedStatement.setInt(1, contact.getId());
            }
            //preparedStatement.setString(1, null);
            preparedStatement.setString(2, contact.getName());
            preparedStatement.setString(3, contact.getSurName());
            preparedStatement.setString(4, contact.getMiddleName());
            preparedStatement.setString(5, contact.getCitizenship());
            preparedStatement.setString(6, contact.getFamilyStatus());
            preparedStatement.setString(7, contact.getWebSite());
            preparedStatement.setString(8, contact.getEmail());
            preparedStatement.setString(9, contact.getCurrentJob());
            preparedStatement.setString(10, contact.getGender());
            //java.lang.ClassCastException: java.util.Date cannot be cast to java.sql.Date
            preparedStatement.setDate(11, (Date) contact.getBirthDate());
            preparedStatement.setString(12, contact.getCountry());
            preparedStatement.setString(13, contact.getCitizenship());
            preparedStatement.setString(14, contact.getStreetHouseApart());
            preparedStatement.setInt(15, contact.getIndex());
            preparedStatement.executeUpdate();

            if (!existId) {
                resultSetContacts = preparedStatement.executeQuery("select last_insert_id() as last_id from persons");
                if (resultSetContacts.next()) {
                    contact.setId(resultSetContacts.getInt("last_id"));
                }
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeResultSet(resultSetContacts);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return -1;
    }

    @Override
    public HashMap<Integer, Contact> getRecords(int from) {
        int range = 11;
        HashMap<Integer, Contact> result = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM persons WHERE id > " + from + " && id < " + from + range);
            resultSetContacts = preparedStatement.executeQuery();

            while (resultSetContacts.next()) {
                Contact contact = new Contact();
                contact.setId(resultSetContacts.getInt(1));
                contact.setName(resultSetContacts.getString("name"));
                contact.setSurName(resultSetContacts.getString("surname"));
                contact.setMiddleName(resultSetContacts.getString("middlename"));
                contact.setCitizenship(resultSetContacts.getString("citizenship"));
                contact.setFamilyStatus(resultSetContacts.getString("familystatus"));
                contact.setWebSite(resultSetContacts.getString("website"));
                contact.setEmail(resultSetContacts.getString("email"));
                contact.setCurrentJob(resultSetContacts.getString("currentjob"));
                contact.setGender(resultSetContacts.getString("gender"));
                contact.setBirthDate(resultSetContacts.getDate("datebirth"));
                contact.setCountry(resultSetContacts.getString("country"));
                contact.setCity(resultSetContacts.getString("city"));
                contact.setStreetHouseApart(resultSetContacts.getString("street_house_apart"));
                contact.setIndex(resultSetContacts.getInt("p_index"));

                ArrayList<Phone> phones = new ArrayList<>();
                ArrayList<Attachment> attachments = new ArrayList<>();

                contact.setPhones(phones);
                contact.setAttachments(attachments);

                result.put(contact.getId(), contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeResultSet(resultSetContacts);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return result;
    }
}
