package com.ynz.demo.accounts.balance.presentation;

import javax.validation.constraints.Positive;

public class BalanceRequestDto {

  @Positive(message = "accountId must be positive.")
  private Long accountId;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
}
