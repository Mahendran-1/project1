package org.example.project1.controller; // ✅ Ensure correct package name

import org.example.project1.Entity.Account; // ✅ Import Account
import org.example.project1.service.AccountService; // ✅ Import AccountService

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:4200") // Adjust based on Angular server
public class AccountController {

    @Autowired
    private AccountService accountService;

    // ✅ Create new account (POST request)
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    // ✅ Get all accounts (GET request)
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}
