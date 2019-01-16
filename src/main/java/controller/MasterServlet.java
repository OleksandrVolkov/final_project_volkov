package controller;

import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;
import model.utility.RequestRowCounter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "master", urlPatterns ={"/master"})
public class MasterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int currentPage = Integer.valueOf(req.getParameter("currentPage"));
        int recordsPerPage = Integer.valueOf(req.getParameter("recordsPerPage"));

        List<Request> requests = null;
        int nOfPages = 0;
        try {
            String status = "is being seen";
            requests = findRequests(currentPage, recordsPerPage, status);
            nOfPages = RequestRowCounter.getNumberOfPagesByStatus(recordsPerPage, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("requests", requests);
        req.setAttribute("noOfPages", nOfPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("recordsPerPage", recordsPerPage);

        req.getRequestDispatcher("master2.jsp").forward(req, resp);
    }

    private List<Request> findRequests(int currentPage, int recordsPerPage, String status) throws SQLException {
        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage, status);
    }
}




//    public int getNumberOfRowsByStatus(String status) throws SQLException {
//        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequestsByStatus(status);
//    }

































//        List<Request> requests = new ArrayList<>();
//        requests.add(new Request("Id like to make it", "not seen"));
//        requests.add(new Request("See this please", "not seen"));
//        requests.add(new Request("That's a good idea", "not seen"));
//        requests.add(new Request("We haven't seen that", "not seen"));
//        requests.add(new Request("This is a request", "not seen"));
//        requests.add(new Request("This is it!", "not seen"));
//        requests.add(new Request("This is it!", "not seen"));
//        requests.add(new Request("See this please", "not seen"));
//        requests.add(new Request("Id like to make it", "not seen"));
//        requests.add(new Request("See this please", "not seen"));
//        requests.add(new Request("That's a good idea", "not seen"));
//        requests.add(new Request("We haven't seen that", "not seen"));
//        requests.add(new Request("This is a request", "not seen"));
//        requests.add(new Request("This is it!", "not seen"));
//        requests.add(new Request("This is it!", "not seen"));
//        requests.add(new Request("See this please", "not seen"));
//
//
//        int start = currentPage * recordsPerPage - recordsPerPage;
//        System.out.println("START: "+start+", RECORDS PER PAGE: "+recordsPerPage);
//
//
//
//
//        if(recordsPerPage + start <= requests.size())
//            return requests.subList(start, recordsPerPage + start);
//        else
//            return requests.subList(start, requests.size());
