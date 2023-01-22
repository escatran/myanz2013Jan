package com.anz.sample.controller;

import java.util.List;

import com.anz.sample.dto.AccountDTO;
import com.anz.sample.dto.ErrorResponseDTO;
import com.anz.sample.dto.TransactionDTO;
import com.anz.sample.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "View a list of accounts", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AccountDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Account holder does not exist", response = ErrorResponseDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponseDTO.class)
    })
    @GetMapping(value = "/{cif}/accounts", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDTO>> viewAccountList(@PathVariable String cif) {
        return new ResponseEntity<>(accountService.getAccountsByCif(cif), HttpStatus.OK);
    }

    @ApiOperation(value = "View a list of transactions of an account", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TransactionDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Account holder does not exist", response = ErrorResponseDTO.class),
            @ApiResponse(code = 404, message = "Account does not exist", response = ErrorResponseDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponseDTO.class)
    })
    @GetMapping(value = "/{cif}/accounts/{accountNumber}/transactions", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionDTO>> viewAccountTransactions(@PathVariable("cif") String cif,
                                                                        @PathVariable("accountNumber") String accountNumber,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(accountService.getAccountTransactions(cif, accountNumber, page, size), HttpStatus.OK);
    }
}
