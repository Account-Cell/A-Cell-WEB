package com.lastdance.api.controller;

import com.lastdance.api.dto.AccountDTO;
import com.lastdance.api.dto.CreateAccountDTO;
import com.lastdance.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public AccountDTO getAccount(@PathVariable String accountId) {
        return accountService.getAccountById(accountId);
    }

    @PostMapping("/create")
    public AccountDTO createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
        return accountService.createAccount(createAccountDTO);
    }

    @GetMapping("/list")
    public List<AccountDTO> listAccounts() {
        return accountService.listAllAccounts();
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
    }

    @PostMapping("/{accountId}/deposit")
    public String deposit(@PathVariable String accountId, @RequestParam BigDecimal amount) {
        return accountService.deposit(accountId, amount);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String fromAccountId, @RequestParam String toAccountId, @RequestParam BigDecimal amount) {
        return accountService.transfer(fromAccountId, toAccountId, amount);
    }
}
