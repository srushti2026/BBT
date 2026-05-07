package com.bugbank.hooks;

import com.bugbank.config.DriverManager;
import com.bugbank.config.TestConfig;
import com.bugbank.config.Waits;
import com.bugbank.pages.DashboardPage;
import com.bugbank.pages.LoginPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Hooks {

  private static Scenario currentScenario;

  @Before
  public void beforeScenario(Scenario scenario) {
    currentScenario = scenario;
    System.out.println("Starting scenario: " + scenario.getName());
  }

  public static Scenario getCurrentScenario() {
    return currentScenario;
  }

  @After
  public void captureAndAttachScreenshot(Scenario scenario) {

    WebDriver driver = DriverManager.getDriver();
    if (driver == null) return;

    try {
      TakesScreenshot ts = (TakesScreenshot) driver;
      File srcFile = ts.getScreenshotAs(OutputType.FILE);

      String timestamp = LocalDateTime.now()
          .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

      String screenshotName = scenario.getName()
          .replaceAll("[^a-zA-Z0-9]", "_")
          + "_" + timestamp + ".png";

      Path screenshotsDir = Paths.get("target", "screenshots");
      Files.createDirectories(screenshotsDir);

      Path screenshotPath = screenshotsDir.resolve(screenshotName);
      Files.copy(srcFile.toPath(), screenshotPath);

      scenario.attach(
          Files.readAllBytes(screenshotPath),
          "image/png",
          "Screenshot - " + scenario.getName()
      );

      System.out.println("Screenshot saved & attached: " + screenshotPath);

    } catch (IOException | WebDriverException e) {
      System.err.println("Screenshot capture failed: " + e.getMessage());
    }
  }

  /* =======================
     TESTNG (PER SUITE)
     ======================= */

  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {

    // Reduce Selenium logs
    java.util.logging.LogManager.getLogManager().reset();
    java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.SEVERE);
    java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

    WebDriver driver = DriverManager.startDriver();

    driver.get(TestConfig.BASE_URL);
    Waits.waitForPageReady(driver,
        Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));

    LoginPage loginPage = new LoginPage(driver);
    loginPage.clickLoginButton();
    Waits.pauseAfterAction();

    loginPage.login(TestConfig.EMAIL, TestConfig.PASSWORD);
    Waits.pauseAfterAction();

    Waits.waitForPageReady(driver,
        Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));

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