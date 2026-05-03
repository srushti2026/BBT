package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/TF-039.feature",
    glue = {"com.bugbank.steps"},
    plugin = {
      "pretty",
      "json:target/cucumber/TF-039.json",
      "html:target/cucumber/TF-039.html"
    }
)
public class TF039Runner extends AbstractTestNGCucumberTests {
}
