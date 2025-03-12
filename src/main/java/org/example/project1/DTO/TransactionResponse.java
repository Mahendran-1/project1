package org.example.project1.DTO;

public class TransactionResponse {
    private String transactionId;
    private String accountNumber;
    private Double newBalance;
    private String message;

    // Default Constructor
    public TransactionResponse() {}

    // Parameterized Constructor
    public TransactionResponse(String transactionId, String accountNumber, Double newBalance, String message) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.newBalance = newBalance;
        this.message = message;
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Double newBalance) {
        this.newBalance = newBalance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "transactionId='" + transactionId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", newBalance=" + newBalance +
                ", message='" + message + '\'' +
                '}';
    }
}
