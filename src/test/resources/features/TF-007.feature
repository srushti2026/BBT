Feature: TF-007 Multiple accounts in FROM ACCOUNT dropdown

  Scenario: User with multiple accounts sees all accounts in dropdown
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
    Then multiple accounts are listed
    And user can see all account options displayed
    And each account option shows complete details with type, number and balance
