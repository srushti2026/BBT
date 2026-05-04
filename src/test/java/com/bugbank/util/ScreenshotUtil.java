package com.bugbank.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {
  private static final String SCREENSHOT_DIR = "target/screenshots/";

  public ScreenshotUtil() {
  }

  public static String captureScreenshot(WebDriver driver, String testName) {
    try {
      // Create screenshots directory if it doesn't exist
      File screenshotDir = new File(SCREENSHOT_DIR);
      if (!screenshotDir.exists()) {
        screenshotDir.mkdirs();
      }

      // Generate filename with timestamp
      String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
      String filename = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";

      // Take screenshot
      File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      File destFile = new File(filename);
      FileUtils.copyFile(srcFile, destFile);

      System.out.println("Screenshot captured: " + filename);
      return filename;
    } catch (Exception e) {
      System.err.println("Failed to capture screenshot: " + e.getMessage());
      return null;
    }
  }

  public static String captureScreenshot(WebDriver driver) {
    return captureScreenshot(driver, "screenshot");
  }

  public static void takeScreenshotOnSuccess(WebDriver driver, String testName) {
    captureScreenshot(driver, testName + "_success");
  }

  public static void takeScreenshotOnFailure(WebDriver driver, String testName) {
    captureScreenshot(driver, testName + "_failure");
  }

  public static void takeScreenshotOnMessage(WebDriver driver, String testName, String messageType) {
    captureScreenshot(driver, testName + "_" + messageType);
  }
}
