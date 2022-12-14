package page.objects;

import com.utility.ReusableUtilities;
import common.action.ReusableCommonMethods;
import io.cucumber.java.eo.Do;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import stepdefinition.StockAnalysisSteps;

import java.util.HashMap;

public class StockAnalysisPage {

    WebDriver driver;

    @FindBy(xpath = "//input[@name='q']")
    WebElement googleSearchInputBox;
    @FindBy(xpath = "//div[@data-attrid='Price']/child::span[1]/child::span/child::span")
    WebElement currentIndex;
    @FindBy(xpath = "//div[@data-attrid='Price']/child::span[2]/child::span[1]")
    WebElement todaysChange;
    @FindBy(xpath = "//div[@data-attrid='52-wk high']")
    WebElement weekHigh52;
    @FindBy(xpath = "//div[@data-attrid='52-wk low']")
    WebElement weeklow52;

    @FindBy(xpath = "//div[@data-attrid='Chart']")
    WebElement googleSearchChart;


    public StockAnalysisPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean validateGoogleHomePageDisplayed() {
        return ReusableCommonMethods.waitForElementToBeVisible(googleSearchInputBox, driver, 60);
    }

    public boolean enterSearchValueAndClickOnEnterButton(String searchString) {
        try {
            ReusableCommonMethods.enterValueInTextBox(googleSearchInputBox, searchString, driver);
            googleSearchInputBox.sendKeys(Keys.ENTER);
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public void fetchStockAnalysis() {
        try {
            ReusableCommonMethods.waitForElementToBeVisible(googleSearchChart,driver,30);
            //HashMap<String,Double> tempData = new HashMap<>();
            HashMap<String,String> tempData = new HashMap<>();
            System.out.println("Current Index : " + currentIndex.getText().trim());
            System.out.println("Today's Change : " + todaysChange.getText().trim());
            System.out.println("52 Week High : " + weekHigh52.getText().trim());
            System.out.println("52 Week Low : " + weeklow52.getText().trim());

            tempData.put("CUR_INDEX",currentIndex.getText().trim().replaceAll(",",""));
            tempData.put("TODAYS_CHANGE",todaysChange.getText().trim().replaceAll(",",""));
            tempData.put("WEEK_HIGH",weekHigh52.getText().trim().replaceAll(",",""));
            tempData.put("WEEK_LOW",weeklow52.getText().trim().replaceAll(",",""));


            /*tempData.put("CUR_INDEX",convertDouble(currentIndex.getText().trim().replaceAll(",","")));
            tempData.put("TODAYS_CHANGE",convertDouble(todaysChange.getText().trim().replaceAll(",","")));
            tempData.put("WEEK_HIGH",convertDouble(weekHigh52.getText().trim().replaceAll(",","")));
            tempData.put("WEEK_LOW",convertDouble(weeklow52.getText().trim().replaceAll(",","")));*/
            String weekHigh52Data = weekHigh52.getText().trim();
            weekHigh52Data = weekHigh52Data.replaceAll(",","");
            double weekHighDouble = convertDouble(weekHigh52Data);
            String weekLow52Data = weeklow52.getText().trim();
            weekLow52Data = weekLow52Data.replaceAll(",","");
            double weekLowDouble = convertDouble(weekLow52Data);
            double currentIndexDouble = convertDouble(currentIndex.getText().trim().replaceAll(",",""));
            tempData.put("HIGH_MINUS_CURRENT_INDEX",""+ReusableUtilities.sub(weekHighDouble,currentIndexDouble));
            tempData.put("CURRENT_INDEX_MINUS_LOW",""+ReusableUtilities.sub(currentIndexDouble,weekLowDouble));
            StockAnalysisSteps.consolidatedData.add(tempData);
        } catch (Exception e) {
            System.out.println("Exception Occurred while fetching data: " + e);
        }
    }

    public double convertDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return Double.valueOf(value);
        }

    }
}
