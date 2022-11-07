package common.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDBConnection {

    public static Connection connection = null;

    private MySQLDBConnection() throws SQLException {
        connection = DriverManager.getConnection(GlobalConfiguration.MY_SQL_DB_CONNECTION);
    }
    public static Connection getMySQLConnectionObject() throws SQLException {
        if (connection == null) {
            new MySQLDBConnection();
        }
        return connection;
    }
}
