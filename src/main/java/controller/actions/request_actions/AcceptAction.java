package controller.actions.request_actions;

import controller.actions.Action;
import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;
import model.utility.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcceptAction implements Action {
    public String execute(HttpServletRequest req, HttpServletResponse resp){
        String priceStr = req.getParameter("price");
        String url = null;
        Validator validator = new Validator();

        if(validator.isValidPrice(priceStr)) {
            url = this.changeStatus(req, resp, priceStr);
        }else {
            url = "manager_price.jsp";
            req.setAttribute("isValidPrice", true);
        }

        return url;
    }


    public String changeStatus(HttpServletRequest req, HttpServletResponse resp, String priceStr){
        Double price = Double.parseDouble(priceStr);
        String newStatus = "is being seen";

        String request_id = req.getParameter("cur_request_id");
        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
        Integer requestId = Integer.parseInt(request_id);
        Request request = requestDAO.findEntityById(requestId);
        request.setStatus(newStatus);
        request.setPrice(price);
        requestDAO.update(request, request.getId());

        String url = "/managerserv";
        return url;
    }

}
