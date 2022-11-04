package com.report;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GenerateReport {

    public static void main(String args[]) {
        System.out.println("Generating Report");
        String folderName= getTodaysDateAndTime();
        generateReport(folderName);
    }

    public static void generateReport(String folderName) {
        try {
            File reportOutputDirectory = new File("src\\test\\resources\\CucumberReports\\"+folderName);
            List<String> jsonFiles = getJSONFileNames(System.getProperty("user.dir")+"\\target\\cucumber-report\\");

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

    public static String getTodaysDateAndTime()
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            Date date = new Date();
            return formatter.format(date);
        }catch (Exception e)
        {
            return null;
        }
    }
}
