package com.anz.sample.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {

    @Id
    @Column(name = "id")
    private String id;

    @Temporal(TemporalType.DATE)
    private Date valueDate;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "debit_amount")
    private BigDecimal debitAmount;

    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    @Column(name = "narrative")
    private String narrative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;
}