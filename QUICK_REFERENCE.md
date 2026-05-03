# BugBank Test Suite - Quick Reference

## 🚀 Quick Start

### Run All Tests
```bash
double-click run-all-tests.bat
```

### Run Only New Combined Tests
```bash
double-click run-combined-tests.bat
```

### Run Original Tests (TF003-TF008)
```bash
double-click run-tf003-tf008.bat
```

### Clean Old Reports
```bash
double-click cleanup-reports.bat
```

---

## 📊 Report Locations

After tests complete, reports are at:

1. **Cucumber HTML Report** (Most Detailed)
   - `target/cucumber-reports/html/index.html` ✅

2. **Surefire Report**
   - `target/site/surefire-report.html`

3. **TestNG Report**
   - `test-output/index.html`

---

## ✅ Test Cases Summary

| Test ID | Type | Description |
|---------|------|-------------|
| TF003 | Original | Page layout validation |
| TF004-TF007 | Original | UI element validation |
| TF008 | Original | Self-transfer prevention |
| TF023-TF025 | Extended | RTGS validation |
| TF026, TF028 | Extended | NEFT validation |
| **TF02345** | **Combined** | **RTGS with data provider** |
| **TF02123** | **Combined** | **IMPS boundary testing** |
| **TF02678** | **Combined** | **NEFT decimal testing** |
| **TF039** | **Combined** | **Full transfer flow** |

---

## 🔧 Manual Commands

```bash
# Run all tests
mvn test

# Run only combined tests
mvn test -DsuiteXmlFile=src/test/resources/testng-combined.xml

# Run single test
mvn test -Dtest=TF003Runner

# Clean and run
mvn clean test
```

---

## 📁 Project Structure

```
bugbank/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/bugbank/
│       │       ├── pages/          (Page Objects)
│       │       ├── steps/          (Step Definitions)
│       │       ├── runners/        (Test Runners)
│       │       ├── hooks/          (Before/After)
│       │       └── config/         (Configuration)
│       └── resources/
│           ├── features/           (Feature Files)
│           └── testng.xml         (Test Suites)
├── target/
│   ├── cucumber-reports/          (HTML Reports)
│   ├── cucumber/                  (JSON Files)
│   └── site/                      (Surefire Reports)
├── test-output/                   (TestNG Reports)
└── pom.xml                        (Maven Config)
```

---

## 🧹 Cleanup

Remove old reports to save space:

```bash
double-click cleanup-reports.bat
```

**Removed**: `target/`, `test-output/`
**Kept**: All source code, configurations, feature files

---

## 📌 Important Notes

✅ **All test cases updated to use:**
- Receiver Account ID: **12**
- Beneficiary Name: **John Doe**
- Remarks: **Rent** (Fees for TF039)

✅ **New Combined Tests use data providers:**
- TF02345: 3 scenarios (RTGS amounts)
- TF02123: 5 scenarios (IMPS with boundaries)
- TF02678: 5 scenarios (NEFT decimals)

✅ **Reports configured for:**
- Cucumber HTML (detailed)
- Surefire (summary)
- TestNG (execution details)

---

## 🎯 Common Tasks

### I want to run tests
→ Double-click `run-all-tests.bat`

### I want to see the Cucumber report
→ Check `target/cucumber-reports/html/index.html`

### I want to run only new tests
→ Double-click `run-combined-tests.bat`

### I want to delete old reports
→ Double-click `cleanup-reports.bat`

### I want to run a single test
→ Double-click `run-single-test.bat` and enter test name

---

## ⚠️ Important

**Before running tests:**
1. Ensure browser driver is configured
2. Check internet connection
3. Verify test credentials in system properties

**After running tests:**
1. Check Cucumber HTML report for details
2. Review failed test scenarios
3. Check console output for errors

---

**Version**: 1.0
**Last Updated**: May 3, 2026
