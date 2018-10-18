package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.utils.error.CustomLogger;
import com.itechart.contacts.core.utils.CustomUtils;
import com.itechart.contacts.core.utils.error.CustomException;

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
    public Attachment getEntityById(Integer id) throws CustomException {
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
            CustomLogger.logger.error("Exception in AttachmentDAO getEntityById() method", e);
            throw new CustomException("", e);
        }

        return attachment;
    }

    @Override
    public boolean update(Attachment attachment) throws CustomException {
        try {
            preparedStatement = connection.prepareStatement("UPDATE attachments SET filename = ?, comments = ?  WHERE id = ?;");

            preparedStatement.setString(1, attachment.getFileName());
            preparedStatement.setString(2, attachment.getComments());
            preparedStatement.setInt(3, attachment.getId());
            int result = preparedStatement.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            CustomLogger.logger.error("Exception in AttachmentDAO update() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public boolean delete(Integer id) throws CustomException {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM attachments WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            CustomLogger.logger.error("Exception in AttachmentDAO delete() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public int insert(Attachment attachment) throws CustomException {
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
            CustomLogger.logger.error("Exception in AttachmentDAO insert() method", e);
            throw new CustomException("", e);
        } finally {
            CustomUtils.closeResultSet(resultSetAttachments);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public HashMap<Integer, Attachment> getRecords(int from, int range) {
        return null;
    }

    public List<Attachment> getContactAttachment(int persons_id) throws CustomException {
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
            CustomLogger.logger.error("Exception in AttachmentDAO getRecords() method", e);
            throw new CustomException("edsgfds", e);
        }

        return result;
    }
}
