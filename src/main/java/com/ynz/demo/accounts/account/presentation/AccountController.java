package com.ynz.demo.accounts.account.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynz.demo.accounts.account.servoce.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountController {

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping("/accounts")
  public ResponseEntity<?> createAccountForCustomer(
      @Valid @RequestBody AccountDto accountDto, BindingResult bindingResult) throws JsonProcessingException {

    if (bindingResult.hasErrors()) {
      // Handle validation errors and return a custom error response with error details.
      //return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());

      Map<String, String> fieldErrors = new HashMap<>();
      fieldErrors.put("validation errors:","" );
      bindingResult.getFieldErrors().forEach(fieldError -> {
        fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
      });

      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString = objectMapper.writeValueAsString(fieldErrors);

      return ResponseEntity.badRequest().body(jsonString);
    }

    return ResponseEntity.ok(accountService.createAccount(accountDto));
  }
}
