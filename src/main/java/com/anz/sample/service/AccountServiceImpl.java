package com.anz.sample.service;

import com.anz.sample.dao.AccountRepo;
import com.anz.sample.dao.AccountHolderRepo;
import com.anz.sample.dao.AccountTransactionRepo;
import com.anz.sample.dto.AccountDTO;
import com.anz.sample.dto.TransactionDTO;
import com.anz.sample.entity.Account;
import com.anz.sample.entity.AccountHolder;
import com.anz.sample.entity.AccountTransaction;
import com.anz.sample.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountHolderRepo accountHolderRepo;

    @Autowired
    private AccountTransactionRepo accountTransactionRepo;

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public List<AccountDTO> getAccountsByCif(String cif) {
        AccountHolder accountHolder = accountHolderRepo.findByCif(cif);
        if (accountHolder == null) {
            throw new EntityNotFoundException("Account holder " + cif + " does not exist");
        }
        return accountHolder.getAccounts().stream().map(typeMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<TransactionDTO> getAccountTransactions(String cif, String accountNumber, int page, int size) {
        Account account = accountRepo.findByAccountNumberAndAccountHolder_Cif(accountNumber, cif);
        if (account == null) {
            throw new EntityNotFoundException("Account holder " + cif + " does not have account " + accountNumber);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountTransaction> transactionPage = accountTransactionRepo.findByAccount_AccountNumber(accountNumber, pageable);
        return transactionPage.getContent().stream()
                .map(typeMapper::toDto)
                .collect(Collectors.toList());
    }
}
