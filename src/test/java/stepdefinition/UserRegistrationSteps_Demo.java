package stepdefinition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import page.objects.CreateAnAccountPage_Demo;
import page.objects.HomePage_Demo;
import page.objects.SignInPage_Demo;

import java.util.List;
import java.util.Map;

public class UserRegistrationSteps_Demo {

    WebDriver driver = Hooks.driver;
    @When("click on the sign in link of the homepage")
    public void click_on_the_sign_in_link_of_the_homepage() {
        HomePage_Demo homePageObject = new HomePage_Demo(driver);
        homePageObject.clickOnSignInLink(driver);
    }

    @When("enter the registration email address as {string} and click on create account button")
    public void enter_the_registration_email_address_as_and_click_on_create_account_button(String emailID) {
        SignInPage_Demo signInPageDemoObj = new SignInPage_Demo(driver);
        signInPageDemoObj.enterEmailAndCreateAnAccount(driver,emailID);
    }
    @When("enter all the registration details as given below")
    public void enter_all_the_registration_details_as_given_below(DataTable userRegistrationData) {

        try {
            List<Map<String, String>> userRegistrationDataAsMap = userRegistrationData.asMaps();
            CreateAnAccountPage_Demo createAccountPageDemo = new CreateAnAccountPage_Demo(driver);
            //Select Title
            if (userRegistrationDataAsMap.get(0).get("title") != null && !userRegistrationDataAsMap.get(0).get("title").isEmpty()) {
                createAccountPageDemo.selectTitle(driver, userRegistrationDataAsMap.get(0).get("title"));
            }
            //Enter First Name
            createAccountPageDemo.enterFirstName(driver, userRegistrationDataAsMap.get(0).get("firstName"));
            //Enter Last Name
            createAccountPageDemo.enterLastName(driver, userRegistrationDataAsMap.get(0).get("lastName"));
            //Enter password
            createAccountPageDemo.enterPassword(driver, userRegistrationDataAsMap.get(0).get("password"));
            //date of birth
            createAccountPageDemo.enterDateOfBirth(driver, userRegistrationDataAsMap.get(0).get("dob"));

        } catch (Exception e) {

        }

    }
    @Then("the registration should be successful")
    public void the_registration_should_be_successful() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
}
