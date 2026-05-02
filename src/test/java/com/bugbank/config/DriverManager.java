package com.bugbank.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverManager {
  private static WebDriver driver;

  private DriverManager() {
  }

  public static synchronized WebDriver startDriver() {
    if (driver == null) {
      WebDriverManager.chromedriver().setup();
      ChromeOptions options = new ChromeOptions();
      options.addArguments("start-maximized");
      options.addArguments("--disable-gpu");
      options.addArguments("--disable-dev-shm-usage");
      options.addArguments("--no-sandbox");
      options.addArguments("--remote-allow-origins=*");
      driver = new ChromeDriver(options);
      driver.manage().timeouts().implicitlyWait(Duration.ZERO);
      driver.manage().timeouts().pageLoadTimeout(
          Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));
    }
    return driver;
  }

  public static WebDriver getDriver() {
    return driver;
  }

  public static synchronized void stopDriver() {
    if (driver != null) {
      driver.quit();
      driver = null;
    }
  }
}
