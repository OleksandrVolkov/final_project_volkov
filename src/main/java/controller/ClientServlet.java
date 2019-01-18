package controller;

import model.dao.ItemDAO;
import model.dao.RequestDAO;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Item;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int nOfPages = 0;



        String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");
        if(loginSession != null){
            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());

//!!!!!!!!!!!            req.setAttribute("cur_request_id", request_actions.getId());
            System.out.println("SESSION FOUND " + loginSession);
            User user = userDAO.findUserByUsername(loginSession);

            List<Request> requests = this.getLimitedRequestsByUserID(currentPage, recordsPerPage, user.getId());
            nOfPages = RequestRowCounter.getNumberOfPagesByUserId(recordsPerPage, user.getId());

            Map<Integer, Item> items = new HashMap<>();
            ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
            for(Request curRequest: requests)
                items.put(curRequest.getId(), itemDAO.findEntityById(curRequest.getItemId()));

            req.setAttribute("items", items);
            req.setAttribute("requests", requests);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("recordsPerPage", recordsPerPage);
            req.setAttribute("noOfPages", nOfPages);
            req.setAttribute("currentPage", currentPage);

            req.getRequestDispatcher("personalCabinet2.jsp").forward(req, resp);
//                resp.sendRedirect("/lang");
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