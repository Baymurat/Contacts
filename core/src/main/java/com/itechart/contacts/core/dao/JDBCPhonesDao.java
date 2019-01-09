package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.error.CustomLogger;
import com.itechart.contacts.core.utils.CustomUtils;
import com.itechart.contacts.core.utils.error.CustomException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 13.09.2018
 */
public class JDBCPhonesDao implements DAO<Phone, Integer> {

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSetPhones = null;
    private Connection connection;

    public JDBCPhonesDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Phone getEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Phone phone) throws CustomException {
        try {
            preparedStatement = connection.prepareStatement("UPDATE phones SET countrycode = ?, operatorcode = ?, " +
                    "phonebumber = ?, type = ?, comments = ? WHERE id = ?;");
            preparedStatement.setInt(1, phone.getCodeOfCountry());
            preparedStatement.setInt(2, phone.getCodeOfOperator());
            preparedStatement.setInt(3, phone.getPhoneNumber());
            preparedStatement.setString(4, phone.getType());
            preparedStatement.setString(5, phone.getComments());
            preparedStatement.setInt(6, phone.getId());
            int result = preparedStatement.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            CustomLogger.logger.error("Exception in ContactDAO getEntityById() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM phones WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            CustomLogger.logger.error("Exception in ContactDAO delete() method", e);
        } finally {
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return false;
    }

    @Override
    public int insert(Phone phone) throws CustomException {
        try {
            String params = "(persons_id, countrycode, operatorcode, phonebumber, type, comments)";
            preparedStatement = connection.prepareStatement("INSERT INTO phones " + params + " VALUES (?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, phone.getPersons_id());
            preparedStatement.setInt(2, phone.getCodeOfCountry());
            preparedStatement.setInt(3, phone.getCodeOfOperator());
            preparedStatement.setInt(4, phone.getPhoneNumber());
            preparedStatement.setString(5, phone.getType());
            preparedStatement.setString(6, phone.getComments());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT currval('phones_id_seq');");
            resultSetPhones = preparedStatement.executeQuery();

            if (resultSetPhones.next()) {
                phone.setId(resultSetPhones.getInt("currval"));
            }

            return 0;
        } catch (SQLException e) {
            CustomLogger.logger.error("Exception in ContactDAO insert() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closeResultSet(resultSetPhones);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public HashMap<Integer, Phone> getRecords(int from, int range) {
        return null;
    }

    public List<Phone> getContactPhones(int persons_id) throws CustomException {
        List<Phone> resultList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM phones WHERE persons_id = ?");
            preparedStatement.setInt(1, persons_id);
            resultSetPhones = preparedStatement.executeQuery();

            while (resultSetPhones.next()) {
                Phone phone = new Phone();
                phone.setId(resultSetPhones.getInt("id"));
                phone.setCodeOfCountry(resultSetPhones.getInt("countrycode"));
                phone.setCodeOfOperator(resultSetPhones.getInt("operatorcode"));
                phone.setPhoneNumber(resultSetPhones.getInt("phonebumber"));
                phone.setComments(resultSetPhones.getString("comments"));
                phone.setPersons_id(resultSetPhones.getInt("persons_id"));
                phone.setType(resultSetPhones.getString("type"));
                resultList.add(phone);
            }
        } catch (SQLException e) {
            CustomLogger.logger.error("Exception in ContactDAO getRecords() method", e);
            throw new CustomException("", e);
        }

        return resultList;
    }
}
