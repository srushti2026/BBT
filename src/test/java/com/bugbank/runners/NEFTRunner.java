package com.bugbank.runners;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/NEFT.feature",
    glue = {"com.bugbank.steps", "com.bugbank.hooks"},
    plugin = {
      "pretty",
      "json:target/cucumber/NEFT.json",
      "html:target/cucumber/NEFT.html"
    }
)
public class NEFTRunner extends AbstractTestNGCucumberTests {
}
