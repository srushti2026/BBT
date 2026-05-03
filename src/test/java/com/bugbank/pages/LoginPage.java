package com.bugbank.pages;

import com.bugbank.config.TestConfig;
import com.bugbank.config.Waits;
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
  private final By loginButtonId = By.xpath("//*[@id='btn-login']");
  private final By loginButtonFullXpath = By.xpath("/html/body/div[3]/div[2]/button[1]");
  private final By loginButtonClass = By.cssSelector(".btn-landing.btn-primary-land");

  public LoginPage(WebDriver driver) {
    this.driver = driver;
  }

   public void login(String email, String password) {
    ensureLoginModalOpen();
      com.bugbank.config.Waits.pauseAfterAction();
    WebElement emailField = waitForLoginEmailField();
      emailField.clear();
      com.bugbank.config.Waits.pauseAfterAction();
      emailField.sendKeys(email);
      com.bugbank.config.Waits.pauseAfterAction();

    WebElement passwordField = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
      By.xpath("//input[@type='password']"),
      By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'password')]") ),
      Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
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
                loginButtonId));
      } catch (Exception e1) {
        // Try by class
        loginButton = new org.openqa.selenium.support.ui.WebDriverWait(driver,
            Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(
                loginButtonClass));
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
       forceClickLoginButton();
       com.bugbank.config.Waits.pauseAfterAction();
       if (isLoginModalVisible()) {
         return;
       }
       WebElement loginButton = waitForLoginButtonClickable();
       
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
     if (isLoginModalVisible()) {
       return;
     }

    WebElement loginOpenButton = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
      loginButtonFullXpath,
      loginButtonId,
      loginButtonClass,
      By.xpath("//button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'login')]") ),
      Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));

      if (!driver.findElements(By.xpath("//div[contains(@class,'modal-card')]")).isEmpty()) {
        return;
      }

  // Scroll into view
  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginOpenButton);
  com.bugbank.config.Waits.pauseAfterAction();

  // Click the login open button
  ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginOpenButton);
  com.bugbank.config.Waits.pauseAfterAction();

      // Verify modal is open (fail fast if it isn't)
      try {
        ElementFinder.findFirstDisplayed(driver, Arrays.asList(
            By.cssSelector(".modal-card input[type='email']"),
            By.cssSelector(".modal-card input[name*='email']"),
            By.cssSelector(".modal-card input[id*='email']"),
            By.xpath("//input[@type='email']"),
            By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]") ,
            By.xpath("//div[contains(@class,'modal-card')]")
        ), Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
      } catch (Exception e) {
        // Retry clicking the login button once before failing
        try {
          forceClickLoginButton();
          com.bugbank.config.Waits.pauseAfterAction();
        } catch (Exception ignoredRetry) {
          // Ignore
        }
        throw new RuntimeException("Login modal did not appear after clicking login button.", e);
      }
    }

  private boolean isLoginModalVisible() {
    try {
      return !driver.findElements(By.cssSelector(".modal-card")).isEmpty()
          || !driver.findElements(By.xpath("//input[@type='email']")).isEmpty()
          || !driver.findElements(By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]")).isEmpty();
    } catch (Exception e) {
      return false;
    }
  }

  private void forceClickLoginButton() {
    try {
      ((JavascriptExecutor) driver).executeScript(
          "var btn=document.evaluate(\"//*[@id='btn-login']\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; if(btn){btn.click();}");
    } catch (Exception ignored) {
      // Ignore
    }
    try {
      ((JavascriptExecutor) driver).executeScript(
          "var btn=document.evaluate(\"/html/body/div[3]/div[2]/button[1]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; if(btn){btn.click();}");
    } catch (Exception ignored) {
      // Ignore
    }
    try {
      ((JavascriptExecutor) driver).executeScript(
          "var btn=document.querySelector('.btn-landing.btn-primary-land'); if(btn){btn.click();}");
    } catch (Exception ignored) {
      // Ignore
    }
  }

  private WebElement waitForLoginButtonClickable() {
    try {
      return new org.openqa.selenium.support.ui.WebDriverWait(driver,
          Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS))
          .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(loginButtonFullXpath));
    } catch (Exception ignored) {
      try {
        return new org.openqa.selenium.support.ui.WebDriverWait(driver,
            Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(loginButtonId));
      } catch (Exception ignored2) {
        return new org.openqa.selenium.support.ui.WebDriverWait(driver,
            Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(loginButtonClass));
      }
    }
  }

  private WebElement waitForLoginEmailField() {
    try {
      return ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.cssSelector(".modal-card input[type='email']"),
          By.cssSelector(".modal-card input[name*='email']"),
          By.cssSelector(".modal-card input[id*='email']"),
          By.xpath("//input[@type='email']"),
          By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]"),
          By.xpath("//input[contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]"),
          By.xpath("//input[contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]"),
          By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'user')]")),
          Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
    } catch (Exception e) {
      try {
        for (WebElement input : driver.findElements(By.cssSelector(".modal-card input"))) {
          if (input.isDisplayed()) {
            return input;
          }
        }
        for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
          try {
            driver.switchTo().frame(frame);
            WebElement iframeInput = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
                By.cssSelector("input[type='email']"),
                By.cssSelector("input[name*='email']"),
                By.cssSelector("input[id*='email']"),
                By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'email')]")),
                Duration.ofSeconds(2));
            if (iframeInput != null) {
              return iframeInput;
            }
          } catch (Exception ignoredFrame) {
            // Ignore and continue
          } finally {
            driver.switchTo().defaultContent();
          }
        }
      } catch (Exception ignored) {
        // Ignore
      }
      throw new RuntimeException("Login email field not found after opening login modal.", e);
    }
  }
}