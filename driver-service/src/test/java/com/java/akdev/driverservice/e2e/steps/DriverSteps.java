package com.java.akdev.driverservice.e2e.steps;

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


public class DriverSteps {

    Response response;
    private final String uri = "http://localhost:8084";
    private String requestPayload;
    private String updatePayload;

    @Given("I have a driver payload")
    public void i_have_a_driver_payload() {
        requestPayload = """
                {
                    "firstName": "Anton",
                    "lastName": "Kazlouski",
                    "username": "asuha@example.com",
                    "password": "123"
                }
                """;
    }

    @Given("I have a driver update payload")
    public void i_have_a_driver_update_payload() {
        updatePayload = """
                {
                    "firstName": "Anton",
                    "lastName": "Kazlouski",
                    "username": "agusha@gmail.com",
                    "password": "123"
                }
                """;
    }

    @Given("driver with id {string}")
    public void driver_with_id(String driverId) throws Exception {
        response = given()
                .baseUri(uri)
                .get("/passengers/{id}", driverId);
    }

    @When("I send a DELETE request to URL {string}")
    public void i_send_delete_request_to_url(String endpoint) {
        response = given()
                .baseUri(uri)
                .delete(endpoint);
    }

    @When("I send a POST request to URL {string}")
    public void i_send_a_post_request_to_ur(String endpoint) throws Exception {
        response = given()
                .baseUri(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestPayload)
                .post(endpoint);
    }

    @When("I send a GET request to URL {string}")
    public void iSendAGETRequestToURL(String endpoint) throws Exception {
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
    public void status_code_should_be_created() throws Exception {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @Then("status code should be no content")
    public void status_code_should_be_no_content() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Then("status code should be ok")
    public void status_code_driver_should_be_ok() throws Exception {
        response.then().statusCode(HttpStatus.OK.value());
    }

    @And("body must contain key {string} and value {string}")
    public void body_must_contain_key_and_value(String key, String value) {
        response.then().body(key, equalTo(value));
    }

    @And("response must have {string}")
    public void response_should_be_array(String key) throws Exception {
        response.then().body(key, notNullValue());
    }
}
