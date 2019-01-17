package model;

import model.dao.ItemDAO;
import model.dao.RejectDAO;
import model.dao.RequestDAO;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestRejectDAO {
    private RejectDAO rejectDAO;
    private RequestDAO requestDAO;
    private UserDAO userDAO;
    private ItemDAO itemDAO;

    @Parameterized.Parameter
    public Reject reject;

    @Parameterized.Parameter(1)
    public Request request;

    @Parameterized.Parameter(2)
    public User user;

    @Parameterized.Parameter(3)
    public Item item;





    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList( new Object[][]{
                        {
                                new Reject("Just info"),
                                new Request("sadopkasd", "is being seen"),
                                new User("Name","Surname","my_login129","sadsad","al4@gmail.com","client"),
                                new Item("asdino","sdaopjads")
                        }
//                },
//                {
//                        new Reject("Item"),
//                        new Request("already done", "is being seen", 3)
//                }
                }
        );
    }


    @Before
    public void init(){
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        requestDAO = new RequestDAO(ConnectionManager.getConnection());
        itemDAO = new ItemDAO(ConnectionManager.getConnection());
        userDAO = new UserDAO(ConnectionManager.getConnection());
    }

    private void create(Item item, User user, Request request, Reject reject){
        userDAO.create(user);
        itemDAO.create(item);
        System.out.println(user);
        request.setReject(reject);
        request.setUserId(user.getId());
        request.setItemId(item.getId());
        requestDAO.create(request);
        reject.setRequestId(request.getId());
        rejectDAO.create(reject);
    }

    private void delete(Item item, User user, Request request, Reject reject){
        rejectDAO.delete(reject.getId());
        requestDAO.delete(request.getId());
        itemDAO.delete(item.getId());
        userDAO.delete(user.getId());
    }

    @Test
    public void testCreate(){
        create(item, user, request, reject);
        Reject curReject = rejectDAO.findEntityById(reject.getId());
        assertEquals(reject, curReject);
        delete(item, user, request, reject);
    }



    @Test
    public void testUpdate(){
        String curText = "sadsdaa";
        create(item, user, request, reject);

        reject.setText(curText);
        rejectDAO.update(reject, reject.getId());
        Reject rejectTemp = rejectDAO.findEntityById(reject.getId());
        assertEquals(reject, rejectTemp);
        delete(item, user, request, reject);
    }

    @Test
    public void testFindEntityById(){
        create(item, user, request, reject);
        Reject tempReject = rejectDAO.findEntityById(reject.getId());
        assertEquals(reject, tempReject);
        delete(item, user, request, reject);
    }

    @Test
    public void getLastInsertedRejectIndex(){
        create(item, user, request, reject);
        Integer rejectId = rejectDAO.getLastInsertedRejectIndex();
        Reject rejectTemp = rejectDAO.findEntityById(reject.getId());
        assertEquals(reject.getId(), rejectId, rejectTemp.getId());
        delete(item, user, request, reject);
    }

    @Test
    public void testDelete(){
        create(item, user, request, reject);
        delete(item, user, request, reject);
//        rejectDAO.delete(reject.getId());
        assertNull(rejectDAO.findEntityById(reject.getId()));
//        requestDAO.delete(request_actions.getId());
    }

}
