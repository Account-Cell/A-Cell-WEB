package com.lastdance.api.converter;

import com.lastdance.db.entity.Transactions;
import com.lastdance.api.dto.TransactionsDTO;

public class TransactionsConverter {
    public static TransactionsDTO toDTO(Transactions transaction) {
        return new TransactionsDTO(
                transaction.getAccountHistoryId(),
                transaction.getAccountId(),
                transaction.getTransferDate(),
                transaction.getTransferTarget(),
                transaction.getTransferAmount(),
                transaction.getRemainingAmount()
        );
    }

    public static Transactions toEntity(TransactionsDTO transactionsDTO) {
        Transactions transaction = new Transactions();
        transaction.setAccountHistoryId(transactionsDTO.accountHistoryId());
        transaction.setAccountId(transactionsDTO.accountId());
        transaction.setTransferDate(transactionsDTO.transferDate());
        transaction.setTransferTarget(transactionsDTO.transferTarget());
        transaction.setTransferAmount(transactionsDTO.transferAmount());
        transaction.setRemainingAmount(transactionsDTO.remainingAmount());
        return transaction;
    }
}
