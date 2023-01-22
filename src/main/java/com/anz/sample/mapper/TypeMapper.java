package com.anz.sample.mapper;

import com.anz.sample.dto.AccountDTO;
import com.anz.sample.dto.TransactionDTO;
import com.anz.sample.entity.Account;
import com.anz.sample.entity.AccountTransaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    TypeMapper INSTANCE = Mappers.getMapper(TypeMapper.class);

    @Mapping(target = "balanceDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AccountDTO toDto(Account account);

    @Mappings({
        @Mapping(target = "valueDate", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        @Mapping(source = "account.accountName", target = "accountName"),
        @Mapping(source = "account.accountNumber", target = "accountNumber"),
    })
    TransactionDTO toDto(AccountTransaction tran);
}
