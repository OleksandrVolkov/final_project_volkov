package controller;

import model.dao.ItemDAO;
import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Item;
import model.entity.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="preMaster", urlPatterns = {"/preMaster"})
public class PreMasterServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//        List<Request> requests = null;
//        try {
//            requests = requestDAO.findByStatus("is being seen");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        req.setAttribute("requests", requests);




//        int currentPage = Integer.valueOf(req.getParameter("currentPage"));
//        System.out.println(currentPage);
//
//        int recordsPerPage = Integer.valueOf(req.getParameter("recordsPerPage"));
//        System.out.println(recordsPerPage);
//
//
//        List<Request> requests = null;
//
//        requests = findRequests(currentPage, recordsPerPage, "is being seen");
//
//        req.setAttribute("requests", requests);
//
//        req.getRequestDispatcher("request.jsp").forward(req, resp);
    }
}
