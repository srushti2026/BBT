package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/TF-02123.feature",
    glue = {"com.bugbank.steps"},
    plugin = {
      "pretty",
      "json:target/cucumber/TF-02123.json",
      "html:target/cucumber/TF-02123.html"
    }
)
public class TF02123Runner extends AbstractTestNGCucumberTests {
}
