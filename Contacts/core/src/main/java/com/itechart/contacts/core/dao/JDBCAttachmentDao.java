package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.utils.CustomUtils;

import java.sql.*;
import java.util.HashMap;

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
        return null;
    }

    @Override
    public Attachment update(Attachment attachment) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE attachments SET persons_id = ?, filename = ?," +
                    "comments = ?, loaddate = ? WHERE id = ?");
            preparedStatement.setInt(5, attachment.getId());
            statementExecutor(attachment);
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
            preparedStatement = connection.prepareStatement("DELETE FROM attachments WHERE id = ?");
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
    public int insert(Attachment attachment) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO attachments VALUES (null, ?, ?, ?, ?);");
            statementExecutor(attachment);
            resultSetAttachments = preparedStatement.executeQuery("select last_insert_id() as last_id from attachments");
            attachment.setId(resultSetAttachments.getInt("last_id"));

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return -1;
    }

    @Override
    public HashMap<Integer, Attachment> getRecords(int from) {
        int range = 11;

        HashMap<Integer, Attachment> resultMap = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM attachments WHERE persons_id >" + from + " && persons_id < " + (from + range) +";");
            resultSetAttachments = preparedStatement.executeQuery();

            while (resultSetAttachments.next()) {
                int currentId = resultSetAttachments.getInt(1);

                Attachment attachment = new Attachment();
                attachment.setId(resultSetAttachments.getInt("id"));
                attachment.setFileName(resultSetAttachments.getString("filename"));
                attachment.setComments(resultSetAttachments.getString("comments"));
                attachment.setLoadDate(resultSetAttachments.getDate("loaddate"));
                attachment.setPersons_id(resultSetAttachments.getInt("persons_id"));
                resultMap.put(currentId, attachment);
            }

            return resultMap;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeRsultSet(resultSetAttachments);
            CustomUtils.closePreparedStatement(preparedStatement);
        }

        return null;
    }

    private void statementExecutor(Attachment attachment) {
        try {
            preparedStatement.setInt(1, attachment.getPersons_id());
            preparedStatement.setString(2, attachment.getFileName());
            preparedStatement.setString(3, attachment.getComments());
            preparedStatement.setDate(4, (Date) attachment.getLoadDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
