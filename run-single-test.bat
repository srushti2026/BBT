@echo off
REM Run Single Test Case
echo ============================================
echo BugBank - Single Test Case Runner
echo ============================================
echo.
echo Available Test Cases:
echo   TF003Runner
echo   TF004Runner
echo   TF005Runner
echo   TF006Runner
echo   TF007Runner
echo   TF008Runner
echo   TF023Runner
echo   TF024Runner
echo   TF025Runner
echo   TF026Runner
echo   TF028Runner
echo   TF02345Runner
echo   TF02123Runner
echo   TF02678Runner
echo   TF039Runner
echo.
set /p TEST_CASE=Enter Test Runner Name (e.g., TF003Runner):
echo.
echo Cleaning previous test runs...
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean
echo.
echo Running %TEST_CASE% test case...
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -Dtest=%TEST_CASE%
echo.
echo ============================================
echo Test Execution Complete
echo ============================================
echo.
echo Reports Generated:
echo 1. Cucumber HTML Report: target\cucumber-reports\html\index.html
echo 2. Surefire Report: target\site\surefire-report.html
echo.
echo Opening Cucumber Report...
start target\cucumber-reports\html\index.html
