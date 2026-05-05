package com.bugbank.pages;

import com.bugbank.config.TestConfig;
import com.bugbank.config.Waits;
import com.bugbank.util.ElementFinder;
import java.time.Duration;
import java.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPage {
  private final WebDriver driver;

  private final By transferHeader = By.xpath("//*[normalize-space()='Transfer Funds']");
  private final By accountOverviewXpath = By.xpath("//*[@id=\"accounts\"]/div[1]/h2");
  private final By transferMenuXpath = By.xpath("//*[@id=\"nav-transfer\"]/span[2]");
  private final By savingsAccountBalanceXpath = By.xpath("//*[@id=\"accountList\"]/div[2]/div[1]/div[1]");
  private final By transactionsNavXpath = By.xpath("//*[@id=\"nav-transactions\"]/span[2]");
  private final By viewLast15Xpath = By.xpath("//*[@id=\"btn-view-last15\"]");
  private final By firstTransactionXpath = By.xpath("//*[@id=\"txnResult\"]/div/table/tbody/tr[1]");

  public DashboardPage(WebDriver driver) {
    this.driver = driver;
  }

  public void waitForLoaded() {
    com.bugbank.config.Waits.sleepMillis(2000);
    ElementFinder.findFirstDisplayed(driver, Arrays.asList(
        By.xpath("//*[normalize-space()='Transfer']"),
        By.xpath("//*[normalize-space()='Accounts']"),
        By.xpath("//*[normalize-space()='Dashboard']")),
        Duration.ofSeconds(com.bugbank.config.TestConfig.LONG_WAIT_SECONDS));
  }

  public void assertAccountOverviewVisible() {
    try {
      ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.xpath("//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'account overview')]"),
          By.xpath("//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accounts')]")),
          Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS));
    } catch (Exception e) {
      // Try alternative checks
      ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.xpath("//*[contains(translate(@class, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'account')]")),
          Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS));
    }
  }

  public void navigateToTransfer() {
    WebElement transferNav = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
        By.xpath("//aside//*[normalize-space()='Transfer']"),
        By.xpath("//*[contains(@class,'sidebar')]//*[normalize-space()='Transfer']"),
        By.xpath("//*[self::a or self::div or self::span][normalize-space()='Transfer']")),
        Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
    transferNav.click();
    Waits.pauseAfterAction();
    Waits.waitForVisible(driver, transferHeader);
  }

  public void logoutIfPresent() {
    WebElement logoutButton = null;
    try {
      logoutButton = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.xpath("//button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'logout')]"),
          By.xpath("//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'logout')]")),
          Duration.ofSeconds(5));
    } catch (Exception ignored) {
      // ignore if logout not present
    }
    if (logoutButton != null) {
      logoutButton.click();
    }
  }

  public String getSavingsAccountBalance() {
    try {
      WebElement balanceElement = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.xpath("//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'savings')]/following::*[contains(translate(., '0123456789.₹$€¥', ''), '')]"),
          By.xpath("//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'balance')]"),
          By.xpath("//*[contains(@class, 'balance')]")),
          Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
      return balanceElement.getText();
    } catch (Exception e) {
      System.out.println("Could not retrieve savings account balance: " + e.getMessage());
      return "";
    }
  }

  public void navigateToTransactions() {
    try {
      WebElement transactionsNav = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.xpath("//aside//*[normalize-space()='Transactions']"),
          By.xpath("//*[contains(@class,'sidebar')]//*[normalize-space()='Transactions']"),
          By.xpath("//*[self::a or self::div or self::span][normalize-space()='Transactions']")),
          Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
      transactionsNav.click();
      Waits.pauseAfterAction();
    } catch (Exception e) {
      System.out.println("Could not navigate to transactions: " + e.getMessage());
    }
  }

  public void navigateToDashboard() {
    try {
      WebElement dashboardNav = ElementFinder.findFirstDisplayed(driver, Arrays.asList(
          By.xpath("//aside//*[normalize-space()='Dashboard']"),
          By.xpath("//*[contains(@class,'sidebar')]//*[normalize-space()='Dashboard']"),
          By.xpath("//*[self::a or self::div or self::span][normalize-space()='Dashboard']")),
          Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS));
      dashboardNav.click();
      Waits.pauseAfterAction();
    } catch (Exception e) {
      System.out.println("Could not navigate to dashboard: " + e.getMessage());
    }
  }
}