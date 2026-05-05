# Architecture & Flow Diagrams

## System Architecture

```
┌────────────────────────────────────────────────────────────────────┐
│                      Test Execution Flow                           │
└────────────────────────────────────────────────────────────────────┘

                         ┌──────────────┐
                         │  mvn test    │
                         └──────┬───────┘
                                │
                    ┌───────────┼───────────┐
                    │                       │
          ┌─────────▼──────┐      ┌────────▼────────┐
          │  Test Scenario │      │  ScreenshotHooks│
          │   Executes     │      │   @Before       │
          │                │      │   Initialize    │
          └────────┬───────┘      │   driver/scenario
                   │              └────────┬────────┘
                   │                       │
          ┌────────▼──────────────────────┘
          │
          │  Test Steps Execute
          │
    ┌─────┴────────────────────────┐
    │                               │
    │  ┌──────────────────────────┐ │
    │  │  Step 1: Login           │ │
    │  └──────────────────────────┘ │
    │  ┌──────────────────────────┐ │
    │  │  Step 2: Transfer Setup  │ │
    │  └──────────────────────────┘ │
    │  ┌──────────────────────────┐ │
    │  │  Step 3: Send Money      │ │
    │  └──────────────┬───────────┘ │
    │                 │              │
    │  ┌──────────────▼───────────┐  │
    │  │ Manual Screenshot Step   │  │
    │  │ attachScreenshotToReport()   │
    │  │ ↓                        │  │
    │  │ scenario.attach(bytes,   │  │
    │  │   "image/png", name)     │  │
    │  └──────────────┬───────────┘  │
    │                 │              │
    │  ┌──────────────▼───────────┐  │
    │  │  Step 4: Verify Result   │  │
    │  └──────────────────────────┘  │
    │
    │  Test Result
    │
    ├─ PASS → proceed to next test
    └─ FAIL → ScreenshotHooks.@After triggered
                      ↓
             Capture screenshot automatically
                      ↓
             scenario.attach(bytes, "image/png", ...)
                      ↓
             Screenshot embedded in JSON

          JSON Reports Generated
          (with embedded screenshots)
                      ↓
          Maven Cucumber Reporting Plugin
                      ↓
          HTML Report Generated
          (screenshots visible inline)
                      ↓
          Open HTML in Browser
          (View all screenshots!)
```

---

## Component Interaction Diagram

```
┌──────────────────────────────────────────────────────────────────────┐
│                     Component Interactions                           │
└──────────────────────────────────────────────────────────────────────┘

        ┌─────────────────────────────────────────────────┐
        │          Cucumber Framework                     │
        │  ┌────────────────────────────────────────────┐ │
        │  │  Scenario Execution                        │ │
        │  │  • @Before Hooks                           │ │
        │  │  • Step Definitions                        │ │
        │  │  • @After Hooks                            │ │
        │  └────────────────────────────────────────────┘ │
        └──────────────────┬───────────────────────────────┘
                           │
        ┌──────────────────┼───────────────────────────┐
        │                  │                           │
        ▼                  ▼                           ▼
    ┌─────────────┐   ┌──────────────┐    ┌──────────────────┐
    │ Screenshot  │   │ Transfer     │    │ Screenshot       │
    │ Hooks.java  │   │ FundsSteps   │    │ Hooks.java       │
    │             │   │ .java        │    │                  │
    │ • @Before   │   │              │    │ • @After Hook    │
    │ • @After    │   │ • Step Defs  │    │ • Auto Capture   │
    │ • Auto      │   │ • Manual     │    │ • Embed in JSON  │
    │   Capture   │   │   Screenshots│    │                  │
    └──────┬──────┘   └──────┬───────┘    └────────┬─────────┘
           │                 │                      │
           └─────────────────┼──────────────────────┘
                             │
                ┌────────────▼────────────┐
                │  Scenario.attach()      │
                │                         │
                │  Adds screenshot bytes  │
                │  to Cucumber report     │
                └────────────┬────────────┘
                             │
              ┌──────────────▼──────────────┐
              │   Cucumber JSON Report      │
              │   (*.json files)            │
              │                             │
              │   Contains:                 │
              │   • Test steps              │
              │   • Step results            │
              │   • Embedded screenshots    │
              │     (base64 encoded)        │
              └──────────────┬──────────────┘
                             │
              ┌──────────────▼──────────────────┐
              │  Maven Cucumber Reporting      │
              │  Plugin                        │
              │                                │
              │  • Reads JSON                  │
              │  • Detects embedded images     │
              │  • Generates HTML              │
              └──────────────┬──────────────────┘
                             │
              ┌──────────────▼──────────────────┐
              │  HTML Report                   │
              │  (index.html)                  │
              │                                │
              │  • Test scenarios              │
              │  • Pass/Fail status            │
              │  • Screenshots embedded        │
              │  • Inline images visible       │
              └────────────────────────────────┘

        ┌────────────────────────────────────┐
        │  Physical Screenshots               │
        │  target/screenshots/*.png           │
        │  (Backup/Archive)                   │
        └────────────────────────────────────┘
```

---

## Screenshot Capture Flow

```
┌──────────────────────────────────────────────────────────────┐
│              Screenshot Capture & Embedding                  │
└──────────────────────────────────────────────────────────────┘

AUTOMATIC CAPTURE (On Test Failure)
═══════════════════════════════════════

Test Execution
    ↓
Test Assertion Fails
    ↓
TestNG Exception Thrown
    ↓
Cucumber Catches Exception
    ↓
ScreenshotHooks.@After(Scenario scenario) called
    ↓
if (scenario.isFailed()) {
    ↓
    WebDriver.TakesScreenshot
    ↓
    getScreenshotAs(OutputType.BYTES)
    ↓
    scenario.attach(bytes, "image/png", "TestName_FAILED")
    ↓
    Screenshot embedded in Cucumber JSON ✓
}


MANUAL CAPTURE (At Specific Steps)
═══════════════════════════════════

@Then("some test step")
public void someStep() {
    ↓
    // Do something
    ↓
    attachScreenshotToReport("Step Description")
    ↓
    byte[] screenshot = driver.getScreenshotAs(BYTES)
    ↓
    scenario.attach(screenshot, "image/png", "Step Description")
    ↓
    Screenshot embedded in Cucumber JSON ✓
    ↓
    // Make assertions
}


BOTH FLOWS CONVERGE
═══════════════════

Embedded Screenshots in JSON
    ↓
Maven Cucumber Reporting Plugin
    ↓
Reads JSON Files
    ↓
Finds "attachment" objects with type "image/png"
    ↓
Embeds images in HTML
    ↓
Generates index.html with inline images
    ↓
Open in Browser
    ↓
Screenshots visible inline ✓
```

---

## File Processing Pipeline

```
┌──────────────────────────────────────────────────────────┐
│            File Processing Pipeline                      │
└──────────────────────────────────────────────────────────┘

Step 1: Test Execution
────────────────────
    ScreenshotUtil.captureScreenshot()
           ↓
    Saves to: target/screenshots/TF039_0_*.png
           ↓
    Physical file created ✓


Step 2: Scenario Attachment
────────────────────────────
    ScreenshotHooks OR TransferFundsSteps
           ↓
    scenario.attach(bytes, "image/png", name)
           ↓
    Added to Scenario object in memory
           ↓
    Available for reporting ✓


Step 3: Cucumber Report Generation
───────────────────────────────────
    Cucumber Framework
           ↓
    Gathers test results
    + Scenario objects (with attachments)
           ↓
    Writes to: target/cucumber/*.json
           ↓
    JSON contains embedded screenshot bytes (base64) ✓


Step 4: Maven Report Generation
────────────────────────────────
    maven-cucumber-reporting plugin
           ↓
    Reads: target/cucumber/*.json
           ↓
    Processes attachments
           ↓
    Generates: target/cucumber-reports/html/index.html
           ↓
    HTML includes embedded images ✓


Step 5: Browser Viewing
──────────────────────
    User opens HTML in browser
           ↓
    Browser decodes base64 images
           ↓
    Screenshots display inline ✓


Final Result
────────────
    ┌─ target/screenshots/
    │  ├─ Physical PNG files (backup)
    │  └─ Can be archived separately
    │
    ├─ target/cucumber/
    │  ├─ JSON reports with embedded images
    │  └─ Can be stored in version control
    │
    └─ target/cucumber-reports/html/
       ├─ index.html (open in browser)
       └─ Screenshots visible inline ✓
```

---

## Class Relationships

```
┌────────────────────────────────────────────────────────┐
│              Class Diagram                             │
└────────────────────────────────────────────────────────┘

┌─────────────────────────────────┐
│  ScreenshotHooks                │
├─────────────────────────────────┤
│ - driver: WebDriver             │
│ - scenario: Scenario            │
├─────────────────────────────────┤
│ + setUp(scenario): void         │
│ + tearDown(scenario): void      │
│ - attachScreenshotToReport(): void
└──────────────┬──────────────────┘
               │ uses
               ▼
┌─────────────────────────────────┐
│  ScreenshotUtil                 │
├─────────────────────────────────┤
│ - SCREENSHOT_DIR: String        │
├─────────────────────────────────┤
│ + captureScreenshot(): String   │
│ + captureScreenshotAsBase64():  │
│   String                        │
└─────────────────────────────────┘

┌──────────────────────────────────┐
│  TransferFundsSteps              │
├──────────────────────────────────┤
│ - driver: WebDriver              │
│ - scenario: Scenario             │
│ - transferFundsPage: Page        │
├──────────────────────────────────┤
│ + various @Given/@When/@Then     │
│   methods                        │
│ - attachScreenshotToReport():    │
│   void                           │
└──────────────┬───────────────────┘
               │ uses
               ▼
┌──────────────────────────────────┐
│  TransferFundsPage               │
├──────────────────────────────────┤
│ - driver: WebDriver              │
│ - various Page Object elements   │
├──────────────────────────────────┤
│ + captureScreenshot(): void      │
│ + captureScreenshotAsBytes():    │
│   byte[]                         │
│ + other page methods             │
└──────────────────────────────────┘

Data Flow:
──────────

Scenario attachment
    ↓
TransferFundsSteps.attachScreenshotToReport()
    ↓
TransferFundsPage.captureScreenshotAsBytes()
    ↓
ScreenshotUtil (optional base64 encoding)
    ↓
scenario.attach(bytes, "image/png", name)
    ↓
Cucumber JSON Report (with embedded image)
```

---

## Test Report Structure

```
┌──────────────────────────────────────────────────────────┐
│           Generated Report Structure                     │
└──────────────────────────────────────────────────────────┘

After: mvn clean test

target/
│
├─ cucumber/                          (JSON Reports)
│  ├─ IMPS.json                      (Contains embedded screenshots)
│  ├─ NEFT.json                      (Contains embedded screenshots)
│  ├─ RTGS.json                      (Contains embedded screenshots)
│  ├─ RecieverID.json                (Contains embedded screenshots)
│  ├─ SelfTransfer.json              (Contains embedded screenshots)
│  ├─ PageFeatures.json              (Contains embedded screenshots)
│  └─ TF-039.json                    (Contains embedded screenshots)
│
├─ cucumber-reports/
│  └─ html/                          (Generated HTML Report)
│     ├─ index.html                  ← OPEN THIS IN BROWSER
│     ├─ report.js                   (Handles display logic)
│     ├─ report-*.js                 (Supporting scripts)
│     └─ ...
│
└─ screenshots/                       (Physical PNG Files)
   ├─ TF039_0_Transfer_Success_Message_*.png
   ├─ TF039_1_Initial_Balance_*.png
   ├─ TF039_2_Transactions_Page_*.png
   └─ ...


Browser View (index.html)
────────────────────────

┌─────────────────────────────────────────┐
│  BugBank Test Report                    │
├─────────────────────────────────────────┤
│                                         │
│  Test Results:                          │
│  ├─ [PASSED] PageFeatures               │
│  ├─ [FAILED] RecieverID - Scenario 1    │
│  │           [EMBEDDED SCREENSHOT]     │
│  ├─ [FAILED] RTGS - Amount ₹200,000    │
│  │           [EMBEDDED SCREENSHOT]     │
│  ├─ [PASSED] TF-039 - Complete Flow    │
│  │           [EMBEDDED SCREENSHOT]     │
│  └─ ...                                 │
│                                         │
└─────────────────────────────────────────┘

Click any test to see:
  • Full step details
  • Pass/fail status
  • Embedded screenshots inline
```

---

## Data Flow: Screenshot to Report

```
┌──────────────────────────────────────────────────────────┐
│       Screenshot to HTML Report Data Flow               │
└──────────────────────────────────────────────────────────┘

Browser Window
    ↓ (WebDriver.takeScreenshot)
    ↓
Raw Screenshot Bytes (PNG data)
    ↓ (Base64 encoding)
    ↓
Base64 String
    ↓
scenario.attach(bytes, "image/png", name)
    ↓
Cucumber Scenario Object (stores attachment)
    ↓
Cucumber Framework
    ↓ (JSON serialization)
    ↓
JSON File (target/cucumber/*.json)
    ↓ (contains: "attachments": [{ "data": "base64_string", ... }])
    ↓
Maven Cucumber Reporting Plugin
    ↓ (reads JSON, decodes base64)
    ↓
HTML Generation
    ↓ (embeds <img src="data:image/png;base64,..." />)
    ↓
HTML File (index.html)
    ↓
Browser opens HTML
    ↓ (browser decodes base64)
    ↓
Image rendered inline ✓
```

---

## Summary

✅ **Automatic Flow**: Test Fail → Screenshot → JSON → HTML → Browser
✅ **Manual Flow**: Step Execute → attachScreenshot → JSON → HTML → Browser
✅ **Dual Storage**: Physical files + HTML embedding
✅ **Self-Contained**: All images embedded in HTML
✅ **Zero Config**: Works out of the box
✅ **Easy Viewing**: Open HTML in any browser

