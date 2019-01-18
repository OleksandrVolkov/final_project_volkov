package controller;

import model.dao.ItemDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Item;
import model.entity.Request;
import model.utility.RequestRowCounter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "managerserv",urlPatterns = {"/managerserv"})
public class ManagerServlet extends HttpServlet {
//    private static int currentPage = 1;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPageStr = req.getParameter("currentPage");
        String recordsPerPageStr = req.getParameter("recordsPerPage");

        int currentPage = 0;
        int recordsPerPage = 0;
        if(currentPageStr == null || currentPageStr.equals(""))
            currentPage = 1;
        else
            currentPage = Integer.parseInt(currentPageStr);
        if(recordsPerPageStr == null || recordsPerPageStr.equals(""))
            recordsPerPage = 5;
        else
            recordsPerPage = Integer.parseInt(recordsPerPageStr);

        int nOfPages = 0;
        List<Request> requests = null;

        requests = RequestRowCounter.findRequests(currentPage, recordsPerPage);
        nOfPages = RequestRowCounter.getNumberOfPages(recordsPerPage);

        Map<Integer, Item> items = new HashMap<>();
        ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
        for(Request curRequest: requests)
            items.put(curRequest.getId(), itemDAO.findEntityById(curRequest.getItemId()));


        req.setAttribute("items", items);
        req.setAttribute("requests", requests);
        req.setAttribute("noOfPages", nOfPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("recordsPerPage", recordsPerPage);

        req.getRequestDispatcher("manager2.jsp").forward(req, resp);
    }
}

















//    public List<Request> findRequests(int currentPage, int recordsPerPage) throws SQLException {
//        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage);
//    }
////
////    public int getNumberOfRows(String status) throws SQLException {
////        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequestsByStatus();
////    }































//    public List<Request> findRequests(int currentPage, int recordsPerPage) throws SQLException {
////        List<Request> requests = new ArrayList<>();
////        requests.add(new Request("Id like to make it", "not seen"));
////        requests.add(new Request("See this please", "not seen"));
////        requests.add(new Request("That's a good idea", "not seen"));
////        requests.add(new Request("We haven't seen that", "not seen"));
////        requests.add(new Request("This is a request_actions", "not seen"));
////        requests.add(new Request("This is it!", "not seen"));
////        requests.add(new Request("This is it!", "not seen"));
////        requests.add(new Request("See this please", "not seen"));
////        requests.add(new Request("Id like to make it", "not seen"));
////        requests.add(new Request("See this please", "not seen"));
////        requests.add(new Request("That's a good idea", "not seen"));
////        requests.add(new Request("We haven't seen that", "not seen"));
////        requests.add(new Request("This is a request_actions", "not seen"));
////        requests.add(new Request("This is it!", "not seen"));
////        requests.add(new Request("This is it!", "not seen"));
////        requests.add(new Request("See this please", "not seen"));
////
////
////        int start = currentPage * recordsPerPage - recordsPerPage;
////        System.out.println("START: "+start+", RECORDS PER PAGE: "+recordsPerPage);
////
////
////
////
////        if(recordsPerPage + start <= requests.size())
////            return requests.subList(start, recordsPerPage + start);
////        else
////            return requests.subList(start, requests.size());
//
//
//        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage);
//    }
//
//    public int getNumberOfRows() throws SQLException {
////        List<Request> requests = new ArrayList<>();
////        requests.add(new Request("Id like to make it", "not seen"));
////        requests.add(new Request("See this please", "not seen"));
////        requests.add(new Request("That's a good idea", "not seen"));
////        requests.add(new Request("We haven't seen that", "not seen"));
////        requests.add(new Request("This is a request_actions", "not seen"));
////        requests.add(new Request("This is it!", "not seen"));
////        requests.add(new Request("This is it!", "not seen"));
////        requests.add(new Request("This is it!", "not seen"));
////        requests.add(new Request("See this please", "not seen"));
////        requests.add(new Request("Id like to make it", "not seen"));
////        requests.add(new Request("See this please", "not seen"));
////        requests.add(new Request("That's a good idea", "not seen"));
////        requests.add(new Request("We haven't seen that", "not seen"));
////        requests.add(new Request("This is a request_actions", "not seen"));
////        requests.add(new Request("This is it!", "not seen"));
////        requests.add(new Request("This is it!", "not seen"));
////        requests.add(new Request("See this please", "not seen"));
////
////
////        return requests.size();
//        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequests();
//    }


//!!!!!!        List<Integer> requestWithPricesIDs = new ArrayList<>();
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//        for(Request request_actions: requests)
//            if(!requestDAO.isRequestHasPrice(request_actions.getId()) && request_actions.getStatus().equals("not seen"))
//                requestWithPricesIDs.add(request_actions.getId());




//        int nOfPages = requests.size() / recordsPerPage;


//!!!!        req.setAttribute("notNeededToConfirm", requestWithPricesIDs);