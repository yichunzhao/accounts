package com.ynz.demo.accounts.account.servoce;

import com.ynz.demo.accounts.account.domain.AccountEntity;
import com.ynz.demo.accounts.account.domain.AccountRepository;
import com.ynz.demo.accounts.account.presentation.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public AccountEntity findAccountById(Long accountId) {
    return accountRepository.findById(accountId).orElse(null);
  }

  public Long createAccount(AccountDto accountDto) {
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setName(accountDto.getName());

    AccountEntity persisted = accountRepository.save(accountEntity);

    return persisted.getId();
  }
}
