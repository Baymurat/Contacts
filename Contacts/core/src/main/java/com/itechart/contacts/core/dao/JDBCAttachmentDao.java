package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.utils.CustomErrorHandler;
import com.itechart.contacts.core.utils.CustomUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 13.09.2018
 */
public class JDBCAttachmentDao implements DAO<Attachment, Integer> {
    private ResultSet resultSetAttachments = null;
    private PreparedStatement preparedStatement = null;
    private Connection connection;

    public JDBCAttachmentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Attachment getEntityById(Integer id) {
        Attachment attachment = new Attachment();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM attachments WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSetAttachments = preparedStatement.executeQuery();

            while (resultSetAttachments.next()) {
                attachment.setId(resultSetAttachments.getInt("id"));
                attachment.setFileName(resultSetAttachments.getString("filename"));
                attachment.setComments(resultSetAttachments.getString("comments"));
                attachment.setLoadDate(resultSetAttachments.getDate("loaddate"));
                attachment.setPersons_id(resultSetAttachments.getInt("persons_id"));
            }
        } catch (SQLException e) {
            CustomErrorHandler.logger.error("Exception in AttachmentDAO getEntityById() method", e);
        }

        return attachment;
    }

    @Override
    public boolean update(Attachment attachment) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE attachments SET filename = ?, comments = ?  WHERE id = ?;");

            preparedStatement.setString(1, attachment.getFileName());
            preparedStatement.setString(2, attachment.getComments());
            preparedStatement.setInt(3, attachment.getId());
            int result = preparedStatement.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            CustomErrorHandler.logger.error("Exception in AttachmentDAO update() method", e);
        } finally {
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM attachments WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            CustomErrorHandler.logger.error("Exception in AttachmentDAO delete() method", e);
        } finally {
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return false;
    }

    @Override
    public int insert(Attachment attachment) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO attachments VALUES (null, ?, ?, ?, ?);");
            preparedStatement.setInt(1, attachment.getPersons_id());
            preparedStatement.setString(2, attachment.getFileName());
            preparedStatement.setString(3, attachment.getComments());
            preparedStatement.setDate(4, attachment.getLoadDate());
            preparedStatement.executeUpdate();

            resultSetAttachments = preparedStatement.executeQuery("select last_insert_id() as last_id from attachments");

            if (resultSetAttachments.next()) {
                attachment.setId(resultSetAttachments.getInt("last_id"));
            }
            return 0;
        } catch (SQLException e) {
            CustomErrorHandler.logger.error("Exception in AttachmentDAO insert() method", e);
        } finally {
            CustomUtils.closeResultSet(resultSetAttachments);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return -1;
    }

    @Override
    public HashMap<Integer, Attachment> getRecords(int from, int range) {
        return null;
    }

    public List<Attachment> getContactAttachment(int persons_id) {
        List<Attachment> result = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM attachments WHERE persons_id = ? ");
            preparedStatement.setInt(1, persons_id);
            resultSetAttachments = preparedStatement.executeQuery();

            while (resultSetAttachments.next()) {
                //int currentId = resultSetAttachments.getInt(1);

                Attachment attachment = new Attachment();
                attachment.setId(resultSetAttachments.getInt("id"));
                attachment.setFileName(resultSetAttachments.getString("filename"));
                attachment.setComments(resultSetAttachments.getString("comments"));
                attachment.setLoadDate(resultSetAttachments.getDate("loaddate"));
                attachment.setPersons_id(resultSetAttachments.getInt("persons_id"));
                result.add(attachment);
            }
        } catch (SQLException e) {
            CustomErrorHandler.logger.error("Exception in AttachmentDAO getRecords() method", e);
        }

        return result;
    }
}
