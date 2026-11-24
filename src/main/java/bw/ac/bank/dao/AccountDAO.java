package bw.ac.bank.dao;

import bw.ac.bank.model.Account;
import java.util.List;

public interface AccountDAO {
    void save(Account a);
    List<Account> findAll();
}

