package com.ynz.demo.accounts.balance.service;

import com.ynz.demo.accounts.balance.domain.BalanceEntity;
import com.ynz.demo.accounts.balance.domain.BalanceRepository;
import com.ynz.demo.accounts.transaction.domian.TransactionEntity;
import com.ynz.demo.accounts.transaction.domian.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class BalanceService {

  private BalanceRepository balanceRepository;

  private TransactionRepository transactionRepository;

  @Autowired
  public BalanceService(BalanceRepository balanceRepository) {
    this.balanceRepository = balanceRepository;
  }

  BalanceEntity getCurrentBalanceByAccountId(Long accountId) {

    // find the latest balance entry for this accountId.
    BalanceEntity historicBalanceEntity =
        balanceRepository.findFirstByAccountIdOrderByLastUpdatedAtDesc(accountId).orElse(null);

    // query the latest transactions since the lastUpdateAt.
    LocalDateTime lastUpdatedAt = historicBalanceEntity.getLastUpdatedAt();
    List<TransactionEntity> transactionEntityList =
        transactionRepository.findByAccountIdAndCreatedAtAfter(accountId, lastUpdatedAt);

    BigDecimal currentBalance = historicBalanceEntity.getBalance();

    // earliest at index 0
    transactionEntityList.sort(Comparator.comparing(TransactionEntity::getCreatedAt));

    BalanceEntity newBalanceEntity = null;
    // creating balance entry for each transaction.
    for (TransactionEntity transactionEntity : transactionEntityList) {

      BigDecimal amount = transactionEntity.getAmount();

      // -1: withdraw ; +1 : deposit, converting BigDecimal.
      BigDecimal transactionType = BigDecimal.valueOf(transactionEntity.getType());

      // the transaction creates a new balance
      currentBalance = currentBalance.add(amount.multiply(transactionType));

      // creating a new Balance entry in DB.
      newBalanceEntity = new BalanceEntity();

      newBalanceEntity.setBalance(currentBalance);
      newBalanceEntity.setAccountId(accountId);
      newBalanceEntity.setLastUpdatedAt(LocalDateTime.now());

      balanceRepository.save(newBalanceEntity);
    }

    return newBalanceEntity != null ? newBalanceEntity : historicBalanceEntity;
  }


}
