package com.revature.Dao;

import com.revature.Model.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;

public class LoanDao {
    private final String Url;
    private final String dbUser;
    private final String dbPassword;

    private final Logger logger = Logger.getLogger(LoanDao.class.getName());

    public LoanDao(String Url, String dbUser, String dbPassword) {
        this.Url = Url;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public boolean loanApplication(Loan newLoan) {
        String sqlQuery = "INSERT INTO Loans (loan_type, amount_requested, id_user) VALUES (?,?,?);";

        try (Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
                stmt.setString(1, newLoan.getType());
                stmt.setInt(2, newLoan.getAmount());
                stmt.setInt(3, newLoan.getIdUser());
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated >= 1) {
                    return true;
                }
        }   catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return false;
    }

    public boolean changeStatus(int idUser, int idLoan, String status) {
        String sqlQuery = "UPDATE Loans SET loan_status = ? WHERE (id_user = ? AND id_loan = ?);";

        try(Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setString(1, status);
            stmt.setInt(2, idUser);
            stmt.setInt(3, idLoan);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated >= 1) {
                return true;
            }
        }   catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return false;
    }

    public ArrayList<Loan> viewApplicationsByUser(int idUser) {
        ArrayList<Loan> loansList = new ArrayList<>();

        String sqlQuery  = "SELECT * FROM Loans WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Loan loanApplication = new Loan(
                        rs.getInt("id_loan"),
                        rs.getString("loan_status"),
                        rs.getString("loan_type"),
                        rs.getInt("amount_requested"),
                        rs.getInt("id_user"));
                loansList.add(loanApplication);
            }
            return loansList;
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return new ArrayList<>();
    }

    public ArrayList<Loan> allApplications() {
        ArrayList<Loan> loansList = new ArrayList<>();

        String sqlQuery  = "SELECT * FROM Loans";
        try (Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                Loan loanApplication = new Loan(
                        rs.getInt("id_loan"),
                        rs.getString("loan_status"),
                        rs.getString("loan_type"),
                        rs.getInt("amount_requested"),
                        rs.getInt("id_user"));
                loansList.add(loanApplication);
            }
            return loansList;
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return new ArrayList<>();
    }

    public boolean updateApplication(int idUser, String type, Integer amount, int idLoan) {
        String sqlQuery = "UPDATE Loans SET loan_type = ?, amount_requested = ? WHERE (id_user = ? AND id_loan = ?);";

        try (Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setString(1, type);
            stmt.setInt(2, amount);
            stmt.setInt(3, idUser);
            stmt.setInt(4, idLoan);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated >= 1) {
                return true;
            }
        }   catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return false;
    }

    public ArrayList<Loan> viewApplications(int idUser) {
        ArrayList<Loan> loansList = new ArrayList<>();

        String sqlQuery  = "SELECT * FROM Loans WHERE id_user = ?";
        try (Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Loan loanApplication = new Loan(
                        rs.getInt("id_loan"),
                        rs.getString("loan_status"),
                        rs.getString("loan_type"),
                        rs.getInt("amount_requested"),
                        rs.getInt("id_user"));
                loansList.add(loanApplication);
            }
            return loansList;
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return new ArrayList<>();
    }
}