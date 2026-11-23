package bw.ac.bank.controller;

import bw.ac.bank.model.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankingController {

    private List<Customer> customers = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private Customer currentCustomer;
    private Account currentAccount;

    public String registerCustomer(String firstName, String surname, String email) {
        if (firstName == null || firstName.isBlank() || surname == null || surname.isBlank()) {
            return "Error: First name and surname are required.";
        }
        Customer c = new Customer();
        c.setFirstName(firstName.trim());
        c.setSurname(surname.trim());
        c.setEmail(email);
        c.setEmployed(true); // for demo account assume all customers are employed
        customers.add(c);
        currentCustomer = c;
        return "Customer registered: " + c.getFullName();
    }

    public String openAccount(String type, String accountNumber, String amountText) {
        if (currentCustomer == null) {
            return "Error: Register a customer first.";
        }
        if (accountNumber == null || accountNumber.isBlank()) {
            return "Error: Account number is required.";
        }
        try {
            BigDecimal amount = new BigDecimal(amountText);
            Account acc = null;
            if (type.equals("SAVINGS")) {
                acc = new SavingsAccount(accountNumber, amount, "Gaborone", currentCustomer);
            } else if (type.equals("INVESTMENT")) {
                acc = new InvestmentAccount(accountNumber, amount, "Gaborone", currentCustomer);
            } else if (type.equals("CHEQUE")) {
                acc = new ChequeAccount(accountNumber, amount, "Gaborone", currentCustomer);
            } else {
                return "Error: Unknown account type.";
            }
            accounts.add(acc);
            currentCustomer.addAccount(acc);
            currentAccount = acc;
            return "Account opened: " + type + " " + accountNumber + " Balance: BWP " + acc.getBalance();
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: Invalid amount. Use e.g. 1000.00";
        }
    }

    public String deposit(String amountText) {
        if (currentAccount == null) return "Error: Open an account first.";
        try {
            BigDecimal amount = new BigDecimal(amountText);
            currentAccount.deposit(amount);
            return "Deposited BWP " + amount + ". New balance: BWP " + currentAccount.getBalance();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String withdraw(String amountText) {
        if (currentAccount == null) return "Error: Open an account first.";
        try {
            BigDecimal amount = new BigDecimal(amountText);
            currentAccount.withdraw(amount);
            return "Withdrew BWP " + amount + ". New balance: BWP " + currentAccount.getBalance();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String postInterest() {
        if (currentAccount == null) return "Error: Open an account first.";
        if (currentAccount instanceof InterestBearing ib) {
            BigDecimal interest = ib.postMonthlyInterest();
            return "Interest posted: BWP " + interest + ". New balance: BWP " + currentAccount.getBalance();
        }
        return "Error: Cheque accounts do not earn interest.";
    }

    public String getBalance() {
        if (currentAccount == null) return "Balance: BWP 0.00";
        return "Balance: BWP " + currentAccount.getBalance();
    }
}
