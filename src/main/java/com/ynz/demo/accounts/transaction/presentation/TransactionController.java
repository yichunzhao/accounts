package com.ynz.demo.accounts.transaction.presentation;

import com.ynz.demo.accounts.transaction.domian.TransactionEntity;
import com.ynz.demo.accounts.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

  private TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/transactions")
  public ResponseEntity<?> processATransaction(
      @Valid @RequestBody TransactionRequestDto transactionRequestDto,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
    }

    Long persistedTransactionId = transactionService.processTransaction(transactionRequestDto);

    return ResponseEntity.ok(persistedTransactionId);
  }

  @GetMapping("/transactions/{accountId}")
  public ResponseEntity<?> getAccountLast10TransactionsById(
      @PathVariable("accountId") long accountId) {

    List<TransactionEntity> transactionEntityList =
        transactionService.getLast10Transactions(accountId);

    List<TransactionResponseDto> dtoList =
        transactionEntityList.stream()
            .map(this::convertToTransactionDto)
            .collect(Collectors.toList());

    return ResponseEntity.ok(dtoList);
  }

  private TransactionResponseDto convertToTransactionDto(TransactionEntity transactionEntity) {
    TransactionResponseDto dto = new TransactionResponseDto();
    dto.setAccountId(transactionEntity.getAccountId());
    dto.setAmount(transactionEntity.getAmount());
    dto.setType(transactionEntity.getType());
    dto.setCreatedAt(transactionEntity.getCreatedAt());
    return dto;
  }
}
