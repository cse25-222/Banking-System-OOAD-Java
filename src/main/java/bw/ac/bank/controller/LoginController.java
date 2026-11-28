package bw.ac.bank.controller;

import bw.ac.bank.dao.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    // Default demo user - PDF requires Login, this is the simplest valid login
    // You will show this at presentation: admin / admin123
    private static final String DEFAULT_USER = "admin";
    private static final String DEFAULT_PASS = "admin123";

    public String login(String username, String password) {
        if (username == null || username.isBlank()) return "Error: Username is required";
        if (password == null || password.isBlank()) return "Error: Password is required";

        String user = username.trim();
        String pass = password.trim();

        // 1. Check default admin
        if (user.equals(DEFAULT_USER) && pass.equals(DEFAULT_PASS)) {
            return "SUCCESS: Welcome, admin";
        }

        // 2. Check DB users table if it exists (for future / seeded users)
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "SUCCESS: Welcome, " + user;
            }
        } catch (Exception e) {
            // users table may not exist yet - ignore, fall through to error
        }

        return "Error: Invalid username or password. Try admin / admin123";
    }
}