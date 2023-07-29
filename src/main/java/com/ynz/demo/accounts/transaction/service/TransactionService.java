package com.ynz.demo.accounts.transaction.service;

import com.ynz.demo.accounts.balance.domain.BalanceRepository;
import com.ynz.demo.accounts.transaction.domian.TransactionEntity;
import com.ynz.demo.accounts.transaction.domian.TransactionRepository;
import com.ynz.demo.accounts.transaction.presentation.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class TransactionService {

  private TransactionRepository transactionRepository;
  private BalanceRepository balanceRepository;

  @Autowired
  public TransactionService(
      TransactionRepository transactionRepository, BalanceRepository balanceRepository) {
    this.transactionRepository = transactionRepository;
    this.balanceRepository = balanceRepository;
  }

  public Long processTransaction(TransactionDto transactionDto) {
    int typeOfTransaction = transactionDto.getType();
    Long accountId = transactionDto.getAccountId();
    BigDecimal amount = transactionDto.getAmount();

    TransactionEntity transactionEntity = null;
    if (typeOfTransaction == -1) {
      transactionEntity = createTransactionEntity(accountId, typeOfTransaction, amount);
    }

    if (typeOfTransaction == 1) {
      transactionEntity = createTransactionEntity(accountId, typeOfTransaction, amount);
    }

    Long persistedTransactionId = null;
    if (transactionEntity != null) {
      TransactionEntity persisted = transactionRepository.save(transactionEntity);
      persistedTransactionId = persisted.getTransactionId();
    }

    return persistedTransactionId;
  }

  TransactionEntity createTransactionEntity(
      Long accountId, int transactionType, BigDecimal amount) {
    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setAmount(amount);
    transactionEntity.setType(transactionType);
    transactionEntity.setAccountId(accountId);

    return transactionEntity;
  }
}
