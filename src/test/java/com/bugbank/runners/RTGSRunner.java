package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/RTGS.feature",
    glue = {"com.bugbank.steps"},
    plugin = {
      "pretty",
      "json:target/cucumber/RTGS.json",
      "html:target/cucumber/RTGS.html"
    }
)
public class RTGSRunner extends AbstractTestNGCucumberTests {
}
