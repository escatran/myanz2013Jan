package com.anz.sample.dao;

import com.anz.sample.entity.AccountTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepo extends JpaRepository<AccountTransaction, String> {
    Page<AccountTransaction> findByAccount_AccountNumber(String accountNumber, Pageable pageable);
}
