package com.java.akdev.reviewservice.e2e.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReviewSteps {

    private Response response;
    private final String uri = "http://localhost:8083";
    private String payload;

    @Given("I have a review payload:}")
    public void i_have_a_review_payload(String payload) {
        this.payload = payload;
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
                .body(payload)
                .post(endpoint);
    }

    @When("I send a GET request to URL {string}")
    public void i_send_a_get_request_to_url(String endpoint) {
        response = given()
                .baseUri(uri)
                .get(endpoint);
    }

    @When("I send PUT request to URL {string}")
    public void i_send_put_request_to_url(String endpoint) {
        response = given()
                .baseUri(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(payload)
                .put(endpoint);
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int statusCode) {
        response
                .then()
                .statusCode(statusCode);
    }

    @And("body must contain key {string} and value {string}")
    public void body_must_contain_key_and_value(String key, String value) {
        response
                .then()
                .body(key, equalTo(value));
    }

    @And("response must have {string} and equal to {int}")
    public void response_should_be_array_and_equal_to(String key, int amount) {
        response
                .then()
                .body(key, equalTo(amount));
    }
}
