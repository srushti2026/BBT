@echo off
REM Run Combined Tests (New Test Cases)
echo ============================================
echo BugBank - Combined Test Cases Only
echo ============================================
echo.
echo This will run: TF02345, TF02123, TF02678, TF039
echo.
echo Cleaning previous test runs...
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean
echo.
echo Running combined test cases only...
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -DsuiteXmlFile=src/test/resources/testng-combined.xml
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
