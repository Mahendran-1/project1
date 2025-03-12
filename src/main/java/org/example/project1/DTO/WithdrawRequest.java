package org.example.project1.DTO;

public class WithdrawRequest {
    private String accountNumber;
    private Double amount;
    private String remarks;

    // Default Constructor
    public WithdrawRequest() {}

    // Parameterized Constructor
    public WithdrawRequest(String accountNumber, Double amount, String remarks) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.remarks = remarks;
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "WithdrawRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
