package com.revature.Model;

public class Loan {

    private int idUser;
    private Integer idLoan;
    private String status;
    private String type;
    private Integer amount;

    public Loan() {}

    public Loan(Integer idLoan, String status, String type, Integer amount, int idUser) {
        this.idLoan = idLoan;
        this.status = status;
        this.type = type;
        this.amount = amount;
        this.idUser = idUser; }

    public int getIdUser() {return idUser;}
    public Integer getIdLoan() {return idLoan;}
    public String getStatus() {return status;}
    public String getType() {return type;}
    public Integer getAmount() {return amount;}

    public void setIdUser(int idUser) {this.idUser = idUser;}
    public void setType(String type) {this.type = type;}
    public void setAmount(Integer amount) {this.amount = amount;}
}