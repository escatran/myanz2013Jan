package com.anz.sample.dto;

import com.anz.sample.entity.AccountType;
import com.anz.sample.entity.Currency;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private String accountNumber;

    private String accountName;

    private String accountType;

    private String balanceDate;

    private String currency;

    private String openingAvailableBalance;
}
