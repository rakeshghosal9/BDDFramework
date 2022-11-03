package stepdefinition;


import common.action.GlobalConfiguration;
import common.action.ReusableCommonMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
                Assert.fail("No environment is provided in command line or if provided it's not matching with any properties file at \\src\\test\\resources\\environments location");
            } else {
                URL = prop.getProperty("URL");
                browser = prop.getProperty("BROWSER");
                prop.clear();
            }
            System.out.println("Executing Hooks");
            if (driver == null) {
                if (browser.equalsIgnoreCase("chrome")) {
                    driver = launchChromeBrowser();
                    driver.get(URL);
                } else if (browser.equalsIgnoreCase("Firefox")) {
                    driver = launchFirefoxBrowser();
                    driver.get(URL);
                } else if (browser.equalsIgnoreCase("Edge")) {
                    driver = launchEdgeBrowser();
                    driver.get(URL);
                } else if (browser.equalsIgnoreCase("random")) {
                    String supportedBrowsers[] = {"Chrome", "Firefox", "Edge"};
                    String selectedBrowser = supportedBrowsers[ReusableCommonMethods.getRandomIntegerBetweenZeroAndGivenMaxInteger(2)];
                    System.out.println("Browser Selected : "+selectedBrowser);
                    if (selectedBrowser.equalsIgnoreCase("Chrome")) {
                        launchChromeBrowser();
                    } else if (selectedBrowser.equalsIgnoreCase("Firefox")) {
                        launchFirefoxBrowser();
                    } else {
                        launchEdgeBrowser();
                    }
                    driver.get(URL);
                }
            }
        } catch (Exception e) {
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

}
