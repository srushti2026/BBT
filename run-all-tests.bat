@echo off
REM Clean and Run All Tests with Proper Reports
echo ============================================
echo BugBank - Complete Test Suite
echo ============================================
echo.
echo Cleaning previous test runs...
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean
echo.
echo Running complete test suite...
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test
echo.
echo ============================================
echo Test Execution Complete
echo ============================================
echo.
echo Reports Generated:
echo 1. Cucumber HTML Report: target\cucumber-reports\html\index.html
echo 2. Surefire Report: target\site\surefire-report.html
echo 3. TestNG Report: test-output\index.html
echo.
echo Opening Cucumber Report...
start target\cucumber-reports\html\index.html
