package com.lastdance.api.service;

import com.lastdance.api.dto.TransactionsDTO;
import com.lastdance.db.entity.Transactions;
import com.lastdance.db.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    public TransactionsDTO getTransactionById(Long accountHistoryId) {
        Transactions transaction = transactionsRepository.findById(accountHistoryId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return new TransactionsDTO(
                transaction.getAccountHistoryId(),
                transaction.getAccountId(),
                transaction.getTransferDate(),
                transaction.getTransferTarget(),
                transaction.getTransferAmount(),
                transaction.getRemainingAmount()
        );
    }

    public List<TransactionsDTO> getTransactionsByAccountId(String accountId) {
        List<Transactions> transactions = transactionsRepository.findByAccountId(accountId);
        return transactions.stream()
                .map(transaction -> new TransactionsDTO(
                        transaction.getAccountHistoryId(),
                        transaction.getAccountId(),
                        transaction.getTransferDate(),
                        transaction.getTransferTarget(),
                        transaction.getTransferAmount(),
                        transaction.getRemainingAmount()
                ))
                .collect(Collectors.toList());
    }

}
