package com.java.akdev.reviewservice.e2e.steps;

import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReviewSteps extends E2eTestBase{

    private Response response;
    private final String uri = "http://localhost:8084";
    private String payload;

    @BeforeStep
    public void before_all() {
        stubFor(get(urlEqualTo("/api/v1/passengers/1826829b-d77a-4908-b1b4-94cf5346a038"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "firstName": "2344352",
                                    "lastName": 10000000,
                                    "username": "1826829b-d77a-4908-b1b4-94cf5346a038"
                                }
                                """)));

        stubFor(get(urlEqualTo("/api/v1/drivers/9a9952bf-e389-4e40-b00f-b66876f42aec"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "firstName": "2344352",
                                    "lastName": 10000000,
                                    "username": "1826829b-d77a-4908-b1b4-94cf5346a038"
                                }
                                """)));
        stubFor(get(urlEqualTo("/api/v1/rides/2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "startLocation": "minsk",
                                    "endLocation": "minsk",
                                    "ridePrice": "12.0",
                                    "driver": "Anton"
                                }
                                """)));
    }

    @Given("I have a review payload:")
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

    @And("body must contain key {string} and value {int}")
    public void body_must_contain_key_and_value(String key, int value) {
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
