# Test Case Modifications Summary

## Changes Made to BugBank Test Automation Framework

### 1. **LoginPage.java** - Updated with Proper XPaths
- Updated all XPaths to use the specification-provided XPaths
- Implemented dynamic waits (WebDriverWait) instead of static waits
- All methods use explicit waits for better reliability
- XPaths used:
  - Login button: `//*[@id="btn-login"]`
  - Email input: `//*[@id="email"]`
  - Password input: `//*[@id="password"]`
  - Sign-in button: `//*[@id="btn-login-submit"]`

### 2. **TransferFundsPage.java** - Major Updates
- Updated all XPaths to match specification:
  - From Account Dropdown: `//*[@id="fromAccountSelect"]`
  - Receiver Account ID: `//*[@id="toAccountId"]`
  - Beneficiary Nickname: `//*[@id="transferNickname"]`
  - Amount: `//*[@id="transferAmount"]`
  - Category: `//*[@id="category"]`
  - Schedule Now: `//*[@id="transfer"]/div[2]/div[1]/div[7]/div/label[1]/input`
  - Submit Button: `//*[@id="btn-transfer-submit"]`
  - **Remarks/Description: `//*[@id="transferRemarks"]`** (NEW)

- Implemented dynamic waits throughout
- Added ScreenshotUtil for capturing screenshots on success/failure
- Enhanced account number extraction logic
- Added screenshot capture methods:
  - `captureSuccessScreenshot(String testName)`
  - `captureFailureScreenshot(String testName)`
  - `captureMessageScreenshot(String testName)`
  - `captureAndGetMessage(String testName)`

### 3. **TransferFundsSteps.java** - New Step Definitions
- Added `userEntersRemarksWithAssertionForDescriptionField()` step
- Added `errorIndicatesReceiverAccountIdRequired()` step
- Updated NEFT transfer result verification for false assertions
- Screenshots now captured automatically for validation messages

### 4. **Feature Files - Updated**

#### TF-023.feature (RTGS minimum amount validation)
- Receiver Account ID: "12"
- Beneficiary Nickname: "John Doe"
- Remarks: "Rent" (in description field)

#### TF-024.feature (RTGS amount below minimum)
- Same field values as TF-023
- Uses "for description field" variant of remarks step

#### TF-025.feature (RTGS amount above minimum)
- Same field values as TF-023

#### TF-005.feature (NEW - RECEIVER ACCOUNT ID Required Validation)
- **Changed from**: FROM ACCOUNT required validation
- **Changed to**: RECEIVER ACCOUNT ID required validation
- Selects a FROM ACCOUNT but leaves RECEIVER ACCOUNT ID empty
- Validates that error appears when RECEIVER ACCOUNT ID is missing
- Validates that transfer fails without receiver account

#### TF-008.feature (Self-Transfer Prevention)
- **Updated step**: Now uses "user enters RECEIVER ACCOUNT ID with same account number with assertion"
- The account ID is extracted from the selected FROM ACCOUNT
- This validates that self-transfers (using same account) are prevented
- Account number extraction improved to handle format: "Account Type - Account Number (Balance)"

#### TF-0003567.feature (NEW - Combined Test Case)
- Combines test scenarios from TF-003, TF-004, TF-006, and TF-007
- Validates:
  1. At least one account is visible in dropdown
  2. All accounts display account type, number, and balance
  3. Account format validation
  4. Multiple accounts are listed
  5. Each account shows complete details
  6. Account can be selected successfully
  7. Transfer completes successfully with all valid data

### 5. **Utility Classes**

#### ScreenshotUtil.java (NEW)
- Captures screenshots on test success/failure
- Saves to `target/screenshots/` directory
- Generates timestamped filenames
- Integrates with test reports

### 6. **Test Runners**

#### TF0003567Runner.java (NEW)
- Runner for the combined TF-0003567 test case
- Generates HTML and JSON reports
- Output: `target/cucumber/TF-0003567.html` and `target/cucumber/TF-0003567.json`

### 7. **Key Improvements**

**Dynamic Waits vs Static Waits:**
- All methods now use WebDriverWait with ExpectedConditions
- Timeout: 30 seconds (configurable via TestConfig.WAIT_TIMEOUT_SECONDS)
- More reliable element detection

**Field Value Accuracy:**
- Receiver Account ID: Always "12" (or user-specified)
- Beneficiary Nickname: "John Doe" (consistent across all test cases)
- Remarks/Description: "Rent" (entered in `//*[@id="transferRemarks"]`)

**Account Number Extraction (TF-008):**
- Extracts only the account ID from selected account
- Ignores balance and account type
- Handles formats: "Type - Number (Balance)" properly

**False Assertions for Invalid Data:**
- Applied to test cases: TF-024, TF-025, TF-026, TF-027, TF-028, TF-02123, TF-02345, TF-02678
- When invalid amounts (≤0 or exceeding limits) are tested, failure is expected and test passes
- Screenshots capture the error message for verification

### 8. **Maven Command for Running Tests**

```bash
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test
```

This command:
- Compiles test code
- Runs all test cases in `src/test/resources/testng.xml`
- Generates test reports in `target/surefire-reports/`
- Generates Cucumber reports in `target/cucumber/`
- Captures screenshots in `target/screenshots/`

### 9. **Test Case Mapping**

| Test Case | Purpose | Status |
|-----------|---------|--------|
| TF-003 | Minimal account validation | Combined in TF-0003567 |
| TF-004 | Multiple accounts validation | Combined in TF-0003567 |
| TF-005 | Receiver Account ID required | UPDATED |
| TF-006 | Account format validation | Combined in TF-0003567 |
| TF-007 | Multiple accounts detailed | Combined in TF-0003567 |
| TF-008 | Self-transfer prevention | UPDATED |
| TF-0003567 | Combined validation | NEW |
| TF-023, 024, 025 | RTGS amount validation | UPDATED |
| TF-026, 028 | NEFT validation | UPDATED |
| TF-02123, 02345, 02678 | Boundary testing | UPDATED |

### 10. **Screenshots and Reports**

- Screenshots captured at critical points (message displays)
- Location: `target/screenshots/`
- Format: `{testName}_{timestamp}.png`
- Integrated into Cucumber HTML reports
- Surefire reports in `target/surefire-reports/`

