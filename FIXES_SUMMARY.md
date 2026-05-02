# BugBank Test Suite - Fixes Summary

## Issues Fixed

### 1. **Login Button Not Being Clicked** 
   - **Issue**: The initial "Login" button (ID: `btn-login`) on the URL page was not being clicked properly
   - **Root Cause**: Element was located but not being clicked reliably due to missing clickable wait and improper scroll handling
   - **Fix**: Updated `LoginPage.clickLoginButton()` method to:
     - Use explicit ID `btn-login` with `elementToBeClickable` wait
     - Properly scroll element into view before clicking
     - Use JavaScript executor for reliable click action
     - Throw clear error message if button cannot be clicked

### 2. **Sign-In Button Not Being Clicked**
   - **Issue**: After entering credentials, the "Sign-In" button was not being clicked properly
   - **Root Cause**: Method was trying multiple fallback locators instead of using specific button ID `btn-login-submit`
   - **Fix**: Updated `LoginPage.clickSignInButton()` method to:
     - Directly use ID `btn-login-submit` 
     - Wait for element to be clickable
     - Scroll element into view
     - Use JavaScript executor for reliable click
     - Provide clear error message if button cannot be clicked

### 3. **Amount Being Entered in Receiver Account ID Field**
   - **Issue**: When filling the amount field (5000), the value was being entered in the "Receiver Account ID" field instead
   - **Root Cause**: The original `amountInput` locator was too generic: `By.xpath("//input[contains(@placeholder, '5000')]")` - this could match any input with "5000" placeholder
   - **Fix**: Updated `TransferFundsPage.java` line 23-26 to use a more specific locator:
     ```java
     private final By amountInput = By.xpath("//label[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'amount')]/following::input[1]"
         + " | //input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'amount')]"
         + " | //input[contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'amount')]"
         + " | //input[@type='number'][position()=2]");
     ```
   - Enhanced `fillAmount()` method to:
     - First try primary locator with label lookup
     - Use fallback locators specifically for "amount" field
     - As last resort, select the 2nd numeric input (first is receiver account)
     - Clear field before entering value
     - Add proper wait and scroll handling

## Code Changes

### File: `LoginPage.java`

#### 1. `assertLoginPageLoaded()` - Line 60-67
- Now directly uses ID `btn-login`
- More reliable page load verification

#### 2. `clickLoginButton()` - Line 69-85
**Before**: Used ElementFinder with multiple XPath fallbacks
**After**: 
```java
try {
  WebElement loginButton = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_TIMEOUT_SECONDS))
      .until(ExpectedConditions.elementToBeClickable(By.id("btn-login")));
  // Scroll and click with JS executor
} catch (Exception e) {
  throw new RuntimeException("Failed to click login button with ID 'btn-login': " + e.getMessage(), e);
}
```

#### 3. `login()` - Line 20-58
- Updated to use `btn-login-submit` for sign-in button click
- Properly waits for element to be clickable
- Clear error handling

#### 4. `clickSignInButton()` - Line 119-135
**Before**: Used multiple fallback locators with complex XPath searches
**After**:
```java
try {
  WebElement signInButton = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.LONG_WAIT_SECONDS))
      .until(ExpectedConditions.elementToBeClickable(By.id("btn-login-submit")));
  // Scroll and click with JS executor
} catch (Exception e) {
  throw new RuntimeException("Failed to click sign-in button with ID 'btn-login-submit': " + e.getMessage(), e);
}
```

#### 5. `ensureLoginModalOpen()` - Line 137-165
- Simplified modal opening logic
- Uses direct ID lookup for `btn-login`
- Cleaner error handling

### File: `TransferFundsPage.java`

#### 1. `amountInput` Locator - Line 23-26
**Before**:
```java
private final By amountInput = inputByLabelOrPlaceholder("amount", "5000");
```

**After**:
```java
private final By amountInput = By.xpath("//label[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'amount')]/following::input[1]"
    + " | //input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'amount')]"
    + " | //input[contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'amount')]"
    + " | //input[@type='number'][position()=2]");
```

#### 2. `fillAmount()` Method - Line 300-344
- Enhanced to specifically target amount field
- Uses position-based selector as last resort (position()=2 for second numeric input)
- Better fallback logic to prevent confusion with receiver account field
- Proper scroll and clear before entering value

## Credentials Configuration

The credentials are properly configured in `TransferFundsSteps.java` (line 45-46):
```java
String email = System.getProperty("test.email", "prtwo@gmail.com");
String password = System.getProperty("test.password", "Pleasework@05");
```

These credentials are used whenever:
- `userEntersValidCredentials()` is called
- Login flow is executed in any test case

## Button IDs Used

| Button | ID | Location |
|--------|---|----------|
| Initial Login Button | `btn-login` | URL page |
| Sign-In Button | `btn-login-submit` | Login modal after entering credentials |

## Test Cases Affected

All test cases in the suite will benefit from these fixes:
- **TF-003**: Initial page layout
- **TF-004**: FROM ACCOUNT dropdown population
- **TF-005**: FROM ACCOUNT required validation
- **TF-006**: Account format validation
- **TF-007**: Multiple accounts
- **TF-008**: Self-transfer prevention

## How to Run Tests

### Using Maven from Command Line:
```bash
cd C:\Users\Administrator\Documents\ccc\bugbank
mvn clean test
```

### Using Maven to Run Specific Test Class:
```bash
mvn clean test -Dtest=TF003Runner
```

### Using Maven to Run All Tests with Detailed Output:
```bash
mvn clean test -X
```

### Using TestNG directly (if Maven not available):
```bash
java -cp target/classes:target/test-classes org.testng.TestNG src/test/resources/testng.xml
```

## Verification

After running tests, check:
1. All test cases complete successfully
2. Login buttons are properly clicked
3. Amount field receives correct value (not in receiver account field)
4. Transfer forms are filled correctly
5. No "element not clickable" errors

## Files Modified

1. `src/test/java/com/bugbank/pages/LoginPage.java` - Fixed button clicking logic
2. `src/test/java/com/bugbank/pages/TransferFundsPage.java` - Fixed amount field locator and filling logic

## No Changes Required

- `TransferFundsSteps.java` - Already has correct credentials configured
- Feature files - No changes needed
- Test configuration - No changes needed
