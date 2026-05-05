package com.bugbank.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import com.bugbank.config.DriverManager;
import com.bugbank.steps.TransferFundsSteps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotHooks {
  
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

    if (driver == null) {
      return;
    }

    try {
      TakesScreenshot ts = (TakesScreenshot) driver;

      File srcFile = ts.getScreenshotAs(OutputType.FILE);

      String timestamp = LocalDateTime.now()
          .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

      String screenshotName =
          scenario.getName().replaceAll("[^a-zA-Z0-9]", "_")
              + "_" + timestamp + ".png";

      Path screenshotsDir = Paths.get("target", "screenshots");
      Files.createDirectories(screenshotsDir);

      Path screenshotPath = screenshotsDir.resolve(screenshotName);
      Files.copy(srcFile.toPath(), screenshotPath);

      byte[] screenshotBytes = Files.readAllBytes(screenshotPath);

      scenario.attach(
          screenshotBytes,
          "image/png",
          "Screenshot - " + scenario.getName()
      );

      System.out.println("Screenshot saved & attached: " + screenshotPath);

    } catch (IOException | WebDriverException e) {
      System.err.println("Screenshot capture failed: " + e.getMessage());
    }
  }
}