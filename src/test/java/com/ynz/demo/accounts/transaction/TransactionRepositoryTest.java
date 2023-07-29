package com.ynz.demo.accounts.transaction;

import com.ynz.demo.accounts.transaction.domian.TransactionEntity;
import com.ynz.demo.accounts.transaction.domian.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {

  private final TransactionRepository transactionRepository;

  private final TestEntityManager testEntityManager;

  @Autowired
  public TransactionRepositoryTest(
      TransactionRepository transactionRepository, TestEntityManager testEntityManager) {
    this.transactionRepository = transactionRepository;
    this.testEntityManager = testEntityManager;
  }

  @Test
  public void
      testFindByAccountIdAndCreatedAtAfter_IfLastUpdatedAtBeforeAllCreatedAt_ThenGetSize2() {
    Long accountId = 2L;

    for (TransactionEntity transactionEntity : getTransactionEntitiesForTest(accountId)) {
      testEntityManager.persist(transactionEntity);
    }
    testEntityManager.flush();

    List<TransactionEntity> result =
        transactionRepository.findByAccountIdAndCreatedAtAfter(
            accountId, LocalDateTime.now().minusDays(3));

    assertThat(result).hasSize(2);

    result =
        transactionRepository.findByAccountIdAndCreatedAtAfter(
            accountId, LocalDateTime.now().plusMinutes(1));
    assertThat(result).isEmpty();
  }

  @Test
  public void
      testFindByAccountIdAndCreatedAtAfter_IfLastUpdatedAtBehindAllCreatedAt_ThenGetSizeZero() {
    Long accountId = 2L;

    getTransactionEntitiesForTest(accountId)
        .forEach(transactionEntity -> testEntityManager.persist(transactionEntity));
    testEntityManager.flush();

    List<TransactionEntity> result =
        transactionRepository.findByAccountIdAndCreatedAtAfter(
            accountId, LocalDateTime.now().plusMinutes(1));

    assertThat(result).isEmpty();
  }

  @Test
  public void
      testFindByAccountIdAndCreatedAtAfter_IfLastUpdatedAtBehindCreatedAtNowALittleBit_ThenGetSizeOne() {
    Long accountId = 2L;

    getTransactionEntitiesForTest(accountId)
        .forEach(transactionEntity -> testEntityManager.persist(transactionEntity));
    testEntityManager.flush();

    List<TransactionEntity> result =
        transactionRepository.findByAccountIdAndCreatedAtAfter(
            accountId, LocalDateTime.now().minusMinutes(1));

    assertThat(result).hasSize(1);
  }

  @Test
  public void testPersistingTransactions_ThenMayFindThemBack() {
    Long accountId = 2L;
    List<TransactionEntity> transactionEntityList = getTransactionEntitiesForTest(accountId);

    List<TransactionEntity> savedList = new ArrayList<>();
    for (TransactionEntity entity : transactionEntityList) {
      TransactionEntity saved = transactionRepository.save(entity);
      savedList.add(saved);
    }

    List<Long> ids =
        savedList.stream().map(TransactionEntity::getTransactionId).collect(Collectors.toList());

    for (Long id : ids) {
      TransactionEntity foundTransactionEntity =
          testEntityManager.find(TransactionEntity.class, id);
      assertThat(foundTransactionEntity).isNotNull();
    }
  }

    @Test
    public void testMayGet10TopTransactions() {
      Long accountId = 2L;

      LocalDateTime now = LocalDateTime.now();

      // process 20 transactions for this account
      for (int i = 1; i <= 20; i++) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setCreatedAt(now.minusMinutes(i));
        transactionEntity.setType(i % 2 == 0 ? -1 : 1);
        transactionEntity.setAccountId(accountId);
        transactionEntity.setAmount(new BigDecimal(10 * i));
        testEntityManager.persist(transactionEntity);
      }

      testEntityManager.flush();

      List<TransactionEntity> last10Transactions =
          transactionRepository.findTop10ByAccountIdOrderByCreatedAtDesc(accountId);
      assertThat(last10Transactions).hasSize(10);

      assertThat(last10Transactions).isSortedAccordingTo((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt())); // Should be sorted in descending order of createdAt
      assertThat(last10Transactions).allMatch(t -> t.getAccountId().equals(accountId)); // Should belong to the specified account ID

    }

  private List<TransactionEntity> getTransactionEntitiesForTest(Long accountId) {

    TransactionEntity transactionEntity1 = new TransactionEntity();
    transactionEntity1.setAccountId(accountId);
    transactionEntity1.setType(1);
    transactionEntity1.setAmount(BigDecimal.valueOf(3000));
    transactionEntity1.setCreatedAt(LocalDateTime.now());

    TransactionEntity transactionEntity2 = new TransactionEntity();
    transactionEntity2.setAccountId(accountId);
    transactionEntity2.setAmount(BigDecimal.valueOf(2000));
    transactionEntity2.setType(-1);
    transactionEntity2.setCreatedAt(LocalDateTime.now().minusDays(1));

    return Arrays.asList(transactionEntity1, transactionEntity2);
  }
}
