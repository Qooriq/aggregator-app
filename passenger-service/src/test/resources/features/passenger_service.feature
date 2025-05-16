Feature: Passenger Service e2e Tests

  Scenario:  find all passengers
    When I send a GET request to URL "/api/v1/passengers?page=1&size=10&sortField=ID&order=ASC"
    Then status code should be ok
    And response must have "totalElements"

  Scenario: find passenger by id
    Given passenger with id "4ebba608-6315-447e-9bf5-4e20da6fb0b0"
    When I send a GET request to URL "/api/v1/passengers/4ebba608-6315-447e-9bf5-4e20da6fb0b0"
    Then status code should be ok

  Scenario: create passenger with payload
    Given I have a passenger payload
    When  I send a POST request to URL "/api/v1/passengers"
    Then status code should be created

  Scenario: update passenger by id
    Given I have a passenger update payload
    When I send PUT request to URL "/api/v1/passengers/4ebba608-6315-447e-9bf5-4e20da6fb0b0"
    Then status code should be ok
    And body must contain key "username" and value "agusha@gmail.com"

  Scenario: delete passenger by id
    Given passenger with id "4ebba608-6315-447e-9bf5-4e20da6fb0b0"
    When I send a DELETE request to URL "api/v1/passengers/4ebba608-6315-447e-9bf5-4e20da6fb0b0"
    Then status code should be no content