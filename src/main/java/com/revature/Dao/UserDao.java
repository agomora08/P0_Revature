package com.revature.Dao;

import com.revature.Model.User;

import java.sql.*;
import java.util.logging.*;

public class UserDao {
    private final String Url;
    private final String dbUser;
    private final String dbPassword;

    private final Logger logger = Logger.getLogger(UserDao.class.getName());

    public UserDao(String Url, String dbUser, String dbPassword) {
        this.Url = Url;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword; }

    public boolean createProfile(User newUser) {
        String sqlUsers = "INSERT INTO Users (name, ssn, role) VALUES (?, ?, ?);";
        String sqlCredentials = "INSERT INTO Credentials (username, hashed_password, id_user) VALUES (?, ?, ?);";
        String sqlAddress = "INSERT INTO Mailing_address (street, street_number, zip_code, state, id_user) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
             PreparedStatement stmt1 = conn.prepareStatement(sqlUsers, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmt2 = conn.prepareStatement(sqlCredentials, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmt3 = conn.prepareStatement(sqlAddress, Statement.RETURN_GENERATED_KEYS)) {
                stmt1.setString(1, newUser.getName());
                stmt1.setString(2, newUser.getSSN());
                stmt1.setString(3, newUser.getRole());
                stmt1.executeUpdate();

                 try (ResultSet generatedKeys = stmt1.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        newUser.setID(newId);

                        stmt2.setString(1, newUser.getUsername());
                        stmt2.setString(2, newUser.getUserPassword());
                        stmt2.setInt(3, newUser.getID());
                        stmt2.executeUpdate();

                        stmt3.setString(1, newUser.getStreet());
                        stmt3.setString(2, newUser.getStreetNumber());
                        stmt3.setString(3, newUser.getZipCode());
                        stmt3.setString(4, newUser.getState());
                        stmt3.setInt(5, newUser.getID());
                        stmt3.executeUpdate();
                    }
                }
                return true;
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
            return false;
        }
    }

    public User getUserByUsername(String username) {
        String sqlQuery = "SELECT * FROM Credentials WHERE username = ?";

        try(Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("hashed_password"));
                        user.setRole(getUserRole(rs.getInt("id_user")));
                        return user;
                    }
                }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return null;
    }

    private String getUserRole(int idUser) {
        String sqlQuery = "SELECT * FROM Users WHERE id_user = ?";

        try (Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
                stmt.setInt(1, idUser);
                try(ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("role");
                    }
                }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return "null";
    }

    public User viewUserProfile(int id) {
        String sqlQuery = """
                SELECT newTable.id, name, username, ssn, role, street, street_number, zip_code, state
                FROM Mailing_address FULL OUTER JOIN
                (SELECT Users.id_user as id, name, username, ssn, role FROM Users FULL OUTER JOIN
                Credentials ON Users.id_user = Credentials.id_user) as newTable ON
                Mailing_address.id_user = newTable.id WHERE newTable.id = ?;
                """;

        try(Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("name"),
                                rs.getString("ssn"),
                                rs.getString("role"),
                                rs.getString("street"),
                                rs.getString("street_number"),
                                rs.getString("zip_code"),
                                rs.getString("state"));
                    }
                }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return null;
    }

    public boolean updateUserProfile(User updatedUser) {
        String sqlUsers = """
                          UPDATE Users SET name = ?, ssn = ?, role = ? WHERE id_user = ?;
                          UPDATE Credentials SET username = ? WHERE id = ?;
                          UPDATE Mailing_address SET street = ?, street_number = ?, zip_code = ?, state = ? WHERE id = ?;
                          """;

        try(Connection conn = DriverManager.getConnection(Url, dbUser, dbPassword);
            PreparedStatement stmt = conn.prepareStatement(sqlUsers)) {
                stmt.setString(1, updatedUser.getName());
                stmt.setString(2, updatedUser.getSSN());
                stmt.setString(3, updatedUser.getRole());
                stmt.setInt(4, updatedUser.getID());
                stmt.setString(5, updatedUser.getUsername());
                stmt.setInt(6, updatedUser.getID());
                stmt.setString(7, updatedUser.getStreet());
                stmt.setString(8, updatedUser.getStreetNumber());
                stmt.setString(9, updatedUser.getZipCode());
                stmt.setString(10, updatedUser.getState());
                stmt.setInt(11, updatedUser.getID());
                int updatedRows = stmt.executeUpdate();
                if (updatedRows >= 1) {
                    return true;
                }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception caught:", e);
        }
        return false;
    }
}