package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "stepdefinition",
        features = {"src\\test\\resources\\features\\automation_Practice\\UserRegistration.feature"}
        //plugin = {"json:target/cucumber-report/Runner_LoginValidation_scenario001_run001_IT.json"}
)

public class debug {
}
