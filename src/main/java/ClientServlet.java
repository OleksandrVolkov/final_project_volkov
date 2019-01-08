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
        int currentPage = Integer.valueOf(req.getParameter("currentPage"));
        int recordsPerPage = Integer.valueOf(req.getParameter("recordsPerPage"));

        List<Request> requests = null;
        int nOfPages = 0;

        try {
            requests = this.getLimitedRequestsByStatus(currentPage, recordsPerPage, "done", "rejected");
            nOfPages = RequestRowCounter.getNumberOfPagesByStatus(recordsPerPage,"done", "rejected");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("requests", requests);
        req.setAttribute("noOfPages", nOfPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("recordsPerPage", recordsPerPage);

        req.getRequestDispatcher("personalCabinet2.jsp").forward(req, resp);
    }

    public static List<Request> getLimitedRequestsByStatus(int currentPage, int recordsPerPage, String firstStatus, String secondStatus) throws SQLException {
        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage, firstStatus, secondStatus);
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