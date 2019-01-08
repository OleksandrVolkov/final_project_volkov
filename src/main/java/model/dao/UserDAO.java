package model.dao;

import model.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User>{
    public UserDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");


                users.add(new User(id, name, surname, login, password, email, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User findEntityById(int id) {
        String query = "SELECT * FROM users WHERE id = " + id + ";";
        User user = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                user = new User(id, name, surname, login, password, email, role);
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM users WHERE id = " + id + ";";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean create(User user) {
        String query = "INSERT INTO users(name, surname, login, email, password, role) VALUES(?,?,?,?,?,?)";
        try{
            preparedStatement = connection.prepareStatement(query);
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

            preparedStatement.execute();
            user.setId(getUserIndexByEmail(user.getEmail()));
            return true;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    //email - unique in sql!!!!
    public Integer getUserIndexByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = '" + email + "';";
        System.out.println(query);

            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return id;

    }

    @Override
    public User update(User user, int key) {
        String query = "UPDATE users SET name = ?, surname = ?, login = ?, email = ?, password = ? WHERE id = "+key+";";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());

            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean validateUser(String login, String password) {
        String query = "SELECT * FROM users WHERE login=? AND password=?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailTaken(String email){
        String query = "SELECT * FROM users WHERE email = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isUsernameTaken(String username){
        String query = "SELECT * FROM users WHERE login = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
