Feature: Checkout Process

  As a customer
  I want to complete the checkout process
  So that I can place my order successfully

  Background:
    Given the application is launched
    And SauceLab user submit credentials "standard_user" and "secret_sauce"
    When he is on checkout page
    
  Scenario: Start checkout process
    Given he has items in the cart
    When he initiates the checkout
    Then the checkout step one screen should be displayed

  Scenario Outline: Submit personal info
    Given he is on checkout step one
    When he submits "<firstName>", "<lastName>", and "<postalCode>"
    Then the checkout step two screen should be displayed

    Examples:
      | firstName | lastName | postalCode |
      | John      | Doe      | 12345      |
      | Jane      | Smith    | 98765      |

#  Scenario Outline: Verify order summary details
#    Given he is on checkout step two
#    Then it should include "<item1>" priced at <price1>
#    And it should include "<item2>" priced at <price2>
#    And the payment method should be "<paymentMethod>"
#    And the shipping method should be "<shippingMethod>"
#    And the item total should be <itemTotal>
#    And the tax should be <tax>
#    And the final total should be <total>
#
#    Examples:
#      | item1                  | price1 | item2                    | price2 | paymentMethod    | shippingMethod         | itemTotal | tax  | total  |
#      | Sauce Labs Backpack    | 29.99  | Sauce Labs Fleece Jacket | 49.99  | SauceCard #31337 | Free Pony Express Delivery! | 79.98     | 6.40 | 86.38  |
#      | Sauce Labs Bike Light  | 9.99   | Sauce Labs Bolt T-Shirt  | 15.99  | SauceCard #42424 | Standard Delivery       | 25.98     | 2.00 | 27.98  |


  Scenario: Complete checkout
    Given he is on checkout step two
    When he finishes the checkout
    Then the confirmation screen should be displayed

  Scenario: Cancel from personal info
    Given he is on checkout step one
    When he cancels the checkout
    Then the cart screen should be displayed

  Scenario: Cancel from overview
    Given he is on checkout step two
    When he cancels the checkout
    Then the cart screen should be displayed
