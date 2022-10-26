package page.objects;

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

}
