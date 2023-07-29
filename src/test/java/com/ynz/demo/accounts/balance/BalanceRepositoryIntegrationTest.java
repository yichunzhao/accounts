package com.ynz.demo.accounts.balance;

import com.ynz.demo.accounts.balance.domain.BalanceEntity;
import com.ynz.demo.accounts.balance.domain.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BalanceRepositoryIntegrationTest {

  private final BalanceRepository balanceRepository;
  private final TestEntityManager entityManager;

  @Autowired
  BalanceRepositoryIntegrationTest(
      BalanceRepository balanceRepository, TestEntityManager entityManager) {
    this.balanceRepository = balanceRepository;
    this.entityManager = entityManager;
  }

  @Test
  public void testFindByAccountId() {
    Long accountId = 1L;
    BalanceEntity balanceEntity = new BalanceEntity();
    balanceEntity.setAccountId(accountId);
    balanceEntity.setBalance(BigDecimal.valueOf(100));
    balanceEntity.setLastUpdatedAt(LocalDateTime.now());

    entityManager.persist(balanceEntity);
    entityManager.flush();

    Optional<BalanceEntity> result = balanceRepository.findByAccountId(accountId);

    assertThat(result).isPresent();
    assertThat(result.get().getAccountId()).isEqualTo(accountId);
  }

  @Test
  public void testFindLatestBalanceByAccountId() {
    Long accountId = 2L;
    LocalDateTime now = LocalDateTime.parse("2023-05-03T12:00:00");

    for (int i = 0; i < 3; i++) {

      BalanceEntity balanceEntity = new BalanceEntity();
      balanceEntity.setAccountId(accountId);
      balanceEntity.setBalance(BigDecimal.valueOf(100 * i));
      balanceEntity.setLastUpdatedAt(now);
      now = now.plusDays(1L);

      entityManager.persist(balanceEntity);
    }

    entityManager.flush();

    Optional<BalanceEntity> balanceEntityOptional =
        balanceRepository.findFirstByAccountIdOrderByLastUpdatedAtDesc(accountId);
    assertThat(balanceEntityOptional).isPresent();

    BalanceEntity balanceEntity = balanceEntityOptional.get();
    LocalDateTime latestLastUpdateAt = balanceEntity.getLastUpdatedAt();
    assertThat(latestLastUpdateAt.getDayOfMonth()).isEqualTo(5);
  }
}
