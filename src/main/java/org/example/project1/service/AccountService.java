package org.example.project1.service;

import org.example.project1.DTO.AddCashRequest;
import org.example.project1.Entity.Account;
import org.example.project1.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public Account addCash(AddCashRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Ensure initialDeposit is never null (important)
        if (account.getInitialDeposit() == null) {
            account.setInitialDeposit(0.0);
        }

        // Add the deposited amount directly to initialDeposit
        account.setInitialDeposit(account.getInitialDeposit() + request.getAmount());

        return accountRepository.save(account);
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
