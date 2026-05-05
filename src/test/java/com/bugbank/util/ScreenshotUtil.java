package com.bugbank.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;

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

  /**
   * Captures screenshot and returns base64 encoded image string for embedding in reports
   */
  public static String captureScreenshotAsBase64(WebDriver driver, String testName) {
    try {
      // Take screenshot as bytes
      byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      
      // Also save the file for reference
      File screenshotDir = new File(SCREENSHOT_DIR);
      if (!screenshotDir.exists()) {
        screenshotDir.mkdirs();
      }
      String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
      String filename = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";
      Files.write(Paths.get(filename), screenshotBytes);
      System.out.println("Screenshot captured: " + filename);
      
      // Return base64 encoded string
      return Base64.getEncoder().encodeToString(screenshotBytes);
    } catch (Exception e) {
      System.err.println("Failed to capture screenshot as base64: " + e.getMessage());
      return null;
    }
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
