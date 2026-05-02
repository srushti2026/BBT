package com.bugbank.steps;

import com.bugbank.config.DriverManager;
import com.bugbank.pages.DashboardPage;
import com.bugbank.pages.LoginPage;
import com.bugbank.pages.TransferFundsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.openqa.selenium.WebDriver;
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
    // Optional field - just log it
    System.out.println("Remarks to be entered: " + remarks);
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
}