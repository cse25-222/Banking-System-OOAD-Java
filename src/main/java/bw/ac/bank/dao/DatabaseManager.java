package bw.ac.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:banking.db";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static void initDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS customers (" +
                "id TEXT PRIMARY KEY, name TEXT, email TEXT, " +
                "employed INTEGER, employerName TEXT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                "accountNumber TEXT PRIMARY KEY, customerId TEXT, " +
                "type TEXT, balance REAL)");

            System.out.println("Database ready: banking.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}