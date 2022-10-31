package page.objects;

import common.action.ReusableCommonMethods;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AddNewCustomerPage {

    WebDriver driver;

    public AddNewCustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@name='name']")
    WebElement customerName;

    @FindBy(xpath = "//input[@name='rad1' and @value='m']")
    WebElement gender_male;

    @FindBy(xpath = "//input[@name='rad1' and @value='f']")
    WebElement gender_female;

    @FindBy(xpath = "//input[@id='dob']")
    WebElement dateOfBirth;

    @FindBy(xpath = "//textarea[@name='addr']")
    WebElement address;

    @FindBy(xpath = "//input[@name='city']")
    WebElement city;

    @FindBy(xpath = "//input[@name='state']")
    WebElement state;

    @FindBy(xpath = "//input[@name='pinno']")
    WebElement pin;

    @FindBy(xpath = "//input[@name='telephoneno']")
    WebElement phoneNumber;

    @FindBy(xpath = "//input[@name='emailid']")
    WebElement email;

    @FindBy(xpath = "//input[@name='password']")
    WebElement password;

    @FindBy(xpath = "//input[@name='sub']")
    WebElement submit;

    @FindBy(xpath = "//input[@name='res']")
    WebElement reset;

    @FindBy(xpath = "//div[@id='dismiss-button']")
    WebElement addDismissButton;

    @FindBy(xpath = "//iframe[@id='ad_iframe']")
    WebElement advertisementIFrame;


    public void addNewCustomer(String customerNameTxt, String genderTxt, String dobTxt, String addressTxt, String cityTxt,
                               String stateTxt, String pinTxt, String mobileTxt, String emailTxt, String passwordTxt) {
        try {
            ReusableCommonMethods.enterValueInTextBox(customerName, customerNameTxt, driver);
            if (genderTxt.equalsIgnoreCase("male")) {
                ReusableCommonMethods.clickOnWebElement(driver, gender_male);
            } else {
                ReusableCommonMethods.clickOnWebElement(driver, gender_female);
            }
            //ReusableCommonMethods.enterValueInTextBox(dateOfBirth, dobTxt, driver);
            enterDOB(driver);
            ReusableCommonMethods.enterValueInTextBox(address, addressTxt, driver);
            ReusableCommonMethods.enterValueInTextBox(city, cityTxt, driver);
            ReusableCommonMethods.enterValueInTextBox(state, stateTxt, driver);
            ReusableCommonMethods.enterValueInTextBox(pin, pinTxt, driver);
            ReusableCommonMethods.enterValueInTextBox(phoneNumber, mobileTxt, driver);
            ReusableCommonMethods.enterValueInTextBox(email, emailTxt, driver);
            ReusableCommonMethods.enterValueInTextBox(password, passwordTxt, driver);

        } catch (Exception e) {
            Assert.fail("Exception occurred while adding new customer");

        }

    }

    public void clickOnSubmitButton(WebDriver driver) {
        Assert.assertTrue("Submit button is clicked successfully on New Customer Page : ",
                ReusableCommonMethods.clickOnWebElement(driver, submit));
    }

    public void enterDOB(WebDriver driver) {
        try {
            dateOfBirth.click();
            Actions act = new Actions(driver);
            act.keyDown(Keys.NUMPAD1).keyUp(Keys.NUMPAD1).build().perform();
        } catch (Exception e) {
            System.out.println("Exception : " + e);

        }
    }

    public void handleAdDismissButton(WebDriver driver) {

        try {
            List<WebElement> allFrame = driver.findElements(By.xpath("//iframe"));
            for(int i=0;i<allFrame.size();i++)
            {
                System.out.println(allFrame.get(i).getAttribute("id"));
            }

        }catch (Exception e)
        {
            System.out.println(e);
        }

        /*try {
            if (ReusableCommonMethods.waitForAnElementWhileWaitingForOtherElementToBeDisplayed(advertisementIFrame,
                    customerName, driver, 30)) {
                System.out.println("Add Pop up button is displayed, clicking on close");
                driver.switchTo().frame("ad_iframe");
                ReusableCommonMethods.clickOnWebElement(driver, addDismissButton);
                driver.switchTo().defaultContent();
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }*/
    }

    public static boolean waitForAnElementWhileWaitingForOtherElementToBeDisplayed(WebElement elementToWait, WebElement
            elementToWaitToBeDisplayed,
                                                                                   WebDriver driver, int time) {
        int count = 0;
        while (count <= time) {
            try {
                System.out.println("Waiting for element : "+elementToWait);
                new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.visibilityOf(elementToWait));
                return true;
            } catch (TimeoutException TE) {
                try {
                    System.out.println("Waiting for second element : "+elementToWaitToBeDisplayed);
                    new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.
                            visibilityOf(elementToWaitToBeDisplayed));
                    return false;

                } catch (TimeoutException TET) {
                    count++;
                }

            }
        }
        return false;
    }


}
