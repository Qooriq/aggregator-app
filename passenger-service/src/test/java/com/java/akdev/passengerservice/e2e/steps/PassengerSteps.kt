package com.java.akdev.passengerservice.e2e.steps

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.RestAssured.given
import io.restassured.response.Response
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType


class PassengerSteps {

    var response: Response? = null
    private val uri = "http://localhost:8082"
    private var requestPayload: String? = null
    private var updatePayload: String? = null

    @Given("I have a passenger payload")
    fun i_have_a_passenger_payload() {
        requestPayload = """
                {
                    "firstName": "Anton",
                    "lastName": "Kazlouski",
                    "username": "asuha@example.com",
                    "password": "123"
                }
                """
    }

    @Given("I have a passenger update payload")
    fun i_have_a_passenger_update_payload() {
        updatePayload = """
                {
                    "firstName": "Anton",
                    "lastName": "Kazlouski",
                    "username": "agusha@gmail.com",
                    "password": "123"
                }
                """
    }

    @Given("passenger with id {string}")
    @Throws(Exception::class)
    fun passenger_with_id(passengerId: String?) {
        response = given()
            .baseUri(uri)
            .get("/passengers/{id}", passengerId)
    }

    @When("I send a DELETE request to URL {string}")
    fun i_send_delete_request_to_url(endpoint: String?) {
        response = given()
            .baseUri(uri)
            .delete(endpoint)
    }

    @When("I send a POST request to URL {string}")
    @Throws(Exception::class)
    fun i_send_a_post_request_to_ur(endpoint: String?) {
        response = given()
            .baseUri(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(requestPayload)
            .post(endpoint)
    }

    @When("I send a GET request to URL {string}")
    @Throws(Exception::class)
    fun iSendAGETRequestToURL(endpoint: String?) {
        response = given()
            .baseUri(uri)
            .get(endpoint)
    }

    @When("I send PUT request to URL {string}")
    fun i_send_put_request_to_url(endpoint: String?) {
        response = given()
            .baseUri(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(updatePayload)
            .put(endpoint)
    }

    @Then("status code should be created")
    @Throws(Exception::class)
    fun status_code_should_be_created() {
        response?.then()?.statusCode(HttpStatus.CREATED.value())
    }

    @Then("status code should be no content")
    fun status_code_should_be_no_content() {
        response?.then()?.statusCode(HttpStatus.NO_CONTENT.value())
    }

    @Then("status code should be ok")
    @Throws(Exception::class)
    fun status_code_passenger_should_be_ok() {
        response?.then()?.statusCode(HttpStatus.OK.value())
    }

    @And("body must contain key {string} and value {string}")
    fun body_must_contain_key_and_value(key: String?, value: String?) {
        response?.then()?.body(key, equalTo(value))
    }

    @And("response must have {string}")
    @Throws(Exception::class)
    fun response_should_be_array(key: String?) {
        response?.then()?.body(key, notNullValue())
    }
}