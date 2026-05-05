Feature: PageFeatures Combined FROM ACCOUNT and Account Format Validation

  Scenario: Complete FROM ACCOUNT validation including format and multiple accounts
    Given user navigates to the application URL
    And login page is loaded with logo and login button visible
    When user clicks on login button
    Then login popup with email and password fields appears
    When user enters valid credentials
    And user clicks on sign in button in login popup
    Then dashboard page loads successfully
    And account overview text is visible on dashboard
    When user clicks on transfer option in menu
    Then transfer funds page is loaded
    And transfer page layout is complete
    When user opens FROM ACCOUNT dropdown
    Then at least one account is visible in the dropdown
    And all accounts in dropdown display account type
    And all accounts in dropdown display account number
    And all accounts in dropdown display balance in parentheses with currency symbol
    And accounts follow the format "Type - Number (Symbol Balance)"
    And user can see all account options displayed
    And each account option shows complete details with type, number and balance
    When user selects first available account from dropdown with assertion
    Then verify FROM ACCOUNT has been selected
    And user enters RECEIVER ACCOUNT ID "12" with assertion
    And user enters BENEFICIARY NICKNAME "John Doe" with assertion
    And user selects TRANSFER TYPE "NEFT" with assertion
    And user enters AMOUNT "5000" with assertion
    And user selects CATEGORY "Friends & Family" with assertion
    And user selects SCHEDULE "Now" with assertion
    And user enters REMARKS "Rent" with assertion for description field
    And user submits transfer
    Then transfer is successful
