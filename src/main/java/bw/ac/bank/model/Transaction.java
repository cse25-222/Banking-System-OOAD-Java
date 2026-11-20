package bw.ac.bank.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class Transaction {
    private long transactionId;
    private long accountId;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String description;
    public Transaction() {}
    public Transaction(long accountId, String type, BigDecimal amount, String description) {
        this.accountId = accountId; this.transactionType = type; this.amount = amount; this.description = description;
    }
    public long getTransactionId() { return transactionId; }
    public void setTransactionId(long id) { this.transactionId = id; }
    public long getAccountId() { return accountId; }
    public void setAccountId(long id) { this.accountId = id; }
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String v) { this.transactionType = v; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal v) { this.amount = v; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime v) { this.timestamp = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
}
