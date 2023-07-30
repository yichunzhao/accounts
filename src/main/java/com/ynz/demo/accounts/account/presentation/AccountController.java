package com.ynz.demo.accounts.account.presentation;

import com.ynz.demo.accounts.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountController {

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping("/accounts")
  public ResponseEntity<?> createAccountForCustomer(
      @Valid @RequestBody AccountDto accountDto, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      // Handle validation errors and return a custom error response with error details.
      return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
    }

    return ResponseEntity.ok(accountService.createAccount(accountDto));
  }
}
