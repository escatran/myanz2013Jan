package com.anz.sample.entity;


import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_name")
    private String accountName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cif", nullable = false)
    private AccountHolder accountHolder;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Temporal(TemporalType.DATE)
    @Column(name = "balance_date")
    private Date balanceDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "opening_available_balance")
    private BigDecimal openingAvailableBalance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountTransaction> transactions;
}
