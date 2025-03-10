package com.revature.Service;

import com.revature.Model.Loan;
import com.revature.Dao.LoanDao;

import java.util.ArrayList;

public class LoanService {
    private final LoanDao loanDao;

    public LoanService(LoanDao loanDao) {this.loanDao = loanDao; }

    public boolean loanApplication(String type, Integer amount, Integer id) {
        Loan newLoan = new Loan();
        newLoan.setType(type);
        newLoan.setAmount(amount);
        newLoan.setIdUser(id);
        return loanDao.loanApplication(newLoan);
    }

    public boolean changeStatus(int idUser, int idLoan, String status) {
        return loanDao.changeStatus(idUser, idLoan, status);
    }

    public ArrayList<Loan> viewApplicationsByUser(int idUser) {
        return loanDao.viewApplicationsByUser(idUser);
    }

    public ArrayList<Loan> allApplications() {
        return loanDao.allApplications();
    }

    public boolean updateApplication(int idUser, String type, Integer amount, int IdLoan) {
        return loanDao.updateApplication(idUser, type, amount, IdLoan);
    }

    public ArrayList<Loan> viewApplications(int idUser) {
        return loanDao.viewApplications(idUser);
    }
}