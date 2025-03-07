package org.example.project1.service;

import org.example.project1.Entity.Account; //  Ensure Account is imported
import org.example.project1.repository.AccountRepository; //  Ensure AccountRepository is imported

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account); //  Auto-generates account number
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
