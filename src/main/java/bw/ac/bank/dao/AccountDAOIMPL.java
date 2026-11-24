package bw.ac.bank.dao;

import bw.ac.bank.model.*;
import java.sql.*;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public void save(Account a) {
        String sql = "INSERT OR REPLACE INTO accounts(accountNumber, customerId, type, balance) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getAccountNumber());
            ps.setString(2, a.getCustomer().getCustomerId());
            ps.setString(3, a.getClass().getSimpleName());
            ps.setDouble(4, a.getBalance());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public java.util.List<Account> findAll() {
        // For now we just return empty - we load from controller memory
        // Full reload needs Customer lookup, we do that in Part 3
        return new java.util.ArrayList<>();
    }
}