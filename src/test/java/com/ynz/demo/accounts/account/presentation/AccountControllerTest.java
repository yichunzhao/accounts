package com.ynz.demo.accounts.account.presentation;

import com.ynz.demo.accounts.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountControllerTest {

  @LocalServerPort private int port;

  @Autowired private AccountService accountService;

  @Test
  void testCreateAccountForCustomerWithValidInput() {
    AccountDto accountDto = new AccountDto();
    accountDto.setName("Test User");

    given()
        .port(port)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(accountDto)
        .when()
        .post("/accounts")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(is(notNullValue()));
  }

  @Test
  void testCreateAccountForCustomerWithInvalidInput() {
    AccountDto accountDto = new AccountDto();

    given()
        .port(port)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(accountDto)
        .when()
        .post("/accounts")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }
}
