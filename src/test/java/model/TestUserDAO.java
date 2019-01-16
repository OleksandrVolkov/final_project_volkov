package model;

import model.dao.RequestDAO;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;
import model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestUserDAO {
    private UserDAO userDAO;

    @Parameterized.Parameter
    public User user;


    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList( new Object[][]{
                        {new User("Name","Surname","my_login123","sadsad","al@gmail.com","client")}
                }
        );
    }


    @Before
    public void init(){
        userDAO = new UserDAO(ConnectionManager.getConnection());
    }

    @Test
    public void testCreate(){
        userDAO.create(user);
        User curUser = userDAO.findEntityById(user.getId());
        assertEquals(user, curUser);
        userDAO.delete(user.getId());
    }

    @Test
    public void testDelete(){
        userDAO.create(user);
        userDAO.delete(user.getId());
        assertNull(userDAO.findEntityById(user.getId()));
    }

    @Test
    public void testUpdate(){
        String curEmail = "val@gmail.com";
        String curPass = "poajsd";
        userDAO.create(user);
        user.setEmail(curEmail);
        user.setPassword(curPass);
        userDAO.update(user, user.getId());
        User userTemp = userDAO.findEntityById(user.getId());
        assertEquals(user, userTemp);
        userDAO.delete(user.getId());
    }

    @Test
    public void testFindEntityById(){
        userDAO.create(user);
        User tempUser = userDAO.findEntityById(user.getId());
        assertEquals(user, tempUser);

        userDAO.delete(user.getId());
    }

    @Test
    public void testGetUserIndexByEmail(){
            userDAO.create(user);
//            int userId = userDAO.getUserIndexByEmail(user.getEmail());
            int userId = userDAO.getLastInsertedUserId();
            assertEquals(user.getId(), userId);
            userDAO.delete(user.getId());
    }

    @Test
    public void testValidateUser(){
        userDAO.create(user);
        boolean isValidated = userDAO.validateUser(user.getLogin(), user.getPassword());
        userDAO.delete(user.getId());
        assertTrue(isValidated);

    }

    @Test
    public void testIsEmailTaken(){
        userDAO.create(user);
        boolean isEmailTaken = userDAO.isEmailTaken(user.getEmail());
        userDAO.delete(user.getId());
        assertTrue(isEmailTaken);
    }

    @Test
    public void testIsUsernameTaken(){
        userDAO.create(user);
        boolean isUsernameTaken = userDAO.isUsernameTaken(user.getLogin());
        userDAO.delete(user.getId());
        assertTrue(isUsernameTaken);
    }
}
