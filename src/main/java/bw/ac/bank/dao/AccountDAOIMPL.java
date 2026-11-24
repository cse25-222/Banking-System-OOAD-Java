package bw.ac.bank.dao;

import bw.ac.bank.model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class AccountDAOImpl implements AccountDAO {
    @Override
    public void save(Account a) {
        String sql = "INSERT OR REPLACE INTO accounts(accountNumber, customerId, type, balance) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getAccountNumber());
            ps.setLong(2, a.getCustomer().getCustomerId());
            ps.setString(3, a.getAccountType() != null ? a.getAccountType() : a.getClass().getSimpleName());
            ps.setBigDecimal(4, a.getBalance());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>();
    }
}