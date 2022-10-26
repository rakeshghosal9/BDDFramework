package page.objects;

import common.action.ReusableCommonMethods;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;
    @FindBy(xpath = "//table//tr[@class='heading3']")
    WebElement homePageUserName;

    @FindBy(xpath = "//a[text()='New Customer']")
    WebElement newCustomerMenuLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Get the User name from Home Page
    public String getHomePageDashboardUserName() {
        try {
            Assert.assertTrue("User Name on Home Page is not displayed after login",
                    ReusableCommonMethods.waitForElementToBeVisible(homePageUserName, driver, 60));
            return homePageUserName.getText();
        }catch (Exception e)
        {
            System.out.println("Exception occurred while getting the home page dashboard user name");
            return null;
        }
    }

    public void clickOnMenuLinkOnHomePage(WebDriver driver,String menuName) {
        try {
            //String formedXpath = "//a[text()='" + menuName + "']";
            Assert.assertTrue("Menu link [" + menuName + "] is not clicked successfully on home page ",
                    ReusableCommonMethods.clickOnWebElement(driver, newCustomerMenuLink));

        } catch (Exception e) {
            Assert.fail("Exception occurred while clicking on Menu Link ["+menuName+"]");
        }

    }
}
