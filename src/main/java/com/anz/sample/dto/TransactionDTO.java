package com.anz.sample.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private String accountNumber;

    private String accountName;

    private String valueDate;

    private String currency;

    private String debitAmount;

    private String creditAmount;

    private String narrative;
}
