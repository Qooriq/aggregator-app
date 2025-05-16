package com.java.akdev.driverservice.e2e.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class DriverSteps {

    Response response;
    private final String uri = "http://localhost:8084";
    private String requestPayload;

//    @Given("I have a driver payload")
//    public void i_have_a_driver_payload() {
//        requestPayload = """
//                {
//                    "firstName": "Anton",
//                    "lastName": "Kazlouski",
//                    "username": "asuha@example.com",
//                    "password": "123"
//                }
//                """;
//    }
//
//    @When("I send a POST request to URL {string}")
//    public void i_send_a_post_request_to_ur(String endpoint) throws Exception {
//        response = given()
//                .baseUri(uri)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(requestPayload)
//                .post(endpoint);
//    }
//
//    @Then("status code should be created")
//    public void status_code_should_be_created() throws Exception {
//        response.then().statusCode(HttpStatus.CREATED.value());
//    }

    @When("I send a GET request to URL {string}")
    public void iSendAGETRequestToURL(String endpoint) throws Exception {
        response = given()
                .baseUri(uri)
                .get(endpoint);
    }

    @Then("status code should be ok")
    public void statusCode_should_be_ok() throws Exception {
        response.then().statusCode(HttpStatus.OK.value());
    }

    @And("response must be {string}")
    public void response_should_be_array(String key) throws Exception {
        response.then().body(key, notNullValue());
    }
}
