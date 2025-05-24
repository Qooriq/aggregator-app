Feature: review Service e2e Tests

  Scenario:  find all reviews
    When I send a GET request to URL "/api/v1/reviews?page=1&size=10&sortField=ID&order=ASC"
    Then status code should be ok
    And response must have "totalElements"

  Scenario: find review by id
    Given review with id "1"
    When I send a GET request to URL "/api/v1/reviews/1"
    Then status code should be ok

  Scenario: create review with payload
    Given I have a review payload
    When  I send a POST request to URL "/api/v1/reviews"
    Then status code should be created

  Scenario: update review by id
    Given I have a review update payload
    When I send PUT request to URL "/api/v1/reviews/1"
    Then status code should be ok
    And body must contain key "review" and value "1"

  Scenario: delete review by id
    Given review with id "1"
    When I send a DELETE request to URL "api/v1/reviews/1"
    Then status code should be no content 