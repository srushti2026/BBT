# BugBank Transfer Funds Tests

This project runs Selenium + TestNG + Cucumber tests for Transfer Funds (TF-003 to TF-008).

## Prerequisites
- Java 11+ installed
- Maven installed
- Chrome installed

## Run all tests (TestNG suite)
```cmd
mvn test
```

## Optional: override base URL or credentials
```cmd
mvn test -DbaseUrl=https://smartbank-j2m0.onrender.com/ -Demail=prtwo@gamil.com -Dpassword=Pleasework@05
```

## Reports
- Surefire reports: `target/surefire-reports`
- Cucumber JSON/HTML: `target/cucumber`
- Aggregated Cucumber report: `target/cucumber-report`

## Notes
- The browser starts maximized and logs out after the full suite finishes.
- Explicit waits are used to handle slow page loads (Render deployment).
