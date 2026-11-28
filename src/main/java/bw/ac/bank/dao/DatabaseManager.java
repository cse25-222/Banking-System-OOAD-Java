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
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            // PDF requires: customers, accounts, transactions
            st.execute("CREATE TABLE IF NOT EXISTS customers(id INTEGER PRIMARY KEY, name TEXT, email TEXT, employed INTEGER, employerName TEXT)");
            st.execute("CREATE TABLE IF NOT EXISTS accounts(accountNumber TEXT PRIMARY KEY, customerId INTEGER, type TEXT, balance DECIMAL)");
            st.execute("CREATE TABLE IF NOT EXISTS transactions(id TEXT PRIMARY KEY, accountNumber TEXT, type TEXT, amount DECIMAL, balanceAfter DECIMAL, timestamp TEXT)");
            st.execute("CREATE TABLE IF NOT EXISTS users(username TEXT PRIMARY KEY, password TEXT, role TEXT)");
            // Seed default user for Login
            st.execute("INSERT OR IGNORE INTO users(username, password, role) VALUES('admin','admin123','ADMIN')");
            // Seed 10 sample records for Presentation - PDF Page 9 requirement
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(1,'Kagiso Suping','kagiso@test.com',1,'Bank of Botswana')");
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(2,'Neo Molefe','neo@test.com',1,'BTC')");
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(3,'Tebogo Kgosi','tebogo@test.com',0,'')");
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(4,'Mpho Dube','mpho@test.com',1,'Debswana')");
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(5,'Keabetswe Moeti','kea@test.com',0,'')");
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(6,'Thabo Sebego','thabo@test.com',1,'BPC')");
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(7,'Lesego Tau','lesego@test.com',1,'Ministry of Finance')");
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(8,'Boipelo Kgosana','boipelo@test.com',0,'')");
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(9,'Kabelo Motsumi','kabelo@test.com',1,'First National Bank')");
            st.execute("INSERT OR IGNORE INTO customers(id, name, email, employed, employerName) VALUES(10,'Oratile Gaongalelwe','ora@test.com',1,'Orange Botswana')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}