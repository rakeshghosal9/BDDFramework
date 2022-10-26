package stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import page.objects.HomePage;
import page.objects.LoginPage;

public class LoginValidationStep {

    WebDriver driver = Hooks.driver;

    @When("enter the user name as {string} and password as {string}")
    public void enter_the_user_name_as_and_password_as(String userName, String passWord) {
        LoginPage loginValidationObject = new LoginPage(driver);
        loginValidationObject.setUserName(userName);
        loginValidationObject.setPassword(passWord);
        loginValidationObject.clickLogin();
    }

    @Then("the login should be successful")
    public void the_login_should_be_successful() {

        HomePage homepageObject = new HomePage(driver);
        homepageObject.getHomePageDashboardUserName();
    }
}
