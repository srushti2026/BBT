# Quick Reference: Screenshot Embedding

## View Report with Embedded Screenshots

After running tests with `mvn clean test`, open:
```
target/cucumber-reports/html/index.html
```

Screenshots will be visible inline in the HTML report.

---

## Automatic Screenshot Capture (On Failure)

✅ **Automatic** - No code changes needed!

When a test fails:
1. `ScreenshotHooks.@After` is triggered
2. Screenshot is captured automatically
3. Embedded in Cucumber JSON report
4. Visible in generated HTML report

---

## Manual Screenshot Attachment (In Test Steps)

Use the helper method to attach screenshots at specific steps:

```java
@Then("some test step")
public void someTestStep() {
  // Take screenshot and save to file
  transferFundsPage.captureScreenshot(driver, "MyScreenshot");
  
  // Embed in HTML report
  attachScreenshotToReport("My Step Description");
  
  // Make assertions
  Assert.assertTrue(someCondition);
}
```

---

## Example: TF-039 Test

```java
@Then("transfer is successful with screenshot")
public void transferIsSuccessfulWithScreenshot() {
  // Save to file system
  transferFundsPage.captureScreenshot(driver, "TF039_0_Transfer_Success_Message");
  
  // Embed in report (visible in HTML)
  attachScreenshotToReport("Transfer Success Message");
  
  // Continue with test
  transferIsSuccessful();
}
```

Result:
- ✅ Screenshot saved to: `target/screenshots/TF039_0_Transfer_Success_Message_*.png`
- ✅ Screenshot embedded in: `target/cucumber-reports/html/index.html`

---

## File Locations

| Type | Location |
|------|----------|
| Physical Screenshots | `target/screenshots/` |
| JSON Reports (with embedded images) | `target/cucumber/` |
| HTML Report (view this!) | `target/cucumber-reports/html/index.html` |

---

## How to Add Screenshots to New Tests

### Option 1: Automatic (On Failure Only)
Nothing needed - already works automatically via `ScreenshotHooks`

### Option 2: Manual (At Specific Steps)
```java
@Then("your step description")
public void yourStep() {
  // Your test code
  
  // Add this line to embed screenshot in report
  attachScreenshotToReport("Step Description");
}
```

---

## Key Files

| File | Purpose |
|------|---------|
| `src/.../hooks/ScreenshotHooks.java` | Auto-captures on failure |
| `src/.../steps/TransferFundsSteps.java` | Manual attach support |
| `src/.../util/ScreenshotUtil.java` | Screenshot utilities |
| `src/.../pages/TransferFundsPage.java` | Page object screenshots |
| `SCREENSHOT_EMBEDDING_GUIDE.md` | Full documentation |

---

## Run Tests & View Report

```bash
# 1. Run tests with screenshot embedding
mvn clean test

# 2. Open HTML report in browser
target/cucumber-reports/html/index.html

# 3. Click on test scenarios to see:
#    - Test steps
#    - Inline embedded screenshots
#    - Pass/Fail status
```

---

## Benefits

✅ Screenshots visible in HTML report (no file browsing needed)
✅ Automatic on failure (no manual steps needed)
✅ Physical files retained for archival
✅ Self-contained reports (can share anywhere)
✅ Easy debugging with visual evidence

---

## Troubleshooting

**Q: Screenshots not showing in HTML report?**
- Verify `ScreenshotHooks.java` exists
- Run `mvn clean test` (not just `mvn test`)
- Check `target/cucumber-reports/html/index.html` exists

**Q: How to add more screenshots?**
- Use `attachScreenshotToReport("Description")` at any step

**Q: Where are physical screenshot files?**
- `target/screenshots/` folder (backup copies)

**Q: Can I share the HTML report?**
- Yes! All images are embedded - report is self-contained

---

## Code Snippet Library

### Capture on Success
```java
@Then("transfer is successful with screenshot")
public void transferIsSuccessfulWithScreenshot() {
  transferFundsPage.captureScreenshot(driver, "SuccessMessage");
  attachScreenshotToReport("Transfer Success");
  // assertions...
}
```

### Capture on Error
```java
@Then("error message appears")
public void errorMessageAppears() {
  String errorMsg = transferFundsPage.getAllVisibleMessages();
  attachScreenshotToReport("Error State");
  Assert.assertTrue(transferFundsPage.isErrorMessageVisible());
}
```

### Capture Specific Condition
```java
@Then("verify account balance updated")
public void verifyBalance() {
  String balance = dashboardPage.getSavingsAccountBalance();
  attachScreenshotToReport("Balance Verification - " + balance);
  Assert.assertNotNull(balance);
}
```

---

## Next Run

```bash
cd c:\Users\Administrator\Documents\Git\testing\BBT
mvn clean test
```

Then open: `target/cucumber-reports/html/index.html`

All screenshots will be embedded and visible! 🎉
