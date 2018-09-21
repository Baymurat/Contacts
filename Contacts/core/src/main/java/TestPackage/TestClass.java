package TestPackage;

import com.itechart.contacts.core.utils.ConnectionPool;
import com.itechart.contacts.core.utils.CustomUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestClass {
    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        try {
            DataSource dataSource = connectionPool.setUpPool();
            Connection connection = dataSource.getConnection();
            //
            PreparedStatement statement = connection.prepareStatement("INSERT INTO persons VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            statement.setString(1, "Paren");
            statement.setString(2, "Kakoyto");
            statement.setString(3, "Otchestvo");
            statement.setString(4, null);
            statement.setString(5, null);
            statement.setString(6, null);
            statement.setString(7, null);
            statement.setString(8, null);
            statement.setString(9, null);
            statement.setString(10, null);
            statement.setString(11, null);
            statement.setString(12, null);
            statement.setString(13, null);
            statement.setString(14, null);

            System.out.println(statement.executeUpdate());

            CustomUtils.closePreparedStatement(statement);
            CustomUtils.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


