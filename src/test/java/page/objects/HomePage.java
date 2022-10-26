package page.objects;

import common.action.ReusableCommonMethods;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;
    @FindBy(xpath = "//table//tr[@class='heading3']")
    WebElement homePageUserName;

    @FindBy(xpath = "//a[text()='New Customer']")
    WebElement newCustomerHyperLink;



    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Get the User name from Home Page
    public String getHomePageDashboardUserName() {
        System.out.println("In Home Page Factory");
        Assert.assertTrue("User Name on Home Page is not displayed after login", ReusableCommonMethods.waitForElementToBeVisible(homePageUserName, driver, 60));
        return homePageUserName.getText();
    }
}
