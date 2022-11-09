package com.report;

import common.action.MariaDBConnection;
import common.action.ReusableCommonMethods;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GenerateReport {

    public static List<String> jsonFiles = new ArrayList<>();
    public static HashMap<String, String> allScenariosWithJSONPath = new HashMap<>();
    public static Set<String> failedScenarioNames = new HashSet<>();
    public static String WRITE_FAILED_CASES_TO_MYSQL_DB = "Yes";
    //Mention the table name to store failed scenarios in MariaDB in SCHEMA_NAME.TABLE_NAME format
    public static String failedScenariosTableName = "bdd_framework.execution_statistics";

    public static void main(String args[]) throws SQLException {

        //Check if the scenario need not to be rerun
        jsonFiles = getJSONFileNames(System.getProperty("user.dir") + "\\target\\cucumber-report\\");
        for (String jsonFileName :
                jsonFiles) {
            getAllScenariosWithJSONPath(jsonFileName);
        }
        // Create Final Report Directory
        ReusableCommonMethods.createDirectoryIfNotExists(System.getProperty("user.dir") + "\\target\\final-report");
        if (System.getProperty("rerunFile") != null && !System.getProperty("rerunFile").isEmpty()) {
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader(System.getProperty("user.dir") +
                        "\\src\\test\\resources\\FailedScenarios\\" + System.getProperty("rerunFile")));
                JSONObject failedScenarios = (JSONObject) obj;
                for (String scenarioName : allScenariosWithJSONPath.keySet()) {
                    if (failedScenarios.get(scenarioName) != null &&
                            failedScenarios.get(scenarioName).toString().trim().equalsIgnoreCase("Failed")) {
                        String newPath = allScenariosWithJSONPath.get(scenarioName).
                                replace("cucumber-report", "final-report");
                        copyFile(allScenariosWithJSONPath.get(scenarioName), newPath);
                    }
                }
            }catch (NoSuchFileException NE) {
                //If the JSON File name provided is not present then copy all the JSON files to Final-Report folder
                for (String scenarioName : allScenariosWithJSONPath.keySet()) {
                        String newPath = allScenariosWithJSONPath.get(scenarioName).
                                replace("cucumber-report", "final-report");
                        copyFile(allScenariosWithJSONPath.get(scenarioName), newPath);
                }

            }
            catch (Exception e) {
                System.out.println("Exception occurred : " + e);
            }
        } else if (System.getProperty("rerunKey") != null && !System.getProperty("rerunKey").isEmpty()) {
            try {
                String query = "SELECT SCENARIO_NAME FROM " + failedScenariosTableName + " " +
                        " WHERE RERUN_KEY = '" + System.getProperty("rerunKey") + "';";
                HashMap<String, String> failedScenarios = MariaDBConnection.getFailedScenariosByRerunKey(query);
                for (String scenarioName : allScenariosWithJSONPath.keySet()) {
                    if (failedScenarios.get(scenarioName) != null &&
                            failedScenarios.get(scenarioName).equalsIgnoreCase("Failed")) {
                        String newPath = allScenariosWithJSONPath.get(scenarioName).
                                replace("cucumber-report", "final-report");
                        copyFile(allScenariosWithJSONPath.get(scenarioName), newPath);
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception occurred while getting rerun key scenarios from Maria DB: " + e);
            }
        } else {
            for (String scenarioName : allScenariosWithJSONPath.keySet()) {
                String newPath = allScenariosWithJSONPath.get(scenarioName).
                        replace("cucumber-report", "final-report");
                copyFile(allScenariosWithJSONPath.get(scenarioName), newPath);
            }
        }
        System.out.println("Generating Report");
        String folderName = getTodaysDateAndTime();

        jsonFiles = new ArrayList<>();
        jsonFiles = getJSONFileNames(System.getProperty("user.dir") + "\\target\\final-report\\");

        for (String jsonFileName:
             jsonFiles) {
            getScenarioNameWithOverallStatus(jsonFileName);
        }
        generateFailedScenariosJSONFile(failedScenarioNames, System.getProperty("user.dir") +
                "\\src\\test\\resources\\FailedScenarios\\FailedScenarios_" + folderName);
        if (WRITE_FAILED_CASES_TO_MYSQL_DB != null &&
                WRITE_FAILED_CASES_TO_MYSQL_DB.equalsIgnoreCase("Yes")) {
            writeFailedScenariosInMySQLDB(failedScenarioNames, folderName);
        } else {
            System.out.println("WRITE_FAILED_CASES_TO_MYSQL_DB property is set as NO in global config properties file");
        }
        generateReport(folderName);
    }

    public static void generateReport(String folderName) {
        try {
            File reportOutputDirectory = new File("src\\test\\resources\\CucumberReports\\" + folderName);
            String buildNumber = "1";
            String projectName = "BDDFramework";

            Configuration configuration = new Configuration(reportOutputDirectory, projectName);
            // optional configuration - check javadoc for details
            configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
            // do not make scenario failed when step has status SKIPPED
            configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
            configuration.setBuildNumber(buildNumber);
            // addidtional metadata presented on main page
            configuration.addClassifications("Platform", "Windows");
            configuration.addClassifications("Browser", "Firefox");
            configuration.addClassifications("Branch", "release/1.0");

            /*// optionally add metadata presented on main page via properties file
            List<String> classificationFiles = new ArrayList<>();
            classificationFiles.add("properties-1.properties");
            classificationFiles.add("properties-2.properties");
            configuration.addClassificationFiles(classificationFiles);

            // optionally specify qualifiers for each of the report json files
            configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
            configuration.setQualifier("cucumber-report-1", "First report");
            configuration.setQualifier("cucumber-report-2", "Second report");*/

            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            Reportable result = reportBuilder.generateReports();
            // and here validate 'result' to decide what to do if report has failed
        } catch (Exception e) {

            System.out.println("Exception occurred while generating report : " + e);
        }
    }

    public static List<String> getJSONFileNames(String folderPath) {
        try {
            File folder = new File(folderPath);
            File[] listOfFiles = folder.listFiles();
            int totalFiles = 0;
            if (listOfFiles != null) {
                totalFiles = listOfFiles.length;
            }
            List<String> jsonFileName = new ArrayList<>(totalFiles);
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    if (file.getName().trim().endsWith("json")) {
                        jsonFileName.add(folderPath + "\\" + file.getName());
                    }
                }
            }
            return jsonFileName;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static String getTodaysDateAndTime() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            Date date = new Date();
            return formatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static void getScenarioNameWithOverallStatus(String jsonPath) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(jsonPath));
            JSONArray jsonArray = (JSONArray) obj;
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            JSONArray jsonArrayElements = (JSONArray) jsonObject.get("elements");
            for (int totalSteps = 0; totalSteps < jsonArrayElements.size(); totalSteps++) {
                jsonObject = (JSONObject) jsonArrayElements.get(totalSteps);
                jsonArray = (JSONArray) jsonObject.get("steps");
                if (!getResultFromSteps(jsonArray)) {
                    storeFailedScenarios(jsonArrayElements);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }

    }

    public static boolean getResultFromSteps(JSONArray jsonArray) {
        try {
            for (int step = 0; step < jsonArray.size(); step++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(step);
                JSONObject result = (JSONObject) jsonObject.get("result");
                String status = (String) result.get("status");
                System.out.println(status);
                if (status.equalsIgnoreCase("failed") || status.equalsIgnoreCase("skipped")) {
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println("Exception occurred while fetching result status from JSONArray : " + e);
            return false;
        }
    }

    public static void storeFailedScenarios(JSONArray jsonArrayElements) {
        try {
            for (int totalSection = 0; totalSection < jsonArrayElements.size(); totalSection++) {
                String type = ((JSONObject) jsonArrayElements.get(totalSection)).get("type").toString();
                if (type.equalsIgnoreCase("scenario")) {
                    failedScenarioNames.add(((JSONObject) jsonArrayElements.get(totalSection)).get("name").toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred : " + e);
        }
    }

    public static void generateFailedScenariosJSONFile(Set<String> failedScenarios, String fileLocation) {
        try {
            JSONObject jsonObject = new JSONObject();
            for (String scenarioName : failedScenarios) {
                jsonObject.put(scenarioName.trim(), "Failed");
            }
            FileWriter file = new FileWriter(fileLocation);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (Exception e) {
            System.out.println("Exception Occurred : " + e);
        }
    }

    public static void writeFailedScenariosInMySQLDB(Set<String> failedScenarios, String runID) {
        try {
            Connection conn = MariaDBConnection.getMySQLConnection();
            String rerunKey = generateRerunKey();
            for (String scenarioName :
                    failedScenarios) {
                String query = "INSERT INTO "+failedScenariosTableName+" VALUES " +
                        "('" + runID + "','" + scenarioName + "','" + rerunKey + "');";
                MariaDBConnection.executeQuery(query, conn);
            }
            if(conn!=null)
            conn.close();

        } catch (Exception e) {
            System.out.println("Exception occurred while inserting failed scenario details : " + e);
        }
    }

    public static String generateRerunKey() throws SQLException {
        Connection conn = MariaDBConnection.getMySQLConnection();
        String rerunKey = null;
        try {
            String query = "SELECT COUNT(*) FROM "+failedScenariosTableName+" WHERE RERUN_KEY IN ('" + rerunKey + "');";
            while (true) {
                rerunKey = ReusableCommonMethods.generateRandomAlphnumericString(4);
                if (MariaDBConnection.validateRerunKeyPresent(query, conn) <= 0) {
                    if(conn!=null)
                    conn.close();
                    return rerunKey;
                }
            }

        } catch (Exception e) {
            if(conn!=null)
            conn.close();
            return rerunKey;
        }
    }

    public static boolean copyFile(String srcFileName, String destFileName) {
        try {
            File srcFile = new File(srcFileName);
            File destFile = new File(destFileName);
            Files.copy(srcFile.toPath(), destFile.toPath());
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred : " + e);
            return false;
        }
    }

    public static void getAllScenariosWithJSONPath(String jsonPath) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(jsonPath));
            JSONArray jsonArray = (JSONArray) obj;
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            JSONArray jsonArrayElements = (JSONArray) jsonObject.get("elements");
            for (int i = 0; i < jsonArrayElements.size(); i++) {
                JSONObject jsonElementObject = (JSONObject) jsonArrayElements.get(i);
                if (jsonElementObject.get("type") != null &&
                        jsonElementObject.get("type").toString().trim().equalsIgnoreCase("scenario")) {
                    allScenariosWithJSONPath.put(jsonElementObject.get("name").toString().trim(), jsonPath);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred : " + e);
        }
    }

}
