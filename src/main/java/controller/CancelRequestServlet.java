package controller;

import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Reject;
import model.entity.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "cancelReq", urlPatterns = {"/cancelReq"})
public class CancelRequestServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reason = req.getParameter("reason");
        Integer requestId = Integer.parseInt(req.getParameter("cur_request_id"));
        System.out.println(reason);

        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
        Request request = requestDAO.findEntityById(requestId);
        request.setStatus("rejected");
        request.setReject(new Reject(reason, requestId));

        requestDAO.update(request, request.getId());

        req.getRequestDispatcher("/managerserv").forward(req, resp);
    }
}




//        req.setAttribute("currentPage", req.getParameter("currentPage"));
//        req.setAttribute("recordsPerPage", req.getParameter("recordsPerPage"));
