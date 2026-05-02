package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/TF-006.feature",
    glue = {"com.bugbank.steps"},
    plugin = {
      "pretty",
      "json:target/cucumber/TF-006.json",
      "html:target/cucumber/TF-006.html"
    }
)
public class TF006Runner extends AbstractTestNGCucumberTests {
}
