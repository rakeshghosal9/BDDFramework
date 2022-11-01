package common.action;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import page.objects.AddNewCustomerPage;

import javax.xml.bind.Element;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.util.Properties;

public class ReusableCommonMethods {

    /**
     * This method will return a Wait<WebDriver> object of type fluent wait by accepting timeout and polling time
     * as parameter.
     * @param timeout
     * @param pollingTime
     * @param driver
     * @return Wait<WebDriver>
     */
    public static Wait<WebDriver> getFluentWaitObject(int timeout, int pollingTime, WebDriver driver) {
        return new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * This method will return a Wait<WebDriver> object of type fluent wait for which timeout and polling time would
     * be taken from config.properties file.
     * @param driver
     * @return Wait<WebDriver>
     */
    public static Wait<WebDriver> getFluentWaitObject(WebDriver driver) {
        return new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(GlobalConfiguration.GLOBAL_EXPLICIT_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(GlobalConfiguration.GLOBAL_EXPLICIT_POLLING_TIME))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * This method will wait for a WebElement to be visible using Fluent Wait with timeout time as 60 secs and polling
     *  time as 2 secs.
     * @param element
     * @param driver
     * @param timeout
     * @return boolean
     */
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

    /**
     * This method will enter value in a text box WebElement by accepting element, value as parameter.
     * @param element
     * @param value
     * @param driver
     * @return
     */
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

    /**
     * This method will return a Properties file object that helps to load properties of a Properties file by passing
     * the file path of the Properties file. If the file path is invalid, then it returns null.
     * @param filePath
     * @return Properties
     */
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

    /**
     *  This method will click on a WebElement using Fluent Wait with global config parameters
     * @param driver
     * @param element
     * @return boolean
     */
    public static boolean clickOnWebElement(WebDriver driver, WebElement element) {
        try {
            Wait<WebDriver> fluentWait = getFluentWaitObject(driver);
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            System.out.println("Successfully clicked on element [" + element + "]");
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while clicking on web element [" + element + "], "+e);
            return false;
        }
    }

    /**
     * This method will help to select a dropdown value from a dropdown. It returns false if the dropdown is not
     * present
     * @param driver
     * @param element
     * @param dropdownValue
     * @return boolean
     */

    public static boolean selectDropdownValue(WebDriver driver,WebElement element, String dropdownValue) {
        try {
            System.out.println("Selecting dropdown value : "+dropdownValue);
            /*Wait<WebDriver> fluentWait = getFluentWaitObject(driver);
            fluentWait.until(ExpectedConditions.visibilityOf(element));*/
            Select dropdown = new Select(element);
            dropdown.selectByValue(dropdownValue);
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while selecting dropdown value [" + dropdownValue + "] " + e);
            return false;
        }
    }

    /**
     * This method will help to select a dropdown value from a dropdown. It returns false if the dropdown is not
     * present
     * @param driver
     * @param element
     * @param dropdownValue
     * @return boolean
     */

    public static boolean selectDropdownByVisibleText(WebDriver driver,WebElement element, String dropdownValue) {
        try {
            System.out.println("Selecting dropdown value : "+dropdownValue);
            Select dropdown = new Select(element);
            dropdown.selectByVisibleText(dropdownValue);
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while selecting dropdown value [" + dropdownValue + "] " + e);
            return false;
        }
    }

    public static String generateRandomAlphabaticString(int length)
    {
        try
        {
            return RandomStringUtils.randomAlphabetic(length);
        }catch (Exception e)
        {
            return null;
        }
    }

}
