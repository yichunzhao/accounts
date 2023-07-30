package com.ynz.demo.accounts.transaction.service;

import com.ynz.demo.accounts.balance.domain.BalanceRepository;
import com.ynz.demo.accounts.transaction.domian.TransactionEntity;
import com.ynz.demo.accounts.transaction.domian.TransactionRepository;
import com.ynz.demo.accounts.transaction.presentation.TransactionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TransactionService {

  private TransactionRepository transactionRepository;

  // Todo: should invoking RestAPI from Balance context.
  private BalanceRepository balanceRepository;

  @Autowired
  public TransactionService(
      TransactionRepository transactionRepository, BalanceRepository balanceRepository) {
    this.transactionRepository = transactionRepository;
    this.balanceRepository = balanceRepository;
  }

  public Long processTransaction(TransactionRequestDto transactionRequestDto) {
    int typeOfTransaction = transactionRequestDto.getType();
    Long accountId = transactionRequestDto.getAccountId();
    BigDecimal amount = transactionRequestDto.getAmount();

    TransactionEntity transactionEntity = null;
    if (typeOfTransaction == -1) {
      transactionEntity =
          createTransactionEntity(accountId, typeOfTransaction, amount, LocalDateTime.now());
    }

    if (typeOfTransaction == 1) {
      transactionEntity =
          createTransactionEntity(accountId, typeOfTransaction, amount, LocalDateTime.now());
    }

    Long persistedTransactionId = null;
    if (transactionEntity != null) {
      TransactionEntity persisted = transactionRepository.save(transactionEntity);
      persistedTransactionId = persisted.getTransactionId();
    }

    return persistedTransactionId;
  }

  public List<TransactionEntity> getLast10Transactions(Long accountId) {
    return transactionRepository.findTop10ByAccountIdOrderByCreatedAtDesc(accountId);
  }

  TransactionEntity createTransactionEntity(
      Long accountId, int transactionType, BigDecimal amount, LocalDateTime createdAt) {
    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setAmount(amount);
    transactionEntity.setType(transactionType);
    transactionEntity.setAccountId(accountId);
    transactionEntity.setCreatedAt(createdAt);

    return transactionEntity;
  }
}
