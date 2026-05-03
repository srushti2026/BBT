# 🎯 BugBank Test Suite - Start Here

Welcome to the BugBank Transfer Funds Automated Test Suite!

This file will guide you to the right documentation and help you get started quickly.

---

## 📚 Documentation Files

Choose what you need:

### 🚀 **Quick Start** (Read This First!)
- **File**: `QUICK_REFERENCE.md`
- **Best for**: Getting started in 5 minutes
- **Contains**: 
  - How to run tests (batch files)
  - Report locations
  - Quick command reference

### 💻 **Command Line Users**
- **File**: `COMMANDS_REFERENCE.md`
- **Best for**: Running tests from command prompt
- **Contains**:
  - All Maven commands
  - Test mapping
  - Full workflow examples

### 📖 **Complete Guide**
- **File**: `TEST_EXECUTION_GUIDE.md`
- **Best for**: Understanding everything
- **Contains**:
  - Detailed explanation
  - Test case descriptions
  - Troubleshooting tips
  - Data provider examples

### 📝 **What Changed**
- **File**: `CHANGES_SUMMARY.md`
- **Best for**: Understanding modifications
- **Contains**:
  - All changes made
  - New test cases explained
  - File structure changes

---

## 🎬 Quick Start - 3 Steps

### Step 1: Choose How to Run
```
A) Easy Way (Batch Files)
   - Just double-click a .bat file
   - No command line needed
   
B) Command Line Way
   - Run mvn commands manually
   - More control
```

### Step 2: Run Tests
```
A) All Tests:
   double-click run-all-tests.bat
   
B) New Tests Only:
   double-click run-combined-tests.bat
   
C) Original Tests Only:
   double-click run-tf003-tf008.bat
```

### Step 3: View Reports
```
Reports open automatically in your browser!
Location: target/cucumber-reports/html/index.html
```

---

## 📁 Batch Files (Click to Run)

| File | Purpose | Best For |
|------|---------|----------|
| `run-all-tests.bat` | Run all 16 test cases | Complete testing |
| `run-combined-tests.bat` | Run new tests (TF02345, TF02123, TF02678, TF039) | Testing new features |
| `run-tf003-tf008.bat` | Run original 6 tests | Regression testing |
| `run-single-test.bat` | Run one specific test | Debugging |
| `cleanup-reports.bat` | Delete old reports | Free up space |

**How to use**: Just double-click any .bat file and watch it run!

---

## 📊 Test Cases Overview

### Original Tests (6 cases)
```
TF003  → Page layout validation
TF004  → Dropdown validation
TF005  → Field validation
TF006  → Account format validation
TF007  → Multiple accounts
TF008  → Self-transfer prevention
```

### Extended Tests (5 cases)
```
TF023  → RTGS minimum validation
TF024  → RTGS below minimum
TF025  → RTGS above minimum
TF026  → NEFT large amount
TF028  → Decimal amounts
```

### NEW! Combined Tests (4 cases with data providers)
```
TF02345 → RTGS with 3 amount scenarios
TF02123 → IMPS with 5 boundary scenarios
TF02678 → NEFT with 5 decimal scenarios
TF039   → Complete transfer flow with verification
```

**Total: 15 test cases × data scenarios = 27 individual test scenarios**

---

## 📊 Reports Generated

After running tests, you get:

### 1. 🎨 **Cucumber HTML Report** (Best)
- **File**: `target/cucumber-reports/html/index.html`
- **Features**: Interactive, detailed, beautiful
- **Use**: View all test results with screenshots

### 2. 📋 **Surefire Report** (For Email)
- **File**: `target/site/surefire-report.html`
- **Features**: Summary, statistics, emailable
- **Use**: Send to stakeholders

### 3. 📝 **TestNG Report**
- **File**: `test-output/index.html`
- **Features**: Execution logs, details
- **Use**: Debug failed tests

### 4. 🔗 **JSON Reports** (For CI/CD)
- **File**: `target/cucumber/*.json`
- **Features**: Machine readable
- **Use**: Integration with Jenkins, GitHub Actions, etc.

---

## 💡 Usage Examples

### Example 1: First Time Using This Suite
```bash
1. double-click run-all-tests.bat
2. Wait for tests to complete
3. View report that opens automatically
4. Read test results
```

### Example 2: Running Only New Tests
```bash
1. double-click run-combined-tests.bat
2. Tests run and report opens
3. Check TF02345, TF02123, TF02678, TF039 results
```

### Example 3: Testing a Single Feature
```bash
1. double-click run-single-test.bat
2. When prompted, type: TF039Runner
3. Report opens with just TF039 results
```

### Example 4: Before Committing Code
```bash
1. double-click cleanup-reports.bat
   (Delete old reports)
2. double-click run-all-tests.bat
   (Run fresh tests)
3. Check reports
4. Commit if all pass
```

---

## 🔍 Finding Reports

After running tests:

**Immediate Opening**: Report opens automatically in browser after tests complete

**Manual Opening**: 
- Right-click `target/cucumber-reports/html/index.html`
- Select "Open with" → Browser

**Or Navigate**:
1. Go to: `C:\Users\Administrator\Documents\ccc\bugbank\`
2. Open: `target` → `cucumber-reports` → `html`
3. Double-click: `index.html`

---

## 🧹 Cleaning Up

### Before Running New Tests
```bash
double-click cleanup-reports.bat
```
This removes:
- Old report files
- Old JSON files
- Compiled test classes

This KEEPS safe:
- All source code
- All feature files
- All configuration
- Your code is never deleted!

---

## ⚙️ Troubleshooting

### Tests Won't Run?
→ Read: `TEST_EXECUTION_GUIDE.md` → Troubleshooting section

### Reports Not Generated?
→ Read: `COMMANDS_REFERENCE.md` → Check Maven output

### Want Different Test Data?
→ Read: `TEST_EXECUTION_GUIDE.md` → Test Configuration section

### Need Specific Command?
→ Read: `COMMANDS_REFERENCE.md` → Full list of all commands

---

## 📞 Quick Help

| Question | Answer |
|----------|--------|
| How do I run all tests? | `double-click run-all-tests.bat` |
| Where are the reports? | `target/cucumber-reports/html/index.html` |
| How do I delete old reports? | `double-click cleanup-reports.bat` |
| How do I run one test? | `double-click run-single-test.bat` |
| Can I use command line? | Yes! Read `COMMANDS_REFERENCE.md` |
| What's TF02345, TF02123, etc? | New combined tests with data providers |

---

## 📌 Important Information

### Test Data Used (All Tests)
```
Receiver Account ID: 12
Beneficiary Name: John Doe
Remarks: Rent (Fees for TF039)
Category: Friends & Family
Schedule: Now
```

### Test Type
```
Framework: Selenium + Cucumber + TestNG
Language: Java 11+
Browser: Chrome/Firefox
Application: https://smartbank-j2m0.onrender.com/
```

### Report Features
```
✅ Colored pass/fail indicators
✅ Detailed step information
✅ Execution timeline
✅ Failure reasons
✅ Screenshots (if configured)
✅ Test duration
✅ Environment details
```

---

## 🎓 Next Steps

### Step 1: Run Tests
```
👉 double-click run-all-tests.bat
```

### Step 2: View Reports
```
👉 Report opens automatically
   Check the results
```

### Step 3: Explore Documentation
```
👉 Read QUICK_REFERENCE.md for advanced usage
👉 Read COMMANDS_REFERENCE.md for command line
👉 Read TEST_EXECUTION_GUIDE.md for complete guide
```

### Step 4: Integrate with CI/CD (Optional)
```
👉 Use: mvn clean test
👉 Publish: target/cucumber-reports/html/
```

---

## 📞 Documentation Quick Links

- **Just starting?** → `QUICK_REFERENCE.md` ⭐
- **Using command line?** → `COMMANDS_REFERENCE.md`
- **Need full details?** → `TEST_EXECUTION_GUIDE.md`
- **Want to know changes?** → `CHANGES_SUMMARY.md`
- **Project overview?** → `README.md`

---

## ✨ What You Get

✅ **15 Automated Test Cases** covering Transfer Funds feature
✅ **Data-Driven Testing** with multiple scenarios per test
✅ **Beautiful Reports** in HTML, JSON, and more
✅ **Easy Batch Files** - just double-click to run
✅ **Complete Documentation** - never wonder what to do
✅ **CI/CD Ready** - integrate with your pipeline
✅ **Professional Quality** - production-ready test framework

---

## 🚀 Ready to Start?

**Choose One:**

| I want to... | Do this |
|--------------|---------|
| Run all tests | `double-click run-all-tests.bat` |
| Test only new features | `double-click run-combined-tests.bat` |
| Run from command line | Read `COMMANDS_REFERENCE.md` |
| Understand everything | Read `TEST_EXECUTION_GUIDE.md` |
| See what changed | Read `CHANGES_SUMMARY.md` |

---

**Last Updated**: May 3, 2026
**Status**: ✅ Ready to Use
**Questions**: Check the documentation files above

Enjoy automated testing! 🎉
