# ANZ Wholesale Engineering Sample Project (Backend Development)

## Assumptions:
- Account Holder is just an artificial entity to represent the users and each Account Holder can have many Accounts.
- Each account has one currency
- The response type is in JSON only
## Limitations:
Because of the small scope of this demo:
- No authorization in API level.
- Pagination is only available for GET transactions API because we assume that each Account Holder can not have
more than 10 Accounts.
- No monitoring supports such as metrics via HTTP endpoint or JMX
- There are integration tests to 
verify the program's logic from API to database level. 
The unit tests for each class is skipped because 
there is no code which handle the business logic, the program merely do the CRUD tasks.
## How to run the program
Run this command:
`mvn clean spring-boot:run`

if the above command does not work then:

`./mvnw clean spring-boot:run`
### Swagger endpoints:
http://localhost:8080/swagger-ui.html

From the swagger-ui, you can try to invoke the APIs:

- `GET /api/{cif}/accounts`: use `CIF1` as the cif
- `GET /api/{cif}/accounts/{accountNumber}/transactions`: use `CIF1` and `791066619` as the cif
## Design
The spring-boot version is `2.5.14`

There are 3 layers:
- Controller: exposes REST APIs and Swagger UI document. The controller is built on top of `org.springframework.boot:spring-boot-starter-web`
- Service: contains business logic. This layer also converts the entity objects to DTO objects for security, 
flexibility and performance. The conversion is done by the maven dependency `org.mapstruct:mapstruct:1.4.0.Final`
- DAO: handles database related matters. This layer is implemented based on Spring Data JPA `org.springframework.boot:spring-boot-starter-data-jpa`.

### Database
The program use the embedded RDBMS H2. The SQL script to init the data is `src/main/resources/data.sql`

To see the data, please go to http://localhost:8080/h2-console. Use the username and password provided 
in `src\main\resources\application.properties` file.

```properties
spring.datasource.username=sa
spring.datasource.password=
```

Table: ACCOUNT

| Column                    | Data Type     | Constraint            |
|---------------------------|---------------|-----------------------|
| CIF                       | VARCHAR(100)  | FK ACCOUNT_HOLDER.CIF |
| ACCOUNT_NUMBER            | VARCHAR(30)   | PRIMARY KEY           |
| ACCOUNT_NAME              | VARCHAR(255)  |                       |
| ACCOUNT_TYPE              | VARCHAR(50)   |                       |
| BALANCE_DATE              | DATE          |                       |
| CURRENCY                  | VARCHAR(10)   |                       |
| OPENING_AVAILABLE_BALANCE | DECIMAL(19,6) |                       |


Table: ACCOUNT_TRANSACTION

| Column         | Data Type     | Constraint                |
|----------------|---------------|---------------------------|
| ID             | VARCHAR(255)  | PRIMARY KEY               |
| CREDIT_AMOUNT  | DECIMA(19,6)  |                           |
| CURRENCY       | VARCHAR(255)  |                           |
| DEBIT_AMOUNT   | DECIMAL(19,6) |                           |
| NARRATIVE      | VARCHAR(100)  |                           |
| VALUE_DATE     | DATE          |                           |
| ACCOUNT_NUMBER | VARCHAR(30)   | FK ACCOUNT_ACCOUNT_NUMBER |




## Tests
To run the tests, simply issue the following command:
`mvn clean test`

if the above command does not work then:
`./mvnw clean test`

Every test has 3 portions:
- Setup/preparation: run the SQL script to init the data.
```java
@Test
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
@Sql(scripts = "/noaccount.sql") // this script help to init the data for this end to end integration test
void viewAccountList_NoAccountFound_ShouldReturnEmpty() throws IOException, JSONException {
```
- Execution: calling the REST APIs directly 
```java
ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/cif1/accounts",
                HttpMethod.GET, null, String.class);
```
- Assertions: verify the HTTP response code and JSON payload

```java
assertEquals(HttpStatus.OK, response.getStatusCode());
JSONAssert.assertEquals(readStringFromFile("json/no_accounts.json"), response.getBody(), true);
```