package com.revature.Controller;

import com.revature.Model.User;
import com.revature.Service.UserService;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService; }

    public void register(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);

        if (newUser.getUsername() == null || newUser.getUserPassword() == null || newUser.getName() == null ||
                newUser.getSSN() == null || newUser.getRole() == null || newUser.getStreet() == null ||
                newUser.getStreetNumber() == null || newUser.getZipCode() == null || newUser.getState() == null) {
            ctx.status(400).json("{\"error\":\"It looks like some data is missing!\"}");
        }   else {
            boolean success = userService.registerUser(newUser.getUsername(), newUser.getUserPassword(),
                    newUser.getName(), newUser.getSSN(), newUser.getRole(), newUser.getStreet(), newUser.getStreetNumber(),
                    newUser.getZipCode(), newUser.getState());
            if (success) {
                ctx.status(201).json("{\"message\":\"User registered successfully!\"}");
            }   else {
                ctx.status(403).json("{\"error\":\"The registration attempt failed!\"}");
            }
        }
    }

    public void login(Context ctx) {
        User user = ctx.bodyAsClass(User.class);

        if (user.getUsername() == null || user.getUserPassword() == null) {
            ctx.status(400).json("{\"error\":\"Credentials are missing!\"}");
        }   else {
            User loggedInUser = userService.loginUser(user.getUsername(), user.getUserPassword());
            if (loggedInUser != null) {
                ctx.status(200).json("{\"message\":\"You logged in successfully!\"}");
                HttpSession httpSession = ctx.req().getSession(true);
                httpSession.setAttribute("user", loggedInUser);
            }   else {
                ctx.status(403).json("{\"error\":\"You could not log in!\"}");
            }
        }
    }

    public void logout(Context ctx) {
        HttpSession httpSession = ctx.req().getSession(false);
        if (httpSession == null) {
            ctx.status(403).json("{\"error:\" You must log in!\"}");
        }   else {
            httpSession.invalidate();
            ctx.status(200).json("{\"message\":\" You logged out successfully!\"}");
        }
    }

    public void viewUserProfile(Context ctx) {
        HttpSession httpSession = ctx.req().getSession(false);
        if (httpSession == null) {
            ctx.status(403).json("{\"error:\" You must log in!\"}");
        }   else {
            User user = (User) httpSession.getAttribute("user");
            User registeredUser = userService.viewUserProfile(user.getID());
            if (user.getRole().equals("Regular")) {
                ctx.status(200).json(registeredUser);
            }   else {
                ctx.status(403).json("{\"error:\" Invalid request!\"}");
            }
        }
    }

    public void updateUserProfile(Context ctx) {
        User updatedUser = ctx.bodyAsClass(User.class);

        HttpSession httpSession = ctx.req().getSession(false);
        if (httpSession == null) {
            ctx.status(403).json("{\"error:\" You must log in!\"}");
        }   else {
            User user = (User) httpSession.getAttribute("user");
            if (user.getRole().equals("Regular")) {
                boolean success = userService.updateUserProfile(user.getID(), updatedUser.getUsername(),
                        updatedUser.getName(), updatedUser.getSSN(), updatedUser.getRole(), updatedUser.getStreet(),
                        updatedUser.getStreetNumber(), updatedUser.getZipCode(), updatedUser.getState());
                if (success) {
                    ctx.status(201).json("{\"message\":\" Profile successfully updated!\"}");
                }   else {
                    ctx.status(403).json("{\"error:\" Something went wrong!\"}");
                }
            }   else {
                ctx.status(403).json("{\"error:\" Invalid action!\"}");
            }
        }
    }
}