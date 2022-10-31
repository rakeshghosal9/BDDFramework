package stepdefinition;

import common.action.ReusableCommonMethods;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import page.objects.AddNewCustomerPage;
import page.objects.HomePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewCustomerSteps {

    WebDriver driver = Hooks.driver;

    @Then("add a new customer with below attributes")
    public void addNewCustomer(DataTable newCustomerData) {
        try {
            List<Map<String, String>> newCustomerTestValue = newCustomerData.asMaps();
            HomePage homePageObject = new HomePage(driver);
            AddNewCustomerPage addNewCustomerObject = new AddNewCustomerPage(driver);
            for (int i = 0; i < newCustomerTestValue.size(); i++) {
                homePageObject.clickOnMenuLinkOnHomePage(driver, "New Customer");
                addNewCustomerObject.handleAdDismissButton(driver);
                addNewCustomerObject.addNewCustomer(newCustomerTestValue.get(i).get("customer_name"),
                        newCustomerTestValue.get(i).get("gender"),
                        newCustomerTestValue.get(i).get("dob)"),
                        newCustomerTestValue.get(i).get("address"),
                        newCustomerTestValue.get(i).get("city"),
                        newCustomerTestValue.get(i).get("state"),
                        newCustomerTestValue.get(i).get("pin"),
                        newCustomerTestValue.get(i).get("mobile"),
                        newCustomerTestValue.get(i).get("email"),
                        newCustomerTestValue.get(i).get("password"));
                addNewCustomerObject.clickOnSubmitButton(driver);

            }

        } catch (Exception e) {
            Assert.fail("Add New Customer is not successful, exception occurred : " + e);
        }
    }
}
