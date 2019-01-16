package model;

import model.dao.ItemDAO;
import model.dao.RejectDAO;
import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import model.entity.Item;
import model.entity.Reject;
import model.entity.Request;
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

    @Parameterized.Parameter
    public Reject reject;

    @Parameterized.Parameter(1)
    public Request request;

    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList( new Object[][]{
                {
                        new Reject("Just info"),
                        new Request("sadopkasd", "is being seen", 2)
                },
                {
                        new Reject("Item"),
                        new Request("already done", "is being seen", 3)}
                }
        );
    }


    @Before
    public void init(){
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        requestDAO = new RequestDAO(ConnectionManager.getConnection());
    }

    @Test
    public void testCreate(){
        request.setReject(reject);
        requestDAO.create(request);
        reject.setRequestId(request.getId());

        rejectDAO.create(reject);
        Reject curReject = rejectDAO.findEntityById(reject.getId());
        rejectDAO.delete(reject.getId());
        requestDAO.delete(request.getId());
        assertEquals(reject, curReject);
    }



    @Test
    public void testUpdate(){
        String curText = "sadsdaa";
        request.setReject(reject);
        requestDAO.create(request);
        reject.setRequestId(request.getId());

        rejectDAO.create(reject);
        reject.setText(curText);
        rejectDAO.update(reject, reject.getId());
        Reject rejectTemp = rejectDAO.findEntityById(reject.getId());
        assertEquals(reject, rejectTemp);
        rejectDAO.delete(reject.getId());
        requestDAO.delete(request.getId());
    }

    @Test
    public void testFindEntityById(){
        request.setReject(reject);
        requestDAO.create(request);
        reject.setRequestId(request.getId());

        rejectDAO.create(reject);
        Reject tempReject = rejectDAO.findEntityById(reject.getId());
        assertEquals(reject, tempReject);
        rejectDAO.delete(reject.getId());
        requestDAO.delete(request.getId());
    }

    @Test
    public void getLastInsertedRejectIndex(){
        request.setReject(reject);
        requestDAO.create(request);
        reject.setRequestId(request.getId());

        rejectDAO.create(reject);
        Integer rejectId = rejectDAO.getLastInsertedRejectIndex();
        Reject rejectTemp = rejectDAO.findEntityById(reject.getId());
        assertEquals(reject.getId(), rejectId, rejectTemp.getId());
        rejectDAO.delete(reject.getId());
        requestDAO.delete(request.getId());
    }

    @Test
    public void testDelete(){
        request.setReject(reject);
        requestDAO.create(request);
        reject.setRequestId(request.getId());

        rejectDAO.create(reject);
        rejectDAO.delete(reject.getId());
        assertNull(rejectDAO.findEntityById(reject.getId()));
        requestDAO.delete(request.getId());
    }

}
