package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/IMPS.feature",
    glue = {"com.bugbank.steps", "com.bugbank.hooks"},
    plugin = {
      "pretty",
      "json:target/cucumber/IMPS.json",
      "html:target/cucumber/IMPS.html"
    }
)
public class IMPSRunner extends AbstractTestNGCucumberTests {
}
