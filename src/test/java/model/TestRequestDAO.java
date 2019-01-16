package model;

import model.dao.FeedbackDAO;
import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import model.entity.Item;
import model.entity.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class TestRequestDAO {
    private RequestDAO requestDAO;

    @Parameterized.Parameter
    public Request request;
    @Parameterized.Parameter(1)
    public double price;
    @Parameterized.Parameter(2)
    public int currentPage;
    @Parameterized.Parameter(3)
    public int recordsPerPage;
    @Parameterized.Parameter(4)
    public String status;
    @Parameterized.Parameter(5)
    public String firstStatus;
    @Parameterized.Parameter(6)
    public String secondStatus;



    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList( new Object[][]{
                        {new Request("sddpasd", "not seen", 2), 23.2, 1, 5, "rejected", "is being seen", "rejected"}
//                {new Request("sadpoja", "not seen"),19.2}
                }
        );
    }


    @Before
    public void init(){
        requestDAO = new RequestDAO(ConnectionManager.getConnection());
    }

    @Test
    public void testCreate(){
        requestDAO.create(request);
        Request curRequest = requestDAO.findEntityById(request.getId());
        assertEquals(request.getText(), curRequest.getText());
        assertEquals(request.getStatus(), curRequest.getStatus());
        requestDAO.delete(request.getId());
    }

    @Test
    public void testUpdate(){
        String status = "rejected";
        String curText = "a";
        requestDAO.create(request);
        request.setText(curText);
        request.setStatus(status);
        requestDAO.update(request, request.getId());
        Request requestTemp = requestDAO.findEntityById(request.getId());
        assertEquals(requestTemp.getStatus(), status);
        assertEquals(requestTemp.getText(), curText);
        requestDAO.delete(request.getId());
    }

    @Test
    public void testDelete(){
        requestDAO.create(request);
        requestDAO.delete(request.getId());
        assertNull(requestDAO.findEntityById(request.getId()));
    }

    @Test
    public void testFindEntityById(){
        requestDAO.create(request);
        Request tempRequest = requestDAO.findEntityById(request.getId());
        assertEquals(request.getStatus(), tempRequest.getStatus());
        assertEquals(request.getText(), tempRequest.getText());
        assertEquals(request.getId(), tempRequest.getId());

        requestDAO.delete(request.getId());
    }

    @Test
    public void testAddPriceToRequest(){
        requestDAO.create(request);
        request.setPrice(price);
        requestDAO.addPriceToRequest(request);
        Double tempPrice = requestDAO.getPriceByRequestId(request.getId());
        assertEquals(request.getPrice(), tempPrice);
        requestDAO.delete(request.getId());
    }

    @Test
    public void testFindLimitRequestsByTwoStatuses(){

            List<Request> requests = requestDAO.findLimitRequests(currentPage, recordsPerPage, firstStatus, secondStatus);

            int start = requestDAO.getStartIndex(currentPage, recordsPerPage);

            List<Request> allRequests = requestDAO.findAll();

            List<Request> subRequests = new ArrayList<>();
            int counter = 0;
            for(int i = 0; i < allRequests.size(); i++){
                if(i == start && counter!=5) {
                    while(true) {
                        if(counter == recordsPerPage)
                            break;
                        if(i>=allRequests.size())
                            break;
                        if(allRequests.get(i).getStatus().equals(firstStatus) || allRequests.get(i).getStatus().equals(secondStatus)) {
                            subRequests.add(allRequests.get(i));
                            i++;
                            counter++;
                        }else {
                            i++;
                        }
                    }
                }
            }
            assertEquals(subRequests, requests);
    }

    @Test
    public void testFindLimitRequestsByStatus(){
            List<Request> requests = requestDAO.findLimitRequests(currentPage, recordsPerPage, status);
            System.out.println(requests);

            int start = requestDAO.getStartIndex(currentPage, recordsPerPage);

            List<Request> allRequests = requestDAO.findAll();
            List<Request> subRequests = new ArrayList<>();

            int counter = 0;
            for(int i = 0; i < allRequests.size(); i++){
                if(i == start && counter!=5) {
                    while(true) {
                        if(counter == recordsPerPage)
                            break;
                        if(i>=allRequests.size())
                            break;
                        if(allRequests.get(i).getStatus().equals(status)) {
                            subRequests.add(allRequests.get(i));
                            counter++;
                        }
                        i++;
                    }
                }
            }

        assertEquals(requests, subRequests);
    }

    @Test
    public void testGetPriceByRequestId(){
        requestDAO.create(request);
        request.setPrice(123.1);
        requestDAO.addPriceToRequest(request);
        double price = requestDAO.getPriceByRequestId(request.getId());
        assertEquals(price, 123.1, 0.0001);
        requestDAO.delete(request.getId());
    }

    @Test
    public void testCreateRequestsWithItems(){
        requestDAO.create(request);
        List<Item> items = new ArrayList<>();
        items.add(new Item("IOHASDO","PASDJOADS"));
        request.setItems(items);
        boolean flag = false;

        flag = requestDAO.createRequestsWithItems(request);

        assertEquals(true, flag);
    }

    @Test
    public void testGetAmountOfRequestsByTwoStatuses(){
        List<Request> allRequests = requestDAO.findAll();
        int counter = 0;
        for(Request request: allRequests){
            if(request.getStatus().equals(firstStatus) || request.getStatus().equals(secondStatus)){
                counter++;
            }
        }

        int amount = requestDAO.getAmountOfRequestsByTwoStatuses(firstStatus, secondStatus);
        assertEquals(amount, counter);
    }

    @Test
    public void testGetAmountOfRequestsByStatus(){
        List<Request> requests = requestDAO.findAll();

        int counter = 0;
        for(Request request: requests)
            if(request.getStatus().equals(status))
                counter++;

        int factCounter = requestDAO.getAmountOfRequestsByStatus(status);
        assertEquals(factCounter, counter);
    }

    @Test
    public void testGetAmountOfRequests(){
        List<Request> requests = requestDAO.findAll();
        int factCounter = requestDAO.getAmountOfRequests();
        assertEquals(factCounter, requests.size());
    }

    @Test
    public void testGetLastInsertedRequestIndex(){
        requestDAO.create(request);
        Integer requestId = requestDAO.getLastInsertedRequestIndex();
        Request tempRequest = requestDAO.findEntityById(request.getId());

        assertEquals(request.getId(), requestId, tempRequest.getId());
        requestDAO.delete(request.getId());
    }
}