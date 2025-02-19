/*CREATING TABLES*/

CREATE TABLE people (
    id number(10) GENERATED AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR2(40) NOT NULL,
    cpf VARCHAR2(8) NOT NULL,
    birth_date DATE NOT NULL
);

CREATE TABLE accounts (
    id INT GENERATED AS IDENTITY NOT NULL PRIMARY KEY,
    balance FLOAT NOT NULL,
    withdraw_limit FLOAT NOT NULL,
    active_flag NUMBER(1) not null,
    account_type VARCHAR2(20) NOT NULL,
    date_created DATE NOT NULL,
    person_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES people(id),
    CONSTRAINT chk_account_type CHECK(account_type IN ('checking', 'savings'))
);

CREATE TABLE transactions (
    id INT GENERATED AS IDENTITY NOT NULL PRIMARY KEY,
    value FLOAT NOT NULL,
    transaction_date DATE NOT NULL,
    account_id INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);
    
    