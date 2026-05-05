package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/SelfTransfer.feature",
    glue = {"com.bugbank.steps"},
    plugin = {
      "pretty",
      "json:target/cucumber/SelfTransfer.json",
      "html:target/cucumber/SelfTransfer.html"
    }
)
public class SelfTransferRunner extends AbstractTestNGCucumberTests {
}
