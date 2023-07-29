package com.ynz.demo.accounts.balance.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {
  Optional<BalanceEntity> findByAccountId(Long accountId);

  Optional<BalanceEntity> findFirstByAccountIdOrderByLastUpdatedAtDesc(Long accountId);
}
