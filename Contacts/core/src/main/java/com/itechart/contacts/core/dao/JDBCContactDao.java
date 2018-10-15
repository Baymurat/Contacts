package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.entities.Contact;
import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.CustomUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        Contact contact = new Contact();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM persons WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSetContacts = preparedStatement.executeQuery();

            while (resultSetContacts.next()) {
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
                contact.setCountry(resultSetContacts.getString("country"));
                contact.setCity(resultSetContacts.getString("city"));
                contact.setStreetHouseApart(resultSetContacts.getString("street_house_apart"));
                contact.setIndex(resultSetContacts.getInt("p_index"));

                Date date = resultSetContacts.getDate("datebirth");
                contact.setBirthDate(parseToString(date));

                ArrayList<Phone> phones = new ArrayList<>();
                ArrayList<Attachment> attachments = new ArrayList<>();

                contact.setPhones(phones);
                contact.setAttachments(attachments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeResultSet(resultSetContacts);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return contact;
    }

    @Override
    public boolean update(Contact contact) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE persons SET name = ?, surname = ?, " +
                    "middlename = ?, citizenship = ?, familystatus = ?, website = ?, email = ?, currentjob = ?, " +
                    "gender = ?, datebirth = ?, country = ?, city = ?, street_house_apart = ?, p_index = ? WHERE id = ?");

            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getSurName());
            preparedStatement.setString(3, contact.getMiddleName());
            preparedStatement.setString(4, contact.getCitizenship());
            preparedStatement.setString(5, contact.getFamilyStatus());
            preparedStatement.setString(6, contact.getWebSite());
            preparedStatement.setString(7, contact.getEmail());
            preparedStatement.setString(8, contact.getCurrentJob());
            preparedStatement.setString(9, contact.getGender());
            preparedStatement.setString(11, contact.getCountry());
            preparedStatement.setString(12, contact.getCity());
            preparedStatement.setString(13, contact.getStreetHouseApart());
            preparedStatement.setInt(14, contact.getIndex());
            preparedStatement.setInt(15, contact.getId());

            Date date = parseToDate(contact.getBirthDate());
            preparedStatement.setDate(10, date);
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
            preparedStatement.setString(2, contact.getName());
            preparedStatement.setString(3, contact.getSurName());
            preparedStatement.setString(4, contact.getMiddleName());
            preparedStatement.setString(5, contact.getCitizenship());
            preparedStatement.setString(6, contact.getFamilyStatus());
            preparedStatement.setString(7, contact.getWebSite());
            preparedStatement.setString(8, contact.getEmail());
            preparedStatement.setString(9, contact.getCurrentJob());
            preparedStatement.setString(10, contact.getGender());
            preparedStatement.setString(12, contact.getCountry());
            preparedStatement.setString(13, contact.getCitizenship());
            preparedStatement.setString(14, contact.getStreetHouseApart());
            preparedStatement.setInt(15, contact.getIndex());

            Date date = parseToDate(contact.getBirthDate());
            preparedStatement.setDate(11, date);
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
    public HashMap<Integer, Contact> getRecords(int from, int range) {
        return null;
    }

    public List<Contact> getContacts(int from, int range, String searchDescription) {
        List<Contact> result = new ArrayList<>();
        try {
            if (searchDescription == null) {
                preparedStatement = connection.prepareStatement("SELECT * FROM persons ORDER BY name LIMIT ?, ?");
                preparedStatement.setInt(1, from);
                preparedStatement.setInt(2, range);
            } else {
                String like = "%" + searchDescription + "%";
                preparedStatement = connection.prepareStatement("SELECT * FROM persons WHERE name LIKE ? OR surname LIKE ? OR " +
                        "middlename LIKE ? OR citizenship LIKE ? OR familystatus LIKE ? OR website LIKE ? OR email LIKE ? OR  currentjob LIKE ? OR " +
                        "gender LIKE ? OR country LIKE ? OR city LIKE ? OR street_house_apart LIKE ? OR p_index LIKE ? ORDER BY name LIMIT ?, ?");
                preparedStatement.setString(1, like);
                preparedStatement.setString(2, like);
                preparedStatement.setString(3, like);
                preparedStatement.setString(4, like);
                preparedStatement.setString(5, like);
                preparedStatement.setString(6, like);
                preparedStatement.setString(7, like);
                preparedStatement.setString(8, like);
                preparedStatement.setString(9, like);
                preparedStatement.setString(10, like);
                preparedStatement.setString(11, like);
                preparedStatement.setString(12, like);
                preparedStatement.setString(13, like);
                preparedStatement.setInt(14, from);
                preparedStatement.setInt(15, range);
            }

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
                contact.setCountry(resultSetContacts.getString("country"));
                contact.setCity(resultSetContacts.getString("city"));
                contact.setStreetHouseApart(resultSetContacts.getString("street_house_apart"));
                contact.setIndex(resultSetContacts.getInt("p_index"));

                Date date = resultSetContacts.getDate("datebirth");
                contact.setBirthDate(parseToString(date));

                ArrayList<Phone> phones = new ArrayList<>();
                ArrayList<Attachment> attachments = new ArrayList<>();

                contact.setPhones(phones);
                contact.setAttachments(attachments);

                result.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeResultSet(resultSetContacts);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return result;
    }

    public int getAllElementsCount() {
        int allElementsCount = 0;
        try {
            preparedStatement = connection.prepareStatement("SELECT COUNT(id) AS counts FROM persons");
            resultSetContacts = preparedStatement.executeQuery();
            while (resultSetContacts.next()) {
                allElementsCount = resultSetContacts.getInt("counts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeResultSet(resultSetContacts);
            CustomUtils.closePreparedStatement(preparedStatement);
        }

        return allElementsCount;
    }

    public List<Contact> getContactsByDateBirth(String dateBirth) {
        List<Contact> contactList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM persons WHERE datebirth = ?");
            preparedStatement.setString(1, dateBirth);
            resultSetContacts = preparedStatement.executeQuery();

            while (resultSetContacts.next()) {
                Contact contact = new Contact();
                contact.setId(resultSetContacts.getInt(1));
                contact.setName(resultSetContacts.getString("name"));
                contact.setSurName(resultSetContacts.getString("surname"));
                contact.setMiddleName(resultSetContacts.getString("middlename"));
                contact.setEmail(resultSetContacts.getString("email"));
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactList;
    }

    private String parseToString(Date date) {
        String string = date.toString();
        String[] strings = string.split("-");

        return strings[2] + "/" + strings[1] + "/" + strings[0];
    }

    private Date parseToDate(String string) {
        String[] strings = string.split("/");

        return Date.valueOf(strings[2] + "-" + strings[1] + "-" + strings[0]);
    }
}
