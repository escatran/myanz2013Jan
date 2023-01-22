package com.anz.sample.controller;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Sql(scripts = "/sql/accounts/account.sql") // this script help to init the data for this end to end integration test
    void viewAccountList_AccountsFound_ShouldReturnData() throws IOException, JSONException {
        final String cif = "CIF1";

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/" + cif + "/accounts",
                HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(readStringFromFile("json/accounts/accounts.json"), response.getBody(), true);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Sql(scripts = "/sql/accounts/noaccount.sql") // this script help to init the data for this end to end integration test
    void viewAccountList_NoAccountFound_ShouldReturnEmpty() throws IOException, JSONException {
        final String cif = "CIF1";

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/" + cif + "/accounts",
                HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(readStringFromFile("json/accounts/no_accounts.json"), response.getBody(), true);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void viewAccountList_AccountHolderNotExist_ShouldReturn404() throws IOException, JSONException {
        final String cif = "CIF1";

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/" + cif + "/accounts",
                HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JSONAssert.assertEquals(readStringFromFile("json/accounts/no_account_holder.json"), response.getBody(), true);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Sql(scripts = "/sql/transactions/no_account.sql") // this script help to init the data for this end to end integration test
    void viewAccountList_AccountNotFound_ShouldReturn404() throws IOException, JSONException {
        final String cif = "CIF1";
        final String accountNo = "791066619";

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/" + cif
                        + "/accounts/" + accountNo + "/transactions",
                HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JSONAssert.assertEquals(readStringFromFile("json/transactions/no_accounts.json"), response.getBody(), true);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Sql(scripts = "/sql/transactions/transactions.sql") // this script help to init the data for this end to end integration test
    void viewAccountList_TransactionFound_ShouldReturnData() throws IOException, JSONException {
        final String cif = "CIF1";
        final String accountNo = "791066619";

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/" + cif
                        + "/accounts/" + accountNo + "/transactions",
                HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(readStringFromFile("json/transactions/transactions.json"), response.getBody(), true);
    }

    private String readStringFromFile(String filePath) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
        }
    }
}
