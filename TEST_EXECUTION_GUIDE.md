# BugBank Test Suite - Execution Guide

## Overview
This project contains automated test cases for the BugBank application's Transfer Funds feature using Selenium, Cucumber, and TestNG.

## Test Cases Included

### Original Test Cases (TF003-TF008)
- **TF003**: Transfer Funds initial page layout
- **TF004**: FROM ACCOUNT dropdown validation
- **TF005**: Receiver Account ID validation
- **TF006**: Account format validation
- **TF007**: Multiple accounts display
- **TF008**: Self-transfer prevention

### Extended Test Cases (TF023, TF024, TF025, TF026, TF028)
- **TF023**: RTGS minimum amount validation
- **TF024**: RTGS amount slightly below minimum
- **TF025**: RTGS above minimum amount validation
- **TF026**: NEFT large amount
- **TF028**: Amount with decimal values

### Combined/Data-Driven Test Cases (New)
- **TF02345**: RTGS amount validation with multiple values
  - Tests: 200000 (pass), 199999 (fail), 205000 (pass)
  
- **TF02123**: IMPS amount boundary testing with data provider
  - Tests: 5000, 100000, 500000 (pass), 500001, -5000 (fail)
  - Validates IMPS limit (0-5 lakh) and rejects negative amounts
  
- **TF02678**: NEFT with decimal and boundary testing
  - Tests: 10000 (pass), 0, -5000 (fail), 5.56, 5000.56
  - Validates decimal amounts and boundary conditions
  
- **TF039**: Complete transfer flow with transaction verification
  - Captures account balance before transfer
  - Performs transfer
  - Navigates to transactions section
  - Verifies balance deduction

---

## Running Tests

### Method 1: Using Batch Files (Easiest)

#### Run All Tests
```bash
double-click run-all-tests.bat
```
This will:
- Clean previous reports
- Run all test cases (original + extended + combined)
- Generate Cucumber HTML report
- Generate Surefire report
- Auto-open the Cucumber report

#### Run Combined Tests Only
```bash
double-click run-combined-tests.bat
```
This will run only the new combined test cases (TF02345, TF02123, TF02678, TF039)

#### Run Original Tests (TF003-TF008)
```bash
double-click run-tf003-tf008.bat
```
This will run only the original test suite

#### Run Single Test Case
```bash
double-click run-single-test.bat
```
Follow the prompt to enter the test case name (e.g., TF003Runner)

#### Clean Old Reports
```bash
double-click cleanup-reports.bat
```
This removes old test artifacts and makes space for new reports

---

### Method 2: Using Command Line (Manual)

#### Run All Tests
```bash
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test
```

#### Run Combined Tests Only
```bash
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -DsuiteXmlFile=src/test/resources/testng-combined.xml
```

#### Run TF003-TF008 Only
```bash
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -DsuiteXmlFile=src/test/resources/testng-tf003-tf008.xml
```

#### Run Single Test
```bash
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -Dtest=TF003Runner
```

#### Run Multiple Specific Tests
```bash
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -Dtest=TF003Runner,TF004Runner,TF005Runner
```

#### Clean and Run
```bash
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean test
```

---

## Reports Generated

After running tests, the following reports are generated:

### 1. Cucumber HTML Report
**Location**: `target/cucumber-reports/html/index.html`
- Interactive HTML report with test scenarios
- Pass/Fail status for each scenario
- Detailed step information
- Screenshots (if configured)
- Timeline view

### 2. Surefire Report
**Location**: `target/site/surefire-report.html`
- TestNG execution summary
- Test statistics
- Pass/Fail breakdown

### 3. TestNG Report
**Location**: `test-output/index.html`
- Detailed test execution report
- Test groups
- Failed test information
- Logs

### 4. Cucumber JSON Reports
**Location**: `target/cucumber/`
- JSON files for each test runner
- Used as input for HTML report generation

---

## Data Provider Test Cases

### TF02345 - RTGS Amount Validation
| Amount | Expected Result |
|--------|-----------------|
| 200000 | Successful      |
| 199999 | Blocked         |
| 205000 | Successful      |

### TF02123 - IMPS Boundary Testing
| Amount | Limit | Expected Result |
|--------|-------|-----------------|
| 5000   | Valid | Successful      |
| 100000 | Valid | Successful      |
| 500000 | Valid | Successful      |
| 500001 | Exceed Limit | Failed |
| -5000  | Negative | Failed |

### TF02678 - NEFT Decimal Testing
| Amount | Description | Expected Result |
|--------|-------------|-----------------|
| 10000  | Valid Amount | Successful |
| 0      | Zero Amount | Failed |
| -5000  | Negative Amount | Failed |
| 5.56   | Small Decimal | Failed |
| 5000.56| Valid Decimal | Successful |

---

## Common Test Data

All transfer test cases use:
- **Receiver Account ID**: 12
- **Beneficiary Nickname**: John Doe
- **Remarks/Description**: Rent (or Fees for TF039)
- **Category**: Friends & Family
- **Schedule**: Now

---

## Cleaning Up Old Reports

To delete old reports and make space:

### Option 1: Use Batch File
```bash
double-click cleanup-reports.bat
```

### Option 2: Manual Commands
```bash
rmdir /s /q target
rmdir /s /q test-output
```

### What Gets Deleted
- `target/` - Old Cucumber reports, JSON files, classes, etc.
- `test-output/` - Old TestNG reports

### What Doesn't Get Deleted (Safe)
- Source code (src/)
- Feature files (src/test/resources/features/)
- Page objects (src/test/java/com/bugbank/pages/)
- Step definitions (src/test/java/com/bugbank/steps/)
- Runners (src/test/java/com/bugbank/runners/)
- Configuration files (pom.xml, testng.xml)

---

## Test Configuration

### pom.xml Updates
- **Maven Surefire Plugin**: Runs tests and generates basic reports
- **Cucumber Reporting Plugin**: Generates detailed HTML Cucumber reports
- **Surefire Report Plugin**: Generates HTML test reports
- **Maven Antrun Plugin**: Echoes report locations after execution

### Updated Test Suite Files
- `testng.xml`: Main test suite with all test runners
- `testng-tf003-tf008.xml`: Original tests only
- `testng-combined.xml`: Combined/new tests only

---

## Troubleshooting

### Reports Not Generated
1. Ensure all tests ran successfully
2. Check that `target/cucumber/` directory has JSON files
3. Verify pom.xml plugins are configured correctly

### Tests Not Running
1. Verify Java 11+ is installed
2. Check Maven is in PATH
3. Ensure all dependencies are downloaded
4. Run `mvn clean compile` first

### Port Issues
1. Ensure port 4444 (WebDriver) is not in use
2. Check firewall settings

---

## Continuous Integration

To integrate with CI/CD:

1. Use `mvn clean test` in your CI pipeline
2. Publish reports from `target/cucumber-reports/html/`
3. Use JUnit reports from `target/surefire-reports/`

---

## Support

For issues or questions:
1. Check test output in console
2. Review detailed reports in `target/cucumber-reports/html/`
3. Check test logs in `test-output/`

---

**Last Updated**: May 3, 2026
**Project**: BugBank Transfer Funds Test Suite
