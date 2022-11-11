
# BDD Framework

Behavior Driven Development (BDD) Framework enables software testers to complete test scripting in plain English. BDD mainly focuses on the behavior of the product and user acceptance criteria.To implement BDD Framework we have used Cucumber. Cucumber is one of the best tools used to develop in the BDD Framework.It uses Gherkin, an ordinary language parser, that permits writing scripts in English that can be easily understood. It is used to run the acceptance tests written in BDD.




## Content

- [Highlights](#Highlights)
- [Prerequisites](#Prerequisites)
- [Maven Dependencies](#Maven-Dependencies)
- [Project Hierarchy](#Project-Hierarchy)
- [Global Configurations](#Global-Configuration)
- [Environment Configuration](#Environment-Configuration)
- [Browsers Supported](#Browsers-Supported)
- [Page Object Model Implementation](#Page-Object-Model-Implementation)
- [How Cucable Works](#How-Cucable-Works)
- [Running Tests](#Running-Test)
- Report Generation
- Rerun Failed Tests



## Highlights

Let's see what are the features that you will get in this Framework.

- Usage of Selenium 4 and it's latest features. 
- Junit Framework.
- Support BDD style using Cucumber, easy to read feature files for all stakeholder, writted in plain english. 
- The Framework is built on Maven, hence all the dependencies are automatically downloaded and no need to do any manual set up. 
- Followed Page Object Model that provides easy initialization of WebElements using Page Factory design pattern. 
- Multi-browser support (Chrome, Firefox and Edge) for execution. 
- Ability to execute test on Selenium Grid, Saucelab and locally. 
- Easy to configure parameters from properties file. 
- Ability to define parameters for various environments.
- Support parallel execution with ability to decide the number of threads.
- Generation of JSON report for each scenario which can be used to generate most of the external reports. 
- Generation of Cucumber Report in a folder with date and time.
- Ability to rerun the failures. 
- Various reusable methods available such as Click, Selection of dropdown, Enter value in textbox etc. 
- Maria DB connection available. 
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

We need to pass the environment in which we would like to run our tests in Maven Command **(For Example -Denv = "INTG1")**, in code we read the corresponding properties file from **@Before** hooks in **Hooks.java** inside the stepdefition folder. 

**Note :** If we pass **-Denv="INTG1"** in the Maven Command line for an example, then there must be a properties file name **INTG1.properties** present in **src\test\resources\environments** folder. 
## Browsers Supported

As of now the framework can support Chrome, Firefox and Edge browsers. We need to define the browser in the **environment.properties** file as mentioned in the previous section. Valid values for BROWSER are:

         CHROME - to execute tests on Chrome Browser.
         FIREFOX - to execute tests on Firefox Browser. 
         EDGE - to execute tests on Edge Browser. 
         RANDOM - if we mention the browser as RANDOM, then the test will run on any of these above browsers. 

Please note we need to download the executable drivers for Chrome, Firefox and Edge in **src/test/resources/drivers** folder according to your browser version, this is required to run the tests locally. You can find the drivers from below:

- Chrome - [Click Here](https://chromedriver.chromium.org/downloads)
- Firefox - [Click Here](https://github.com/mozilla/geckodriver/releases)
- Edge - [Click Here](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/)

**Note :** Please note that Selenium has recently launched Selenium Manager from version 4.6.0 with which there is no need to download the executable drivers anymore to launch these browsers, Selenium itself can handle it. But as this is under beta version we have still not implemented, once the version is stabilized we will implement in our framework.

Now let's talk about Selenium Grid execution. In this case you need to download the executable drivers on the node machine that you want to connect to the Selenium Hub. I would suggest to go through the Selenium Grid concept from any of the tutorial page over the internet. I prefer from [here](https://www.guru99.com/introduction-to-selenium-grid.html), but you are open to explore other options. To help you with the command that I prefer to connect my Grid Node with the Grid Hub is given below:

- Chrome

           java -Dwebdriver.chrome.driver=chromedriver.exe -jar selenium-server-standalone-3.141.59.jar -port 5564 -role node -hub http://123.456.7.8:4444/grid/register/ -browser "browserName=MicrosoftEdge,version=ANY,maxInstances=5,platform=WINDOWS" -maxSession 5

- Firefox

           java -Dwebdriver.gecko.driver=geckodriver.exe -jar selenium-server-standalone-3.141.59.jar -port 5564 -role node -hub http://123.456.7.8:4444/grid/register/ -browser "browserName=Firefox,version=ANY,maxInstances=5,platform=WINDOWS" -maxSession 5
- Edge

           java -Dwebdriver.edge.driver=msedgedriver.exe -jar selenium-server-standalone-3.141.59.jar -port 5564 -role node -hub http://123.456.7.8:4444/grid/register/ -browser "browserName=MicrosoftEdge,version=ANY,maxInstances=5,platform=WINDOWS" -maxSession 5


And talking about the last option Saucelab, you need not to worry about drivers. You just need an account on Saucelab and need to provide the Saucelab connection url in config.properties. 


## Page Object Model Implementation

Let's discuss how we have implemented Page Object Mode in this framework. As mentioned in the project structure we have a package named **"page.objects"** under **src/test/java**. In Page Onject Model we should create one Java Class for each Page and capture all the WebElements of the Page. Now, surely we can perform various oprarations on that Page. All these operations should be defined as Java Method in the same class. 

For Example we have a Login Page in which we have User ID and Password as textbox and a Submit button. In this case we should create a java class, let's say LoginPage.java. We should capture below WebElements:

- Identifier (id,name,xpath etc. ) for User ID text box.
- Identifier for Password text box. 
- Identifier for Submit button. 

And below three java methods to define the operations.

- method to enter value in User ID. 
- method to enter value in Password. 
- method to click on Submit button. 

Now the question is how shall we initialize the Page Object. Ideally it should be done from the Step Definition file. To give you a background, Step Definition file is a Java class in which we should write code for each step that is written in the Cucumber Feature File. 

In a Page Object java class, we initialize the WebElements within it's constructor. Please find below the sample code. 

        public UserRegistration_Para(WebDriver driver) {
             this.driver = driver;
             PageFactory.initElements(driver, this);
    }

So, when we create an object of this class UserRegistration_Para.java, the constructor is getting called automatically and using the PageFactory, all the WebElements are initialized. 

Page Object Mode is a big topic to cover, if you would like to know more, you may visit [here](https://www.guru99.com/page-object-model-pom-page-factory-in-selenium-ultimate-guide.html).
## How Cucable Works

Let's talk about running test now. We are using Cucable plugin to run our test. You can learn more about this plugin from [here](https://github.com/trivago/cucable-plugin).

But let's breif about it. Cucable is a maven plugin that helps to generate JUnit runners on the fly. Cucable scans the feature files you have created and finds the scenarios matching with the tag given. Cucuable uses a template to generate the JUnit runner file. We have kept it in **src\test\java\some\template** folder. In our template we have put below :

     glue - defines the package name of the step definition.
     features - defines the feature file name to be executed. 
     plugin - defines that a JSON report needs to be generated post execution. 


Now as mentioned earlier, as per the matching tag, Cucuable will generate one single feature file for each scenario and one individual runner to run that feature file. 

Seems confusing? Let's explain with an example. Let's say we have 5 feature files each containing 3 scenarios, total 15 scenarios. Out of 15 scenario 10 scenarios are having a tag **@TEST_TO_RUN**. Now, we have provided tag **@TEST_TO_RUN** in the maven command. Cucuable will scan all the 15 scenarios and found that 10 scenarios are matching with the given tag. Cucable will create 10 separate feature files for these 10 matching scenarios containing one scenario in each feature file. It also creates 10 different JUnit runners and link each runner to a single feature file. So, now we have 10 feature files and 10 corresponding runner files. Cucable is now ready for the execution. If we mention the fork count as 5, Cucuable will run 5 JUnit runners at the same time in parallel. 

## Running-Test


## About Me

My Name is Rakesh Ghosal. I'm a Test Automation Architect with total 10 years of experience. During my career I have built many test automation framework such as BDDFramework, Keyword Driven Framework, Data Driven Framework. I'm passionate about technologies and love to learn  new skill whenever get time. 

