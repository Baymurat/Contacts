package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Attachment;
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
public class JDBCAttachmentDao implements DAO<Attachment, Integer> {
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    private Connection connection;

    public JDBCAttachmentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Attachment getEntityById(Integer id) {
        return null;
    }

    @Override
    public Attachment update(Attachment attachment) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean insert(Attachment attachment) {
        return false;
    }

    @Override
    public List<Attachment> getAll() {
        List<Attachment> result = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM attachments");
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

    private void simpleMethod(ResultSet resultSet, Collection<Attachment> attachments) {
        try {
            while (resultSet.next()) {
                Attachment attachment = new Attachment();
                attachment.setFileName(resultSet.getString("filename"));
                attachment.setComments(resultSet.getString("comments"));
                attachment.setLoadDate(resultSet.getDate("loaddate"));

                attachments.add(attachment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
