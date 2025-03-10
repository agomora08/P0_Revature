package com.revature;

import com.revature.Controller.LoanController;
import com.revature.Controller.UserController;
import com.revature.Dao.LoanDao;
import com.revature.Service.LoanService;
import com.revature.Service.UserService;
import com.revature.Dao.UserDao;
import com.revature.Tables.TablesSQL;

import io.javalin.Javalin;
import java.sql.*;
import java.util.logging.*;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String dbUser = "postgres";
        String dbPassword = "1234";

        setDatabase(jdbcUrl, dbUser, dbPassword); // Initial database setup.

        UserDao userDao = new UserDao(jdbcUrl, dbUser, dbPassword);
        UserService userService = new UserService(userDao);
        UserController userController = new UserController(userService);

        LoanDao loanDao = new LoanDao(jdbcUrl, dbUser, dbPassword);
        LoanService loanService = new LoanService(loanDao);
        LoanController loanController = new LoanController(loanService);

        Javalin javalinApp = Javalin.create().start(7000); // Javalin.

        javalinApp.post("/register", userController::register);
        javalinApp.post("/login", userController::login);
        javalinApp.post("/logout", userController::logout);
        javalinApp.get("/view_profile", userController::viewUserProfile);
        javalinApp.put("/update_profile", userController::updateUserProfile);
        javalinApp.post("/apply", loanController::apply);
        javalinApp.get("/loans", loanController::viewApplications);
        javalinApp.get("/loans/user/{id}", loanController::viewApplicationsByUser);
        javalinApp.patch("/update_loan/{id}", loanController::updateApplication);
        javalinApp.patch("/loans/{id}/status", loanController::change_status);
        javalinApp.get("/all_loans", loanController::allApplications);
    }

        private static void setDatabase(String jdbcUrl, String dbUser, String dbPassword) {
            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword );
                 Statement stmt = conn.createStatement()) {
                    stmt.execute(TablesSQL.DROP_TABLES_SQL);
                    stmt.execute(TablesSQL.CREATE_TABLES_SQL); }
            catch (SQLException e) {
                logger.log(Level.SEVERE, "Exception caught:", e);
            }
        }
}