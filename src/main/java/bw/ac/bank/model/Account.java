package bw.ac.bank.model;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Account {
    private long accountId;
    private String accountNumber;
    private BigDecimal balance = BigDecimal.ZERO;
    private String branch;
    private String accountType;
    private Customer customer;

    protected Account() {}
    protected Account(String accountNumber, BigDecimal balance, String branch, Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Account must have a customer");
        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Balance cannot be negative");
        this.accountNumber = accountNumber;
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        this.branch = branch;
        this.customer = customer;
    }
    public long getAccountId() { return accountId; }
    public void setAccountId(long id) { this.accountId = id; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String v) { this.accountNumber = v; }
    public BigDecimal getBalance() { return balance; }
    protected void setBalance(BigDecimal v) { this.balance = v.setScale(2, RoundingMode.HALF_UP); }
    public String getBranch() { return branch; }
    public void setBranch(String v) { this.branch = v; }
    public String getAccountType() { return accountType; }
    protected void setAccountType(String v) { this.accountType = v; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer c) { this.customer = c; }

    public void deposit(BigDecimal amount) { deposit(amount, "Deposit"); }
    public void deposit(BigDecimal amount, String description) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Deposit must be > 0");
        setBalance(balance.add(amount));
    }
    public abstract void withdraw(BigDecimal amount);
    public abstract void withdraw(BigDecimal amount, String description);
}