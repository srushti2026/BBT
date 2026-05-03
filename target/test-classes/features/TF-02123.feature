Feature: TF-02123 IMPS amount validation with boundary testing

  Scenario Outline: IMPS transfer with boundary amount validation
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
    And user selects TRANSFER TYPE "IMPS" with assertion
    And user enters AMOUNT "<amount>" with assertion
    And user selects CATEGORY "Friends & Family" with assertion
    And user selects SCHEDULE "Now" with assertion
    And user enters REMARKS "Rent" with assertion for description field
    And user submits transfer
    Then verify IMPS transfer result as "<result>" for amount "<amount>"

    Examples:
      | amount  | result      |
      | 5000    | successful  |
      | 100000  | successful  |
      | 500000  | successful  |
      | 500001  | failed      |
      | -5000   | failed      |
