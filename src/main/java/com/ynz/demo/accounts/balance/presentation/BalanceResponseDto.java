package com.ynz.demo.accounts.balance.presentation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BalanceResponseDto {

  private Long accountId;
  private BigDecimal balance;
  private LocalDateTime balanceDateTime;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public LocalDateTime getBalanceDateTime() {
    return balanceDateTime;
  }

  public void setBalanceDateTime(LocalDateTime balanceDateTime) {
    this.balanceDateTime = balanceDateTime;
  }
}
