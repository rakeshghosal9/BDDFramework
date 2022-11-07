package common.action;

import java.sql.*;

public class MariaDBConnection {

    public static Connection connection = null;
    public static String mariaDBConnectionString = "jdbc:mariadb://localhost:3307";
    public static String schemaName = "bdd_framework";
    public static String userName = "root";
    public static String password = "MariaDB@2022";


    public static Connection getMySQLConnection() {
        try {
            Connection conn = DriverManager.
                    getConnection(mariaDBConnectionString+"/"+schemaName, userName, password);
            return conn;

        } catch (Exception e) {
            System.out.println("Exception occurred while connecting My SQL Server : " + e);
            return null;
        }
    }

    public static boolean executeQuery(String query, Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return true;
        } catch (Exception e) {
            System.out.println("Exception Occurred : " + e);
            return false;
        }
    }

    public static Integer validateRecordPresent(String query, Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                return rs.getInt(1);
            }
            return -1;

        } catch (Exception e) {
            System.out.println("Exception occurred : " + e);
            return -1;
        }
    }

}
