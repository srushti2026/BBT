package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/RecieverID.feature",
    glue = {"com.bugbank.steps"},
    plugin = {
      "pretty",
      "json:target/cucumber/RecieverID.json",
      "html:target/cucumber/RecieverID.html"
    }
)
public class RecieverIDRunner extends AbstractTestNGCucumberTests {
}
