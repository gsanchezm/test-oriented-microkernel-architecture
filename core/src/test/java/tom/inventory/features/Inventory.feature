Feature: View and interact with product catalog

  Scenario: User sees a list of available products
    Given the application is launched
    When the user navigates to the inventory page
    Then the user should see a list of available products

  Scenario: User adds a specific product to the cart
    Given the user is on the inventory page
    When the user adds the product "Sauce Labs Backpack" to the cart
    Then the cart should reflect the item "Sauce Labs Backpack"

  Scenario: User verifies product price
    Given the user is on the inventory page
    When the user checks the price of "Sauce Labs Fleece Jacket"
    Then the displayed price should be "$49.99"

  Scenario: User sorts the products
    Given the user is on the inventory page
    When the user selects the sort option "Name (A to Z)"
    Then the products should be sorted accordingly
