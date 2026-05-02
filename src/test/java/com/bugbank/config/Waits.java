package com.bugbank.config;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class Waits {
  private Waits() {
  }

  public static WebDriverWait shortWait(WebDriver driver) {
    return new WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
  }

  public static void waitForPageReady(WebDriver driver, Duration timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    wait.until(d -> {
      Object state = ((JavascriptExecutor) d).executeScript("return document.readyState");
      return "complete".equals(state);
    });
  }

  public static WebElement waitForVisible(WebDriver driver, By by) {
    return shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(by));
  }

  public static WebElement waitForClickable(WebDriver driver, By by) {
    return shortWait(driver).until(ExpectedConditions.elementToBeClickable(by));
  }

  public static void pauseAfterAction() {
    sleepMillis(TestConfig.ACTION_PAUSE_MILLIS);
  }

  public static void sleepMillis(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}