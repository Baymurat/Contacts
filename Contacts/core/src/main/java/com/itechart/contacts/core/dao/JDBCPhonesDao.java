package com.itechart.contacts.core.dao;

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
    public HashMap<Integer, Phone> getRecords(int from) {
        return null;
    }
}
