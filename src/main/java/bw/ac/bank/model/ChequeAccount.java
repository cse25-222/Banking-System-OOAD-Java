package bw.ac.bank.model;
import java.math.BigDecimal;
public class ChequeAccount extends Account {
    public ChequeAccount() { setAccountType("CHEQUE"); }
    public ChequeAccount(String num, BigDecimal bal, String branch, Customer c) {
        super(num, bal, branch, c);
        if (!c.isEmployed()) throw new IllegalArgumentException("Cheque account requires employed customer");
        if (c.getEmployerName() == null || c.getEmployerName().isBlank()) throw new IllegalArgumentException("Employer name required");
        if (c.getEmployerAddress() == null || c.getEmployerAddress().isBlank()) throw new IllegalArgumentException("Employer address required");
        setAccountType("CHEQUE");
    }
    @Override public void withdraw(BigDecimal amount) { withdraw(amount, "Withdraw"); }
    @Override public void withdraw(BigDecimal amount, String description) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Withdraw must be > 0");
        if (amount.compareTo(getBalance()) > 0) throw new IllegalArgumentException("Insufficient funds");
        setBalance(getBalance().subtract(amount));
    }
}