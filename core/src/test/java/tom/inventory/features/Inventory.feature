Feature: Manage product catalog in the inventory page

  Background:
    Given the application is launched
    And SauceLab user submit credentials "standard_user" and "secret_sauce"
    And he is on the inventory page

  Scenario: View list of available products
    Then the user should see a list of available products

  Scenario Outline: Add a product to the cart
    When the user adds the product "<Product>" to the cart
    Then the cart should reflect the item "<product>"

    Examples:
      | Product                  |
      | Sauce Labs Backpack      |
      | Sauce Labs Fleece Jacket |
      | Sauce Labs Onesie        |

  Scenario Outline: Verify a product price
    Then the displayed "<Product>" price should be "<Price>"

    Examples:
      | Product                 | Price  |
      | Sauce Labs Bike Light   | $9.99  |
      | Sauce Labs Bolt T-Shirt | $15.99 |
      | Sauce Labs Onesie       | $7.99  |

  Scenario: Sort products by name
    When the user selects the sort option "Name (A to Z)"
    Then the products should be sorted accordingly
