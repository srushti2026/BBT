# What You'll See in the HTML Report

## Report Structure

After running `mvn clean test`, open `target/cucumber-reports/html/index.html` in your browser.

### Main Report Page

```
┌─────────────────────────────────────────────────────────────┐
│                  BugBank Test Report                        │
│                                                              │
│  Status Summary:                                            │
│  ├─ Total Scenarios: 17                                    │
│  ├─ Passed: 5 ✓                                            │
│  ├─ Failed: 12 ✗                                           │
│  └─ Duration: 11m 25s                                      │
│                                                              │
│  Test Results:                                             │
│  ├─ [PASSED] PageFeatures                                  │
│  ├─ [FAILED] RecieverID - Scenario 1 (Empty ID)            │
│  ├─ [FAILED] RecieverID - Scenario 2 (ID: 0)              │
│  ├─ [FAILED] RTGS - ₹200,000 Transfer                     │
│  ├─ [PASSED] TF-039 - Complete Transaction                │
│  └─ ... (more scenarios)                                   │
└─────────────────────────────────────────────────────────────┘
```

---

## Detailed Scenario View (Failed Test with Screenshot)

When you click on a **FAILED** scenario:

```
┌─────────────────────────────────────────────────────────────┐
│ Scenario: verify transfer result for receiver ID "" should   │
│           be "invalid"                                       │
│ Status: ✗ FAILED                                            │
└─────────────────────────────────────────────────────────────┘

Steps:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

✓ Given: user navigates to the application URL
  └─ Duration: 2.34s

✓ And: login page is loaded with logo and login button visible
  └─ Duration: 1.12s

✓ When: user clicks on login button
  └─ Duration: 0.89s

✓ Then: login popup with email and password fields appears
  └─ Duration: 1.45s

✓ When: user enters valid credentials
  └─ Duration: 0.56s

✓ And: clicks the login button
  └─ Duration: 2.34s

✓ And: clicks on Transfer Funds menu
  └─ Duration: 1.23s

✓ Then: transfer page is loaded
  └─ Duration: 1.45s

┌──────────────────────────────────────────────────────────────┐
│ EMBEDDED SCREENSHOT #1: "Error on Blank Receiver ID"        │
│ ┌────────────────────────────────────────────────────────┐  │
│ │                                                        │  │
│ │  [Screenshot Image - Actual browser screen captured]  │  │
│ │                                                        │  │
│ │  Showing: Error message displayed                      │  │
│ │           Transfer form with validation error         │  │
│ │           Timestamp: 2026-05-05 21:50:41              │  │
│ │                                                        │  │
│ └────────────────────────────────────────────────────────┘  │
│ Size: 1920x1080 pixels | Format: PNG | Embedded in HTML     │
└──────────────────────────────────────────────────────────────┘

✗ Then: verify transfer result for receiver ID "" should be "invalid"
  └─ Duration: 0.45s
  └─ Error: Transfer should not succeed with invalid receiver ID: .
     Messages: ✅ Transfer Successful ✕ | ✅ | ✕ |
     expected [false] but found [true]

┌──────────────────────────────────────────────────────────────┐
│ EMBEDDED SCREENSHOT #2: "Failure State"                     │
│ ┌────────────────────────────────────────────────────────┐  │
│ │                                                        │  │
│ │  [Screenshot Image - Screen at moment of failure]     │  │
│ │                                                        │  │
│ │  Showing: Unexpected "Transfer Successful" message    │  │
│ │           When blank receiver ID was used             │  │
│ │           Timestamp: 2026-05-05 21:50:42              │  │
│ │                                                        │  │
│ └────────────────────────────────────────────────────────┘  │
│ Size: 1920x1080 pixels | Format: PNG | Embedded in HTML     │
│ Captured: Automatically on test failure by ScreenshotHooks  │
└──────────────────────────────────────────────────────────────┘
```

---

## Successful Test with Manual Screenshot

When you click on a **PASSED** scenario with screenshots:

```
┌─────────────────────────────────────────────────────────────┐
│ Scenario: Full transaction flow with balance verification   │
│ Status: ✓ PASSED                                           │
└─────────────────────────────────────────────────────────────┘

Steps:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

✓ Given: user navigates to the application URL
✓ And: login page is loaded with logo and login button visible
✓ When: user clicks on login button
✓ Then: login popup with email and password fields appears
✓ When: user enters valid credentials
✓ And: clicks the login button

✓ Then: user capture's savings account balance with screenshot
  └─ Duration: 0.78s

┌──────────────────────────────────────────────────────────────┐
│ EMBEDDED SCREENSHOT #1: "Initial Balance"                   │
│ ┌────────────────────────────────────────────────────────┐  │
│ │                                                        │  │
│ │  [Screenshot showing dashboard with account balance]  │  │
│ │                                                        │  │
│ │  Account: SAVINGS 9282244                             │  │
│ │  Balance: ₹4,996.80                                   │  │
│ │                                                        │  │
│ └────────────────────────────────────────────────────────┘  │
│ Captured at: 2026-05-05 21:07:12 | Size: 1920x1080         │
└──────────────────────────────────────────────────────────────┘

✓ And: clicks on Transfer Funds menu
✓ Then: transfer page is loaded
✓ When: selects "IMPS" as transfer type
✓ And: enters "12" as receiver account ID
✓ And: enters "John Doe" as beneficiary name
✓ And: enters "100" as transfer amount
✓ And: enters "Rent" as remarks
✓ When: clicks the send money button
  └─ Duration: 1.23s

✓ Then: transfer is successful with screenshot
  └─ Duration: 0.56s

┌──────────────────────────────────────────────────────────────┐
│ EMBEDDED SCREENSHOT #2: "Transfer Success Message"          │
│ ┌────────────────────────────────────────────────────────┐  │
│ │                                                        │  │
│ │  [Screenshot showing success message dialog]          │  │
│ │                                                        │  │
│ │  ✅ Transfer Successful ✕                             │  │
│ │  Your transfer of ₹100 to account 12 completed       │  │
│ │  Transaction ID: TXN123456789                         │  │
│ │                                                        │  │
│ └────────────────────────────────────────────────────────┘  │
│ Captured at: 2026-05-05 21:07:15 | Size: 1920x1080         │
│ Attached manually via: attachScreenshotToReport()            │
└──────────────────────────────────────────────────────────────┘

✓ And: user navigates back to dashboard
✓ Then: verify savings account balance has been deducted correctly
  └─ Duration: 2.12s

✓ And: transaction details match the transfer details
```

---

## Report Summary Statistics

```
┌──────────────────────────────────────────────────────────────┐
│                    Test Execution Summary                    │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│ Total Test Cases:              17                           │
│ Passed:                        5  (29.4%)   ✓ PASS         │
│ Failed:                        12 (70.6%)   ✗ FAIL         │
│                                                              │
│ Execution Time:                11m 25s                       │
│ Average Time per Test:         40s                           │
│                                                              │
│ Features Tested:                                            │
│ ├─ PageFeatures                ✓ PASS                       │
│ ├─ RecieverID                  ✗ FAIL (3/4 scenarios)      │
│ ├─ SelfTransfer                ✗ FAIL                       │
│ ├─ RTGS                         ✗ FAIL (2/3 scenarios)      │
│ ├─ IMPS                         ✗ FAIL (1/5 scenarios)      │
│ ├─ NEFT                         ✗ FAIL (2/5 scenarios)      │
│ └─ TF-039                       ✓ PASS                       │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

---

## How to Navigate the Report

### 1. **View Overall Results**
   - Main page shows all test scenarios
   - Green checkmark = Passed
   - Red X = Failed

### 2. **View Specific Test**
   - Click on any scenario name
   - See all test steps
   - See embedded screenshots
   - See error messages (if failed)

### 3. **Examine Screenshots**
   - Scroll through scenario details
   - Screenshots appear inline below steps
   - Show actual browser state at that moment
   - Multiple screenshots per scenario possible

### 4. **Download or Share**
   - Right-click screenshot → Save image
   - Report is self-contained (all images embedded)
   - Can email the HTML file directly

---

## Screenshot Details in Report

Each embedded screenshot includes:

✅ **Full browser screen capture**
- Resolution: 1920x1080 pixels (or configured resolution)
- Format: PNG (lossless compression)
- Quality: Full detail visible

✅ **Metadata**
- Timestamp of capture
- Step name/description
- Whether automatic or manual capture
- File size in HTML (embedded as base64)

✅ **Context**
- Which test scenario
- Which step in the test
- Pass/Fail status at that point

---

## Key Features

### Automatic Screenshot on Failure
```
Test Fails → Screenshot automatically captured → 
Embedded in JSON → HTML report generated → 
Visible in HTML report
```
**No manual action needed!**

### Manual Screenshot at Key Steps
```
@Then("transfer is successful with screenshot")
public void transferIsSuccessfulWithScreenshot() {
  captureScreenshot(driver, "SuccessMessage");
  attachScreenshotToReport("Transfer Success");
}
```

### File Organization
```
File System:        target/screenshots/          (Backup copies)
                    └─ TF039_0_*.png
                    └─ TF039_1_*.png
                    
HTML Report:        target/cucumber-reports/    (Main report)
                    └─ html/index.html           (← Open this)
                       └─ Screenshots embedded in HTML
```

---

## What Happens During Test Run

```
1. mvn clean test
   │
   ├─ Tests execute
   ├─ Screenshots saved to: target/screenshots/
   │
   ├─ If test FAILS:
   │  └─ ScreenshotHooks captures screenshot
   │     └─ Embeds in Cucumber JSON
   │
   ├─ If manual step calls attachScreenshotToReport():
   │  └─ Screenshot embedded in Cucumber JSON
   │
   ├─ Cucumber JSON reports generated
   │  └─ target/cucumber/*.json (contains embedded images)
   │
   ├─ Maven Cucumber Reporting plugin runs
   │  └─ Reads JSON files
   │  └─ Detects embedded images
   │  └─ Generates HTML with images
   │
   └─ Result: target/cucumber-reports/html/index.html
      └─ Ready to view with embedded screenshots!
```

---

## Viewing the Report

### After tests complete:

**Windows:**
```powershell
start target/cucumber-reports/html/index.html
```

**macOS:**
```bash
open target/cucumber-reports/html/index.html
```

**Linux:**
```bash
xdg-open target/cucumber-reports/html/index.html
```

Or simply open the file with any modern web browser!

---

## Summary

✅ All screenshots embedded directly in HTML report
✅ No external file navigation needed
✅ Automatic capture on test failures
✅ Manual capture at key steps
✅ Physical files retained for archival
✅ Report is self-contained and portable
✅ Easy debugging with visual evidence

**Everything is ready - just run the tests and open the HTML report!** 🎉
