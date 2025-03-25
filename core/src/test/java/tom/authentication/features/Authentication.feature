Feature: Sauce Labs Authentication

  Scenario Outline: Valid login
    Given the login page is displayed
    When SauceLab user submit credentials "<username>" and "secret_sauce"
    Then the system should grant access

    Examples:
      | username      |
      | standard_user |

  Scenario: Invalid login
    Given the login page is displayed
    When SauceLab user submit credentials "error_user" and "secret_sauce"
    Then the system should show an error

  Scenario: Missing credentials
    Given the login page is displayed
    When SauceLab user submit empty credentials
    Then the system should show an error

  Scenario: Session handling
    Given SauceLab user submit credentials "standard_user" and "secret_sauce"
    When he log out
    Then the system should return to the login page