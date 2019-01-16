package controller;

import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "acceptReq", urlPatterns = {"/acceptReq"})
public class AcceptRequestServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newStatus = "is being seen";
        String request_id = req.getParameter("cur_request_id");
        Double price = Double.parseDouble(req.getParameter("price"));

        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
        Integer requestId = Integer.parseInt(request_id);
        Request request = requestDAO.findEntityById(requestId);
        request.setStatus(newStatus);
        request.setPrice(price);
        requestDAO.update(request, request.getId());

        req.getRequestDispatcher("/managerserv").forward(req, resp);
    }
}
