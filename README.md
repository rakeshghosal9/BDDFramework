
# BDD Framework

Behavior Driven Development (BDD) Framework enables software testers to complete test scripting in plain English. BDD mainly focuses on the behavior of the product and user acceptance criteria.To implement BDD Framework we have used Cucumber. Cucumber is one of the best tools used to develop in the BDD Framework.It uses Gherkin, an ordinary language parser, that permits writing scripts in English that can be easily understood. It is used to run the acceptance tests written in BDD.




## Content

- [Prerequisites](#Prerequisites)
- [Maven Dependencies](#Maven-Dependencies)
- [Project Hierarchy](#Project-Hierarchy)
- [Global Configurations](#Global-Configuration)
- [Environment Configuration](#Environment-Configuration)
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

Please find below the project structure. You may not be able to understand everything at this moment, don't worry, everything will be explained in detail later. 

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
## Global Configuration

Let's discuss about Global Configuration of the framework. All the global parameters are mentioned in config.properties file residing in src/test/resources/global_config folder. Please find below the global parameters and it's use:

- **GLOBAL_EXPLICIT_TIMEOUT** - We use Selenium 4 FluentWait in various cases, such as to click on a WebElement, to insert value in a text box etc. In FluentWait we use two parameters - polling seconds and overall timeout period until which system will wait for the condition to be true. By this parameter we define the total timeout for FluentWait used in this framework. **Please note that the framework has the flexibility to pass your desired FluentWait polling time and total timeout perriod.**
- **GLOBAL_EXPLICIT_POLLING_TIME** - As mentioned above this parameter is being used to define FluentWait polling time. 
- **TAKE_SCREENSHOT_FOR_EACH_STEP** -  Set this parameter as "Yes" or "No". "Yes" if you want to capture screenshot at every step of the scenario. "No" if you don't want. **Please note that if you are running more number of test at once then it's not advisible to keep this parameter as "Yes", as it may cause memory issue to capture screenshot at every step.**
- **TAKE_SCREENSHOT_ON_FAILURE** - If the above parameter is set as "No", then it's recommended to set this parameter as "Yes" to capture failure screenshot only if a scenario is failed. 
- **EXECUTION_TYPE** - This is an important parameter to consider. Possible values are **LOCAL,REMOTE** and **SAUCELAB**.  

        LOCAL - Set the parameter as LOCAL if you want to execute the test locally. Probably you should set it as LOCAL when you are developing the script. 
        REMOTE - Set the parameter as REMOTE if you want to execute the test on Selenium GRID. 
        SAUCELAB - Set the parameter as SAUCELAB if you want to execute the test on Saucelab. 

- **GRID_URL** - Mention the GRID URL. This is only required if you set the EXECUTION_TYPE parameter as REMOTE. Else you can keep the parameter as blank. 
- **SAUCE_LAB_URL** - Mention the SAUCELAB URL. This is only required if you set the EXECUTION_TYPE parameter as SAUCE_LAB. 

From a technical aspect, we have a GlobalConfiguration java class that reads config.properties and load the global parameters in static global variables which then can be used in Java Classes anytime.
## Environment Configuration

In real project we deal with multiple environments such as Dev, Integration, QA, UAT, Production etc. All these environments usually have multiple URLs, Database Parameters etc. To handle this we need to create properties file for each environment and put it into **src/test/resources/environments** folder. As of now only **URL** and **BROWSER** - these 2 parameters must be mentioned in each environment properties file.

We need to pass the environment in which we would like to run our tests in Maven Command **(For Example -Denv = "INTG1")**, in code we read the corresponding properties file from **@Before** hooks in **Hooks.java** from the stepdefition folder. 

**Note :** If we pass -Denv="INTG1" in the Maven Command line for an example, then there must be a properties file name **INTG1.properties** should be present in **src\test\resources\environments** folder. 
## About Me

My Name is Rakesh Ghosal. I'm a Test Automation Architect with total 10 years of experience. During my career I have built many test automation framework such as BDDFramework, Keyword Driven Framework, Data Driven Framework. I'm passionate about technologies and love to learn  new skill whenever get time. 

