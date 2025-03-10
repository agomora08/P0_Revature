package com.revature.Model;

public class User {
    private int idUser;
    private String username;
    private String password;
    private String name;
    private String ssn;
    private String role;
    private String street;
    private String streetNumber;
    private String zipCode;
    private String state;

    public User() {}

    public User(int idUser, String username, String password) {
        this.idUser = idUser;
        this.username = username;
        this.password = password; }

    public User(int idUser, String username, String name, String ssn, String role,
                String street, String streetNumber, String zipCode, String state) {
        this.idUser = idUser;
        this.username = username;
        this.name = name;
        this.ssn = ssn;
        this.role = role;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.state = state; }

    public int getID() {return idUser;}
    public String getUsername() {return username;}
    public String getUserPassword() {return password;}
    public String getName() {return name;}
    public String getSSN() {return ssn;}
    public String getRole() {return role;}
    public String getStreet() {return street;}
    public String getStreetNumber() {return streetNumber;}
    public String getZipCode() {return zipCode;}
    public String getState() {return state;}

    public void setID(int idUser) {this.idUser = idUser;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setName(String name) {this.name = name;}
    public void setSSN(String ssn) {this.ssn = ssn;}
    public void setRole(String role) {this.role = role;}
    public void setStreet(String street) {this.street = street;}
    public void setStreetNumber(String streetNumber) {this.streetNumber = streetNumber;}
    public void setZipCode(String zipCode) {this.zipCode = zipCode;}
    public void setState(String state) {this.state = state;}
}