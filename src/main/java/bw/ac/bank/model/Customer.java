package bw.ac.bank.model;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private long customerId;
    private String firstName;
    private String surname;
    private String address;
    private String email;
    private boolean employed;
    private String employerName;
    private String employerAddress;
    private List<Account> accounts = new ArrayList<>();

    public Customer() {}
    public Customer(String firstName, String surname, String address, String email) {
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.email = email;
    }
    public long getCustomerId() { return customerId; }
    public void setCustomerId(long id) { this.customerId = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }
    public String getSurname() { return surname; }
    public void setSurname(String v) { this.surname = v; }
    public String getAddress() { return address; }
    public void setAddress(String v) { this.address = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public boolean isEmployed() { return employed; }
    public void setEmployed(boolean v) { this.employed = v; }
    public String getEmployerName() { return employerName; }
    public void setEmployerName(String v) { this.employerName = v; }
    public String getEmployerAddress() { return employerAddress; }
    public void setEmployerAddress(String v) { this.employerAddress = v; }
    public List<Account> getAccounts() { return accounts; }
    public void addAccount(Account a) { accounts.add(a); }
    public String getFullName() { return firstName + " " + surname; }
}