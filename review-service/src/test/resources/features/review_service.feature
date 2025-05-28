Feature: review Service e2e Tests

  Scenario:  find all reviews
    When I send a GET request to URL "/api/v1/reviews?page=1&size=10&sortField=ID&order=ASC"
    Then status code should be 200
    And response must have "totalElements" and equal to 4

  Scenario: find review by id
    Given review with id "1"
    When I send a GET request to URL "/api/v1/reviews/1"
    Then status code should be 200

  Scenario: create review with payload
    Given I have a review payload:
    """
                {
                    "receiverId": "1826829b-d77a-4908-b1b4-94cf5346a038",
                    "reviewerId": "9a9952bf-e389-4e40-b00f-b66876f42aec",
                    "review": 1,
                    "comment": " ",
                    "receiver": "PASSENGER",
                    "rideId": 2
                }
    """
    When  I send a POST request to URL "/api/v1/reviews"
    Then status code should be 201

  Scenario: update review by id
    Given I have a review payload:
    """
                {
                    "receiverId": "9a9952bf-e389-4e40-b00f-b66876f42aec",
                    "reviewerId": "9a9952bf-e389-4e40-b00f-b66876f42aec",
                    "review": 1,
                    "comment": " ",
                    "receiver": "PASSENGER",
                    "rideId": 2
                }
    """
    When I send PUT request to URL "/api/v1/reviews/1"
    Then status code should be 200
    And body must contain key "review" and value "1"

  Scenario: delete review by id
    Given review with id "1"
    When I send a DELETE request to URL "api/v1/reviews/1"
    Then status code should be 204