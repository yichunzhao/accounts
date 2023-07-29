package com.ynz.demo.accounts.transaction.presentation;

import com.ynz.demo.accounts.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionController {

  private TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/transactions")
  public ResponseEntity<?> processATransaction(
      @Valid @RequestBody TransactionDto transactionDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // Handle validation errors and return a custom error response
      // or use the default error response provided by Spring.
        return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
    }

    Long persistedTransactionId = transactionService.processTransaction(transactionDto);

    return ResponseEntity.ok(persistedTransactionId);
  }
}
