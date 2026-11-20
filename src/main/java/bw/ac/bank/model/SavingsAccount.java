package bw.ac.bank.model;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class SavingsAccount extends Account implements InterestBearing {
    public static final BigDecimal RATE = new BigDecimal("0.0005");
    public SavingsAccount() { setAccountType("SAVINGS"); }
    public SavingsAccount(String num, BigDecimal bal, String branch, Customer c) {
        super(num, bal, branch, c); setAccountType("SAVINGS");
    }
    @Override public void withdraw(BigDecimal amount) { withdraw(amount, "Withdraw"); }
    @Override public void withdraw(BigDecimal amount, String description) {
        throw new UnsupportedOperationException("Savings account does not allow withdrawals");
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