Feature: Wallet Service e2e Tests

  Scenario:  find all wallets
    When I send a GET request to URL "/api/v1/wallets?page=1&size=10&sortField=ID&order=ASC"
    Then status code should be ok
    And response must have "totalElements"

  Scenario: find wallet by id
    Given wallet with id "1"
    When I send a GET request to URL "/api/v1/wallets/1"
    Then status code should be ok

  Scenario: create wallet with payload
    Given I have a wallet payload
    When  I send a POST request to URL "/api/v1/wallets"
    Then status code should be created

  Scenario: update wallet by id
    Given I have a wallet update payload
    When I send PUT request to URL "/api/v1/wallets/1"
    Then status code should be ok
    And body must contain key "cardNumber" and value "2344352"

  Scenario: delete wallet by id
    Given wallet with id "1"
    When I send a DELETE request to URL "api/v1/wallets/1"
    Then status code should be no content