package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.entities.Attachment;
import com.itechart.contacts.core.utils.CustomUtils;

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
        return null;
    }

    @Override
    public boolean update(Attachment attachment) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE attachments SET filename = ?, comments = ?  WHERE id = ?;");

            preparedStatement.setString(1, attachment.getFileName());
            preparedStatement.setString(2, attachment.getComments());
            preparedStatement.setInt(3, attachment.getId());
            int result = preparedStatement.executeUpdate();
            boolean isNew = preparedStatement.execute("SELECT id FROM phones WHERE id = " + attachment.getId());

            return result == 1 || !isNew;
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
            preparedStatement.setInt(1, attachment.getPersons_id());
            preparedStatement.setString(2, attachment.getFileName());
            preparedStatement.setString(3, attachment.getComments());
            //java.lang.ClassCastException: java.util.Date cannot be cast to java.sql.Date
            preparedStatement.setDate(4, (Date) attachment.getLoadDate());
            preparedStatement.executeUpdate();

            resultSetAttachments = preparedStatement.executeQuery("select last_insert_id() as last_id from attachments");

            if (resultSetAttachments.next()) {
                attachment.setId(resultSetAttachments.getInt("last_id"));
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CustomUtils.closeResultSet(resultSetAttachments);
            CustomUtils.closePreparedStatement(preparedStatement);
        }
        return -1;
    }

    @Override
    public HashMap<Integer, Attachment> getRecords(int from, int range) {
        /*HashMap<Integer, Attachment> resultMap = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM attachments ORDER BY id LIMIT ?, ? ");
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, range);
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
            CustomUtils.closeResultSet(resultSetAttachments);
            CustomUtils.closePreparedStatement(preparedStatement);
        }*/

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
            e.printStackTrace();
        }

        return result;
    }
}
