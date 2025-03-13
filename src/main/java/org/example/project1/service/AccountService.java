package org.example.project1.service;

import org.example.project1.DTO.AddCashRequest;
import org.example.project1.DTO.WithdrawRequest;
import org.example.project1.DTO.TransactionResponse;
import org.example.project1.Entity.Account;
import org.example.project1.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        // Ensure initialDeposit is never null
        if (account.getInitialDeposit() == null) {
            account.setInitialDeposit(0.0);
        }

        // Add deposited amount
        account.setInitialDeposit(account.getInitialDeposit() + request.getAmount());

        return accountRepository.save(account);
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Transactional
    public TransactionResponse withdrawCash(WithdrawRequest request) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(request.getAccountNumber());

        if (optionalAccount.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account account = optionalAccount.get();
        Double currentBalance = account.getInitialDeposit();

        if (request.getAmount() < 10) {
            throw new RuntimeException("Minimum withdrawal amount is $10");
        }

        if (currentBalance < request.getAmount()) {
            throw new RuntimeException("Insufficient funds");
        }

        // Deduct amount from initialDeposit
        account.setInitialDeposit(currentBalance - request.getAmount());
        accountRepository.save(account);

        return new TransactionResponse(
                UUID.randomUUID().toString(),
                account.getAccountNumber(),
                account.getInitialDeposit(),
                "Withdrawal successful"
        );
    }
    public String deactivateAccount(String accountNumber, String remarks) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();

            // Check if balance is zero before deletion
            if (account.getInitialDeposit() != null && account.getInitialDeposit() > 0) {
                return "Account cannot be deactivated. Please withdraw all funds before deactivation.";
            }

            // If balance is zero, delete the account
            accountRepository.delete(account);
            return "Account " + accountNumber + " successfully deactivated.";
        }

        return "Account not found.";
    }
}
