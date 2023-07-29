package com.ynz.demo.accounts.transaction.domian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

  List<TransactionEntity> findByAccountIdAndCreatedAtAfter(
      Long accountId, LocalDateTime lastUpdatedAt);

  List<TransactionEntity> findTop10ByAccountIdOrderByCreatedAtDesc(Long accountId);
}
