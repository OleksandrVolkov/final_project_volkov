package services;

import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.User;

public class UserService {
    UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
    public UserService(){

    }
    public void createUser(User user){
        userDAO.create(user);
    }

}
