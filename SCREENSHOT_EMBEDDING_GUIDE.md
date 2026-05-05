# Screenshot Embedding in Test Reports Guide

## Overview
Screenshots are now automatically embedded directly in the Cucumber HTML test reports. They will be visible in the HTML report without requiring manual linking to external files.

## How It Works

### 1. **Automatic Screenshot Capture on Failures**
The `ScreenshotHooks` class automatically captures a screenshot when a test fails and embeds it in the report.

**Location**: `src/test/java/com/bugbank/hooks/ScreenshotHooks.java`

```java
@After
public void tearDown(Scenario scenario) {
  if (scenario.isFailed()) {
    attachScreenshotToReport(scenario, "FAILED");
  }
}
```

### 2. **Manual Screenshot Attachment in Test Steps**
You can also manually attach screenshots at specific steps using the helper method in `TransferFundsSteps`.

```java
@Then("transfer is successful with screenshot")
public void transferIsSuccessfulWithScreenshot() {
  transferFundsPage.captureScreenshot(driver, "TF039_0_Transfer_Success_Message");
  // Attach screenshot to Cucumber report
  attachScreenshotToReport("Transfer Success Message");
  transferIsSuccessful();
}
```

### 3. **Dual Storage**
Screenshots are stored in **two locations**:

1. **File System**: `target/screenshots/` - Physical PNG files for archival
2. **HTML Report**: Embedded as base64 images in the Cucumber HTML report

### 4. **Enhanced ScreenshotUtil**
The `ScreenshotUtil` class now has additional methods:

```java
// Original - saves to file system
public static String captureScreenshot(WebDriver driver, String testName)

// New - returns base64 encoded image for report embedding
public static String captureScreenshotAsBase64(WebDriver driver, String testName)
```

## Key Components

### ScreenshotHooks.java
- Intercepts test execution start and end
- Captures screenshot on test failure
- Embeds screenshot directly in the JSON report
- Scenario object is injected by Cucumber framework

### TransferFundsSteps.java
- Added `scenario` field to store Cucumber Scenario object
- Added `attachScreenshotToReport()` helper method
- All screenshot steps now attach to the report

### TransferFundsPage.java
- Added `captureScreenshotAsBytes()` method
- Returns screenshot as byte array for embedding

### ScreenshotUtil.java
- Added `captureScreenshotAsBase64()` method
- Returns base64 encoded screenshot string

## Report Structure

After test execution, you'll find:

```
target/
├── cucumber/                          # Cucumber JSON reports (with embedded screenshots)
│   ├── IMPS.json
│   ├── NEFT.json
│   ├── RTGS.json
│   ├── RecieverID.json
│   ├── SelfTransfer.json
│   ├── PageFeatures.json
│   └── TF-039.json
├── cucumber-reports/html/            # Generated HTML report (with embedded screenshots)
│   ├── index.html                    # Main report entry point
│   ├── report.js
│   └── ...
└── screenshots/                       # Physical PNG files (backup)
    ├── TF039_0_Transfer_Success_Message_*.png
    ├── TF039_1_Initial_Balance_*.png
    └── ...
```

## Viewing Screenshots in Reports

### HTML Report
1. Navigate to `target/cucumber-reports/html/index.html`
2. Click on any test scenario
3. Screenshots will be displayed inline within the report
4. Screenshots appear at the bottom of each scenario step

### Features with Screenshots

#### TF-039 (Complete Transaction)
- **Step**: `transfer is successful with screenshot`
- **Screenshot**: Captured of the "Transfer Successful" message
- **Location**: Embedded in `TF-039.json` and visible in HTML report

#### RecieverID Tests
- **Step**: Failure screenshots automatically captured
- **Screenshots**: Shown when receiver ID validation fails
- **Location**: Embedded in `RecieverID.json`

#### RTGS/IMPS/NEFT Tests
- **Step**: Failure screenshots automatically captured
- **Screenshots**: Shown when transfer fails
- **Location**: Embedded in respective JSON reports

## Configuration

### Maven Plugins Required
✅ Already configured in `pom.xml`:

```xml
<!-- Maven Cucumber Reporting Plugin -->
<plugin>
  <groupId>net.masterthought</groupId>
  <artifactId>maven-cucumber-reporting</artifactId>
  <version>5.8.1</version>
  <!-- Automatically detects embedded images in JSON -->
</plugin>
```

### Screenshot Dimensions
- **Resolution**: Depends on browser resolution (maximized by default)
- **Format**: PNG
- **Encoding**: Base64 in reports, raw files in `target/screenshots/`

## Benefits

✅ **Visibility**: Screenshots visible without external file navigation
✅ **Archival**: Physical files retained in `target/screenshots/`
✅ **Debugging**: Quick identification of failures with visual evidence
✅ **Documentation**: Screenshots serve as test case documentation
✅ **Portability**: Embedded images make reports self-contained
✅ **Automation**: Automatic capture on failures requires no manual steps

## Example Report Display

When viewing the HTML report, each failed test scenario will show:

```
Scenario: transfer is successful with screenshot
├── Given: user navigates to the application URL
├── And: login page is loaded with logo and login button visible
├── When: user clicks on login button
├── Then: login popup with email and password fields appears
├── When: user enters valid credentials
├── And: clicks the login button
│
├── SCREENSHOT: [Embedded image showing the actual screen state]
│
├── Then: transfer is successful with screenshot
└── PASSED ✓
```

## Troubleshooting

### Screenshots Not Appearing in Report
1. Verify `ScreenshotHooks.java` is in `src/test/java/com/bugbank/hooks/`
2. Ensure Scenario object is injected in hooks
3. Check that Cucumber plugin version is 5.8.1 or higher
4. Run: `mvn clean test`

### Large Report Files
- Embedded images increase JSON report size
- Physical files in `target/screenshots/` serve as backup
- For CI/CD, consider archiving screenshots separately

### Screenshot Quality
- Maximize browser window for full screenshot capture
- High DPI displays may produce large file sizes
- Use headless mode for consistent screenshots

## Running Tests with Screenshot Embedding

```bash
# Full test execution with screenshot embedding
mvn clean test

# View generated report
target/cucumber-reports/html/index.html
```

## Advanced Customization

### Custom Screenshot Points
To add screenshots at additional test steps:

```java
@Then("some specific step")
public void someStep() {
  // Do something
  
  // Manually attach screenshot
  attachScreenshotToReport("Step Description");
  
  // Continue with assertions
  Assert.assertTrue(someCondition);
}
```

### Screenshot Naming Convention
Current naming: `{TestName}_{StepName}_{Timestamp}.png`

Example: `TF039_0_Transfer_Success_Message_20260505_205041_705.png`

### Batch Screenshot Operations
Use the helper method for consistency:

```java
// In TransferFundsSteps.java
private void attachScreenshotToReport(String stepName) {
  if (scenario != null && driver != null) {
    byte[] screenshot = ((org.openqa.selenium.TakesScreenshot) driver)
        .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
    scenario.attach(screenshot, "image/png", stepName);
  }
}
```

## Summary

✅ Screenshots are automatically captured on test failures
✅ Manual screenshots can be attached at key test steps
✅ Screenshots embedded in HTML reports for easy viewing
✅ Physical files retained in `target/screenshots/` for archival
✅ No manual report configuration required
✅ Reports are self-contained and portable
