package common.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class MariaDBConnection {

    public static Connection connection = null;
    public static String mariaDBConnectionString = "jdbc:mariadb://localhost:3307";
    public static String schemaName = "bdd_framework";
    public static String userName = "root";
    public static String password = "MariaDB@2022";


    public static Connection getMySQLConnection() {
        try {
            return DriverManager.
                    getConnection(mariaDBConnectionString+"/"+schemaName, userName, password);

        } catch (Exception e) {
            System.out.println("Exception occurred while connecting My SQL Server : " + e);
            return null;
        }
    }

    public static boolean executeQuery(String query, Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred : " + e);
            return false;
        }
    }

    public static Integer validateRerunKeyPresent(String query, Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;

        } catch (Exception e) {
            System.out.println("Exception occurred : " + e);
            return -1;
        }
    }

    public static HashMap<String,String> getFailedScenariosByRerunKey(String query) {
        HashMap<String, String> failedScenarios = new HashMap<>();
        try {
            Connection conn = getMySQLConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                failedScenarios.put(rs.getString(1), "Failed");
            }
            return failedScenarios;

        } catch (Exception e) {
            return failedScenarios;
        }
    }

}
