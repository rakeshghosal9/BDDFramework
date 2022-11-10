
# BDD Framework

Behavior Driven Development (BDD) Framework enables software testers to complete test scripting in plain English. BDD mainly focuses on the behavior of the product and user acceptance criteria.To implement BDD Framework we have used Cucumber. Cucumber is one of the best tools used to develop in the BDD Framework.It uses Gherkin, an ordinary language parser, that permits writing scripts in English that can be easily understood. It is used to run the acceptance tests written in BDD.




## Content

- [Prerequisites](#Prerequisites)
- [Maven Dependencies](#Maven-Dependencies)
- [Project Hierarchy](#Project-Hierarchy)
- Global Configurations
- Environment Configurations
- Browsers Supported
- Execution Types
- Running Tests
- Report Generation
- Rerun Failed Tests



## Prerequisites
 - JDK 1.8 or above installed on the machine.
 - Maven installed.
 - Chrome, Firefox, Edge  installed if cross browser testing is required.
 - Eclipse/Intellij or other IDE to import the project.
 - Internet access to download the dependencies from Maven Central Repository.
## Maven Dependencies
Maven Dependencies are mentioned in the pom.xml of a maven project. We are using below important dependencies in this project.

- Cucumber - 6.9.0
- Selenium - 4.4.0 (Selenium 4)

Please check pom.xml for other dependencies. 
## Project Hierarchy

Please find below the project structure. 

- src/test/java
 
           com.report - java classes related to report generation.
           common.action - reusable Java methods, DB Connection, Global Configuration Initialization.
           page.objects - to store the WebElements and corresponding actions.
           runner - Sample jUnit runner for debugging purpose.
           some.template - jUnit runner template based on which the runner will be generated automatically.
           stepdefinition - java classes to store step definitions.

- src/test/resources

           CucumberReports - cucumber report generated after each execution keeping past results as well. 
           drivers - executable drivers for Chrome, Firefox and Edge browsers.
           environments - properties file for each environments containing parameters specific to an environment. 
           FailedScenarios - folder to generate failed scenarios in JSON format. 
           features - store cucumber feature files. 
           global_config - containing config.properties file having global parameters. 

- target

           cucumber-report - folder in which all JSON report will be generated for each scenario. 
           final-report - folder in which only previously failed scenarios JSON report will be generated in case on rerunning failures. 
           parallel - to store dynamically generated features and runners. 
## About Me

My Name is Rakesh Ghosal. I'm a Test Automation Architect with total 10 years of experience. During my career I have built many test automation framework such as BDDFramework, Keyword Driven Framework, Data Driven Framework. I'm passionate about technologies and love to learn  new skill whenever get time. 

