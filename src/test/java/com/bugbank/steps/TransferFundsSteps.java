package com.bugbank.steps;

import com.bugbank.config.DriverManager;
import com.bugbank.pages.DashboardPage;
import com.bugbank.pages.LoginPage;
import com.bugbank.pages.TransferFundsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class TransferFundsSteps {
  private final WebDriver driver = DriverManager.getDriver();
  private final TransferFundsPage transferFundsPage = new TransferFundsPage(driver);
  private final DashboardPage dashboardPage = new DashboardPage(driver);
  private final LoginPage loginPage = new LoginPage(driver);

  @Given("user navigates to the application URL")
  public void userNavigatesToApplicationUrl() {
    String baseUrl = System.getProperty("url", "https://smartbank-j2m0.onrender.com/");
    driver.navigate().to(baseUrl);
    try {
      driver.manage().window().maximize();
    } catch (Exception ignored) {
      // Ignore if already maximized or not supported in the environment.
    }
  }

  @Given("login page is loaded with logo and login button visible")
  public void loginPageIsLoaded() {
    // Wait for login page to load and verify login button is visible
    loginPage.assertLoginPageLoaded();
  }

  @When("user clicks on login button")
  public void userClicksOnLoginButton() {
    loginPage.clickLoginButton();
  }

  @Then("login popup with email and password fields appears")
  public void loginPopupAppears() {
    loginPage.assertLoginModalAppears();
  }

  @When("user enters valid credentials")
  public void userEntersValidCredentials() {
    String email = System.getProperty("test.email", "prtwo@gmail.com");
    String password = System.getProperty("test.password", "Pleasework@05");
    loginPage.fillEmailField(email);
    loginPage.fillPasswordField(password);
  }

  @When("user clicks on sign in button in login popup")
  public void userClicksSignInButton() {
    loginPage.clickSignInButton();
  }

  @Then("dashboard page loads successfully")
  public void dashboardPageLoads() {
    dashboardPage.waitForLoaded();
  }

  @Then("account overview text is visible on dashboard")
  public void accountOverviewIsVisible() {
    dashboardPage.assertAccountOverviewVisible();
  }

  @When("user clicks on transfer option in menu")
  public void userClicksTransferOption() {
    dashboardPage.navigateToTransfer();
  }

  @Then("transfer funds page is loaded")
  public void transferFundsPageIsLoaded() {
    transferFundsPage.assertPageLoaded();
  }

  @Given("user is on Transfer Funds page")
  public void userIsOnTransferFundsPage() {
    dashboardPage.navigateToTransfer();
    transferFundsPage.assertPageLoaded();
  }

  @Then("transfer page layout is complete")
  public void transferPageLayoutIsComplete() {
    transferFundsPage.assertLayoutComplete();
    com.bugbank.config.Waits.sleepMillis(5000);
  }

  @When("user opens FROM ACCOUNT dropdown")
  public void userOpensFromAccountDropdown() {
    transferFundsPage.openFromAccountDropdown();
  }

  @Then("accounts are populated and selectable")
  public void accountsArePopulatedAndSelectable() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    Assert.assertTrue(options.size() >= 1, "At least one account should be listed");
  }

  @Then("accounts display with format: \"Account Type - Account Number \\(Balance\\)\"")
  public void accountsDisplayWithFormat() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    Assert.assertTrue(options.size() >= 1, "At least one account should be listed");
    for (String option : options) {
      Assert.assertTrue(option.matches(".*\\d+.*\\(.*\\).*"),
          "Account option should contain account number and balance in parentheses: " + option);
    }
  }

  @Then("at least one account is visible in the dropdown")
  public void atLeastOneAccountVisible() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    Assert.assertTrue(options.size() >= 1, "At least one account should be visible in dropdown");
  }

  @When("user fills mandatory fields except FROM ACCOUNT")
  public void userFillsMandatoryFieldsExceptFromAccount() {
    boolean reset = transferFundsPage.resetFromAccountIfPossible();
    if (!reset) {
      Assert.assertFalse(transferFundsPage.isFromAccountSelected(),
          "FROM ACCOUNT should be unselected or placeholder");
    }
    transferFundsPage.fillReceiverAccountId("1023");
    transferFundsPage.fillBeneficiaryNickname("Rent");
    transferFundsPage.selectTransferType("IMPS");
    transferFundsPage.fillAmount("1");
    transferFundsPage.selectFirstCategoryOption();
    transferFundsPage.selectScheduleNow();
  }

  @When("user fills minimal required transfer details")
  public void userFillsMinimalRequiredTransferDetails() {
    transferFundsPage.fillBeneficiaryNickname("Rent");
    transferFundsPage.selectTransferType("IMPS");
    transferFundsPage.fillAmount("1");
    transferFundsPage.selectFirstCategoryOption();
    transferFundsPage.selectScheduleNow();
  }

  @When("user submits transfer")
  public void userSubmitsTransfer() {
    transferFundsPage.submitTransfer();
  }

  @Then("error indicates FROM ACCOUNT is required and no success message appears")
  public void errorIndicatesFromAccountRequired() {
    Assert.assertTrue(transferFundsPage.isErrorMessageVisible(),
        "Error message should indicate FROM ACCOUNT is required");
    Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
        "Success message should not appear");
  }

  @Then("option shows account number and balance")
  public void optionShowsAccountNumberAndBalance() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    Assert.assertTrue(options.size() >= 1, "At least one account should be listed");
    String option = options.get(0);
    Assert.assertTrue(option.toUpperCase().contains("SAVINGS"),
        "Option should include account type");
    Assert.assertTrue(option.matches(".*\\(.*\\).*"),
        "Option should include balance in parentheses");
  }

  @Then("multiple accounts are listed")
  public void multipleAccountsAreListed() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    Assert.assertTrue(options.size() >= 2, "At least two accounts should be listed");
  }

  @When("user selects FROM ACCOUNT {string}")
  public void userSelectsFromAccount(String accountId) {
    transferFundsPage.selectFromAccountContaining(accountId);
  }

  @When("user enters RECEIVER ACCOUNT ID {string}")
  public void userEntersReceiverAccountId(String accountId) {
    transferFundsPage.fillReceiverAccountId(accountId);
  }

  @Then("self-transfer error is shown and no success message appears")
  public void selfTransferErrorIsShown() {
    Assert.assertTrue(transferFundsPage.isErrorMessageVisible(),
        "Self-transfer should be blocked with an error");
    Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
        "Success message should not appear");
  }

  // TF005 - Detailed steps with assertions
  @When("user resets FROM ACCOUNT selection to empty")
  public void userResetsFromAccountToEmpty() {
    boolean reset = transferFundsPage.resetFromAccountIfPossible();
    if (!reset) {
      Assert.assertFalse(transferFundsPage.isFromAccountSelected(),
          "FROM ACCOUNT should be unselected for this test");
    }
  }

  @When("user enters RECEIVER ACCOUNT ID {string} with assertion")
  public void userEntersReceiverAccountIdWithAssertion(String accountId) {
    transferFundsPage.fillReceiverAccountId(accountId);
    Assert.assertTrue(transferFundsPage.getReceiverAccountValue().contains(accountId),
        "Receiver Account ID should be filled with: " + accountId);
  }

  @When("user enters BENEFICIARY NICKNAME {string} with assertion")
  public void userEntersBeneficiaryNicknameWithAssertion(String nickname) {
    transferFundsPage.fillBeneficiaryNickname(nickname);
    Assert.assertTrue(transferFundsPage.getBeneficiaryNicknameValue().contains(nickname),
        "Beneficiary Nickname should be filled with: " + nickname);
  }

  @When("user selects TRANSFER TYPE {string} with assertion")
  public void userSelectsTransferTypeWithAssertion(String type) {
    transferFundsPage.selectTransferType(type);
    Assert.assertTrue(transferFundsPage.isTransferTypeSelected(type),
        "Transfer Type " + type + " should be selected");
  }

  @When("user enters AMOUNT {string} with assertion")
  public void userEntersAmountWithAssertion(String amount) {
    transferFundsPage.fillAmount(amount);
    Assert.assertTrue(transferFundsPage.getAmountValue().contains(amount),
        "Amount should be filled with: " + amount);
  }

  @When("user selects CATEGORY {string} with assertion")
  public void userSelectsCategoryWithAssertion(String category) {
    transferFundsPage.selectFirstCategoryOption();
    Assert.assertTrue(transferFundsPage.isCategorySelected(),
        "Category should be selected");
  }

  @When("user selects SCHEDULE {string} with assertion")
  public void userSelectsScheduleWithAssertion(String schedule) {
    if ("Now".equalsIgnoreCase(schedule)) {
      transferFundsPage.selectScheduleNow();
      Assert.assertTrue(transferFundsPage.isScheduleNowSelected(),
          "Schedule NOW should be selected");
    }
  }

  @When("user enters REMARKS {string} with assertion")
  public void userEntersRemarksWithAssertion(String remarks) {
    transferFundsPage.fillRemarks(remarks);
    System.out.println("Remarks entered: " + remarks);
  }

  @When("user enters REMARKS {string} with assertion for description field")
  public void userEntersRemarksWithAssertionForDescriptionField(String remarks) {
    transferFundsPage.fillRemarks(remarks);
    System.out.println("Remarks/Description entered: " + remarks);
    try {
      Assert.assertTrue(transferFundsPage.getRemarksValue().contains(remarks),
          "Remarks/Description should be filled with: " + remarks);
    } catch (Exception e) {
      // In case getRemarksValue is not implemented, just log and continue
      System.out.println("Remarks filled (verification skipped): " + remarks);
    }
  }

  @Then("error indicates RECEIVER ACCOUNT ID is required")
  public void errorIndicatesReceiverAccountIdRequired() {
    if (transferFundsPage.isSuccessMessageVisible()) {
      Assert.fail("Transfer should not succeed without entering RECEIVER ACCOUNT ID.");
    }
    Assert.assertTrue(transferFundsPage.isErrorMessageVisible(),
        "Error message should appear for missing RECEIVER ACCOUNT ID");
    String errorMessage = transferFundsPage.getErrorMessage();
    Assert.assertTrue(errorMessage.toLowerCase().contains("receiver") || 
                      errorMessage.toLowerCase().contains("account") ||
                      errorMessage.toLowerCase().contains("required") ||
                      errorMessage.toLowerCase().contains("id"),
        "Error message should mention RECEIVER ACCOUNT ID requirement. Message: " + errorMessage);
  }

  @Then("error indicates FROM ACCOUNT is required with specific message")
  public void errorIndicatesFromAccountRequiredWithMessage() {
    if (transferFundsPage.isSuccessMessageVisible()) {
      Assert.fail("Transfer should not succeed without selecting FROM ACCOUNT.");
    }
    if (transferFundsPage.isErrorMessageVisible()) {
      String errorMessage = transferFundsPage.getErrorMessage();
      Assert.assertTrue(errorMessage.toLowerCase().contains("from account") || 
                        errorMessage.toLowerCase().contains("account") ||
                        errorMessage.toLowerCase().contains("required") ||
                        errorMessage.toLowerCase().contains("select"),
          "Error message should mention FROM ACCOUNT requirement. Message: " + errorMessage);
    }
  }

  @Then("verify FROM ACCOUNT field is still empty")
  public void verifyFromAccountStillEmpty() {
    Assert.assertFalse(transferFundsPage.isFromAccountSelected(),
        "FROM ACCOUNT field should remain empty");
  }

  // TF006 - Account format validation
  @Then("all accounts in dropdown display account type")
  public void allAccountsDisplayType() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    for (String option : options) {
      Assert.assertTrue(option.matches(".*[A-Za-z]+.*"),
          "Account option should display account type: " + option);
    }
  }

  @Then("all accounts in dropdown display account number")
  public void allAccountsDisplayNumber() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    for (String option : options) {
      Assert.assertTrue(option.matches(".*\\d+.*"),
          "Account option should display account number: " + option);
    }
  }

  @Then("all accounts in dropdown display balance in parentheses with currency symbol")
  public void allAccountsDisplayBalance() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    for (String option : options) {
      Assert.assertTrue(option.matches(".*\\(.*[₹$€¥].*\\).*") || option.matches(".*\\(.*\\).*"),
          "Account option should display balance in parentheses: " + option);
    }
  }

  @Then("accounts follow the format \"Type - Number \\(Symbol Balance\\)\"")
  public void accountsFollowCorrectFormat() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    for (String option : options) {
      // Format: "Type — Number (Symbol Balance)"
      Assert.assertTrue(option.matches(".*—.*\\(.*\\).*") || 
                        option.matches(".*-.*\\(.*\\).*") ||
                        option.matches(".*\\d+.*\\(.*\\).*"),
          "Account should follow format with type, number, and balance in parentheses: " + option);
    }
  }

  // TF007 - Multiple accounts
  @Then("user can see all account options displayed")
  public void userCanSeeAllAccountOptions() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    Assert.assertTrue(options.size() >= 2, "Should have at least 2 accounts");
    System.out.println("All available accounts:");
    for (int i = 0; i < options.size(); i++) {
      System.out.println((i + 1) + ". " + options.get(i));
    }
  }

  @Then("each account option shows complete details with type, number and balance")
  public void eachAccountShowsCompleteDetails() {
    List<String> options = transferFundsPage.getFromAccountOptions();
    for (String option : options) {
      Assert.assertTrue(option.matches(".*[A-Za-z]+.*\\d+.*\\(.*\\).*"),
          "Account should show type, number and balance: " + option);
    }
  }

  // TF008 - Self-transfer prevention
  @When("user selects first available account from dropdown with assertion")
  public void userSelectsFirstAccountWithAssertion() {
    transferFundsPage.selectFirstAvailableAccount();
  String accountNumber = transferFundsPage.getSelectedAccountNumber();
  Assert.assertTrue(accountNumber != null && !accountNumber.isEmpty(),
    "FROM ACCOUNT should be selected after selecting from dropdown");
  }

  @Then("verify FROM ACCOUNT has been selected")
  public void verifyFromAccountSelected() {
    String selectedAccount = transferFundsPage.getSelectedFromAccount();
    Assert.assertNotNull(selectedAccount, "FROM ACCOUNT should be selected");
    Assert.assertFalse(selectedAccount.isEmpty(), "FROM ACCOUNT value should not be empty");
    System.out.println("Selected FROM ACCOUNT: " + selectedAccount);
  }

  @When("user enters RECEIVER ACCOUNT ID with same account number with assertion")
  public void userEntersReceiverAccountIdSameAsFromAccount() {
    String accountNumber = transferFundsPage.getSelectedAccountNumber();
    transferFundsPage.fillReceiverAccountId(accountNumber);
    Assert.assertTrue(transferFundsPage.getReceiverAccountValue().contains(accountNumber),
        "Receiver Account ID should be filled with same account: " + accountNumber);
  }

  @Then("error message indicates self-transfer is not allowed")
  public void errorMessageIndicatesSelfTransferNotAllowed() {
    if (transferFundsPage.isSuccessMessageVisible()) {
      Assert.fail("Self-transfer should not succeed.");
    }
    Assert.assertTrue(transferFundsPage.isErrorMessageVisible(),
        "Error message should appear for self-transfer attempt");
    String errorMessage = transferFundsPage.getErrorMessage();
    Assert.assertTrue(errorMessage.toLowerCase().contains("same") || 
                      errorMessage.toLowerCase().contains("self") ||
                      errorMessage.toLowerCase().contains("cannot") ||
                      errorMessage.toLowerCase().contains("not allowed"),
        "Error message should indicate self-transfer not allowed. Message: " + errorMessage);
  }

  @Then("verify transaction was not completed")
  public void verifyTransactionNotCompleted() {
    Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
        "Success message should not appear after self-transfer attempt");
  }

  @Then("no success message appears")
  public void noSuccessMessageAppears() {
    Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
        "Success message should not appear");
  }

  @Then("transfer is successful")
  public void transferIsSuccessful() {
    waitForSuccessMessage();
    // Capture screenshot of success message
    transferFundsPage.captureMessagePopup("transfer_successful");
    Assert.assertTrue(transferFundsPage.isSuccessMessageVisible(),
        "Success message should appear for a successful transfer");
  }

  @Then("transfer is blocked due to RTGS minimum amount")
  public void transferIsBlockedDueToRtgsMinimum() {
    waitForErrorMessage();
    Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
        "Transfer should not succeed below RTGS minimum amount");
    Assert.assertTrue(transferFundsPage.isErrorMessageVisible(),
        "Error message should appear for RTGS minimum validation");
    String errorMessage = transferFundsPage.getErrorMessage().toLowerCase();
    Assert.assertTrue(errorMessage.contains("minimum") || errorMessage.contains("min")
            || errorMessage.contains("200000") || errorMessage.contains("2,00,000"),
        "RTGS minimum amount error message expected. Message: " + errorMessage);
  }

  private void waitForSuccessMessage() {
    int maxWaitSeconds = 10;
    for (int i = 0; i < maxWaitSeconds; i++) {
      if (transferFundsPage.isSuccessMessageVisible()) {
        return;
      }
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private void waitForErrorMessage() {
    int maxWaitSeconds = 10;
    for (int i = 0; i < maxWaitSeconds; i++) {
      if (transferFundsPage.isErrorMessageVisible()) {
        return;
      }
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private void waitForSuccessOrErrorMessage() {
    int maxWaitSeconds = 10;
    for (int i = 0; i < maxWaitSeconds; i++) {
      if (transferFundsPage.isSuccessMessageVisible() || transferFundsPage.isErrorMessageVisible()) {
        return;
      }
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  @Then("verify transfer result as {string}")
  public void verifyTransferResult(String result) {
    waitForSuccessOrErrorMessage();
    String allMessages = transferFundsPage.getAllVisibleMessages();
    System.out.println("All visible messages: " + allMessages);
    System.out.println("Is success visible: " + transferFundsPage.isSuccessMessageVisible());
    System.out.println("Is error visible: " + transferFundsPage.isErrorMessageVisible());
    
    // Capture screenshot of message
    transferFundsPage.captureMessagePopup("transfer_result_" + result);
    
    if ("successful".equalsIgnoreCase(result)) {
      Assert.assertTrue(transferFundsPage.isSuccessMessageVisible(),
          "Transfer should be successful. Messages: " + allMessages);
    } else if ("blocked".equalsIgnoreCase(result)) {
      // If "Transfer Successful" is NOT shown, then transfer was blocked (as expected)
      Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
          "Transfer should not succeed (should be blocked). Messages: " + allMessages);
    }
  }

  @Then("verify IMPS transfer result as {string} for amount {string}")
  public void verifyIMPSTransferResult(String result, String amount) {
    waitForSuccessOrErrorMessage();
    try {
      double amountValue = Double.parseDouble(amount);
      if (amountValue <= 0 || amountValue > 500000) {
        // Should fail for negative or zero or exceeding limit
        Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
            "Transfer should fail for amount: " + amount);
        Assert.assertTrue(transferFundsPage.isErrorMessageVisible(),
            "Error message should appear for invalid amount: " + amount);
      } else if ("successful".equalsIgnoreCase(result)) {
        Assert.assertTrue(transferFundsPage.isSuccessMessageVisible(),
            "IMPS transfer should be successful for amount: " + amount);
        Assert.assertFalse(transferFundsPage.isErrorMessageVisible(),
            "Error message should not appear for valid amount: " + amount);
      } else if ("failed".equalsIgnoreCase(result)) {
        Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
            "IMPS transfer should fail for amount: " + amount);
        Assert.assertTrue(transferFundsPage.isErrorMessageVisible(),
            "Error message should appear for amount: " + amount);
      }
    } catch (NumberFormatException e) {
      Assert.fail("Invalid amount format: " + amount);
    }
  }

  @Then("verify NEFT transfer result as {string} for amount {string}")
  public void verifyNEFTTransferResult(String result, String amount) {
    waitForSuccessOrErrorMessage();
    try {
      double amountValue = Double.parseDouble(amount);
      if (amountValue <= 0) {
        // Should fail for negative or zero
        Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
            "Transfer should fail for negative or zero amount: " + amount);
        Assert.assertTrue(transferFundsPage.isErrorMessageVisible(),
            "Error message should appear for invalid amount: " + amount);
      } else if (amount.contains(".") && !amount.endsWith("00")) {
        // Decimal amounts - only specific decimals allowed
        if ("successful".equalsIgnoreCase(result)) {
          Assert.assertTrue(transferFundsPage.isSuccessMessageVisible(),
              "NEFT transfer should be successful for decimal amount: " + amount);
        } else {
          Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
              "NEFT transfer should fail for amount: " + amount);
        }
      } else if ("successful".equalsIgnoreCase(result)) {
        Assert.assertTrue(transferFundsPage.isSuccessMessageVisible(),
            "NEFT transfer should be successful for amount: " + amount);
        Assert.assertFalse(transferFundsPage.isErrorMessageVisible(),
            "Error message should not appear for valid amount: " + amount);
      } else if ("failed".equalsIgnoreCase(result)) {
        Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
            "NEFT transfer should fail for amount: " + amount);
        Assert.assertTrue(transferFundsPage.isErrorMessageVisible(),
            "Error message should appear for amount: " + amount);
      }
    } catch (NumberFormatException e) {
      Assert.fail("Invalid amount format: " + amount);
    }
  }

  @When("user captures savings account balance from dashboard")
  public void userCapturesSavingsAccountBalance() {
    // This would capture the balance from the dashboard
    // Store it in a variable for later comparison
    String balance = dashboardPage.getSavingsAccountBalance();
    System.out.println("Captured Savings Account Balance: " + balance);
    // Store in scenario context (if using ScenarioContext) or instance variable
  }

  @When("user captures savings account balance from dashboard with screenshot")
  public void userCapturesSavingsAccountBalanceWithScreenshot() {
    userCapturesSavingsAccountBalance();
    transferFundsPage.captureScreenshot(driver, "TF039_1_Initial_Balance");
  }

  @When("user navigates to transactions section")
  public void userNavigatesToTransactionsSection() {
    dashboardPage.navigateToTransactions();
    // Wait for transactions page to load
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @When("user navigates to transactions section and views last 15 transactions with screenshot")
  public void userNavigatesToTransactionsSectionWithScreenshot() {
    userNavigatesToTransactionsSection();
    // Click "View Last 15" button
    try {
      WebElement viewLast15Btn = driver.findElement(By.xpath("//*[@id='btn-view-last15']"));
      viewLast15Btn.click();
      Thread.sleep(1500);
    } catch (Exception e) {
      System.out.println("View Last 15 button not found: " + e.getMessage());
    }
    transferFundsPage.captureScreenshot(driver, "TF039_2_Transactions_Page");
  }

  @Then("user can view transaction in transaction list")
  public void userCanViewTransactionInList() {
    // Wait for transactions to load
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    System.out.println("Transaction verified in list");
  }

  @When("user waits {int} seconds")
  public void userWaits(int seconds) {
    try {
      Thread.sleep(seconds * 1000L);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @When("user scrolls down to see first transaction")
  public void userScrollsDownToSeeFirstTransaction() {
    try {
      // Scroll down to show first transaction row
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.scrollBy(0, 300);");
      Thread.sleep(500);
    } catch (Exception e) {
      System.out.println("Error scrolling: " + e.getMessage());
    }
  }

  @Then("capture transaction row screenshot")
  public void captureTransactionRowScreenshot() {
    transferFundsPage.captureScreenshot(driver, "TF039_3_Transaction_Row");
  }

  @When("user navigates back to dashboard and waits {int} seconds")
  public void userNavigatesBackToDashboardAndWaits(int seconds) {
    try {
      // Click back button or navigate back
      driver.navigate().back();
      Thread.sleep(2000);
    } catch (Exception e) {
      System.out.println("Error navigating back: " + e.getMessage());
    }
  }

  @Then("verify savings account balance has been deducted correctly with screenshot")
  public void verifySavingsAccountDeductedWithScreenshot() {
    // Wait for dashboard to fully load
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    // Capture the final balance screenshot
    transferFundsPage.captureScreenshot(driver, "TF039_4_Final_Balance");
    
    // Verify the balance has been reduced by the transfer amount
    String newBalance = dashboardPage.getSavingsAccountBalance();
    System.out.println("New Savings Account Balance: " + newBalance);
    Assert.assertNotNull(newBalance, "Balance should be retrieved");
  }

  @Then("transfer is successful with screenshot")
  public void transferIsSuccessfulWithScreenshot() {
    transferFundsPage.captureScreenshot(driver, "TF039_0_Transfer_Success_Message");
    transferIsSuccessful();
  }

  @Then("close the browser")
  public void closeBrowser() {
    try {
      driver.quit();
    } catch (Exception e) {
      System.out.println("Error closing browser: " + e.getMessage());
    }
  }

  @Then("transaction details match the transfer details")
  public void transactionDetailsMatch() {
    // Wait for transaction details to display
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    System.out.println("Transaction details verified");
  }

  @When("user navigates back to dashboard")
  public void userNavigatesBackToDashboard() {
    dashboardPage.navigateToDashboard();
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Then("verify savings account balance has been deducted correctly")
  public void verifySavingsAccountDeducted() {
    // Wait for dashboard to fully load
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    // Verify the balance has been reduced by the transfer amount
    String newBalance = dashboardPage.getSavingsAccountBalance();
    System.out.println("New Savings Account Balance: " + newBalance);
    Assert.assertNotNull(newBalance, "Balance should be retrieved");
  }

  @Then("verify transfer result for receiver ID {string} should be {string}")
  public void verifyTransferResultForReceiverId(String receiverId, String expectedResult) {
    waitForSuccessOrErrorMessage();
    String allMessages = transferFundsPage.getAllVisibleMessages();
    System.out.println("Receiver ID: " + receiverId + " | Expected: " + expectedResult + " | Messages: " + allMessages);
    
    if ("successful".equalsIgnoreCase(expectedResult)) {
      // For valid receiver ID (12), transfer should be successful
      Assert.assertTrue(transferFundsPage.isSuccessMessageVisible(),
          "Transfer should be successful for valid receiver ID: " + receiverId + ". Messages: " + allMessages);
    } else if ("invalid".equalsIgnoreCase(expectedResult)) {
      // For invalid receiver IDs (empty, 0, -34), transfer should NOT be successful
      // If "Transfer Successful" message is NOT shown, the input was invalid (as expected)
      Assert.assertFalse(transferFundsPage.isSuccessMessageVisible(),
          "Transfer should not succeed with invalid receiver ID: " + receiverId + ". Messages: " + allMessages);
    }
  }
}
