package bw.ac.bank.controller;

import bw.ac.bank.dao.AccountDAO;
import bw.ac.bank.dao.CustomerDAO;
import bw.ac.bank.dao.DatabaseManager;
import bw.ac.bank.model.Account;
import bw.ac.bank.model.ChequeAccount;
import bw.ac.bank.model.Customer;
import bw.ac.bank.model.InterestBearing;
import bw.ac.bank.model.InvestmentAccount;
import bw.ac.bank.model.SavingsAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankingController {

    private List<Customer> customers = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private Customer currentCustomer;
    private Account currentAccount;

    private CustomerDAO customerDAO = createCustomerDAO();
    private AccountDAO accountDAO = createAccountDAO();
    private long nextCustomerId = 1;

    private CustomerDAO createCustomerDAO() {
        try {
            Class<?> implementation = Class.forName("bw.ac.bank.dao.CustomerDAOImpl");
            java.lang.reflect.Constructor<?> constructor = implementation.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (CustomerDAO) constructor.newInstance();
        } catch (ReflectiveOperationException | SecurityException e) {
            throw new IllegalStateException("Unable to create customer DAO", e);
        }
    }

    private AccountDAO createAccountDAO() {
        try {
            Class<?> implementation = Class.forName("bw.ac.bank.dao.AccountDAOImpl");
            java.lang.reflect.Constructor<?> constructor = implementation.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (AccountDAO) constructor.newInstance();
        } catch (ReflectiveOperationException | SecurityException e) {
            throw new IllegalStateException("Unable to create account DAO", e);
        }
    }

    public BankingController() {
        DatabaseManager.initDatabase();
        customers.addAll(customerDAO.findAll());
        if (!customers.isEmpty()) {
            nextCustomerId = customers.stream().mapToLong(Customer::getCustomerId).max().orElse(0) + 1;
            currentCustomer = customers.get(customers.size() - 1);
        }
    }

    // Called by BankingView: registerCustomer(first, surname, email)
    public String registerCustomer(String firstName, String surname, String email) {
        if (firstName == null || firstName.isBlank()) return "Error: First name is required";
        if (surname == null || surname.isBlank()) return "Error: Surname is required";
        Customer c = new Customer(firstName.trim(), surname.trim(), "", email != null ? email.trim() : "");
        c.setCustomerId(nextCustomerId++);
        customers.add(c);
        currentCustomer = c;
        customerDAO.save(c);
        return "Customer registered: " + c.getFullName();
    }

    // Called by BankingView: openAccount(type, accountNumber, openingAmountString)
    public String openAccount(String type, String accountNumber, String openingAmountStr) {
        if (currentCustomer == null) return "Error: Register a customer first";
        if (accountNumber == null || accountNumber.isBlank()) return "Error: Account number is required";
        BigDecimal opening;
        try {
            opening = new BigDecimal(openingAmountStr.trim());
        } catch (Exception e) {
            return "Error: Invalid opening amount: " + openingAmountStr;
        }
        try {
            Account acc;
            String branch = "Gaborone";
            if (type.equalsIgnoreCase("SAVINGS")) {
                acc = new SavingsAccount(accountNumber.trim(), opening, branch, currentCustomer);
            } else if (type.equalsIgnoreCase("CHEQUE")) {
                acc = new ChequeAccount(accountNumber.trim(), opening, branch, currentCustomer);
            } else if (type.equalsIgnoreCase("INVESTMENT")) {
                acc = new InvestmentAccount(accountNumber.trim(), opening, branch, currentCustomer);
            } else {
                return "Error: Unknown account type: " + type;
            }
            accounts.add(acc);
            currentCustomer.addAccount(acc);
            currentAccount = acc;
            accountDAO.save(acc);
            return "Account opened: " + type + " " + accountNumber + " Balance: BWP " + acc.getBalance();
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Called by BankingView: deposit(amountString) - uses last opened account
    public String deposit(String amountStr) {
        if (currentAccount == null) return "Error: Open an account first";
        try {
            BigDecimal amount = new BigDecimal(amountStr.trim());
            currentAccount.deposit(amount);
            accountDAO.save(currentAccount);
            return "Deposited BWP " + amount + ". New balance: BWP " + currentAccount.getBalance();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String withdraw(String amountStr) {
        if (currentAccount == null) return "Error: Open an account first";
        try {
            BigDecimal amount = new BigDecimal(amountStr.trim());
            currentAccount.withdraw(amount);
            accountDAO.save(currentAccount);
            return "Withdrew BWP " + amount + ". New balance: BWP " + currentAccount.getBalance();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Called by BankingView: postInterest() - no args
    public String postInterest() {
        if (currentAccount == null) return "Error: Open an account first";
        if (!(currentAccount instanceof InterestBearing)) {
            return "Error: This account type does not earn interest";
        }
        try {
            BigDecimal interest = ((InterestBearing) currentAccount).postMonthlyInterest();
            accountDAO.save(currentAccount);
            return "Interest posted: BWP " + interest + ". New balance: BWP " + currentAccount.getBalance();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Called by BankingView after every action
    public String getBalance() {
        if (currentAccount == null) return "Balance: BWP 0.00";
        return "Balance: BWP " + currentAccount.getBalance();
    }

    public List<Customer> getCustomers() { return customers; }
    public List<Account> getAccounts() { return accounts; }
    public Customer getCurrentCustomer() { return currentCustomer; }
    public Account getCurrentAccount() { return currentAccount; }
}