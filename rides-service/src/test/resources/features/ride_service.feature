Feature: Ride Service e2e Tests

  Scenario:  find all rides
    When I send a GET request to URL "/api/v1/rides?page=1&size=10&sortField=ID&order=ASC"
    Then status code should be ok
    And response must have "totalElements"

  Scenario: find ride by id
    Given ride with id "1"
    When I send a GET request to URL "/api/v1/rides/1"
    Then status code should be ok

  Scenario: create ride with payload
    Given I have a ride payload
    When  I send a POST request to URL "/api/v1/rides"
    Then status code should be created

  Scenario: update ride by id
    Given I have a ride update payload
    When I send PUT request to URL "/api/v1/rides/1"
    Then status code should be ok
    And body must contain key "startLocation" and value "minsk"

  Scenario: delete ride by id
    Given ride with id "1"
    When I send a DELETE request to URL "api/v1/rides/1"
    Then status code should be no content