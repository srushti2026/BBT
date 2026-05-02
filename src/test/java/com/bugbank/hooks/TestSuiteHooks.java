package com.bugbank.hooks;

import com.bugbank.config.DriverManager;
import com.bugbank.config.TestConfig;
import com.bugbank.config.Waits;
import com.bugbank.pages.DashboardPage;
import com.bugbank.pages.LoginPage;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestSuiteHooks {
  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    java.util.logging.LogManager.getLogManager().reset();
    java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.SEVERE);
    java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);
    java.util.logging.Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(java.util.logging.Level.SEVERE);
    java.util.logging.Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver").setLevel(java.util.logging.Level.SEVERE);
    WebDriver driver = DriverManager.startDriver();
    driver.get(TestConfig.BASE_URL);
    Waits.waitForPageReady(driver, Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));
    new LoginPage(driver).login(TestConfig.EMAIL, TestConfig.PASSWORD);
    Waits.pauseAfterAction();
    Waits.waitForPageReady(driver, Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));
    new DashboardPage(driver).waitForLoaded();
  }

  @AfterSuite(alwaysRun = true)
  public void afterSuite() {
    WebDriver driver = DriverManager.getDriver();
    if (driver != null) {
      new DashboardPage(driver).logoutIfPresent();
    }
    DriverManager.stopDriver();
  }
}