package com.ynz.demo.accounts.balance.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)


class BalanceControllerTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void testGetAccountBalance_ValidInput_ButNotExist_Returns404() {
    // Prepare the request data
    BalanceRequestDto validBalanceRequest = new BalanceRequestDto();
    validBalanceRequest.setAccountId(12345L);

    // Make the REST API call and assert the response
    given()
        .port(port) // Include the port here
        .contentType("application/json")
        .body(validBalanceRequest)
        .when()
        .get("/transaction")
        .then()
        .statusCode(404);
  }
}
