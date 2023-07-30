package com.ynz.demo.accounts.account.service;

import com.ynz.demo.accounts.account.domain.AccountEntity;
import com.ynz.demo.accounts.account.domain.AccountRepository;
import com.ynz.demo.accounts.account.presentation.AccountDto;
import com.ynz.demo.accounts.balance.domain.BalanceEntity;
import com.ynz.demo.accounts.balance.domain.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService {
  private final AccountRepository accountRepository;

  //TODO: using restTemplate to create balance via RestAPI.
  private final BalanceRepository balanceRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository, BalanceRepository balanceRepository) {
    this.accountRepository = accountRepository;
    this.balanceRepository = balanceRepository;
  }

  public AccountEntity findAccountById(Long accountId) {
    return accountRepository.findById(accountId).orElse(null);
  }

  public Long createAccount(AccountDto accountDto) {
    // TODO: should check its existence first.
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setName(accountDto.getName());
    accountEntity.setCreatedAt(LocalDateTime.now());

    AccountEntity persisted = accountRepository.save(accountEntity);

    // setup initial balance, zero state.
    BalanceEntity balanceEntity = new BalanceEntity();
    balanceEntity.setLastUpdatedAt(LocalDateTime.now());
    balanceEntity.setAccountId(persisted.getId());

    balanceRepository.save(balanceEntity);

    return persisted.getId();
  }
}
