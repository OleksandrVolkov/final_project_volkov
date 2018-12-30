import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "managerserv",urlPatterns = {"/managerserv"})
public class ManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int currentPage = Integer.valueOf(req.getParameter("currentPage"));
        System.out.println(currentPage);
        int recordsPerPage = Integer.valueOf(req.getParameter("recordsPerPage"));
        System.out.println(recordsPerPage);

        List<Request> requests = null;
        try {
            requests = findRequests(currentPage, recordsPerPage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("requests", requests);

//        int nOfPages = requests.size() / recordsPerPage;
        int rows = 0;
        try {
            rows = getNumberOfRows();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int nOfPages = rows / recordsPerPage;

        System.out.println("!!!!"+(nOfPages % recordsPerPage));
        if(nOfPages % recordsPerPage > 0)
            nOfPages++;
        System.out.println(nOfPages);

        req.setAttribute("noOfPages", nOfPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("recordsPerPage", recordsPerPage);

        req.getRequestDispatcher("manager2.jsp").forward(req, resp);
//        dispatcher.forward(req, resp);
//        resp.sendRedirect("manager2.jsp");
    }

    public List<Request> findRequests(int currentPage, int recordsPerPage) throws SQLException {
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


        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage);
    }

    public int getNumberOfRows() throws SQLException {
//        List<Request> requests = new ArrayList<>();
//        requests.add(new Request("Id like to make it", "not seen"));
//        requests.add(new Request("See this please", "not seen"));
//        requests.add(new Request("That's a good idea", "not seen"));
//        requests.add(new Request("We haven't seen that", "not seen"));
//        requests.add(new Request("This is a request", "not seen"));
//        requests.add(new Request("This is it!", "not seen"));
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
//        return requests.size();
        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequests();
    }
}
