Feature: Login Validation for Guru99 Demo Bank

  @LOGIN_VALIDATION
  Scenario: Login Validation
    When enter the user name as "mngr450132" and password as "tYvehur"
    Then the login should be successful
    And add a new customer with below attributes
      | customer_name | gender | dob      | address  | city  | state  | pin    | mobile     | email             | password |
      | Rajesh Patel  | Male   | 4/2/2010 | Address1 | City1 | State1 | 232111 | 2322122212 | example@gmail.com | 12345    |