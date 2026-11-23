package bw.ac.bank.dao;

import bw.ac.bank.model.Customer;
import java.util.List;

public interface CustomerDAO {
    void save(Customer c);
    List<Customer> findAll();
    Customer findById(String id);
}