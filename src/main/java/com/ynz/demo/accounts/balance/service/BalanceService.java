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

  // TODO: invoking restApi to access Transaction context.
  private TransactionRepository transactionRepository;

  @Autowired
  public BalanceService(
      BalanceRepository balanceRepository, TransactionRepository transactionRepository) {
    this.balanceRepository = balanceRepository;
    this.transactionRepository = transactionRepository;
  }

  public BalanceEntity getCurrentBalanceByAccountId(Long accountId) {

    // find the latest balance entry for this accountId; if accountId existed, at least getting an
    // initial balance.
    BalanceEntity historicBalanceEntity =
        balanceRepository.findFirstByAccountIdOrderByLastUpdatedAtDesc(accountId).orElse(null);

    // this means the accountId is not existed.
    if (historicBalanceEntity == null) {
      // by default, an existing account must have an initial balance.
      // if accountId not existed yet, then return null Balance.
      return null;
    }

    // if it is an existing accountId, query the latest transactions since the lastUpdateAt.
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

      // for a transaction, creating a balance for it.
      currentBalance = currentBalance.add(amount.multiply(transactionType));

      // creating a new Balance entry in DB.
      newBalanceEntity = new BalanceEntity();

      newBalanceEntity.setBalance(currentBalance);
      newBalanceEntity.setAccountId(accountId);
      newBalanceEntity.setLastUpdatedAt(LocalDateTime.now());

      balanceRepository.save(newBalanceEntity);
    }

    // if newBalanceEntity is still null, then there is no any new transactions yet, then no need to
    // update the balance.
    return newBalanceEntity != null ? newBalanceEntity : historicBalanceEntity;
  }
}
