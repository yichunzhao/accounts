package com.ynz.demo.accounts.transaction.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TransactionControllerTest {

  @LocalServerPort private int port;

  @Autowired private TransactionController transactionController;

  @Test
  public void testProcessATransaction_ValidInput_Returns200() {
    TransactionRequestDto validTransaction = new TransactionRequestDto();
    validTransaction.setAccountId(12345L);
    validTransaction.setType(1);
    validTransaction.setAmount(BigDecimal.valueOf(100));

    given()
        .port(port)
        .contentType("application/json")
        .body(validTransaction)
        .when()
        .post("/transactions")
        .then()
        .statusCode(200);
  }

  @Test
  public void testProcessATransaction_InvalidInput_Returns400WithValidationErrors() {
    TransactionRequestDto invalidTransaction = new TransactionRequestDto();
    invalidTransaction.setAccountId(null);
    invalidTransaction.setType(3); // Invalid type
    invalidTransaction.setAmount(BigDecimal.valueOf(-100)); // Invalid amount

    given()
        .port(port)
        .contentType("application/json")
        .body(invalidTransaction)
        .when()
        .post("/transactions")
        .then()
        .statusCode(400);
  }
}
