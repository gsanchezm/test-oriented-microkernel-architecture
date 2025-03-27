Feature: Manage product catalog in the inventory page

  Background:
    Given the application is launched
    And SauceLab user submit credentials "standard_user" and "secret_sauce"
    And the user is on the inventory page

  Scenario: View list of available products
    Then the user should see a list of available products

  Scenario: Add a product to the cart
    When the user adds the product "Sauce Labs Backpack" to the cart
    Then the cart should reflect the item "Sauce Labs Backpack"

  Scenario: Verify a product price
    When the user checks the price of "Sauce Labs Fleece Jacket"
    Then the displayed price should be "$49.99"

  Scenario: Sort products by name
    When the user selects the sort option "Name (A to Z)"
    Then the products should be sorted accordingly
