package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "stepdefinition",
        features = {"src\\test\\resources\\features\\stock_Analysis\\StockAnalysis.feature"}
        plugin = {"json:target/cucumber-report/StockAnalysis.json"}
)

public class debug {
}
