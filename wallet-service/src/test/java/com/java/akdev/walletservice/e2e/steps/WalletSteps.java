package com.java.akdev.walletservice.e2e.steps;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.ContentTypeHeader;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wiremock.org.apache.hc.client5.http.entity.mime.Header;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class WalletSteps extends E2eTestBase {

    Response response;
    private final String uri = "http://localhost:8086";
    private String requestPayload;
    private String updatePayload;

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

        stubFor(get(urlEqualTo("/api/v1/drivers/1826829b-d77a-4908-b1b4-94cf5346a038"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                            "firstName": "2344352",
                            "lastName": 10000000,
                            "username": "1826829b-d77a-4908-b1b4-94cf5346a038"
                        }
                        """)));
    }

    @Given("I have a wallet payload")
    public void i_have_a_wallet_payload() {
        requestPayload = """
                {
                    "cardNumber": "2344352",
                    "amount": 10000000,
                    "userId": "1826829b-d77a-4908-b1b4-94cf5346a038",
                    "walletOwner": "PASSENGER"
                }
                """;
    }

    @Given("I have a wallet update payload")
    public void i_have_a_wallet_update_payload() {
        updatePayload = """
                {
                    "cardNumber": "2344352",
                    "amount": 10000000,
                    "userId": "1826829b-d77a-4908-b1b4-94cf5346a038",
                    "walletOwner": "PASSENGER"
                }
                """;
    }

    @Given("wallet with id {string}")
    public void wallet_with_id(String driverId) throws Exception {
        response = given()
                .baseUri(uri)
                .get("/wallets/{id}", driverId);
    }

    @When("I send a DELETE request to URL {string}")
    public void i_send_delete_request_to_url(String endpoint) {
        response = given()
                .baseUri(uri)
                .delete(endpoint);
    }

    @When("I send a POST request to URL {string}")
    public void i_send_a_post_request_to_url(String endpoint) throws Exception {
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
