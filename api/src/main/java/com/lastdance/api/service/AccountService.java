package com.lastdance.api.service;

import com.lastdance.api.dto.AccountDTO;
import com.lastdance.api.dto.CreateAccountDTO;
import com.lastdance.db.entity.Account;
import com.lastdance.db.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO getAccountById(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return new AccountDTO(
                account.getAccountId(),
                account.getUserId(),
                account.getAccountPw(),
                account.getBalance(),
                account.getCreateAt()
        );
    }

    public AccountDTO createAccount(CreateAccountDTO createAccountDTO) {
        Account account = new Account();
        account.setAccountId(generateAccountId()); // 계좌 ID 생성
        account.setUserId(createAccountDTO.getUserId());
        account.setAccountPw(createAccountDTO.getAccountPassword());
        account.setBalance(BigDecimal.ZERO); // 초기 잔액 0
        account.setCreateAt(LocalDateTime.now());

        accountRepository.save(account);
        return new AccountDTO(
                account.getAccountId(),
                account.getUserId(),
                account.getAccountPw(),
                account.getBalance(),
                account.getCreateAt()
        );
    }

    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> new AccountDTO(
                        account.getAccountId(),
                        account.getUserId(),
                        account.getAccountPw(),
                        account.getBalance(),
                        account.getCreateAt()
                ))
                .collect(Collectors.toList());
    }

    public List<AccountDTO> listAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> new AccountDTO(
                        account.getAccountId(),
                        account.getUserId(),
                        account.getAccountPw(),
                        account.getBalance(),
                        account.getCreateAt()
                ))
                .toList();
    }

    public void deleteAccount(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.delete(account);
    }

    public String deposit(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        account.setBalance(account.getBalance().add(amount));
        account.setUpdateAt(LocalDateTime.now());

        accountRepository.save(account);
        return String.format("입금 되었습니다. 남은 잔액은 %s입니다.", account.getBalance());
    }

    public String transfer(String fromAccountId, String toAccountId, BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            return String.format("잔액이 부족합니다. 잔액은 %s입니다.", fromAccount.getBalance());
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        LocalDateTime now = LocalDateTime.now();
        fromAccount.setUpdateAt(now);
        toAccount.setUpdateAt(now);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return String.format("송금이 되었습니다. 남은 잔액은 %s입니다.", fromAccount.getBalance());
    }

    private String generateAccountId() {
        // UUID를 사용하여 계좌 ID 생성
        return java.util.UUID.randomUUID().toString();
    }
}
