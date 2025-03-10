package com.revature.Service;

import com.revature.Dao.UserDao;
import com.revature.Model.User;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {this.userDao = userDao; }

    public boolean registerUser(String username, String password, String name,
                                String ssn, String role, String street, String streetNumber,
                                String zipCode, String state) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setName(name);
        newUser.setSSN(ssn);
        newUser.setRole(role);
        newUser.setStreet(street);
        newUser.setStreetNumber(streetNumber);
        newUser.setZipCode(zipCode);
        newUser.setState(state);
        return userDao.createProfile(newUser);
    }

    public User loginUser(String username, String password) {
        User existingUser = userDao.getUserByUsername(username);
        if (existingUser != null) {
            if (BCrypt.checkpw(password, existingUser.getUserPassword())) {
                return existingUser;
            }
        }
        return null;
    }

    public User viewUserProfile(int id) {
        return userDao.viewUserProfile(id);
    }

    public boolean updateUserProfile(int id, String username, String name,
                                     String ssn, String role, String street, String streetNumber,
                                     String zipCode, String state) {
        User updatedUser = new User();
        updatedUser.setID(id);
        updatedUser.setUsername(username);
        updatedUser.setName(name);
        updatedUser.setSSN(ssn);
        updatedUser.setRole(role);
        updatedUser.setStreet(street);
        updatedUser.setStreetNumber(streetNumber);
        updatedUser.setZipCode(zipCode);
        updatedUser.setState(state);
        return userDao.updateUserProfile(updatedUser);
    }
}