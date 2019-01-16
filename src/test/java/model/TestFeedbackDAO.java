package model;

import model.dao.FeedbackDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestFeedbackDAO {
    private FeedbackDAO feedbackDAO;

    @Parameterized.Parameter
    public Feedback feedback;

    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList( new Object[]{
                        new Feedback("fsafsasfa", "2019-06-02", 40),
                        new Feedback("asodij", "1999-05-01", 39)
                }
        );
    }

    @Before
    public void init(){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
    }

    @Test
    public void testCreate(){
        feedbackDAO.create(feedback);
        Feedback curFeedback = feedbackDAO.findEntityById(feedback.getId());
        assertEquals(feedback.getText(), curFeedback.getText());
        assertEquals(feedback.getDate(), curFeedback.getDate());
        assertEquals(feedback.getRequestId(), curFeedback.getRequestId());
        feedbackDAO.delete(feedback.getId());
    }

    @Test
    public void testDelete(){
        feedbackDAO.create(feedback);
        feedbackDAO.delete(feedback.getId());
        assertNull(feedbackDAO.findEntityById(feedback.getId()));
    }

    @Test
    public void testUpdate(){
        String curDate = "1999-03-02";
        String curText = "a";
        feedbackDAO.create(feedback);
        feedback.setText(curText);
        feedback.setDate(curDate);
        feedbackDAO.update(feedback, feedback.getId());
        Feedback feedbackTemp = feedbackDAO.findEntityById(feedback.getId());
        assertEquals(feedbackTemp.getDate(), curDate);
        assertEquals(feedbackTemp.getText(), curText);
        feedbackDAO.delete(feedback.getId());
    }

    @Test
    public void testFindEntityById(){
        feedbackDAO.create(feedback);
        Feedback tempFeedback = feedbackDAO.findEntityById(feedback.getId());

//        int firstElement = feedbackDAO.findEntityById(fee);

        assertEquals(feedback.getDate(), tempFeedback.getDate());
        assertEquals(feedback.getText(), tempFeedback.getText());
        assertEquals(feedback.getId(), tempFeedback.getId());
        assertEquals(feedback.getRequestId(), tempFeedback.getRequestId());
        feedbackDAO.delete(feedback.getId());
    }

    @Test
    public void testGetFeedbackByRequestId(){
        Feedback feedback_cur = feedbackDAO.getFeedbackByRequestId(feedback.getRequestId());
        System.out.println(feedback_cur);
        assertEquals(feedback, feedback_cur);
    }

    @Test
    public void testGetLastInsertedFeedbackIndex(){
        feedbackDAO.create(feedback);
        Integer feedbackId = feedbackDAO.getLastInsertedFeedbackIndex();
        Feedback feedbackTemp = feedbackDAO.findEntityById(feedback.getId());
        assertEquals(feedback.getId(), feedbackId, feedbackTemp.getId());
        feedbackDAO.delete(feedback.getId());
    }

}
