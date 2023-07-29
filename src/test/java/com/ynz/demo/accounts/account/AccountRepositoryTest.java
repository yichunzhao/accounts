package com.ynz.demo.accounts.account;

import com.ynz.demo.accounts.account.domain.AccountEntity;
import com.ynz.demo.accounts.account.domain.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class AccountRepositoryTest {

  private final AccountRepository accountRepository;

  private final TestEntityManager testEntityManager;

  @Autowired
  public AccountRepositoryTest(
      AccountRepository accountRepository, TestEntityManager testEntityManager) {
    this.accountRepository = accountRepository;
    this.testEntityManager = testEntityManager;
  }

  @Test
  public void createAccount() {
    String expectedName = "Test Me";
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setCreatedAt(LocalDateTime.now());
    accountEntity.setName(expectedName);

    AccountEntity persisted = accountRepository.save(accountEntity);
    assertThat(persisted.getId()).isNotNull();

    AccountEntity found = testEntityManager.find(AccountEntity.class, persisted.getId());
    assertThat(found.getName()).isEqualTo(expectedName);
  }
}
