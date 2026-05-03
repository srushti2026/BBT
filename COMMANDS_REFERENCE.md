# BugBank Test Suite - Commands Reference

## 🎯 Quick Commands

### Windows Batch Files (Easiest - Just Double Click)

```
📁 C:\Users\Administrator\Documents\ccc\bugbank\

🔵 run-all-tests.bat
   → Runs ALL test cases (Original + Extended + Combined)
   → Generates comprehensive Cucumber HTML report
   → Auto-opens the report in browser

🔵 run-combined-tests.bat
   → Runs ONLY new combined tests (TF02345, TF02123, TF02678, TF039)
   → Perfect for testing new features

🔵 run-tf003-tf008.bat
   → Runs ONLY original test suite (TF003-TF008)
   → Good for regression testing

🔵 run-single-test.bat
   → Run ONE specific test
   → Interactive prompt for test name

🔵 cleanup-reports.bat
   → Deletes old reports and artifacts
   → Frees up disk space
   → SAFE - doesn't delete source code
```

---

## 💻 Command Line Commands

### Run Tests from Command Prompt

#### 1. Run All Tests
```cmd
cd C:\Users\Administrator\Documents\ccc\bugbank
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test
```

#### 2. Run Combined Tests Only
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -DsuiteXmlFile=src/test/resources/testng-combined.xml
```

#### 3. Run Original Tests (TF003-TF008)
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -DsuiteXmlFile=src/test/resources/testng-tf003-tf008.xml
```

#### 4. Run Single Test
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -Dtest=TF003Runner
```

#### 5. Run Multiple Tests
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -Dtest=TF003Runner,TF004Runner,TF005Runner
```

#### 6. Clean and Run (Recommended)
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean test
```

#### 7. Compile Only (No Tests)
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean compile
```

#### 8. Clean Only (Remove Old Reports)
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean
```

---

## 📊 Test Case Mappings

### Available Test Runners

```
Original Tests (TF003-TF008):
  -Dtest=TF003Runner    →  Transfer Funds page layout
  -Dtest=TF004Runner    →  FROM ACCOUNT dropdown
  -Dtest=TF005Runner    →  Receiver Account ID validation
  -Dtest=TF006Runner    →  Account format validation
  -Dtest=TF007Runner    →  Multiple accounts display
  -Dtest=TF008Runner    →  Self-transfer prevention

Extended Tests (TF023-TF028):
  -Dtest=TF023Runner    →  RTGS minimum amount validation
  -Dtest=TF024Runner    →  RTGS amount below minimum
  -Dtest=TF025Runner    →  RTGS above minimum amount
  -Dtest=TF026Runner    →  NEFT large amount
  -Dtest=TF028Runner    →  Decimal amount values

New Combined Tests (Data-Driven):
  -Dtest=TF02345Runner  →  RTGS validation (3 scenarios)
  -Dtest=TF02123Runner  →  IMPS boundary testing (5 scenarios)
  -Dtest=TF02678Runner  →  NEFT decimal testing (5 scenarios)
  -Dtest=TF039Runner    →  Complete transfer flow
```

---

## 📈 Report Locations

After running tests, find reports here:

```
Target Directories Created:
📁 target/
  ├── cucumber-reports/html/index.html  ← MAIN CUCUMBER REPORT
  ├── cucumber/
  │   ├── TF-003.json
  │   ├── TF-004.json
  │   ├── TF02345.json
  │   └── ... (all JSON files)
  └── site/
      └── surefire-report.html  ← SUREFIRE REPORT

📁 test-output/
  └── index.html  ← TESTNG REPORT
```

---

## 🧹 Cleanup Commands

### Manual Cleanup (Alternative to batch file)

```cmd
REM Remove target directory
rmdir /s /q target

REM Remove test-output directory
rmdir /s /q test-output

REM Both at once
rmdir /s /q target && rmdir /s /q test-output
```

### Using Maven
```cmd
mvn clean
```

---

## 📝 Suite XML Files

Use these with -DsuiteXmlFile parameter:

```cmd
All Tests:
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

Original Tests (TF003-TF008):
mvn test -DsuiteXmlFile=src/test/resources/testng-tf003-tf008.xml

New Combined Tests:
mvn test -DsuiteXmlFile=src/test/resources/testng-combined.xml
```

---

## 🎯 Recommended Workflows

### Workflow 1: First Time Setup
```cmd
cd C:\Users\Administrator\Documents\ccc\bugbank
mvn clean compile
double-click run-all-tests.bat
```

### Workflow 2: Daily Testing
```cmd
double-click cleanup-reports.bat
double-click run-all-tests.bat
```

### Workflow 3: Testing New Features
```cmd
double-click cleanup-reports.bat
double-click run-combined-tests.bat
```

### Workflow 4: Regression Testing
```cmd
double-click cleanup-reports.bat
double-click run-tf003-tf008.bat
```

### Workflow 5: Single Test Debugging
```cmd
double-click cleanup-reports.bat
double-click run-single-test.bat
# Enter test name when prompted
```

---

## 🚀 Full Command Examples

### Example 1: Run All Tests with Full Output
```cmd
cd C:\Users\Administrator\Documents\ccc\bugbank
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean test -X
```

### Example 2: Run Combined Tests Silently
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -q -DsuiteXmlFile=src/test/resources/testng-combined.xml
```

### Example 3: Run Single Test with Detailed Output
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -Dtest=TF039Runner -e
```

### Example 4: Skip Tests (Only Compile)
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean compile
```

### Example 5: Generate Reports Only (No Tests)
```cmd
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd verify
```

---

## 📋 Test Execution Summary

### What Happens When You Run Tests:

```
1. Maven cleans old artifacts (if clean is used)
2. Compiles all Java code
3. Runs test scenarios from feature files
4. Generates Cucumber JSON reports
5. Generates Surefire HTML reports
6. Generates TestNG HTML reports
7. Displays execution summary

Total Reports Generated:
✅ Cucumber HTML (Interactive, detailed)
✅ Surefire HTML (Summary, emailable)
✅ TestNG HTML (Execution details)
✅ JSON files (For CI/CD)
```

---

## 💡 Tips & Tricks

### Tip 1: Run Tests in Background
```cmd
start /B mvn test
```

### Tip 2: Run and Save Output to File
```cmd
mvn test > test-output.log 2>&1
```

### Tip 3: Run Specific Tests by Pattern
```cmd
mvn test -Dtest=TF0* -q
```

### Tip 4: Skip Surefire Reporting
```cmd
mvn test -Dtest=TF003Runner -DskipTests=false
```

### Tip 5: Run Tests in Parallel (Advanced)
```cmd
mvn test -parallel=all
```

---

## ⚠️ Important Notes

1. **Test Credentials:**
   - Change in pom.xml properties if needed
   - Or use system properties: `-Dtest.email=xxx -Dtest.password=yyy`

2. **Browser:**
   - Ensure WebDriver is configured
   - Firefox or Chrome typically used

3. **Network:**
   - Requires internet connection
   - Target URL: https://smartbank-j2m0.onrender.com/

4. **Time:**
   - Full suite takes ~10-15 minutes
   - Combined tests take ~5-8 minutes
   - Single test takes ~2-3 minutes

---

## 🔗 Related Documentation

- 📖 `TEST_EXECUTION_GUIDE.md` - Detailed guide
- ⚡ `QUICK_REFERENCE.md` - Quick reference
- 📝 `CHANGES_SUMMARY.md` - All changes made
- 📋 `README.md` - Project overview

---

**Date**: May 3, 2026
**Version**: 1.0
**Status**: Ready to Use
