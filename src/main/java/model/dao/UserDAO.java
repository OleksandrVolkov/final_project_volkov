package model.dao;

import model.entity.User;
import model.utility.MD5Handler;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  UserDAO represents a way to access database to the corresponding to the
 *  User entity class table "users" via JDBC API by SQL server.
 *  It represents the way to access to the value needed and make basic CRUD (create,
 *  read, update, delete) operations and some more added functionality.
 *  Moreover, it gives the opportunity to initialize the entity
 *  objects (User class) on the side of model which makes it easier to manipulate with the objects
 *  in the application in the object-oriented way.
 *  It extends an abstract AbstractDAO class and therefore overrides some its methods.
 *
 *
 * @author  Oleksandr Volkov
 * @version 1.0
 * @since   2019-01-15
 */

public class UserDAO extends AbstractDAO<User>{
    private static Logger logger = Logger.getLogger(UserDAO.class);

    public UserDAO(Connection connection){
        super(connection);
    }

    /**
     * This method is used to find all users from the corresponding table in the
     * database.
     * @return List of all of the users available in the table.
     */
    @Override
    public List<User> findAll() {
        logger.trace("Finding all users");
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Craeting statement");
            preparedStatement = connection.prepareStatement(query);
            logger.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                logger.trace("Getting values of the request_actions set");
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                users.add(new User(id, name, surname, username, password, email, role));
                logger.trace("User is added");
            }
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
        } finally{
            close(resultSet);
            close(preparedStatement);
        }

        logger.trace("Returning users");
        return users;
    }

    /**
     * This method is used to find user by its id in the database.
     * @param id This is the id of the user is needed to find.
     * @return User It returns the user by the given id.
     */
    @Override
    public User findEntityById(int id) {
        logger.trace("Finding users where id = " + id);
        String query = "SELECT * FROM users WHERE id = ?;";
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            logger.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                logger.trace("Getting values of the result set");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                user = new User(id, name, surname, username, password, email, role);
                logger.trace("User is created");
            }
            logger.trace("Returning a user");
            return user;
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
            return null;
        } finally{
            close(preparedStatement);
            close(resultSet);
        }
    }


    /**
     * This method is used to delete user by its id.
     * @param id This is id of the user is needed to delete.
     * @return boolean It returns the boolean value depending on whether user was deleted.
     * (by given id)
     */
    @Override
    public boolean delete(int id) {
        logger.trace("Deleting user with id = " + id);
        String query = "DELETE FROM users WHERE id = ?;";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            logger.trace("Creating statement ");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            logger.trace("Executing update");
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
            try {
                connection.rollback();
                logger.warn("Successfully rolled back changes from the database");
            } catch (SQLException e1) {
                e1.printStackTrace();
                logger.warn("Could not rollback updates " + e1.getMessage());
            }
            return false;
        } finally{
            close(preparedStatement);
        }
    }


    /**
     * This method is used to create user by given object of the corresponding class.
     * @param user This is the item which values will be inserted into the table "users".
     * @return boolean It returns the boolean value depending on whether user was successfully created.
     */
    @Override
    public boolean create(User user) {
        logger.trace("Creating user with username = " + user.getLogin());
        String query = "INSERT INTO users(name, surname, username, email, password, role) VALUES(?,?,?,?,?,?)";

        PreparedStatement preparedStatement = null;
        try{
            logger.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            logger.trace("Setting values for the prepared statement");
            String name = user.getName();
            String surname = user.getSurname();
            String username = user.getLogin();
            String email = user.getEmail();
            String password = user.getPassword();
            user.setPassword(MD5Handler.md5Custom(password));
            String role = user.getRole();
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, role);
            logger.trace("Executing prepared statement");
            preparedStatement.execute();
            logger.trace("Setting id for the user");
//            user.setId(getUserIndexByEmail(user.getEmail()));
            user.setId(this.getLastInsertedUserId());
            logger.trace("User is created");
            return true;

        } catch(SQLException e){
            logger.warn("Error a bit:(", e);
            return false;
        } finally{
            close(preparedStatement);
        }
    }


    /**
     * This method is used to get index of the last inserted user.
     * It is mainly used while creating user so that get its index for
     * future handling in the application.
     * @return Integer It returns the index of the last inserted user.
     */
    public Integer getLastInsertedUserId(){
        String query = "SELECT MAX( id ) AS max_id FROM users;";
        int id = 0;
        logger.trace("Creating statement");

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            logger.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logger.trace("Getting values of the result set");
                id = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            close(preparedStatement);
            close(resultSet);
        }
        logger.trace("Returning id = " + id);
        return id;
    }

    /**
     * This method is used to update user value in the database by its key.
     * @param user This is the user which values will be inserted into the table "users"
     * @param key This is the id which is used to update the corresponding value
     * @return User It returns given user
     */
    @Override
    public User update(User user, int key) {
        logger.trace("Updating user with id = " + key);
        String query = "UPDATE users SET name = ?, surname = ?, username = ?, email = ?, password = ? WHERE id = "+key+";";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            logger.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);

            logger.trace("Setting values for the prepared statement");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());

            logger.trace("Executing update");
            preparedStatement.executeUpdate();
            logger.trace("Returning user");
            connection.commit();
            return user;
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
            try {
                connection.rollback();
                logger.warn("Successfully rolled back changes from the database");
            } catch (SQLException e1) {
                e1.printStackTrace();
                logger.warn("Could not rollback updates " + e1.getMessage());
            }
            return null;
        } finally{
            close(preparedStatement);
        }
    }


    /**
     * This method is used to validate the user value in the database by its username and password.
     * @param username This is the username of the user
     * @param password This is the password of the user
     * @return boolean It returns the boolean value depending on whether user was successfully validated.
     */
    public boolean validateUser(String username, String password) {
        logger.trace("Validating user with login = " + username + ", password = " + password);
        String query = "SELECT * FROM users WHERE username=? AND password=?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            logger.trace("Setting values for the prepared statements");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            logger.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                logger.trace("User is found");
                return true;
            }
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
        } finally{
            close(preparedStatement);
            close(resultSet);
        }
        logger.trace("User is not found");
        return false;
    }


    /**
     * This method is used to check whether email was already taken.
     * @param email This is the email of the user
     * @return boolean It returns the boolean value depending on whether email was already taken by somebody.
     */
    public boolean isEmailTaken(String email){
        logger.trace("Checking whether email " + email + " is already taken");
        String query = "SELECT * FROM users WHERE email = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            logger.trace("Setting values for the prepared statement");
            preparedStatement.setString(1, email);
            logger.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                logger.trace("Email " + email + " is found");
                return true;
            }
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
        } finally{
            close(preparedStatement);
            close(resultSet);
        }
        logger.trace("Email " + email + "is not found");
        return false;
    }

    /**
     * This method is used to check whether username was already taken.
     * @param username This is the username of the user
     * @return boolean It returns the boolean value depending on whether username was already taken by somebody.
     */
    public boolean isUsernameTaken(String username){
        logger.trace("Checking whether username " + username + "is already taken");
        String query = "SELECT * FROM users WHERE username = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            logger.trace("Setting values for the prepared statement");
            preparedStatement.setString(1, username);
            logger.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                logger.trace("Username is taken");
                return true;
            }


        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
        } finally{
            close(preparedStatement);
            close(resultSet);
        }
        logger.trace("Username" + username + " is not taken");
        return false;
    }


    /**
     * This method is used to find a user by its username.
     * @param username This is the username of the supposed user
     * @return User It returns the user value fond by the given username
     */
    public User findUserByUsername(String username){
//        String query = "SELECT * FROM users WHERE login = " + username + ";";
        String query = "SELECT * FROM users WHERE username = ?";

        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            logger.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                logger.trace("Getting values of the result set");
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
//                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                user = new User(id, name, surname, username, password, email, role);
                logger.trace("User is created");
            }
            logger.trace("Returning a user");
            return user;
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
            return null;
        } finally{
            close(preparedStatement);
            close(resultSet);
        }
    }
}
