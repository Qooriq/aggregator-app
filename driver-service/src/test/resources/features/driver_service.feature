Feature: Driver Service Component Tests

  Scenario:  find all drivers
    When I send a GET request to URL "/api/v1/drivers?page=1&size=10&sortField=ID&order=ASC"
    Then status code should be ok
    And response must be "totalElements"

  Scenario: find driver by id
    Given driver id with id 4ebba608-6315-447e-9bf5-4e20da6fb0b0
    When I send a GET request to URL "/api/v1/drivers/4ebba608-6315-447e-9bf5-4e20da6fb0b0"
    Then status code should be ok

  Scenario: create driver with payload
    Given I have a driver payload
    When  I send a POST request to URL "/api/v1/drivers"
    Then status code should be created
