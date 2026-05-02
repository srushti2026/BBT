package com.bugbank.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverManager {
  public static WebDriver driver;

  private DriverManager() {
  }

  public static synchronized WebDriver startDriver() {
    if (driver == null) {
      WebDriverManager.chromedriver().setup();
      ChromeOptions options = new ChromeOptions();
      boolean headless = isHeadlessEnabled();
      if (headless) {
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
      }
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

  private static boolean isHeadlessEnabled() {
    String property = System.getProperty("headless");
    if (property != null) {
      return Boolean.parseBoolean(property);
    }
    String env = System.getenv("HEADLESS");
    if (env != null) {
      return Boolean.parseBoolean(env);
    }
    String ci = System.getenv("CI");
    if (ci != null) {
      return Boolean.parseBoolean(ci);
    }
    return false;
  }
}
