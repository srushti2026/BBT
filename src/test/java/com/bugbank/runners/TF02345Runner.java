package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/TF-02345.feature",
    glue = {"com.bugbank.steps"},
    plugin = {
      "pretty",
      "json:target/cucumber/TF-02345.json",
      "html:target/cucumber/TF-02345.html"
    }
)
public class TF02345Runner extends AbstractTestNGCucumberTests {
}
