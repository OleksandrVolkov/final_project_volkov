package controller.trash;

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
//        String newStatus = "is being seen";
//        String request_id = req.getParameter("cur_request_id");
//        Double price = Double.parseDouble(req.getParameter("price"));
//
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//        Integer requestId = Integer.parseInt(request_id);
//        Request request_actions = requestDAO.findEntityById(requestId);
//        request_actions.setStatus(newStatus);
//        request_actions.setPrice(price);
//        requestDAO.update(request_actions, request_actions.getId());
//
//        req.getRequestDispatcher("/managerserv").forward(req, resp);
    }
}
