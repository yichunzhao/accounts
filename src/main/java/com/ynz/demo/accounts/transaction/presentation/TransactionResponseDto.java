package com.ynz.demo.accounts.transaction.presentation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDto {

  private Long accountId;

  private int type;

  private BigDecimal amount;

  private LocalDateTime createdAt;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
