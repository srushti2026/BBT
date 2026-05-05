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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginPage {
  private final WebDriver driver;
  // Updated XPaths as per specification
  private final By loginButtonXpath = By.xpath("//*[@id=\"btn-login\"]");
  private final By loginModalXpath = By.xpath("//*[@id=\"modal\"]/div");
  private final By emailInputXpath = By.xpath("//*[@id=\"email\"]");
  private final By passwordInputXpath = By.xpath("//*[@id=\"password\"]");
  private final By signInButtonXpath = By.xpath("//*[@id=\"btn-login-submit\"]");
  private final By dashboardIdentifierXpath = By.xpath("/html/body/div/nav/div[1]/div");
  private final By logoutButtonXpath = By.xpath("//*[@id=\"btn-logout\"]");

  public LoginPage(WebDriver driver) {
    this.driver = driver;
  }

  public void assertLoginPageLoaded() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
    try {
      WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonXpath));
      Assert.assertTrue(loginButton.isDisplayed(), "Login button should be visible");
    } catch (Exception e) {
      Assert.fail("Login page not loaded or login button not found: " + e.getMessage());
    }
  }

  public void clickLoginButton() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
    try {
      WebElement loginButton = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          loginButtonXpath,
          By.xpath("//button[contains(@id, 'login')]"),
          By.xpath("//button[normalize-space()='Login']"),
          By.xpath("//button[contains(text(), 'Login')]"),
          By.xpath("//a[@id='btn-login']")),
          Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);
      Waits.pauseAfterAction();
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
      Waits.pauseAfterAction();
    } catch (Exception e) {
      Assert.fail("Failed to click login button: " + e.getMessage());
    }
  }

  public void assertLoginModalAppears() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
    try {
      wait.until(ExpectedConditions.visibilityOfElementLocated(loginModalXpath));
      wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputXpath));
      wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputXpath));
    } catch (Exception e) {
      Assert.fail("Login modal did not appear: " + e.getMessage());
    }
  }

  public void fillEmailField(String email) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
    try {
      WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputXpath));
      emailField.clear();
      emailField.sendKeys(email);
      Waits.pauseAfterAction();
    } catch (Exception e) {
      Assert.fail("Failed to fill email field: " + e.getMessage());
    }
  }

  public void fillPasswordField(String password) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
    try {
      WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputXpath));
      passwordField.clear();
      passwordField.sendKeys(password);
      Waits.pauseAfterAction();
    } catch (Exception e) {
      Assert.fail("Failed to fill password field: " + e.getMessage());
    }
  }

  public void clickSignInButton() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
    try {
      WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(signInButtonXpath));
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signInButton);
      Waits.pauseAfterAction();
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signInButton);
      Waits.pauseAfterAction();
    } catch (Exception e) {
      Assert.fail("Failed to click sign-in button: " + e.getMessage());
    }
  }

  public void login(String email, String password) {
    clickLoginButton();
    assertLoginModalAppears();
    fillEmailField(email);
    fillPasswordField(password);
    clickSignInButton();
  }
}
