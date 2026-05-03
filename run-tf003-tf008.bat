@echo off
REM Run TF003-TF008 Tests
echo ============================================
echo BugBank - Original Test Suite (TF003-TF008)
echo ============================================
echo.
echo Cleaning previous test runs...
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd clean
echo.
echo Running TF003-TF008 test cases...
C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.15\bin\mvn.cmd test -DsuiteXmlFile=src/test/resources/testng-tf003-tf008.xml
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
