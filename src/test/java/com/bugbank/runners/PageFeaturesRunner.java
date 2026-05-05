package com.bugbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/PageFeatures.feature",
    glue = {"com.bugbank.steps", "com.bugbank.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber/PageFeatures.html",
        "json:target/cucumber/PageFeatures.json"
    },
    monochrome = true
)
public class PageFeaturesRunner extends AbstractTestNGCucumberTests {
}
