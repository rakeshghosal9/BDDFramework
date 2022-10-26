package common.action;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class ReusableCommonMethods {

    public static boolean waitForElementToBeVisible(WebElement element, WebDriver driver, int timeout) {
        try {
            Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(timeout))
                    .pollingEvery(Duration.ofSeconds(3))
                    .ignoring(NoSuchElementException.class);
            fluentWait.until(ExpectedConditions.visibilityOf(element));
            return true;

        } catch (Exception e) {

            System.out.println("Element not visible after waiting for [" + timeout + "] seconds");
            return false;
        }
    }

}
