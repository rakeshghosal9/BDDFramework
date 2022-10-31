package page.objects;

import common.action.ReusableCommonMethods;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateAnAccountPage_Demo {

    WebDriver driver;

    public CreateAnAccountPage_Demo(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='id_gender1']")
    WebElement title_Mr;

    @FindBy(xpath = "//input[@id='id_gender2']")
    WebElement title_Mrs;

    @FindBy(xpath = "//input[@id='customer_firstname']")
    WebElement customerFirstName;

    @FindBy(xpath = "//input[@id='customer_lastname']")
    WebElement customerLastName;

    @FindBy(xpath = "//input[@id='passwd']")
    WebElement password;

    @FindBy(xpath = "//select[@id='days']")
    WebElement dob_days;

    @FindBy(xpath = "//select[@id='months']")
    WebElement dob_months;

    @FindBy(xpath = "//select[@id='years']")
    WebElement dob_years;

    @FindBy(xpath = "//input[@id='firstname']")
    WebElement addressFirstName;

    @FindBy(xpath = "//input[@id='lastname']")
    WebElement addressLastName;

    @FindBy(xpath = "//input[@id='company']")
    WebElement company;

    @FindBy(xpath = "//input[@id='address1']")
    WebElement address1;

    @FindBy(xpath = "//input[@id='city']")
    WebElement address2;

    @FindBy(xpath = "//input[@id='id_state']")
    WebElement state;

    @FindBy(xpath = "//input[@id='postcode']")
    WebElement postCode;

    @FindBy(xpath = "//select[@id='id_country']")
    WebElement country;

    @FindBy(xpath = "//input[@id='phone']")
    WebElement homePhoneNumber;

    @FindBy(xpath = "//input[@id='phone_mobile']")
    WebElement mobilePhoneNumber;

    @FindBy(xpath = "//input[@id='alias']")
    WebElement addressAlias;

    @FindBy(xpath = "//input[@id='submitAccount']")
    WebElement registerButton;

    public void selectTitle(WebDriver driver, String title) {
        if (title.equalsIgnoreCase("Mr.")) {
            ReusableCommonMethods.clickOnWebElement(driver, title_Mr);
        } else if (title.equalsIgnoreCase("Mrs.")) {
            ReusableCommonMethods.clickOnWebElement(driver, title_Mrs);

        } else {
            Assert.fail("Title [" + title + "] is not available as option, valid values are [Mr. and Mrs.]");
        }
    }

    public void enterFirstName(WebDriver driver, String firstName) {
        Assert.assertTrue("First Name is not entered successfully",
                ReusableCommonMethods.enterValueInTextBox(customerFirstName, firstName, driver));
    }

    public void enterLastName(WebDriver driver, String lastName) {
        Assert.assertTrue("Last Name is not entered successfully",
                ReusableCommonMethods.enterValueInTextBox(customerLastName, lastName, driver));
    }

    public void enterPassword(WebDriver driver, String passwordValue) {
        Assert.assertTrue("Password not entered successfully",
                ReusableCommonMethods.enterValueInTextBox(password, passwordValue, driver));
    }

    public void enterDateOfBirth(WebDriver driver, String dob) {
        String[] dobDetails = dob.split("-");
        Assert.assertTrue("Day of date of birth is not selected",
                ReusableCommonMethods.selectDropdownValue(driver,dob_days,dobDetails[0].trim()));
        Assert.assertTrue("Day of date of birth is not selected",
                ReusableCommonMethods.selectDropdownValue(driver,dob_months,dobDetails[1].trim()));
        Assert.assertTrue("Day of date of birth is not selected",
                ReusableCommonMethods.selectDropdownValue(driver,dob_years,dobDetails[2].trim()));
    }

}
