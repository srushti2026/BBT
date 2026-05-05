Feature: TF-039 Complete transfer flow with transaction verification

  Scenario: Full transfer workflow with dashboard and transaction verification
    Given user navigates to the application URL
    And login page is loaded with logo and login button visible
    When user clicks on login button
    Then login popup with email and password fields appears
    When user enters valid credentials
    And user clicks on sign in button in login popup
    Then dashboard page loads successfully
    And account overview text is visible on dashboard
    When user captures savings account balance from dashboard with screenshot
    And user clicks on transfer option in menu
    Then transfer funds page is loaded
    And transfer page layout is complete
    When user opens FROM ACCOUNT dropdown
    And user selects first available account from dropdown with assertion
    And user enters RECEIVER ACCOUNT ID "12" with assertion
    And user enters BENEFICIARY NICKNAME "John Doe" with assertion
    And user selects TRANSFER TYPE "NEFT" with assertion
    And user enters AMOUNT "120000" with assertion
    And user selects CATEGORY "Loan" with assertion
    And user selects SCHEDULE "Now" with assertion
    And user enters REMARKS "Fees" with assertion for description field
    And user submits transfer
    Then transfer is successful with screenshot
    And user navigates to transactions section and views last 15 transactions with screenshot
    Then user can view transaction in transaction list
    When user waits 3 seconds
    And user scrolls down to see first transaction
    Then capture transaction row screenshot
    When user navigates back to dashboard and waits 2 seconds
    Then verify savings account balance has been deducted correctly with screenshot
    And close the browser
