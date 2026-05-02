Feature: TF-008 Self-transfer prevention

  Scenario: Prevent self-transfer using the same account
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
    Then verify FROM ACCOUNT has been selected
    When user enters RECEIVER ACCOUNT ID with same account number with assertion
    And user enters BENEFICIARY NICKNAME "Rent" with assertion
    And user selects TRANSFER TYPE "NEFT" with assertion
    And user enters AMOUNT "5000" with assertion
    And user selects CATEGORY "Friends & Family" with assertion
    And user selects SCHEDULE "Now" with assertion
    And user submits transfer
    Then error message indicates self-transfer is not allowed
    And no success message appears
    And verify transaction was not completed
