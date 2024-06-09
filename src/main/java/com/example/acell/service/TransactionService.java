package com.example.acell.service;

import com.example.acell.domain.Account;
import com.example.acell.domain.AccountUser;
import com.example.acell.domain.Transaction;
import com.example.acell.dto.TransactionDto;
import com.example.acell.exception.AccountException;
import com.example.acell.repository.AccountRepository;
import com.example.acell.repository.AccountUserRepository;
import com.example.acell.repository.TransactionRepository;
import com.example.acell.type.AccountStatus;
import com.example.acell.type.ErrorCode;
import com.example.acell.type.TransactionResultType;
import com.example.acell.type.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountUserRepository accountUserRepository;
    private final AccountRepository accountRepository;

    /**
     * 사용자가 없는 경우, 사용자 아이디와 계좌 소유주가 다른 경우
     * 계좌가 이미 해지 상태인 경우, 거래 금액이 잔액보다 큰 경우
     * 거래 금액이 너무 작거나 큰 경우 실패 응답
     */
    @Transactional
    public TransactionDto useBalance(Long userId, String accountNumber, Long amount) {
        AccountUser user = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        validateUseBalance(user, account, amount);

        account.useBalance(amount);

        return TransactionDto.fromEntity(saveAndGetTransaction(TransactionType.USE, TransactionResultType.S, account, amount));
    }

    private void validateUseBalance(AccountUser user, Account account, Long amount) {
        if (!Objects.equals(user.getId(), account.getAccountUser().getId())) {
            throw new AccountException(ErrorCode.USER_ACCOUNT_UN_MATCH);
        }
        if (account.getAccountStatus() != AccountStatus.IN_USE) {
            throw new AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED);
        }
        if (account.getBalance() < amount) {
            throw new AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        }
    }

    @Transactional
    public void saveFailedUseTransaction(String accountNumber, Long amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        saveAndGetTransaction(TransactionType.USE, TransactionResultType.F, account, amount);
    }

    private Transaction saveAndGetTransaction(TransactionType transactionType, TransactionResultType transactionResultType, Account account, Long amount) {
        return transactionRepository.save(Transaction.builder()
                .transactionType(transactionType)
                .transactionResultType(transactionResultType)
                .account(account)
                .amount(amount)
                .balanceSnapShot(account.getBalance())
                .transactionId(UUID.randomUUID().toString().replace("-", ""))
                .transactedAt(LocalDateTime.now())
                .build()
        );
    }

    @Transactional
    public TransactionDto cancelBalance(String transactionId, String accountNumber, Long amount) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new AccountException(ErrorCode.TRANSACTION_NOT_FOUND));
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        validateCancelBalance(transaction, account, amount);

        account.cancelBalance(amount);

        return TransactionDto.fromEntity(saveAndGetTransaction(TransactionType.CANCEL, TransactionResultType.S, account, amount));
    }

    private void validateCancelBalance(Transaction transaction, Account account, Long amount) {
        if (!Objects.equals(transaction.getAccount().getId(), account.getId())) {
            throw new AccountException(ErrorCode.TRANSACTION_ACCOUNT_UN_MATCH);
        }
        if (!Objects.equals(transaction.getAmount(), amount)) {
            throw new AccountException(ErrorCode.CANCEL_MUST_FULLY);
        }
        if (transaction.getTransactedAt().isBefore(LocalDateTime.now().minusYears(1))) {
            throw new AccountException(ErrorCode.TOO_OLD_ORDER_TO_CANCEL);
        }
    }

    @Transactional
    public void saveFailedCancelTransaction(String accountNumber, Long amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        saveAndGetTransaction(TransactionType.CANCEL, TransactionResultType.F, account, amount);
    }

    public TransactionDto queryTransaction(String transactionId) {
        return TransactionDto.fromEntity(
                transactionRepository.findByTransactionId(transactionId)
                        .orElseThrow(() -> new AccountException(ErrorCode.TRANSACTION_NOT_FOUND))
        );
    }
}
