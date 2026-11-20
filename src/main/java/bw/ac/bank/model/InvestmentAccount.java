package bw.ac.bank.model;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class InvestmentAccount extends Account implements InterestBearing {
    public static final BigDecimal RATE = new BigDecimal("0.05");
    public static final BigDecimal MIN_OPENING = new BigDecimal("500.00");
    public InvestmentAccount() { setAccountType("INVESTMENT"); }
    public InvestmentAccount(String num, BigDecimal bal, String branch, Customer c) {
        super(num, bal, branch, c);
        if (bal.compareTo(MIN_OPENING) < 0) throw new IllegalArgumentException("Investment requires minimum BWP 500.00");
        setAccountType("INVESTMENT");
    }
    @Override public void withdraw(BigDecimal amount) { withdraw(amount, "Withdraw"); }
    @Override public void withdraw(BigDecimal amount, String description) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Withdraw must be > 0");
        if (amount.compareTo(getBalance()) > 0) throw new IllegalArgumentException("Insufficient funds");
        setBalance(getBalance().subtract(amount));
    }
    @Override public BigDecimal calculateMonthlyInterest() {
        return getBalance().multiply(RATE).setScale(2, RoundingMode.HALF_UP);
    }
    @Override public BigDecimal postMonthlyInterest() {
        BigDecimal interest = calculateMonthlyInterest();
        deposit(interest, "Monthly Interest");
        return interest;
    }
}
