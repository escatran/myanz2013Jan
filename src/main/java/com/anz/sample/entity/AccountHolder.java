package com.anz.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name="ACCOUNT_HOLDER",
    uniqueConstraints=
    @UniqueConstraint(columnNames={"cif"})
)
public class AccountHolder {
    @Id
    @Column(name = "cif")
    private String cif;

    @OneToMany(mappedBy = "accountHolder", cascade = CascadeType.ALL)
    private List<Account> accounts;
}
