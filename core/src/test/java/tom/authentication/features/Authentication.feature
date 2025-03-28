Feature: Sauce Labs Authentication

  Scenario Outline: Valid login
    Given the application is launched
    When SauceLab user submit credentials "<username>" and "secret_sauce"
    Then the system should grant access

    Examples:
      | username      |
      | standard_user |

  Scenario: Invalid login
    Given the application is launched
    When SauceLab user submit credentials "locked_out_user" and "secret_sauce"
    Then the system should show the error "Epic sadface: Sorry, this user has been locked out."

  Scenario: Missing credentials
    Given the application is launched
    When SauceLab user submit empty credentials
    Then the system should show the error "Epic sadface: Username is required"

  Scenario: Session handling
    Given SauceLab user submit credentials "standard_user" and "secret_sauce"
    When he log out
    Then the system should return to the login page