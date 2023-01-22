package com.anz.sample.dao;

import com.anz.sample.entity.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepo extends JpaRepository<AccountHolder, String> {
    AccountHolder findByCif(String cif);
}
