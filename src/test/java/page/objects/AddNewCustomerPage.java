package page.objects;

import common.action.ReusableCommonMethods;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    public void addNewCustomer(String customerNameTxt, String genderTxt, String dobTxt, String addressTxt, String cityTxt,
                               String stateTxt, String pinTxt, String mobileTxt, String emailTxt, String passwordTxt) {
        try {
            ReusableCommonMethods.enterValueInTextBox(customerName, customerNameTxt, driver);
            if (genderTxt.equalsIgnoreCase("male")) {
                ReusableCommonMethods.clickOnWebElement(driver, gender_male);
            } else {
                ReusableCommonMethods.clickOnWebElement(driver, gender_female);
            }
            ReusableCommonMethods.enterValueInTextBox(dateOfBirth, dobTxt, driver);
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

}
