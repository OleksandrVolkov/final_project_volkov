package controller;

import model.dao.RequestDAO;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;
import model.entity.User;
import model.utility.RequestRowCounter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "client", urlPatterns = {"/client"})
public class ClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer currentPage = null;
        if(req.getParameter("currentPage") == null)
            currentPage = 1;
        else
            currentPage = Integer.parseInt(req.getParameter("currentPage"));

        int recordsPerPage = 5;

//        List<Request> requests = null;
        int nOfPages = 0;
        String firstStatus = "done";
        String secondStatus = "rejected";

//        try {
////            requests = this.getLimitedRequestsByStatus(currentPage, recordsPerPage, firstStatus, secondStatus);
//////            nOfPages = RequestRowCounter.getNumberOfPagesByStatus(recordsPerPage,firstStatus, secondStatus);
////            nOfPages = RequestRowCounter.getNumberOfPagesByUserId(recordsPerPage, )
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        req.setAttribute("requests", requests);
//        req.setAttribute("noOfPages", nOfPages);
//        req.setAttribute("currentPage", currentPage);
//        req.setAttribute("recordsPerPage", recordsPerPage);

//        if(req.getSession().getAttribute("LOGGED_USER") != null){
//            String login = (String)req.getSession().getAttribute("LOGGED_USER");
//            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
//            System.out.println("LOGIN: " + login);
//            User user = userDAO.findUserByUsername(login);
//            System.out.println("USER:  "+ user);
//            RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//            Request request = requestDAO.getRequestByUserId(user.getId());
//            System.out.println("REQUEST:  "+request);
//            req.setAttribute("cur_request_id", request.getId());
//        }


//        req.getRequestDispatcher("personalCabinet2.jsp").forward(req, resp);
//        req.getRequestDispatcher("/personalCab").forward(req, resp);







        String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");
        if(loginSession != null){
//            String login = (String)req.getSession().getAttribute("LOGGED_USER");
            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
            RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//            System.out.println("LOGIN: " + login);
            User user = userDAO.findUserByUsername(loginSession);
            System.out.println("USER:  "+ user);
            Request request = requestDAO.getRequestByUserId(user.getId());
            System.out.println("REQUEST:  "+request);

//!!!!!!!!!!!            req.setAttribute("cur_request_id", request.getId());



            System.out.println("SESSION FOUND " + loginSession);
//                if(req.getParameter("currentPage") == null)


//            List<Request> requests = requestDAO.findAll();
//            System.out.println("?????" + requests);
            UserDAO userDAO1 = new UserDAO(ConnectionManager.getConnection());
            User user1 = userDAO1.findUserByUsername(loginSession);

            List<Request> requests = this.getLimitedRequestsByUserID(currentPage, recordsPerPage, user1.getId());
//            nOfPages = RequestRowCounter.getNumberOfPagesByStatus(recordsPerPage,firstStatus, secondStatus);
            nOfPages = RequestRowCounter.getNumberOfPagesByUserId(recordsPerPage, user1.getId());




            req.setAttribute("requests", requests);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("recordsPerPage", recordsPerPage);
            req.setAttribute("noOfPages", nOfPages);
            req.setAttribute("currentPage", currentPage);
//            req.setAttribute("recordsPerPage", recordsPerPage);

            req.getRequestDispatcher("personalCabinet2.jsp").forward(req, resp);
//                resp.sendRedirect("/lang");
//            req.getRequestDispatcher("/personalCab").forward(req, resp);
        } else {
            resp.sendRedirect("/lang");
        }
    }

    private static List<Request> getLimitedRequestsByStatus(int currentPage, int recordsPerPage, String firstStatus, String secondStatus) throws SQLException {
        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage, firstStatus, secondStatus);
    }

    private static List<Request> getLimitedRequestsByUserID(int currentPage, int recordsPerPage, int userId){
        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage, userId);
    }
}









//???????!!!!!        List<Integer> feedbackIsWritten = new ArrayList<>();
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//
//        for(Request request: requests)
//            if(requestDAO.isRequestHasFeedback(request.getId()))
//                feedbackIsWritten.add(request.getId());
//
//
//
//        req.setAttribute("feedbackIsWritten", feedbackIsWritten);



//    public List<Request> findRequests(int currentPage, int recordsPerPage, String status) throws SQLException {
//        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage, status);
//    }
//    public List<Request> findRequestsOfTwoStatuses(int currentPage, int recordsPerPage, String firstStatus, String secondStatus) throws SQLException {
//        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage, firstStatus, secondStatus);
//    }

//    public int getNumberOfRows() throws SQLException {
//        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequests();
//    }
//
//    public int getNumberOfRows(String firstStatus, String secondStatus) throws SQLException {
//        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequestsByTwoStatuses(firstStatus, secondStatus);
//    }