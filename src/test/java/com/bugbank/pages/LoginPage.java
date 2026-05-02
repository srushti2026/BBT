package com.bugbank.pages;

import com.bugbank.config.TestConfig;
import com.bugbank.util.ElementFinder;
import java.time.Duration;
import java.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage {
  private final WebDriver driver;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
  }

   public void login(String email, String password) {
      ensureLoginModalOpen();
      com.bugbank.config.Waits.pauseAfterAction();

      WebElement emailField = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.xpath("//input[@type='email']"),
          By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]"),
          By.xpath("//input[contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]")),
          Duration.ofSeconds(TestConfig.LONG_WAIT_SECONDS));
      emailField.clear();
      com.bugbank.config.Waits.pauseAfterAction();
      emailField.sendKeys(email);
      com.bugbank.config.Waits.pauseAfterAction();

      WebElement passwordField = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.xpath("//input[@type='password']"),
          By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'password')]")),
          Duration.ofSeconds(TestConfig.LONG_WAIT_SECONDS));
      passwordField.clear();
      com.bugbank.config.Waits.pauseAfterAction();
      passwordField.sendKeys(password);
      com.bugbank.config.Waits.pauseAfterAction();

      try {
        // Wait for the button to be clickable using ID 'btn-login-submit'
        WebElement loginButton = new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(TestConfig.LONG_WAIT_SECONDS))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(By.id("btn-login-submit")));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);
        com.bugbank.config.Waits.pauseAfterAction();
        
        // Click the button
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        com.bugbank.config.Waits.pauseAfterAction();
      } catch (Exception e) {
        throw new RuntimeException("Failed to click sign-in button with ID 'btn-login-submit': " + e.getMessage(), e);
      }
    }

  public void assertLoginPageLoaded() {
    try {
      // Try multiple strategies to find the login button
      WebElement loginButton = null;
      try {
        // First try by ID
        loginButton = new org.openqa.selenium.support.ui.WebDriverWait(driver,
            Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(
                By.id("btn-login")));
      } catch (Exception e1) {
        // Try by class
        loginButton = new org.openqa.selenium.support.ui.WebDriverWait(driver,
            Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@class, 'btn-landing') and contains(@class, 'btn-primary-land')]")));
      }
      
      if (loginButton != null) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);
        Waits.pauseAfterAction();
        Assert.assertTrue(loginButton.isDisplayed(), "Login button should be visible");
      } else {
        Assert.fail("Login button not found");
      }
    } catch (Exception e) {
      Assert.fail("Login page not loaded or login button not found: " + e.getMessage());
    }
  }

  public void clickLoginButton() {
     try {
       // Wait for the button to be clickable using ID 'btn-login'
       WebElement loginButton = new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS))
           .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(By.id("btn-login")));
       
       // Scroll into view
       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);
       com.bugbank.config.Waits.pauseAfterAction();
       
       // Click the button
       ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
       com.bugbank.config.Waits.pauseAfterAction();
     } catch (Exception e) {
       throw new RuntimeException("Failed to click login button with ID 'btn-login': " + e.getMessage(), e);
     }
   }

  public void assertLoginModalAppears() {
    try {
      ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.xpath("//input[@type='email']"),
          By.xpath("//div[contains(@class,'modal-card')]")),
          Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
    } catch (Exception e) {
      Assert.fail("Login modal did not appear: " + e.getMessage());
    }
  }

  public void fillEmailField(String email) {
    WebElement emailField = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
        By.xpath("//input[@type='email']"),
        By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]"),
        By.xpath("//input[contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]")),
        Duration.ofSeconds(TestConfig.LONG_WAIT_SECONDS));
    emailField.clear();
    emailField.sendKeys(email);
    com.bugbank.config.Waits.pauseAfterAction();
  }

  public void fillPasswordField(String password) {
    WebElement passwordField = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
        By.xpath("//input[@type='password']"),
        By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'password')]")),
        Duration.ofSeconds(TestConfig.LONG_WAIT_SECONDS));
    passwordField.clear();
    passwordField.sendKeys(password);
    com.bugbank.config.Waits.pauseAfterAction();
  }

  public void clickSignInButton() {
     try {
       // Wait for the button to be clickable using ID 'btn-login-submit'
       WebElement signInButton = new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(TestConfig.LONG_WAIT_SECONDS))
           .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(By.id("btn-login-submit")));
       
       // Scroll into view
       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signInButton);
       com.bugbank.config.Waits.pauseAfterAction();
       
       // Click the button
       ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signInButton);
       com.bugbank.config.Waits.pauseAfterAction();
     } catch (Exception e) {
       throw new RuntimeException("Failed to click sign-in button with ID 'btn-login-submit': " + e.getMessage(), e);
     }
   }

   private void ensureLoginModalOpen() {
     try {
       ElementFinder.findFirstDisplayed(driver, Arrays.asList(
           By.xpath("//input[@type='email']"),
           By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]"),
           By.xpath("//div[contains(@class,'modal-card')]"))
       , Duration.ofSeconds(3));
       return;
     } catch (Exception ignored) {
       // If login UI is not visible quickly, try opening the modal.
     }

      WebElement loginOpenButton = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.id("btn-login"),
          By.xpath("//button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'login')]")),
          Duration.ofSeconds(TestConfig.LONG_WAIT_SECONDS));

      if (!driver.findElements(By.xpath("//div[contains(@class,'modal-card')]")).isEmpty()) {
        return;
      }

      // Scroll into view
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginOpenButton);
      com.bugbank.config.Waits.pauseAfterAction();
      
      // Click the login open button
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginOpenButton);
      com.bugbank.config.Waits.pauseAfterAction();
    }
}