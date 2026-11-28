package bw.ac.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Transaction {
    public enum Type { DEPOSIT, WITHDRAWAL, INTEREST, OPENING }

    private String transactionId;
    private String accountNumber;
    private Type type;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private LocalDateTime timestamp;

    public Transaction(String accountNumber, Type type, BigDecimal amount, BigDecimal balanceAfter) {
        this.transactionId = "TXN" + System.nanoTime();
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public Type getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " | " + type + " | BWP " + amount + " | Balance: BWP " + balanceAfter;
    }
}
