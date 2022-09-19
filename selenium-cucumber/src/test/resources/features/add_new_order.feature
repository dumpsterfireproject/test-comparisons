Feature: Orders Screen

Background:
Given I log into the Grocer IO site
And the "Orders" page is displayed

Scenario: Add a new order
When I click the {ordersPage.addOrderButton} button
And I enter the following order:
| field                            | value         |
| {addOrderModal.customerInput}    | Elvis Presley |
| {addOrderModal.orderNumberInput} | 9001          |
| {addOrderModal.orderPriceInput}  | $350.00       |
And I click the {addOrderModal.addButton} button
Then I see "Elvis Presley" in the list
And I see "9001" in the list
And I see "$350.00" in the list
