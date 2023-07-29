package com.ynz.demo.accounts.transaction.presentation;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

public final class TransactionDto {

  @Min(value = 1, message = "AccountId must be a value bigger than zero")
  private Long accountId;

  @Min(value = -1, message = "Type must be -1")
  @Max(value = 1, message = "Type must be 1")
  private int type;

  @DecimalMin(value = "0.01", message = "cannot having a money amount lessThan one cent.")
  private BigDecimal amount;

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
}
