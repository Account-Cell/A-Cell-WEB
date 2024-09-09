package com.lastdance.api.controller;

import com.lastdance.api.dto.TransactionsDTO;
import com.lastdance.api.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("/{accountHistoryId}")
    public TransactionsDTO getTransaction(@PathVariable Long accountHistoryId) {
        return transactionsService.getTransactionById(accountHistoryId);
    }

    @GetMapping("/account/{accountId}")
    public List<TransactionsDTO> getTransactionsByAccountId(@PathVariable String accountId) {
        return transactionsService.getTransactionsByAccountId(accountId);
    }
}
