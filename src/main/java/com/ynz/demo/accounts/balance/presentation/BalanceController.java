package com.ynz.demo.accounts.balance.presentation;

import com.ynz.demo.accounts.balance.domain.BalanceEntity;
import com.ynz.demo.accounts.balance.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BalanceController {

  private BalanceService balanceService;

  @Autowired
  public BalanceController(BalanceService balanceService) {
    this.balanceService = balanceService;
  }

  @PostMapping("/balances")
  public ResponseEntity<?> getAccountBalance(
      @Valid @RequestBody BalanceRequestDto balanceRequestDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
    }

    Long accountId = balanceRequestDto.getAccountId();
    BalanceEntity balanceEntity = balanceService.getCurrentBalanceByAccountId(accountId);

    if (balanceEntity == null) {
      return ResponseEntity.notFound().build();
    }

    BalanceResponseDto balanceResponseDto = new BalanceResponseDto();
    balanceResponseDto.setBalance(balanceEntity.getBalance());
    balanceResponseDto.setAccountId(accountId);
    balanceResponseDto.setBalanceDateTime(balanceEntity.getLastUpdatedAt());

    return ResponseEntity.ok(balanceResponseDto);
  }
}
