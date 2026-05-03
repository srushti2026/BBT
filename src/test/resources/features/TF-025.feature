Feature: TF-025 RTGS above minimum amount validation

  Scenario: RTGS amount above minimum
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
    And user selects first available account from dropdown with assertion
    And user enters RECEIVER ACCOUNT ID "12" with assertion
    And user enters BENEFICIARY NICKNAME "John Doe" with assertion
    And user selects TRANSFER TYPE "RTGS" with assertion
    And user enters AMOUNT "205000" with assertion
    And user selects CATEGORY "Friends & Family" with assertion
    And user selects SCHEDULE "Now" with assertion
    And user enters REMARKS "Rent" with assertion
    And user submits transfer
    Then transfer is successful
