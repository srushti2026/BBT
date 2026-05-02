Feature: TF-004 FROM ACCOUNT dropdown population

  Scenario: FROM ACCOUNT dropdown shows available accounts with details
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
    When user opens FROM ACCOUNT dropdown
    Then accounts are populated and selectable
    And accounts display with format: "Account Type - Account Number (Balance)"
    And at least one account is visible in the dropdown
