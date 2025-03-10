package com.revature.Controller;

import com.revature.Model.Loan;
import com.revature.Model.User;
import com.revature.Service.LoanService;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public void apply(Context ctx) {
        Loan newLoan = ctx.bodyAsClass(Loan.class);
        HttpSession httpSession = ctx.req().getSession(false);

        if (httpSession == null) {
            ctx.status(403).json("{\"error\":\" You must log in!\"}");
        }   else {
            User user = (User) httpSession.getAttribute("user");
            if (user.getRole().equals("Regular")) {
                if (newLoan.getType() == null || newLoan.getAmount() == null) {
                    ctx.status(400).json("{\"error\":\" Application incomplete!\"}");
                }   else {
                    boolean success = loanService.loanApplication(newLoan.getType(), newLoan.getAmount(), user.getID());
                    if (success) {
                        ctx.status(200).json("{\"message\":\" You've successfully fulfilled the application!\"}");
                    }   else {
                        ctx.status(403).json("{\"error\":\" Something went wrong!\"}");
                    }
                }
            }   else {
                ctx.status(403).json("{\"error\":\" Invalid action!\"}");
            }
        }
    }

    public void change_status(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Loan existingApplication = ctx.bodyAsClass(Loan.class);

        HttpSession httpSession = ctx.req().getSession(false);
        if (httpSession == null) {
            ctx.status(403).json("{\"error\":\" You must log in!\"}");
        }   else {
            User newUser = (User) httpSession.getAttribute("user");
            if (newUser.getRole().equals("Manager")) {
                boolean success = loanService.changeStatus(id, existingApplication.getIdLoan(), existingApplication.getStatus());
                if (success) {
                    ctx.status(200).json("{\"message\":\" You've successfully updated the status!\"}");
                }   else {
                    ctx.status(403).json("{\"error\":\" Something went wrong!\"}");
                }
            }   else {
                ctx.status(403).json("{\"error\":\" Invalid action!\"}");
            }
        }
    }

    public void viewApplicationsByUser(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        HttpSession httpSession = ctx.req().getSession(false);

        if (httpSession == null) {
            ctx.status(403).json("{\"error\":\" You must log in!\"}");
        }   else {
            User user = (User) httpSession.getAttribute("user");
            if (user.getRole().equals("Manager")) {
                ArrayList<Loan> loanApplication = loanService.viewApplicationsByUser(id);
                if (loanApplication.isEmpty()) {
                    ctx.status(403).json("{\"error\":\" Something went wrong!\"}");
                }   else {
                    ctx.status(200).json(loanApplication);
                }
            }
        }
    }

    public void allApplications(Context ctx) {
        HttpSession httpSession = ctx.req().getSession(false);

        if (httpSession == null) {
            ctx.status(403).json("{\"error\":\" You must log in!\"}");
        }   else {
            User user = (User) httpSession.getAttribute("user");
            if (user.getRole().equals("Manager")) {
                ArrayList<Loan> applications = loanService.allApplications();
                if (applications.isEmpty()) {
                    ctx.status(403).json("{\"error\":\" Something went wrong!\"}");
                }   else {
                    ctx.status(200).json(applications);
                }
            }   else {
                ctx.status(403).json("{\"error\":\" Invalid action!\"}");
            }
        }
    }

    public void updateApplication(Context ctx) {
        Loan newLoan = ctx.bodyAsClass(Loan.class);
        HttpSession httpSession = ctx.req().getSession(false);

        if (httpSession == null) {
            ctx.status(403).json("{\"error\":\" You must log in!\"}");
        }   else {
            User user = (User) httpSession.getAttribute("user");
            if (user.getRole().equals("Regular")) {
                if (newLoan.getType() == null || newLoan.getAmount() == null) {
                    ctx.status(400).json("{\"error\":\" Application incomplete!\"}");
                }   else {
                    boolean success = loanService.updateApplication(user.getID(), newLoan.getType(), newLoan.getAmount(), user.getID());
                    if (success) {
                        ctx.status(200).json("{\"message\":\" You've successfully fulfilled the application!\"}");
                    }   else {
                        ctx.status(403).json("{\"error\":\" Something went wrong!\"}");
                    }
                }
            }   else {
                ctx.status(403).json("{\"error\":\" Invalid action!\"}");
            }
        }
    }

    public void viewApplications(Context ctx) {
        HttpSession httpSession = ctx.req().getSession(false);

        if (httpSession == null) {
            ctx.status(403).json("{\"error\":\" You must log in!\"}");
        }   else {
            User user = (User) httpSession.getAttribute("user");
            if (user.getRole().equals("Regular")) {
                ArrayList<Loan> loanApplication = loanService.viewApplications(user.getID());
                if (loanApplication.isEmpty()) {
                    ctx.status(403).json("{\"error\":\" Something went wrong!\"}");
                }   else {
                    ctx.status(200).json(loanApplication);
                }
            }
        }
    }
}