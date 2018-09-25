package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.CustomUtils;

import javax.sql.DataSource;
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

    public JDBCContactDao(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Contact getEntityById(Integer id) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM persons WHERE id = " + id + ";");
            resultSetContacts = preparedStatement.executeQuery();
            return new Contact();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeRsultSet(resultSetContacts);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return null;
    }

    @Override
    public Contact update(Contact contact) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE persons SET name = ?, surname = ?, middlename = ?," +
                    "citizenship = ?, familystatus = ?, website = ?, email = ?, currentjob = ?, gender = ?, datebirth = ?," +
                    "country = ?, city = ?, street_house_apart = ?, p_index = ? WHERE id = ?");
            preparedStatement.setInt(15, contact.getId());
            statementExecutor(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closePreparedStatement(preparedStatement);
        }

        return null;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM persons WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SET @persons_id_count = 0;");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("UPDATE persons SET persons.id = @persons_id_count:= @persons_id_count + 1;");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("ALTER TABLE persons AUTO_INCREMENT = 1");
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
            preparedStatement = connection.prepareStatement("INSERT INTO persons VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            statementExecutor(contact);
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
            CustomUtils.closeRsultSet(resultSetContacts);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return result;
    }

    private void statementExecutor(Contact contact) {
        try {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getSurName());
            preparedStatement.setString(3, contact.getMiddleName());
            preparedStatement.setString(4, contact.getCitizenship());
            preparedStatement.setString(5, contact.getFamilyStatus());
            preparedStatement.setString(6, contact.getWebSite());
            preparedStatement.setString(7, contact.getEmail());
            preparedStatement.setString(8, contact.getCurrentJob());
            preparedStatement.setString(9, contact.getGender());
            preparedStatement.setDate(10, (Date) contact.getBirthDate());
            preparedStatement.setString(11, contact.getCountry());
            preparedStatement.setString(12, contact.getCitizenship());
            preparedStatement.setString(13, contact.getStreetHouseApart());
            preparedStatement.setInt(14, contact.getIndex());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
