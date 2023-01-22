package com.anz.sample.dao;

import com.anz.sample.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account, String> {

    List<Account> findAll();

    List<Account> findAllByAccountHolder_Cif(String cif);

    Account findByAccountNumber(String accountNumber);

    Account findByAccountNumberAndAccountHolder_Cif(String accountNumber, String cif);
}
