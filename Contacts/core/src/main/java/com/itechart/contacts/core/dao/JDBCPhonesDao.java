package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Phone;
import com.itechart.contacts.core.utils.CustomUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Admin on 13.09.2018
 */
public class JDBCPhonesDao implements DAO<Phone, Integer> {
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
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
    public boolean insert(Phone phone) {
        return false;
    }

    @Override
    public List<Phone> getAll() {
        List<Phone> result = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM phone");
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

    private void simpleMethod(ResultSet resultSet, Collection<Phone> phones) {
        try {
            while (resultSet.next()) {
                Phone phone = new Phone();
                phone.setCodeOfCountry(resultSet.getString("country_code"));
                phone.setCodeOfOperator(resultSet.getString("operator_code"));
                phone.setPhoneNumber(resultSet.getString("phone_number"));
                phone.setType(resultSet.getString("number_type"));
                phone.setComments(resultSet.getString("comments"));

                phones.add(phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
