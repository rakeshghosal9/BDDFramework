package stepdefinition;


import common.action.GlobalConfiguration;
import common.action.ReusableCommonMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Hooks {

    public static WebDriver driver;
    public static Scenario currentScenario;

    @Before
    public void launchBrowser(Scenario scenario) {

        try {
            currentScenario = scenario;
            String URL = null;
            String browser = null;
            GlobalConfiguration globalObject = new GlobalConfiguration();
            Properties prop = readEnvironmentFile();
            if (prop == null) {
                Assert.fail("No environment is provided in command line or if provided it's not matching with any " +
                        "properties file at \\src\\test\\resources\\environments location");
            } else {
                URL = prop.getProperty("URL");
                browser = prop.getProperty("BROWSER");
                prop.clear();
            }
            System.out.println("Executing Hooks");
            if (driver == null) {
                if (browser.equalsIgnoreCase("chrome")) {
                    stepsToLaunchChromeBrowser(URL);
                } else if (browser.equalsIgnoreCase("Firefox")) {
                    stepsToLaunchFirefoxBrowser(URL);
                } else if (browser.equalsIgnoreCase("Edge")) {
                    stepsToLaunchEdgeBrowser(URL);
                } else if (browser.equalsIgnoreCase("random")) {
                    String[] supportedBrowsers = {"Chrome", "Firefox", "Edge"};
                    String selectedBrowser = supportedBrowsers[ReusableCommonMethods.getRandomIntegerBetweenZeroAndGivenMaxInteger(2)];
                    System.out.println("Browser Selected : " + selectedBrowser);
                    if (selectedBrowser.equalsIgnoreCase("Chrome")) {
                        stepsToLaunchChromeBrowser(URL);
                    } else if (selectedBrowser.equalsIgnoreCase("Firefox")) {
                        stepsToLaunchFirefoxBrowser(URL);
                    } else {
                        stepsToLaunchEdgeBrowser(URL);
                    }
                }
            }
        }
         catch (Exception e) {
            System.out.println("Exception occurred while launching browser : " + e);
        }

    }

    @After
    public void closeBrowser(Scenario sc) {
        try {
            if (sc.isFailed()) {
                if (GlobalConfiguration.TAKE_SCREENSHOT_ON_FAILURE.equalsIgnoreCase("Yes")) {
                    ReusableCommonMethods.takeScreenshot(driver, sc);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred : " + e);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public Properties readEnvironmentFile() {
        try {
            String environment = System.getProperty("env");
            if (environment != null) {
                FileInputStream fis = null;
                Properties prop = null;
                try {
                    fis = new FileInputStream(System.getProperty("user.dir") +
                            "\\src\\test\\resources\\environments\\" + environment + ".properties");
                    prop = new Properties();
                    prop.load(fis);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(fis!=null) {
                        fis.close();
                    }
                }
                return prop;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while reading property file : " + e);
            return null;
        }
    }

    public WebDriver launchChromeBrowser() {
        try {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +
                    "\\src\\test\\resources\\drivers\\chromedriver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            return driver;
        } catch (Exception e) {
            System.out.println("Exception occurred while launching chrome browser : " + e);
            return null;
        }
    }

    public WebDriver launchFirefoxBrowser() {
        try {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +
                    "\\src\\test\\resources\\drivers\\firefoxdriver\\geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            String strFFBinaryPath = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
            options.setBinary(strFFBinaryPath);
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
            return driver;
        } catch (Exception e) {
            System.out.println("Exception occurred while launching firefox browser : " + e);
            return null;
        }
    }

    public WebDriver launchEdgeBrowser() {
        try {
            System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") +
                            "\\src\\test\\resources\\drivers\\edgedriver\\msedgedriver.exe");
            // Instantiate a EdgeDriver class.
            WebDriver driver = new EdgeDriver();
            // Maximize the browser
            driver.manage().window().maximize();
            return driver;
        } catch (Exception e) {
            System.out.println("Exception occurred while launching edge browser : " + e);
            return null;
        }
    }

    public WebDriver launchRemoteChromeDriver() {
        try {
            String nodeURL = GlobalConfiguration.GRID_URL;
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("platformName", "Windows 10");
            WebDriver driver = new RemoteWebDriver(new URL(nodeURL), chromeOptions);
            driver.manage().window().maximize();
            return driver;
        } catch (Exception e) {
            System.out.println("Exception occurred while launching remote browser : " + e);
            return null;
        }

    }

    public WebDriver launchRemoteEdgeDriver() {
        try {
            String nodeURL = GlobalConfiguration.GRID_URL;
            EdgeOptions options = new EdgeOptions();
            options.setCapability("platformName", "Windows 10");
            WebDriver driver = new RemoteWebDriver(new URL(nodeURL), options);
            driver.manage().window().maximize();
            return driver;
        } catch (Exception e) {
            System.out.println("Exception occurred while launching remote browser : " + e);
            return null;
        }
    }

    public WebDriver launchChromeOnSauceLab()
    {
        try
        {
            ChromeOptions browserOptions = new ChromeOptions();
            browserOptions.setPlatformName("Windows 11");
            browserOptions.setBrowserVersion("latest");
            Map<String, Object> sauceOptions = new HashMap<>();
            sauceOptions.put("build", "Test Build");
            sauceOptions.put("name", "Sample Test");
            browserOptions.setCapability("sauce:options", sauceOptions);
            URL url = new URL(GlobalConfiguration.SAUCE_LAB_URL);
            driver = new RemoteWebDriver(url, browserOptions);
            return driver;
        }catch (Exception e)
        {
            System.out.println("Exception occurred : "+e);
            return null;
        }
    }

    public WebDriver launchEdgeOnSauceLab()
    {
        try
        {
            EdgeOptions browserOptions = new EdgeOptions();
            browserOptions.setPlatformName("Windows 11");
            browserOptions.setBrowserVersion("latest");
            Map<String, Object> sauceOptions = new HashMap<>();
            sauceOptions.put("build", "Test Build");
            sauceOptions.put("name", "Sample Test");
            browserOptions.setCapability("sauce:options", sauceOptions);
            URL url = new URL(GlobalConfiguration.SAUCE_LAB_URL);
            driver = new RemoteWebDriver(url, browserOptions);
            return driver;
        }catch (Exception e)
        {
            System.out.println("Exception occurred : "+e);
            return null;
        }
    }

    public WebDriver launchFirefoxOnSauceLab()
    {
        try
        {
            FirefoxOptions browserOptions = new FirefoxOptions();
            browserOptions.setPlatformName("Windows 11");
            browserOptions.setBrowserVersion("latest");
            Map<String, Object> sauceOptions = new HashMap<>();
            sauceOptions.put("build", "Test Build");
            sauceOptions.put("name", "Sample Test");
            browserOptions.setCapability("sauce:options", sauceOptions);
            URL url = new URL(GlobalConfiguration.SAUCE_LAB_URL);
            driver = new RemoteWebDriver(url, browserOptions);
            return driver;
        }catch (Exception e)
        {
            System.out.println("Exception occurred : "+e);
            return null;
        }
    }

    public void stepsToLaunchChromeBrowser(String URL)
    {
        if (GlobalConfiguration.EXECUTION_TYPE.equalsIgnoreCase("REMOTE")) {
            driver = launchRemoteChromeDriver();
        } else if (GlobalConfiguration.EXECUTION_TYPE.equalsIgnoreCase("SAUCELAB")) {
            driver = launchChromeOnSauceLab();
        } else {
            driver = launchChromeBrowser();
        }
        driver.get(URL);
    }

    public void stepsToLaunchFirefoxBrowser(String URL) {
        if (GlobalConfiguration.EXECUTION_TYPE.equalsIgnoreCase("REMOTE")) {
            //driver = launchRemoteEdgeDriver();
        } else if (GlobalConfiguration.EXECUTION_TYPE.equalsIgnoreCase("SAUCELAB")) {
            System.out.println("Launching firefox browser on Sauce Lab");
            driver = launchFirefoxOnSauceLab();
        } else {
            driver = launchFirefoxBrowser();
        }
        driver.get(URL);
    }

    public void stepsToLaunchEdgeBrowser(String URL) {
        if (GlobalConfiguration.EXECUTION_TYPE.equalsIgnoreCase("REMOTE")) {
            driver = launchRemoteEdgeDriver();
        } else if (GlobalConfiguration.EXECUTION_TYPE.equalsIgnoreCase("SAUCELAB")) {
            System.out.println("Launching edge browser on Sauce Lab");
            driver = launchEdgeOnSauceLab();
        } else {
            driver = launchEdgeBrowser();
        }
        driver.get(URL);
    }

}
