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
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GenerateReport {

    public static List<String> jsonFiles = new ArrayList<>();
    public static Set<String> failedScenarioNames = new HashSet<>();
    public static String WRITE_FAILED_CASES_TO_MYSQL_DB = "Yes";

    public static void main(String args[]) throws SQLException {
        System.out.println("Generating Report");
        String folderName = getTodaysDateAndTime();
        jsonFiles = getJSONFileNames(System.getProperty("user.dir") + "\\target\\cucumber-report\\");
        for (int i = 0; i < jsonFiles.size(); i++) {
            getScenarioNameWithOverallStatus(jsonFiles.get(i));
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

    public static boolean getScenarioNameWithOverallStatus(String jsonPath) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(jsonPath));
            JSONArray jsonArray = (JSONArray) obj;
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            JSONArray jsonArrayElements = (JSONArray) jsonObject.get("elements");
            for (int totalSteps = 0; totalSteps < jsonArrayElements.size(); totalSteps++) {
                jsonObject = (JSONObject) jsonArrayElements.get(totalSteps);
                jsonArray = (JSONArray) jsonObject.get("steps");
                if (getResultFromSteps(jsonArray) == false) {
                    storeFailedScenarios(jsonArrayElements);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Exception : " + e);
            return false;
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

    public static boolean generatePropertiesFile(Set<String> failedScenarios, String fileLocation) {
        try {
            Properties prop = new Properties();
            for (String scenarioName : failedScenarios) {
                prop.put(scenarioName, "FAILED");
            }
            //Instantiating the FileInputStream for output file
            FileOutputStream outputStrem = new FileOutputStream(fileLocation);
            //Storing the properties file
            prop.store(outputStrem, "FailedScenarios");
            System.out.println("Failed Scenarios Created");
            outputStrem.close();
            return true;

        } catch (Exception e) {
            System.out.println("Exception occurred while generating failed Scenarios : " + e);
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

    public static boolean generateFailedScenariosJSONFile(Set<String> failedScenarios, String fileLocation) {
        try {
            JSONObject jsonObject = new JSONObject();
            for (String scenarioName : failedScenarios) {
                jsonObject.put(scenarioName.trim(), "Failed");
            }
            FileWriter file = new FileWriter(fileLocation);
            file.write(jsonObject.toJSONString());
            file.close();
            return true;
        } catch (Exception e) {
            System.out.println("Exception Occurred : " + e);
            return false;
        }
    }

    public static void writeFailedScenariosInMySQLDB(Set<String> failedScenarios,String runID) {
        try {
            Connection conn = MariaDBConnection.getMySQLConnection();
            String rerunKey = generateRerunKey();
            for (String scenarioName :
                    failedScenarios) {
                String query = "INSERT INTO bdd_framework.execution_statistics VALUES " +
                        "('"+runID+"','" + scenarioName + "','" + rerunKey + "');";
                MariaDBConnection.executeQuery(query, conn);
            }
            conn.close();

        } catch (Exception e) {
            System.out.println("Exception occurred while inserting failed scenario details : " + e);
        }
    }

    public static String generateRerunKey() throws SQLException {
        Connection conn = MariaDBConnection.getMySQLConnection();
        String rerunKey = null;
        try {
            String query = null;
            while (true) {
                rerunKey = ReusableCommonMethods.generateRandomAlphnumericString(4);
                query = "SELECT COUNT(*) FROM bdd_framework.execution_statistics WHERE RERUN_KEY IN ('" + rerunKey + "');";
                if (MariaDBConnection.validateRecordPresent(query, conn) <= 0) {
                    conn.close();
                    return rerunKey;
                }
            }

        } catch (Exception e) {
            conn.close();
            return rerunKey;
        }
    }
}
