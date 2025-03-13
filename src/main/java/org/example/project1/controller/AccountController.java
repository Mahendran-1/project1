package org.example.project1.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.project1.DTO.AddCashRequest;
import org.example.project1.DTO.WithdrawRequest;
import org.example.project1.DTO.TransactionResponse;
import org.example.project1.Entity.Account;
import org.example.project1.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        log.info("Received request to create account: {}", account);
        Account createdAccount = accountService.createAccount(account);
        log.info("Account created successfully with account number: {}", createdAccount.getAccountNumber());
        return createdAccount;
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        log.info("Fetching all accounts");
        List<Account> accounts = accountService.getAllAccounts();
        log.info("Total accounts retrieved: {}", accounts.size());
        return accounts;
    }

    @PostMapping("/accounts/add-cash")
    public ResponseEntity<?> addCash(@RequestBody AddCashRequest request) {
        log.info("Received request to add cash: {}", request);
        try {
            Account updatedAccount = accountService.addCash(request);
            String transactionId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Map<String, String> response = new HashMap<>();
            response.put("transactionId", transactionId);
            response.put("accountNumber", updatedAccount.getAccountNumber());
            response.put("newBalance", updatedAccount.getInitialDeposit().toString());

            log.info("Cash added successfully. Transaction ID: {}, Account: {}, New Balance: {}",
                    transactionId, updatedAccount.getAccountNumber(), updatedAccount.getInitialDeposit());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error adding cash: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/accounts/withdraw-cash")
    public ResponseEntity<?> withdrawCash(@RequestBody WithdrawRequest request) {
        log.info("Received request to withdraw cash: {}", request);
        try {
            TransactionResponse response = accountService.withdrawCash(request);
            log.info("Withdrawal successful. Transaction Response: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error withdrawing cash: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<?> getAccountByNumber(@PathVariable String accountNumber) {
        log.info("Fetching account details for account number: {}", accountNumber);
        try {
            Account account = accountService.getAccountByNumber(accountNumber);
            log.info("Account retrieved successfully: {}", account);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            log.error("Error fetching account details: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/accounts/deactivate")
    public ResponseEntity<?> deactivateAccount(@RequestBody Map<String, Object> requestData) {
        log.info("Received request to deactivate account with data: {}", requestData);
        try {
            String accountNumber = (String) requestData.get("accountNumber");
            String remarks = (String) requestData.get("remarks");

            String responseMessage = accountService.deactivateAccount(accountNumber, remarks);
            log.info("Account deactivation response: {}", responseMessage);

            // Generate a unique confirmation ID (UUID)
            String confirmationId = UUID.randomUUID().toString();

            Map<String, String> response = new HashMap<>();
            response.put("message", responseMessage);
            response.put("confirmationId", confirmationId); // Add confirmation ID to response

            return responseMessage.contains("successfully") ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            log.error("Error deactivating account: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
