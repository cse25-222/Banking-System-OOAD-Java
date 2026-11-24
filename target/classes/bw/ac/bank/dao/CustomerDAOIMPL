package bw.ac.bank.dao;

import bw.ac.bank.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public void save(Customer c) {
        String sql = "INSERT OR REPLACE INTO customers(id, name, email, employed, employerName) VALUES(?,?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, c.getCustomerId());
            ps.setString(2, c.getFullName());
            ps.setString(3, c.getEmail());
            ps.setInt(4, c.isEmployed() ? 1 : 0);
            ps.setString(5, c.getEmployerName());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM customers")) {
            while (rs.next()) {
                String full = rs.getString("name");
                String first = full.contains(" ") ? full.substring(0, full.indexOf(" ")) : full;
                String surname = full.contains(" ") ? full.substring(full.indexOf(" ") + 1) : "";
                Customer c = new Customer(first, surname, "", rs.getString("email"));
                c.setCustomerId(rs.getLong("id"));
                c.setEmployed(rs.getInt("employed") == 1);
                c.setEmployerName(rs.getString("employerName"));
                list.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public Customer findById(String id) {
        for (Customer c : findAll()) {
            if (String.valueOf(c.getCustomerId()).equals(id)) return c;
        }
        return null;
    }
}