package com.java.akdev.reviewservice.e2e.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReviewSteps {

    private Response response;
    private final String uri = "http://localhost:8083";
    private String requestPayload;
    private String updatePayload;

    @Given("I have a review payload")
    public void i_have_a_review_payload() {
        requestPayload = """
                {
                    "receiverId": "1826829b-d77a-4908-b1b4-94cf5346a038",
                    "reviewerId": "9a9952bf-e389-4e40-b00f-b66876f42aec",
                    "review": 1,
                    "comment": " ",
                    "receiver": "PASSENGER",
                    "rideId": 2
                }
                """;
    }

    @Given("I have a review update payload")
    public void i_have_a_review_update_payload() {
        updatePayload = """
                {
                    "receiverId": "9a9952bf-e389-4e40-b00f-b66876f42aec",
                    "reviewerId": "9a9952bf-e389-4e40-b00f-b66876f42aec",
                    "review": 1,
                    "comment": " ",
                    "receiver": "PASSENGER",
                    "rideId": 2
                }
                """;
    }

    @Given("review with id {string}")
    public void review_with_id(String driverId) {
        response = given()
                .baseUri(uri)
                .get("/reviews/{id}", driverId);
    }

    @When("I send a DELETE request to URL {string}")
    public void i_send_delete_request_to_url(String endpoint) {
        response = given()
                .baseUri(uri)
                .delete(endpoint);
    }

    @When("I send a POST request to URL {string}")
    public void i_send_a_post_request_to_ur(String endpoint) {
        response = given()
                .baseUri(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestPayload)
                .post(endpoint);
    }

    @When("I send a GET request to URL {string}")
    public void iSendAGETRequestToURL(String endpoint) {
        response = given()
                .baseUri(uri)
                .get(endpoint);
    }

    @When("I send PUT request to URL {string}")
    public void i_send_put_request_to_url(String endpoint) {
        response = given()
                .baseUri(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updatePayload)
                .put(endpoint);
    }

    @Then("status code should be created")
    public void status_code_should_be_created() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @Then("status code should be no content")
    public void status_code_should_be_no_content() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Then("status code should be ok")
    public void status_code_should_be_ok() {
        response.then().statusCode(HttpStatus.OK.value());
    }

    @And("body must contain key {string} and value {string}")
    public void body_must_contain_key_and_value(String key, String value) {
        response.then().body(key, equalTo(value));
    }

    @And("response must have {string}")
    public void response_should_be_array(String key) {
        response.then().body(key, notNullValue());
    }
}
