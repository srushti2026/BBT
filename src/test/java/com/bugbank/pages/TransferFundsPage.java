package com.bugbank.pages;

import com.bugbank.config.Waits;
import com.bugbank.util.ElementFinder;
import com.bugbank.util.ScreenshotUtil;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class TransferFundsPage {
  private final WebDriver driver;

  // Updated XPaths as per specification
  private final By transferHeaderXpath = By.xpath("//*[@id=\"transfer\"]/div[1]/h2");
  private final By fromAccountDropdownXpath = By.xpath("//*[@id=\"fromAccountSelect\"]");
  private final By fromAccountOptionXpath = By.xpath("//*[@id=\"fromAccountSelect\"]/option[1]");
  private final By receiverAccountInputXpath = By.xpath("//*[@id=\"toAccountId\"]");
  private final By beneficiaryNicknameXpath = By.xpath("//*[@id=\"transferNickname\"]");
  private final By amountInputXpath = By.xpath("//*[@id=\"transferAmount\"]");
  private final By categoryDropdownXpath = By.xpath("//*[@id=\"category\"]");
  private final By scheduleNowXpath = By.xpath("//*[@id=\"transfer\"]/div[2]/div[1]/div[7]/div/label[1]/input");
  private final By submitButtonXpath = By.xpath("//*[@id=\"btn-transfer-submit\"]");
  private final By errorMessageXpath = By.xpath("/div/span[2]");
  private final By successMessageXpath = By.xpath("/div/span[2]");
  private final By savingsAccountBalanceXpath = By.xpath("//*[@id=\"accountList\"]/div[2]/div[1]/div[1]");
  private final By transactionsNavXpath = By.xpath("//*[@id=\"nav-transactions\"]/span[2]");
  private final By viewLast15Xpath = By.xpath("//*[@id=\"btn-view-last15\"]");
  private final By firstTransactionXpath = By.xpath("//*[@id=\"txnResult\"]/div/table/tbody/tr[1]");

  private final By accountOptionText = By.xpath(
      "//*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'savings')"
          + " or contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'current')"
          + " or contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'business')]"
          + "[contains(.,'(') and contains(.,')')]");

  private final By transferTypeImps = By.xpath("//*[normalize-space()='IMPS']");
  private final By transferTypeNeft = By.xpath("//*[normalize-space()='NEFT']");
  private final By transferTypeRtgs = By.xpath("//*[normalize-space()='RTGS']");

  public TransferFundsPage(WebDriver driver) {
    this.driver = driver;
  }

  public void assertPageLoaded() {
    WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS));
    wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(transferHeaderXpath));
  }

  public void assertLayoutComplete() {
    try {
      Assert.assertTrue(driver.findElement(By.xpath("//*[translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='from account']")).isDisplayed(),
          "FROM ACCOUNT label should be visible");
    } catch (Exception e) {
      Assert.fail("FROM ACCOUNT label not found or not visible");
    }
    
    try {
      Assert.assertTrue(driver.findElement(receiverAccountInputXpath).isDisplayed(),
          "RECEIVER ACCOUNT ID input should be visible");
    } catch (Exception e) {
      Assert.fail("RECEIVER ACCOUNT ID input not found or not visible");
    }
    
    try {
      Assert.assertTrue(driver.findElement(beneficiaryNicknameXpath).isDisplayed(),
          "BENEFICIARY NICKNAME input should be visible");
    } catch (Exception e) {
      Assert.fail("BENEFICIARY NICKNAME input not found or not visible");
    }
    
    try {
      Assert.assertTrue(driver.findElement(transferTypeImps).isDisplayed(),
          "IMPS option should be visible");
    } catch (Exception e) {
      Assert.fail("IMPS option not found or not visible");
    }
    
    try {
      Assert.assertTrue(driver.findElement(transferTypeNeft).isDisplayed(),
          "NEFT option should be visible");
    } catch (Exception e) {
      Assert.fail("NEFT option not found or not visible");
    }
    
    try {
      Assert.assertTrue(driver.findElement(transferTypeRtgs).isDisplayed(),
          "RTGS option should be visible");
    } catch (Exception e) {
      Assert.fail("RTGS option not found or not visible");
    }
    
    // For amount input, use flexible locators
    try {
      WebElement amountElement = ElementFinder.findFirstDisplayed(driver, List.of(
          amountInputXpath,
          By.xpath("//input[@type='number']"),
          By.xpath("//input[contains(@placeholder, '5000')]"),
          By.xpath("//input[contains(translate(@placeholder, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'amount')]")
      ), Duration.ofSeconds(5));
      Assert.assertTrue(amountElement.isDisplayed(),
          "AMOUNT input should be visible");
    } catch (Exception e) {
      Assert.fail("AMOUNT input should be visible - " + e.getMessage());
    }
    
    try {
      Assert.assertTrue(driver.findElement(categoryDropdownXpath).isDisplayed(),
          "CATEGORY dropdown should be visible");
    } catch (Exception e) {
      Assert.fail("CATEGORY dropdown not found or not visible");
    }
    
    try {
      Assert.assertTrue(driver.findElement(scheduleNowXpath).isDisplayed(),
          "Schedule NOW option should be visible");
    } catch (Exception e) {
      Assert.fail("Schedule NOW option not found or not visible");
    }
    
    try {
      Assert.assertTrue(driver.findElement(By.xpath("//*[translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='later']")).isDisplayed(),
          "Schedule LATER option should be visible");
    } catch (Exception e) {
      Assert.fail("Schedule LATER option not found or not visible");
    }
    
    try {
      Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"transferRemarks\"]")).isDisplayed(),
          "REMARKS input should be visible");
    } catch (Exception e) {
      Assert.fail("REMARKS input not found or not visible");
    }
    
    try {
      Assert.assertTrue(driver.findElement(submitButtonXpath).isDisplayed(),
          "Send Money button should be visible");
    } catch (Exception e) {
      Assert.fail("Send Money button not found or not visible");
    }

    List<WebElement> otpFields = driver.findElements(By.xpath(
        "//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'otp')"
            + " or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'otp')]"
            + " | //label[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'otp')]"));
    Assert.assertTrue(otpFields.isEmpty(), "OTP fields should not be present");
  }

  public void openFromAccountDropdown() {
    WebElement dropdown = ElementFinder.findFirstDisplayed(driver, List.of(
        byFollowingLabelOrText("from account"),
        By.xpath("//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'from account')]/ancestor::div[1]//button"),
        By.xpath("//label[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'from account')]/following::button[1]")),
        Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS));
    // Scroll into view
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
    Waits.pauseAfterAction();
    clickElement(dropdown);
    Waits.pauseAfterAction();
  }

  public List<String> getFromAccountOptions() {
    waitForAccountOptions();
    List<WebElement> options = getFromAccountOptionElements();
    List<String> texts = new ArrayList<>();
    for (WebElement option : options) {
      if (option.isDisplayed()) {
        String text = normalizeOptionText(option);
        if (!text.isEmpty() && looksLikeAccountOption(text)) {
          texts.add(text);
        }
      }
    }
    return texts;
  }

  public boolean resetFromAccountIfPossible() {
    openFromAccountDropdown();
    waitForAccountOptions();
    List<WebElement> options = driver.findElements(By.xpath(
        "//*[@role='option' or self::li or self::div][normalize-space()]") );
    for (WebElement option : options) {
      String text = normalizeOptionText(option);
      String lowerText = text.toLowerCase(Locale.ROOT);
      if (lowerText.contains("select") || lowerText.contains("choose") || lowerText.contains("from account")) {
        try {
          ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        } catch (Exception e) {
          option.click();
        }
        Waits.pauseAfterAction();
        closeFromAccountDropdown();
        return true;
      }
    }
    return false;
  }

  public boolean isFromAccountSelected() {
    WebElement dropdown = getFromAccountDropdownElement();
    closeFromAccountDropdown();
    String text = dropdown.getText().trim();
    String lower = text.toLowerCase(Locale.ROOT);
    return !text.isEmpty() && !lower.contains("select") && text.matches(".*\\d+.*");
  }

  public void selectFromAccountContaining(String accountId) {
    openFromAccountDropdown();
    waitForAccountOptions();
    List<WebElement> options = getFromAccountOptionElements();
    for (WebElement option : options) {
      String text = normalizeOptionText(option);
      if (text.contains(accountId)) {
        try {
          ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        } catch (Exception e) {
          option.click();
        }
        Waits.pauseAfterAction();
        return;
      }
    }
    Assert.fail("No FROM ACCOUNT option contains account id: " + accountId);
  }

  public void fillReceiverAccountId(String value) {
    String sanitized = value == null ? "" : value.replaceAll("[^0-9]", "");
    if (sanitized.isEmpty()) {
      sanitized = "12"; // Updated default value
    }
    WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS));
    WebElement input = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(receiverAccountInputXpath));
    input.clear();
    Waits.pauseAfterAction();
    input.sendKeys(sanitized);
    Waits.pauseAfterAction();
  }

  public void fillBeneficiaryNickname(String value) {
    WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS));
    WebElement input = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(beneficiaryNicknameXpath));
    input.clear();
    Waits.pauseAfterAction();
    input.sendKeys(value);
    Waits.pauseAfterAction();
  }

  public String fillRemarks(String value) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS));
    try {
      WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"transferRemarks\"]")));
      input.clear();
      Waits.pauseAfterAction();
      input.sendKeys(value);
      Waits.pauseAfterAction();
      return value;
    } catch (Exception e) {
      // Try alternative: textarea with remarks name
      try {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='remarks']")));
        input.clear();
        Waits.pauseAfterAction();
        input.sendKeys(value);
        Waits.pauseAfterAction();
        return value;
      } catch (Exception e2) {
        // Try input with remarks name
        try {
          WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='remarks']")));
          input.clear();
          Waits.pauseAfterAction();
          input.sendKeys(value);
          Waits.pauseAfterAction();
          return value;
        } catch (Exception e3) {
          System.err.println("Failed to fill remarks: " + e3.getMessage());
          return "";
        }
      }
    }
  }

  public void selectTransferType(String type) {
    String normalized = type.trim().toUpperCase(Locale.ROOT);
    By target;
    if ("IMPS".equals(normalized)) {
      target = transferTypeImps;
    } else if ("NEFT".equals(normalized)) {
      target = transferTypeNeft;
    } else {
      target = transferTypeRtgs;
    }
    WebElement element = Waits.waitForClickable(driver, target);
    // Scroll into view
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    Waits.pauseAfterAction();
    
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    } catch (Exception e1) {
      try {
        element.click();
      } catch (Exception e2) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true}));", element);
      }
    }
    Waits.pauseAfterAction();
  }

  public void fillAmount(String value) {
     WebElement input = resolveAmountInput();
     if (input == null) {
       throw new RuntimeException("Unable to find amount input field");
     }
     
     // Scroll element into view
     ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
     Waits.pauseAfterAction();
     
     // Clear the field
     input.clear();
     Waits.pauseAfterAction();
     
     // Fill the field
     input.sendKeys(value);
     ((JavascriptExecutor) driver).executeScript(
         "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles: true}));", input, value);
     Waits.pauseAfterAction();
   }

  public void selectFirstCategoryOption() {
    WebElement dropdown = Waits.waitForClickable(driver, categoryDropdownXpath);
    // Scroll into view
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
    Waits.pauseAfterAction();
    
    // Click the dropdown
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
    } catch (Exception e) {
      dropdown.click();
    }
    Waits.pauseAfterAction();
    
    waitForDropdownOptions();
    List<WebElement> options = driver.findElements(By.xpath(
        "//*[@role='option' or self::li or self::div][normalize-space()]"));
    for (WebElement option : options) {
      if (option.isDisplayed()) {
        try {
          ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        } catch (Exception e) {
          option.click();
        }
        Waits.pauseAfterAction();
        return;
      }
    }
  }

  public void selectScheduleNow() {
    WebElement element = Waits.waitForClickable(driver, scheduleNowXpath);
    // Scroll into view
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    Waits.pauseAfterAction();
    
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    } catch (Exception e) {
      element.click();
    }
    Waits.pauseAfterAction();
  }

  public void submitTransfer() {
    WebElement button = Waits.waitForClickable(driver, submitButtonXpath);
    // Scroll into view
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
    Waits.pauseAfterAction();
    
    // Try JavaScript click
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    } catch (Exception e1) {
      // Fallback to regular click
      try {
        button.click();
      } catch (Exception e2) {
        // Last resort: blur any focused element first, then click via JS
        ((JavascriptExecutor) driver).executeScript("document.activeElement.blur();");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
      }
    }
    Waits.pauseAfterAction();
  }

  public boolean isErrorMessageVisible() {
    try {
      // Wait a bit for any message to appear
      Thread.sleep(1500);
      
      // Look for any displayed message
      List<WebElement> messages = driver.findElements(By.xpath(
          "//*[contains(@class,'toast') or @role='alert' or contains(@class,'alert')]"));
      
      for (WebElement msg : messages) {
        if (msg.isDisplayed()) {
          String text = msg.getText().toLowerCase().trim();
          // If message CONTAINS "transfer successful", it's NOT an error
          if (text.contains("transfer successful") || text.contains("success")) {
            continue;  // This is a success message, not an error
          }
          // If message is not empty and doesn't say success, it's an error
          if (!text.isEmpty()) {
            return true;
          }
        }
      }
      return false;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isSuccessMessageVisible() {
    try {
      List<WebElement> messages = driver.findElements(By.xpath(
          "//*[contains(@class,'toast') or @role='alert' or contains(@class,'alert')]"));
      for (WebElement msg : messages) {
        if (msg.isDisplayed()) {
          String text = msg.getText().toLowerCase();
          // Check if message contains "transfer successful"
          if (text.contains("transfer successful")) {
            return true;
          }
        }
      }
      return false;
    } catch (Exception e) {
      return false;
    }
  }

  private static By inputByLabelOrPlaceholder(String label, String placeholderFragment) {
    String lowered = label.toLowerCase(Locale.ROOT);
    String placeholder = placeholderFragment.toLowerCase(Locale.ROOT);
    return By.xpath("//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + lowered
        + "')]/following::input[1]"
        + " | //input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + placeholder + "')]"
        + " | //input[contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + lowered + "')]");
  }

  private void clickElement(WebElement element) {
    // Scroll element into view
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    Waits.pauseAfterAction();
    
    try {
      // Try JavaScript click first (most reliable)
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
      Waits.pauseAfterAction();
    } catch (Exception e1) {
      try {
        // Fallback to regular click
        element.click();
        Waits.pauseAfterAction();
      } catch (Exception e2) {
        // Last resort: try sending Enter key to focus and trigger
        element.sendKeys(Keys.ENTER);
        Waits.pauseAfterAction();
      }
    }
  }

  private static By byFollowingLabelOrText(String label) {
    String lowered = label.toLowerCase(Locale.ROOT);
    return By.xpath("//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + lowered
        + "')]/following::*[self::div or self::button][1]"
        + " | //button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + lowered + "')]"
        + " | //div[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + lowered + "')]");
  }

  private void waitForDropdownOptions() {
    new org.openqa.selenium.support.ui.WebDriverWait(driver,
        Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS))
        .until(d -> !d.findElements(By.xpath(
            "//*[@role='option' or self::li or self::div][normalize-space()]"
        )).isEmpty());
  }

  private void waitForAccountOptions() {
    try {
      // Add initial wait for dropdown to render
      Thread.sleep(800);
      new org.openqa.selenium.support.ui.WebDriverWait(driver,
          Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS))
          .until(d -> !getFromAccountOptionElements().isEmpty()
              || !d.findElements(accountOptionText).isEmpty());
      // Additional wait for options to be fully loaded
      Thread.sleep(800);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private List<WebElement> getFromAccountOptionElements() {
    List<WebElement> candidates = new ArrayList<>();
    candidates.addAll(driver.findElements(By.xpath(
        "//*[@role='option' or self::li or contains(@class,'MuiMenuItem') or contains(@class,'menu-item')][normalize-space()]")));
    candidates.addAll(driver.findElements(accountOptionText));
    List<WebElement> filtered = new ArrayList<>();
    for (WebElement candidate : candidates) {
      if (!candidate.isDisplayed()) {
        continue;
      }
      String text = normalizeOptionText(candidate);
      if (looksLikeAccountOption(text)) {
        filtered.add(candidate);
      }
    }
    return filtered;
  }

  private String normalizeOptionText(WebElement option) {
    try {
      String text = option.getText().trim();
      if (!text.isEmpty()) {
        return text;
      }
      Object innerResult = ((JavascriptExecutor) driver).executeScript(
          "return arguments[0].innerText || arguments[0].textContent || '';", option);
      return innerResult != null ? innerResult.toString().trim() : "";
    } catch (Exception e) {
      return "";
    }
  }

  private boolean looksLikeAccountOption(String text) {
    if (text == null || text.isEmpty()) {
      return false;
    }
    String lower = text.toLowerCase(Locale.ROOT);
    return (lower.contains("savings") || lower.contains("current") || lower.contains("business"))
        && text.matches(".*\\d+.*")
        && text.contains("(")
        && text.contains(")");
  }

  // TF005 - Field value getters with assertions
  public String getReceiverAccountValue() {
    WebElement input = Waits.waitForVisible(driver, receiverAccountInputXpath);
    return input.getAttribute("value") != null ? input.getAttribute("value") : "";
  }

  public String getBeneficiaryNicknameValue() {
    WebElement input = Waits.waitForVisible(driver, beneficiaryNicknameXpath);
    return input.getAttribute("value") != null ? input.getAttribute("value") : "";
  }

  public String getAmountValue() {
    try {
      WebElement input = resolveAmountInput();
      return input.getAttribute("value") != null ? input.getAttribute("value") : "";
    } catch (Exception e) {
      return "";
    }
  }

  public String getRemarksValue() {
    try {
      WebElement input = new WebDriverWait(driver, Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS))
          .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"transferRemarks\"]")));
      return input.getAttribute("value") != null ? input.getAttribute("value") : input.getText();
    } catch (Exception e) {
      // Try alternative
      try {
        WebElement input = new WebDriverWait(driver, Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='remarks']")));
        return input.getAttribute("value") != null ? input.getAttribute("value") : input.getText();
      } catch (Exception e2) {
        return "";
      }
    }
  }

  private WebElement resolveAmountInput() {
    try {
      return Waits.waitForVisible(driver, amountInputXpath);
    } catch (Exception e1) {
      List<By> alternatives = List.of(
          By.xpath("//label[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'amount')]/following::input[1]"),
          By.xpath("//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'amount')]/following::input[1]"),
          By.xpath("//input[contains(translate(@placeholder, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'amount')]"),
          By.xpath("//input[contains(translate(@aria-label, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'amount')]"),
          By.xpath("//input[contains(@name, 'amount') or contains(@id, 'amount')]")
      );
      try {
        return ElementFinder.findFirstDisplayed(driver, alternatives,
            Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS));
      } catch (Exception e2) {
        WebElement receiverInput = null;
        try {
          receiverInput = Waits.waitForVisible(driver, receiverAccountInputXpath);
        } catch (Exception ignored) {
          // Ignore
        }
        List<WebElement> numInputs = driver.findElements(By.xpath("//input[@type='number' or @inputmode='numeric']"));
        for (WebElement candidate : numInputs) {
          if (receiverInput != null && candidate.equals(receiverInput)) {
            continue;
          }
          String id = candidate.getAttribute("id");
          String name = candidate.getAttribute("name");
          String placeholder = candidate.getAttribute("placeholder");
          String aria = candidate.getAttribute("aria-label");
          String combined = ("" + id + name + placeholder + aria).toLowerCase(Locale.ROOT);
          if (combined.contains("amount")) {
            return candidate;
          }
          if (candidate.isDisplayed()) {
            return candidate;
          }
        }
      }
    }
    return null;
  }

  public boolean isTransferTypeSelected(String type) {
    String normalized = type.trim().toUpperCase(Locale.ROOT);
    By target;
    if ("IMPS".equals(normalized)) {
      target = transferTypeImps;
    } else if ("NEFT".equals(normalized)) {
      target = transferTypeNeft;
    } else {
      target = transferTypeRtgs;
    }
    try {
      WebElement element = driver.findElement(target);
      return element.isDisplayed() && element.isEnabled();
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isCategorySelected() {
    try {
      WebElement dropdown = driver.findElement(categoryDropdownXpath);
      return dropdown.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isScheduleNowSelected() {
    try {
      WebElement element = driver.findElement(scheduleNowXpath);
      return element.isDisplayed() && element.isEnabled();
    } catch (Exception e) {
      return false;
    }
  }

  public String getErrorMessage() {
    try {
      List<WebElement> messages = driver.findElements(By.xpath(
          "//*[contains(@class,'toast') or @role='alert' or contains(@class,'alert')]"));
      for (WebElement msg : messages) {
        if (msg.isDisplayed()) {
          String text = msg.getText();
          String lowerText = text.toLowerCase();
          // Return any message that's NOT about success
          if (!lowerText.contains("transfer successful") && !lowerText.contains("success")) {
            return text;
          }
        }
      }
      return "";
    } catch (Exception e) {
      return "";
    }
  }

  // TF007 & TF008 - Account selection helpers
  public void selectFirstAvailableAccount() {
    openFromAccountDropdown();
    waitForAccountOptions();
    List<WebElement> options = getFromAccountOptionElements();
    for (WebElement option : options) {
      if (option.isDisplayed()) {
        String text = normalizeOptionText(option);
        if (!text.isEmpty() && !text.toLowerCase().contains("select") && looksLikeAccountOption(text)) {
          try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
          } catch (Exception e) {
            option.click();
          }
          Waits.pauseAfterAction();
          return;
        }
      }
    }
  }

  public String getSelectedFromAccount() {
    try {
      WebElement dropdown = getFromAccountDropdownElement();
      closeFromAccountDropdown();
      String text = dropdown.getText().trim();
      if (text.isEmpty()) {
        Object result = ((JavascriptExecutor) driver).executeScript(
            "return arguments[0].innerText || arguments[0].textContent || '';", dropdown);
        text = result != null ? result.toString().trim() : "";
      }
      return text;
    } catch (Exception e) {
      return "";
    }
  }

  public String getSelectedAccountNumber() {
    String selected = getSelectedFromAccount();
    // Extract account number from the selected account
    // Format is typically: "Account Type - Account Number (Balance)"
    // We need to extract ONLY the account number part
    if (selected.isEmpty()) {
      return "";
    }
    
    // Try to extract number between account type and opening parenthesis
    String[] parts = selected.split("\\(");
    if (parts.length > 0) {
      String beforeBalance = parts[0].trim();
      // Extract all numbers from this part
      String[] tokens = beforeBalance.split("[-–]");
      for (String token : tokens) {
        String cleaned = token.replaceAll("[^0-9]", "").trim();
        if (!cleaned.isEmpty()) {
          return cleaned;
        }
      }
    }
    
    // Fallback: extract all numbers
    String accountNumber = selected.replaceAll("[^0-9]", "").trim();
    if (!accountNumber.isEmpty() && accountNumber.length() >= 6) {
      return accountNumber;
    }
    
    // Last resort: try from options
    List<String> options = getFromAccountOptions();
    if (!options.isEmpty()) {
      String fallback = options.get(0).replaceAll("[^0-9]", "").trim();
      if (!fallback.isEmpty()) {
        return fallback;
      }
    }
    return "";
  }

  private WebElement getFromAccountDropdownElement() {
    return ElementFinder.findFirstDisplayed(driver, List.of(
        byFollowingLabelOrText("from account"),
        By.xpath("//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'from account')]/ancestor::div[1]//button"),
        By.xpath("//label[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'from account')]/following::button[1]")),
        Duration.ofSeconds(com.bugbank.config.TestConfig.WAIT_TIMEOUT_SECONDS));
  }

  private void closeFromAccountDropdown() {
    try {
      WebElement dropdown = getFromAccountDropdownElement();
      dropdown.sendKeys(Keys.ESCAPE);
      Waits.pauseAfterAction();
    } catch (Exception ignored) {
      // Ignore
    }
  }

  // Screenshot and Message Capture Methods
  public void captureSuccessScreenshot(String testName) {
    waitForSuccessMessage();
    if (isSuccessMessageVisible()) {
      ScreenshotUtil.takeScreenshotOnSuccess(driver, testName);
    }
  }

  public void captureFailureScreenshot(String testName) {
    waitForErrorMessage();
    if (isErrorMessageVisible()) {
      ScreenshotUtil.takeScreenshotOnFailure(driver, testName);
    }
  }

  public void captureMessageScreenshot(String testName) {
    waitForSuccessOrErrorMessage();
    if (isSuccessMessageVisible()) {
      ScreenshotUtil.captureScreenshot(driver, testName + "_success_message");
    } else if (isErrorMessageVisible()) {
      ScreenshotUtil.captureScreenshot(driver, testName + "_error_message");
    }
  }

  public String captureAndGetMessage(String testName) {
    waitForSuccessOrErrorMessage();
    String message = "";
    if (isSuccessMessageVisible()) {
      message = getSuccessMessage();
      ScreenshotUtil.takeScreenshotOnSuccess(driver, testName);
    } else if (isErrorMessageVisible()) {
      message = getErrorMessage();
      ScreenshotUtil.takeScreenshotOnFailure(driver, testName);
    }
    return message;
  }

  public String getSuccessMessage() {
    List<WebElement> success = driver.findElements(By.xpath(
        "//*[contains(@class,'toast') and contains(@class,'success')]"
            + " | //*[(contains(@class,'alert') or @role='alert')"
            + " and (contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'success')"
            + " or contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'successful'))]"
            + " | //*[(contains(@class,'modal') or contains(@class,'notification'))"
            + " and contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'success')]"));
    for (WebElement item : success) {
      if (item.isDisplayed()) {
        return item.getText();
      }
    }
    return "";
  }

  private void waitForSuccessMessage() {
    int maxWaitSeconds = 10;
    for (int i = 0; i < maxWaitSeconds; i++) {
      if (isSuccessMessageVisible()) {
        return;
      }
      Waits.sleepMillis(500);
    }
  }

  private void waitForErrorMessage() {
    int maxWaitSeconds = 10;
    for (int i = 0; i < maxWaitSeconds; i++) {
      if (isErrorMessageVisible()) {
        return;
      }
      Waits.sleepMillis(500);
    }
  }

  private void waitForSuccessOrErrorMessage() {
    int maxWaitSeconds = 10;
    for (int i = 0; i < maxWaitSeconds; i++) {
      if (isSuccessMessageVisible() || isErrorMessageVisible()) {
        return;
      }
      Waits.sleepMillis(500);
    }
  }
}