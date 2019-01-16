package model.dao;

import model.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User>{
    private static Logger logger = Logger.getLogger(UserDAO.class);

    public UserDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<User> findAll() {
        logger.trace("Finding all users");
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Craeting statement");
            statement = connection.createStatement();
            logger.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                logger.trace("Getting values of the request set");
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                users.add(new User(id, name, surname, login, password, email, role));
                logger.trace("User is added");
            }
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
        } finally{
            close(statement);
        }

        logger.trace("Returning users");
        return users;
    }

    @Override
    public User findEntityById(int id) {
        logger.trace("Finding users where id = " + id);
        String query = "SELECT * FROM users WHERE id = " + id + ";";
        User user = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Creating statement");
            statement = connection.createStatement();
            logger.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                logger.trace("Getting values of the result set");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                user = new User(id, name, surname, login, password, email, role);
                logger.trace("User is created");
            }
            logger.trace("Returning a user");
            return user;
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
            return null;
        } finally{
            close(statement);
            close(resultSet);
        }
    }

    @Override
    public boolean delete(int id) {
        logger.trace("Deleting user with id = " + id);
        String query = "DELETE FROM users WHERE id = " + id + ";";
        Statement statement = null;
        try {
            logger.trace("Creating statement ");
            statement = connection.createStatement();
            logger.trace("Executing update");
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
            return false;
        } finally{
            close(statement);
        }
    }

    @Override
    public boolean create(User user) {
        logger.trace("Creating user with username = " + user.getLogin());
        String query = "INSERT INTO users(name, surname, login, email, password, role) VALUES(?,?,?,?,MD5(?),?)";

        PreparedStatement preparedStatement = null;
        try{
            logger.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            logger.trace("Setting values for the prepared statement");
            String name = user.getName();
            String surname = user.getSurname();
            String login = user.getLogin();
            String email = user.getEmail();
            String password = user.getPassword();
            String role = user.getRole();
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, login);
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


//    //email - unique in sql!!!!
//    public Integer getUserIndexByEmail(String email) throws SQLException {
//        logger.trace("Getting user index by email = " + email);
//        String query = "SELECT * FROM users WHERE email = '" + email + "';";
////        System.out.println(query);
//        logger.trace("Creating statement");
//            statement = connection.prepareStatement(query);
//            logger.trace("Creating result set");
//            resultSet = statement.executeQuery(query);
//            int id = 0;
//            while (resultSet.next()) {
//                logger.trace("Getting values of the result set");
//                id = resultSet.getInt("id");
//            }
//            logger.trace("Returning id = " + id);
//            return id;
//
//    }

    public Integer getLastInsertedUserId(){
        String query = "SELECT MAX( id ) AS max_id FROM users;";
        int id = 0;
//        System.out.println(query);
        logger.trace("Creating statement");

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            logger.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                logger.trace("Getting values of the result set");
                id = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            close(statement);
            close(resultSet);
        }
        logger.trace("Returning id = " + id);
        return id;
    }

    @Override
    public User update(User user, int key) {
        logger.trace("Updating user with id = " + key);
        String query = "UPDATE users SET name = ?, surname = ?, login = ?, email = ?, password = ? WHERE id = "+key+";";
        PreparedStatement preparedStatement = null;
        try {
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
            return user;
        } catch (SQLException e) {
            logger.warn("Error a bit:(", e);
            return null;
        } finally{
            close(preparedStatement);
        }
    }


    public boolean validateUser(String login, String password) {
        logger.trace("Validating user with login = " + login + ", password = " + password);
        String query = "SELECT * FROM users WHERE login=? AND password=?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            logger.trace("Setting values for the prepared statements");
            preparedStatement.setString(1, login);
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
    public boolean isUsernameTaken(String username){
        logger.trace("Checking whether username " + username + "is already taken");
        String query = "SELECT * FROM users WHERE login = ?;";

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

    public User findUserByUsername(String username){
//        String query = "SELECT * FROM users WHERE login = " + username + ";";
        String query = "SELECT * FROM users WHERE login = ?";

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
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                user = new User(id, name, surname, login, password, email, role);
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
