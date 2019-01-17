package controller.actions.request_actions;

import controller.actions.Action;
import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcceptAction implements Action {
    public String execute(HttpServletRequest req, HttpServletResponse resp){
        String newStatus = "is being seen";
        String request_id = req.getParameter("cur_request_id");
        Double price = Double.parseDouble(req.getParameter("price"));

        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
        Integer requestId = Integer.parseInt(request_id);
        Request request = requestDAO.findEntityById(requestId);
        request.setStatus(newStatus);
        request.setPrice(price);
        requestDAO.update(request, request.getId());
        System.out.println("RRRRRRRRRRRRRRRRRIIIIIIIIIIIIGGGGGGGHHHHTTTT");

        String url = "/managerserv";
        return url;
    }
}
