package com.lastdance.api.converter;

import com.lastdance.db.entity.Account;
import com.lastdance.api.dto.AccountDTO;

public class AccountConverter {
    public static AccountDTO toDTO(Account account) {
        return new AccountDTO(
                account.getAccountId(),
                account.getUserId(),
                account.getAccountPw(),
                account.getBalance(),
                account.getCreateAt()
        );
    }

    public static Account toEntity(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccountId(accountDTO.accountId());
        account.setUserId(accountDTO.userId());
        account.setAccountPw(accountDTO.accountPw());
        account.setBalance(accountDTO.balance());
        account.setCreateAt(accountDTO.createAt());
        return account;
    }
}
