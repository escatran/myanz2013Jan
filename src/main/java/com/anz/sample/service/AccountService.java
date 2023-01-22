package com.anz.sample.service;

import com.anz.sample.dto.AccountDTO;
import com.anz.sample.dto.TransactionDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccountsByCif(String cif);

    List<TransactionDTO> getAccountTransactions(String cif, String accountNumber, int page, int size);
}
