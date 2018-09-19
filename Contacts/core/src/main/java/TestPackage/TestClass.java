package TestPackage;

import com.itechart.contacts.core.utils.ConnectionPool;
import com.itechart.contacts.core.utils.CustomUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestClass {
    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        try {
            DataSource dataSource = connectionPool.setUpPool();
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO person VALUES (null, ?, ?);");
            statement.setString(1, "Paren");
            statement.setString(2, "Kakoyto");
            statement.executeUpdate();

            CustomUtils.closePreparedStatement(statement);
            CustomUtils.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
