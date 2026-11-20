package bw.ac.bank.model;
import java.math.BigDecimal;
public interface InterestBearing {
    BigDecimal calculateMonthlyInterest();
    BigDecimal postMonthlyInterest();
}
