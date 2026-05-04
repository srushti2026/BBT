package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/TF-0003567.feature",
    glue = {"com.bugbank.steps", "com.bugbank.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber/TF-0003567.html",
        "json:target/cucumber/TF-0003567.json"
    },
    monochrome = true
)
public class TF0003567Runner extends AbstractTestNGCucumberTests {
}
