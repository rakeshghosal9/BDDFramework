Feature: Login Validation for Guru99 Demo Bank

  @LOGIN_VALIDATION
  Scenario: Login Validation
    When enter the user name as "mngr450132" and password as "tYvehur"
    Then the login should be successful
    And add a new customer