package com.bugbank.util;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class ElementFinder {
  private ElementFinder() {
  }

  public static WebElement findFirstDisplayed(WebDriver driver, List<By> candidates,
      Duration timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    return wait.until(d -> {
      for (By by : candidates) {
        List<WebElement> elements = d.findElements(by);
        for (WebElement element : elements) {
          if (element.isDisplayed()) {
            return element;
          }
        }
      }
      return null;
    });
  }
}
