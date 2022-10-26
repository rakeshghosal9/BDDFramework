package common.action;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import javax.xml.bind.Element;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ReusableCommonMethods {

    public static Wait<WebDriver> getFluentWaitObject(int timeout, int pollingTime, WebDriver driver) {
        return new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class);
    }

    public static Wait<WebDriver> getFluentWaitObject(WebDriver driver) {
        return new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(GlobalConfiguration.GLOBAL_EXPLICIT_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(GlobalConfiguration.GLOBAL_EXPLICIT_POLLING_TIME))
                .ignoring(NoSuchElementException.class);
    }

    public static boolean waitForElementToBeVisible(WebElement element, WebDriver driver, int timeout) {
        try {
            Wait<WebDriver> fluentWait = getFluentWaitObject(60, 2, driver);
            fluentWait.until(ExpectedConditions.visibilityOf(element));
            return true;

        } catch (Exception e) {

            System.out.println("Element not visible after waiting for [" + timeout + "] seconds");
            return false;
        }
    }

    public static boolean enterValueInTextBox(WebElement element, String value, WebDriver driver) {
        try {
            Wait<WebDriver> fluentWait = getFluentWaitObject(driver);
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
            element.clear();
            element.sendKeys(value);
            System.out.println("Successfully entered value in text box [" + element + "]");
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering value in text box [" + element + "], Exception is : "+e);
            return false;
        }
    }

    public static Properties getPropertiesFileObject(String filePath) {
        try {
            FileInputStream fis = null;
            Properties prop = null;
            try {
                fis = new FileInputStream(filePath);
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

        } catch (Exception e) {
            System.out.println("Exception occurred while reading properties file : " + e);
            return null;
        }
    }

    public static boolean clickOnWebElement(WebDriver driver, WebElement element) {
        try {
            Wait<WebDriver> fluentWait = getFluentWaitObject(driver);
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            System.out.println("Successfully clicked on element [" + element + "]");
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while clicking on web element [" + element + "]");
            return false;
        }
    }

}
