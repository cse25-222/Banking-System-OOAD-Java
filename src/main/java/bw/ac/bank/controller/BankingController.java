package bw.ac.bank.controller;

import bw.ac.bank.dao.AccountDAO;
import bw.ac.bank.dao.AccountDAOImpl;
import bw.ac.bank.dao.CustomerDAO;
import bw.ac.bank.dao.CustomerDAOImpl;
import bw.ac.bank.dao.DatabaseManager;
import bw.ac.bank.model.Account;
import bw.ac.bank.model.ChequeAccount;
import bw.ac.bank.model.Customer;
import bw.ac.bank.model.InvestmentAccount;
import bw.ac.bank.model.SavingsAccount;

import java.util.ArrayList;
import java.util.List;

public class BankingController {

    private List<Customer> customers = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();

    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private AccountDAO accountDAO = new AccountDAOImpl();

    public BankingController() {
        DatabaseManager.initDatabase();
        // Load existing customers from DB so they are not lost on restart
        customers.addAll(customerDAO.findAll());
    }

    // --- CUSTOMER ---

    public String registerCustomer(String fullName, String email) {
        if (fullName == null || fullName.isBlank()) {
            return "Error: Full name is required";
        }
        if (email == null || email.isBlank()) {
            return "Error: Email is required";
        }

        String id = "CUST" + (customers.size() + 1);
        Customer c = new Customer(id, fullName, email);
        customers.add(c);
        customerDAO.save(c);
        return "Customer registered: " + fullName;
    }

    // Overload for later - when we add Employer Name field
    public String registerCustomer(String fullName, String email, boolean employed, String employerName) {
        if (fullName == null || fullName.isBlank()) {
            return "Error: Full name is required";
        }
        String id = "CUST" + (customers.size() + 1);
        Customer c = new Customer(id, fullName, email);
        c.setEmployed(employed);
        c.setEmployerName(employerName);
        customers.add(c);
        customerDAO.save(c);
        return "Customer registered: " + fullName;
    }

    public Customer findCustomerByName(String fullName) {
        for (Customer c : customers) {
            if (c.getFullName().equalsIgnoreCase(fullName.trim())) {
                return c;
            }
        }
        return null;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    // --- ACCOUNT ---

    public String openAccount(String accountNumber, String customerName, double openingBalance, String type) {
        if (accountNumber == null || accountNumber.isBlank()) {
            return "Error: Account number is required";
        }
        Customer customer = findCustomerByName(customerName);
        if (customer == null) {
            return "Error: Customer not found. Register customer first.";
        }
        if (openingBalance < 0) {
            return "Error: Opening balance cannot be negative";
        }

        Account acc = null;
        try {
            if (type.equalsIgnoreCase("SAVINGS")) {
                acc = new SavingsAccount(accountNumber, customer, openingBalance);
            } else if (type.equalsIgnoreCase("CHEQUE")) {
                acc = new ChequeAccount(accountNumber, customer, openingBalance);
            } else if (type.equalsIgnoreCase("INVESTMENT")) {
                acc = new InvestmentAccount(accountNumber, customer, openingBalance);
            } else {
                return "Error: Unknown account type: " + type;
            }
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }

        accounts.add(acc);
        accountDAO.save(acc);
        return "Account opened: " + type + " " + accountNumber + " Balance: BWP " + String.format("%.2f", acc.getBalance());
    }

    public Account findAccount(String accountNumber) {
        for (Account a : accounts) {
            if (a.getAccountNumber().equalsIgnoreCase(accountNumber.trim())) {
                return a;
            }
        }
        return null;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    // --- TRANSACTIONS ---

    public String deposit(String accountNumber, double amount) {
        Account acc = findAccount(accountNumber);
        if (acc == null) return "Error: Account not found";
        try {
            acc.deposit(amount);
            accountDAO.save(acc);
            return "Deposited BWP " + String.format("%.2f", amount) + ". New balance: BWP " + String.format("%.2f", acc.getBalance());
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String withdraw(String accountNumber, double amount) {
        Account acc = findAccount(accountNumber);
        if (acc == null) return "Error: Account not found";
        try {
            acc.withdraw(amount);
            accountDAO.save(acc);
            return "Withdrew BWP " + String.format("%.2f", amount) + ". New balance: BWP " + String.format("%.2f", acc.getBalance());
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String postInterest(String accountNumber) {
        Account acc = findAccount(accountNumber);
        if (acc == null) return "Error: Account not found";
        double interest = acc.calculateInterest();
        acc.deposit(interest);
        accountDAO.save(acc);
        return "Interest posted: BWP " + String.format("%.2f", interest) + ". New balance: BWP " + String.format("%.2f", acc.getBalance());
    }
}