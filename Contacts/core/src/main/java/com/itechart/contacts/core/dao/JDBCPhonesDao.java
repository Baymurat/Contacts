package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.CustomUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
    public Phone update(Phone phone) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public int insert(Phone phone) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO phones VALUES (null, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, phone.getPersons_id());
            preparedStatement.setInt(2, phone.getCodeOfCountry());
            preparedStatement.setInt(3, phone.getCodeOfOperator());
            preparedStatement.setInt(4, phone.getPhoneNumber());
            preparedStatement.setString(5, phone.getType());
            preparedStatement.setString(6, phone.getComments());
            preparedStatement.executeUpdate();

            resultSetPhones = preparedStatement.executeQuery("select last_insert_id() as last_id from phones");

            if (resultSetPhones.next()) {
                phone.setId(resultSetPhones.getInt("last_id"));
            }

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeResultSet(resultSetPhones);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return -1;
    }

    @Override
    public HashMap<Integer, Phone> getRecords(int from) {
        int range = 11;

        HashMap<Integer, Phone> resultMap = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM phones WHERE persons_id > " + from + " && persons_id < " + (from + range) + ";");
            resultSetPhones = preparedStatement.executeQuery();

            while (resultSetPhones.next()) {
                int currentId = resultSetPhones.getInt(1);

                Phone phone = new Phone();
                phone.setId(resultSetPhones.getInt("id"));
                phone.setCodeOfCountry(resultSetPhones.getInt("countrycode"));
                phone.setCodeOfOperator(resultSetPhones.getInt("operatorcode"));
                phone.setPhoneNumber(resultSetPhones.getInt("phonebumber"));
                phone.setComments(resultSetPhones.getString("comments"));
                phone.setPersons_id(resultSetPhones.getInt("persons_id"));
                resultMap.put(currentId, phone);
            }

            return resultMap;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeResultSet(resultSetPhones);
            CustomUtils.closePreparedStatement(preparedStatement);
        }

        return null;
    }
}
