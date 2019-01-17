package model;

import model.dao.FeedbackDAO;
import model.dao.ItemDAO;
import model.dao.RequestDAO;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import model.entity.Item;
import model.entity.Request;
import model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestFeedbackDAO {
    private ItemDAO itemDAO;
    private RequestDAO requestDAO;
    private UserDAO userDAO;
    private FeedbackDAO feedbackDAO;

    @Parameterized.Parameter
    public Feedback feedback;
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
                        new Feedback("fsafsasfa", "2019-06-02"),
                        new Request("oidsahoadssda", "is being seen"),
                        new User("Name", "Surname", "newqjnweqj21", "sadsad", "sdaioj123@gmail.com", "client"),
                        new Item("newIt", "Just info"),
                }
            }
        );
    }

    @Before
    public void init(){
        userDAO = new UserDAO(ConnectionManager.getConnection());
        requestDAO = new RequestDAO(ConnectionManager.getConnection());
        itemDAO = new ItemDAO(ConnectionManager.getConnection());
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
    }


    private void create(User user, Item item, Request request, Feedback feedback){
        userDAO.create(user);
        itemDAO.create(item);
        request.setItemId(item.getId());
        request.setUserId(user.getId());
        requestDAO.create(request);
        feedback.setRequestId(request.getId());
        feedbackDAO.create(feedback);
    }
    private void delete(User user, Item item, Request request, Feedback feedback){
        feedbackDAO.delete(feedback.getId());
        requestDAO.delete(request.getId());
        System.out.println(userDAO.delete(user.getId()) + "!!!!!!!!!!!!!!");
        itemDAO.delete(item.getId());
    }


    @Test
    public void testCreate(){
        create(user, item, request, feedback);
        Feedback curFeedback = feedbackDAO.findEntityById(feedback.getId());
        assertEquals(feedback, curFeedback);
        delete(user, item, request, feedback);
    }

    @Test
    public void testDelete(){
        create(user, item, request, feedback);
        delete(user, item, request, feedback);
        assertNull(feedbackDAO.findEntityById(feedback.getId()));
    }

    @Test
    public void testUpdate(){
        String curDate = "1999-03-02";
        String curText = "a";
        create(user, item, request, feedback);
        feedback.setText(curText);
        feedback.setDate(curDate);
        feedbackDAO.update(feedback, feedback.getId());
        Feedback feedbackTemp = feedbackDAO.findEntityById(feedback.getId());
        assertEquals(feedbackTemp.getDate(), curDate);
        assertEquals(feedbackTemp.getText(), curText);
        delete(user, item, request, feedback);
    }

    @Test
    public void testFindEntityById(){
        create(user, item, request, feedback);
        Feedback tempFeedback = feedbackDAO.findEntityById(feedback.getId());
        assertEquals(feedback, tempFeedback);
        delete(user, item, request, feedback);
    }

    @Test
    public void testGetFeedbackByRequestId(){
        create(user, item, request, feedback);
        Feedback feedback_cur = feedbackDAO.getFeedbackByRequestId(feedback.getRequestId());
        assertEquals(feedback, feedback_cur);
        delete(user, item, request, feedback);
    }


    @Test
    public void testGetLastInsertedFeedbackIndex(){
        create(user, item, request, feedback);
        Integer feedbackId = feedbackDAO.getLastInsertedFeedbackIndex();
        Feedback feedbackTemp = feedbackDAO.findEntityById(feedback.getId());
        assertEquals(feedback.getId(), feedbackId, feedbackTemp.getId());
        delete(user, item, request, feedback);
    }

}
