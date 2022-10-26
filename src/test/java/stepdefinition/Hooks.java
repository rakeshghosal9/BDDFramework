package stepdefinition;


import common.action.GlobalConfiguration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void launchBrowser() {

        try {
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
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver\\chromedriver.exe");
                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                    driver.get(URL);
                } else if (browser.equalsIgnoreCase("Firefox")) {

                } else if (browser.equalsIgnoreCase("Edge")) {

                } else if (browser.equalsIgnoreCase("random")) {

                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while launching browser : " + e);
        }

    }

    @After
    public void closeBrowser() {
        //driver.quit();
    }

    public Properties readEnvironmentFile() {
        try {
            String environment = System.getProperty("env");
            if (environment != null) {
                FileInputStream fis = null;
                Properties prop = null;
                try {
                    fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\environments\\" + environment + ".properties");
                    prop = new Properties();
                    prop.load(fis);
                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } finally {
                    fis.close();
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

}
