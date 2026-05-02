Feature: TF-006 FROM ACCOUNT option format validation

  Scenario: FROM ACCOUNT option shows account number and balance in correct format
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
