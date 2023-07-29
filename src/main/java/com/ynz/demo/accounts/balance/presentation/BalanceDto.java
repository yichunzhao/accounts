package com.ynz.demo.accounts.balance.presentation;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class BalanceDto {

    private Long accountId;
    private BigDecimal balance;
    private ZonedDateTime balanceDateTime;

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

    public ZonedDateTime getBalanceDateTime() {
        return balanceDateTime;
    }

    public void setBalanceDateTime(ZonedDateTime balanceDateTime) {
        this.balanceDateTime = balanceDateTime;
    }
}
