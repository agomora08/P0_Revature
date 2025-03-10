package com.revature.Tables;

public class TablesSQL {
    public static final String DROP_TABLES_SQL = """
            DROP TABLE IF EXISTS Users CASCADE;
            DROP TABLE IF EXISTS Credentials;
            DROP TABLE IF EXISTS Mailing_address;
            DROP TABLE IF EXISTS Loans;
            """;

    public static final String CREATE_TABLES_SQL = """
            CREATE TABLE IF NOT EXISTS Users (
            id_user SERIAL PRIMARY KEY,
            name VARCHAR(50) NOT NULL,
            ssn VARCHAR(9) NOT NULL UNIQUE CHECK(LENGTH(ssn) = 9),
            role VARCHAR(50) NOT NULL);
            
            CREATE TABLE IF NOT EXISTS Credentials (
            id SERIAL PRIMARY KEY,
            username VARCHAR(50) NOT NULL UNIQUE,
            hashed_password VARCHAR(255) NOT NULL,
            id_user INT NOT NULL,
            FOREIGN KEY (id_user) REFERENCES Users(id_user) ON DELETE CASCADE);
            
            CREATE TABLE IF NOT EXISTS Mailing_address (
            id SERIAL PRIMARY KEY,
            street VARCHAR(50) NOT NULL,
            street_number VARCHAR(50) NOT NULL,
            zip_code VARCHAR(5) NOT NULL CHECK(LENGTH(zip_code) = 5),
            state VARCHAR(50) NOT NULL,
            id_user INT NOT NULL,
            FOREIGN KEY (id_user) REFERENCES Users(id_user) ON DELETE CASCADE);
            
            CREATE TABLE IF NOT EXISTS Loans (
            id_loan SERIAL PRIMARY KEY,
            loan_status VARCHAR(50) NOT NULL DEFAULT 'Pending',
            loan_type VARCHAR(50) NOT NULL DEFAULT 'Personal',
            amount_requested INT NOT NULL CHECK(amount_requested >= 2000),
            id_user INT NOT NULL,
            FOREIGN KEY (id_user) REFERENCES Users(id_user) ON DELETE CASCADE);
            """;
}